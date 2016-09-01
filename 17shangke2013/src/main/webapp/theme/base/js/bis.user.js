/**
 *
 * 功能描述：用户相关<br>
 *
 * 日 期：2011-5-26 12:43:54<br>
 *
 * 作 者：Dove<br>
 *
 * 版权所有：©mosai<br>
 *
 * 版 本 ：v0.01<br>
 *
 * 联系方式：dream-all@163.com<br>
 *
 * 修改备注：{修改人|修改时间}<br>
 *
 */
User = {};
/*=========用户信息更新==============*/
/**
 * 保存用户基本信息
 */
User.saveUser = function() {
	if (isEmpty($("#grade").val())) {
		$("#grade").val("0")
	}
	$("#basicInfoForm").postForm(function(data) {
		$.alert.show(data, function() {
			if (data.status == 1) {
				$.cookie("user.grade", $("#grade").val());
				$.cookie("user.focus", $("#focus").val());
				$.cookie("user.expert", $("#expert").val());
			}
		});
	});
}
/**
 * 更新用户扩展信息
 */
User.updateExtends = function() {
	$("#extendsForm").postForm({
		after : function(request) {
			var birthday = request.getParameter("birthday");
			if (!isEmpty(birthday)) {
				request.setParameter("birthday", birthday.toSecondsTime());
			} else {
				request.setParameter("birthday", 0);
			}
		},
		success : function(data) {
			if (data.status > 0) {
				$.alert.show({
					status : 1,
					message : "更新用户信息成功"
				});
			} else {
				$.alert.show({
					status : 0,
					message : "更新用户信息失败"
				});
			}
		}
	});
}

/**
 * 用户密码更新
 */
User.updatePassword = function() {
	var oldpassword = $("#oldpassword").val();
	var newpassword = $("#newpassword").val();
	$("#passwordForm").postForm({
		handleForm : function(request) {
			request.set("oldpassword", CryptoJS.SHA256(request.get("oldpassword")).toString());
			request.set("newpassword", CryptoJS.SHA256(request.get("newpassword")).toString());
		},
		success : function(data) {
			if (data > 0) {
				$.alert.show({
					status : 1,
					message : "更新密码成功"
				});
			} else {
				$.alert.show({
					status : 0,
					message : "更新密码失败,旧密码输入有误!"
				});
			}
		}
	});
}

User.uploadAvatar = function() {
	//trace("uploadAvatar")
	$("#avatarForm").postForm(function(data) {
		//trace("upload success")
		Avatar.load(data.path);
		$("#avatarForm").data("newphoto", true);
	});
}

User.saveAvatar = function() {
	if (!$("#avatarForm").data("newphoto")) {
		$.alert.warn("请您先上传图片！")
		return;
	}
	
	if(!Avatar.data||Avatar.data.w<50||Avatar.data.h<50){
		$.alert.warn("您未选择图片区域<br/>或者选择的区域过小！")
		return;
	}
	
	$.post("/user/saveAvatar.html", Avatar.data, function(data) {
		$.alert.show(data);
		if (data.path) {
			$("#left_member_header").attr("src", data.path + "?" + $.now());
		}
	})
}