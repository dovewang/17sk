Security = {}
Security.login = function(isWindow) {
	var userName = $.trim($("#userName").val());
	var password = $("#password").val();
	var forwardUrl = $("#forwardUrl").val();
	var remeberme = $("#remeberme").attr("checked") ? 1 : 0;
	var captcha = $.trim($("#captcha").val());
	if (isEmpty(userName)) {
		$("#login_message").text("请您输入登录邮箱或用户名").show();
		return false;
	}

	if (isEmpty(password)) {
		$("#login_message").text("请您输入登录密码").show();
		return false;
	}

	if (isEmpty(captcha)) {
		$("#login_message").text("请您输入验证码").show();
		return false;
	}

	/*这里我们要求所有的子站都必须使用https登录方式，这里我们使用iframe进行提交数据*/

	$.post(Env.SECURE_DOMAIN + "/dologin.html", {
		userName : userName,
		password : CryptoJS.SHA256(password).toString(),
		forwardUrl : forwardUrl,
		captcha : captcha.toLowerCase(),
		remeberme : remeberme
	}, function(data) {
		if (data.status == 1) {
			if (!isWindow){
				location.href = data.result;
			}else{
				/*使用ajax在当前页面直接reload就可以了，最好的做法是直接更新界面的用户信息，不需要刷新界面*/
				parent.location.reload();
			}
		} else {
			$("#login_message").text(data.message).show();
		}
	});
}
/*验证码*/
Security.captcha = function() {
	$("#captcha_image").attr("src", Env.SITE_DOMAIN + "/captcha?" + new Date().getTime());
	return false;
}
/*用户注册*/
Security.sign = function() {
	$("#registerForm").postForm({
		handleForm : function(request) {
			request.set("password", CryptoJS.SHA256(request.get("password")).toString()).set("password1", 0);
		},
		success : function(data) {
			if (data.status = 1) {
				var s = $("#email").val();
				s = s.substring(s.indexOf("@") + 1);
				location.href = Env.SECURE_DOMAIN + "/success.html?t=sign&mail=" + s;
			}
		}
	});
}
/*用户密码找回*/
Security.recovery = function() {
	var mail = $("#email").val();
	var userName = $("#userName").val();
	if (isEmpty(userName)) {
		$("#recoveryMessage").text("请您输入用户名").show();
		return false;
	}
	if (isEmpty(mail) || !window.regexp.email(mail)) {
		$("#recoveryMessage").text("您输入的邮箱格式不符合要求").show();
		return false;
	}
	$("#recoveryForm").postForm(function(data) {
		if (data.status == 1) {
			location.href = Env.SECURE_DOMAIN + "/success.html?t=recovery&mail=" + mail;
		} else {
			$("#recoveryMessage").text(data.message).show();
		}
	});
}

Security.resetPassword = function() {
	$("#recoveryForm").postForm({
		handleForm : function(request) {
			request.set("newpassword", CryptoJS.SHA256(request.get("newpassword")).toString()).set("repeartpassword", 0);
		},
		success : function(data) {
			$.alert.show(data, function() {
				if (data.status == 1) {
					location.href = Env.SECURE_DOMAIN + "/login.html"
				}
			})
		}
	});
}
/*账户授权*/
Security.authorize = function(isParent) {
	var $key = $("#password");
	if (isEmpty($key.val())) {
		return;
	}

	$("#authorizeForm").postForm({
		handleForm : function(request) {
			var key = $key.attr("name");
			request.set(key, CryptoJS.SHA256(request.get(key)).toString());
		},
		success : function(data) {
			if (data.status == 1) {
				if (!isParent)
					location.href = data.message;
				else
					parent.location.href = data.message;
			} else {
				parent.$.alert.show(data, function() {
					location.reload();
				});
			}
		}
	});
}
/*忘记账户密码*/
Security.loseAccount = function() {
	parent.location.href = Env.SECURE_DOMAIN + "/account/recovery.html";
}
/*余额不足*/
Security.balanceMore = function() {
	parent.location.href = Env.SECURE_DOMAIN + "/account/recharge.html";

}