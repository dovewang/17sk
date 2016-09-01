//window.DEBUG = false;
Base = {
	_ROLE : ["游客", "学生", "老师", "家长", "学校", "管理员"]
};

function addFavorite() {
	var sURL = "http://www.17shangke.com", sTitle = "一起上课"
	try {
		window.external.addFavorite(sURL, sTitle);
	} catch (e) {
		try {
			window.sidebar.addPanel(sTitle, sURL, "");
		} catch (e) {
			$.alert.warn("您的浏览器不支持点击收藏，请使用Ctrl+D进行添加");
		}
	}
};

Kiss.addEventListener({
	"show-hot" : function(obj) {
		$(obj).load("/show/hot.html");
	},
	"online-teacher" : function(obj) {
		Base.onlineUser($(obj), "TEACHER", 8)
	},
	"online-student" : function(obj) {
		Base.onlineStudent($(obj), "STUDENT", 8)
	},
	"guess-course" : function(obj) {
		Guess.course($(obj));
	},
	"guess-member" : function(obj) {
		Guess.follow($(obj))
	},

	"slider" : function(obj) {
		$(obj).slider();
	},
	"knowledge" : function(obj) {
		$(obj).knowledge();
	},
	"top-username" : function(obj) {
		Base.roleDrop($(obj));
	},
	"top-muserdrop" : function(obj) {
		Base.muserDrop($(obj));
	},
	"number-steper" : function(obj) {
		var c = $(obj).children();
		var input = $(c.get(1));
		$(c.get(0)).click(function() {
			var v = input.val() * 1 - 1;
			input.val(v <= 0 ? 0 : v);
		});
		input.keydown(function(event) {
			if (event.keyCode != 8 && event.keyCode != 38 && event.keyCode != 40 && !(event.keyCode >= 48 && event.keyCode <= 57) && !(event.keyCode >= 96 && event.keyCode <= 105)) {
				return false;
			}
			if (event.keyCode == 38) {
				input.val(input.val() * 1 + 1);
			}

			if (event.keyCode == 40) {
				var v = input.val() * 1 - 1;
				input.val(v <= 0 ? 0 : v);
			}
		});
		$(c.get(2)).click(function() {
			input.val(input.val() * 1 + 1);
		});
	},
	"totur-demand" : function(obj) {
		Guess.demand($(obj));
	}
});

$(function() {
	/*导航*/
	var gb = $("#global-nav-bar");
	if (gb.length > 0) {
		if (!Env.isLogin) {
			$("#spanRole").text(Base._ROLE[isEmpty($.cookie("u_token_t"), 1)])
		}
		var $gradeChooser = $("#grade-chooser");
		var $gradeChooser_text = $gradeChooser.find("span");
		$gradeChooser.mouseenter(function() {
			$(this).addClass("active")
		}).mouseleave(function() {
			$(this).removeClass("active")
		});
		Guide.start();
	}
});

Base.roleDrop = function($this) {
	var $pop;
	$this.mouseenter(function() {
		$this.addClass("active")
		$pop = $this.dropover({
			id : "role-drop",
			className : "dropover",
			content : "<a href='javascript:;' r=1>学生</a><a href='javascript:;' r=2>老师</a><a href='javascript:;' r=3>家长</a>",
			cache : true,
			afterShow : function($p) {
				$p.find("a").click(function() {
					$("#spanRole").text($(this).text());
					$.cookie("u_token_t", $(this).attr("r"));
					location.reload();
				});
			}
		});
	}).mouseleave(function() {
		$this.removeClass("active")
		Pop.hide($pop);
	});
}

Base.muserDrop = function($this) {
	var $li = $("#user_muser_li");
	if (isEmpty($li)) {
		return;
	}
	var html = [];
	$("#user_muser_li i").each(function() {
		var muser = $(this).attr("muser");
		muser = eval("(" + muser + ")");
		if (muser.userid != parent.Env.USER_ID) {
			html.push("<a href='javascript:;' userid=" + muser.userid + ">" + muser.name + "(" + Base._ROLE[muser.userType == 127 ? (Base._ROLE.length - 1) : muser.userType] + ")</a>");
		};
	});
	var $pop;
	$this.mouseenter(function() {
		$this.addClass("active")
		$pop = $this.dropover({
			id : "user-drop",
			content : html.join(""),
			className : "dropover",
			css : {
				width : 150
			},
			cache : true,
			afterShow : function($p) {
				$p.find("a").click(function() {
					var userid = $(this).attr("userid");
					$.post("/passport/doOtherlogin.html", {
						userid : userid
					}, function(data) {
						location.reload();
					})
				});
			}
		});
	}).mouseleave(function() {
		$this.removeClass("active")
		Pop.hide($pop)
	});
}

Base.onlineUser = function($div, type, nums) {
	$.get("/getUsers.html?t=" + type + "&n=" + nums, function(result) {
		var result = eval("(" + result.result + ")");
		var html = []
		html.push('<h2 class="box-title"><a href = "/search/teacher.html?q=&p=1&c=0,0,0,0,t,0" class = "float-right" >更多&gt;</a>在线老师</h2>');
		html.push('<ul class="clearfix">');
		$(result).each(function(index, data) {
			html.push('	<li><a href="/u/' + data.id + '.html" target="_blank" class="face-link"><img usercard="' + data.id + '" src="' + data.face + '" width="50" height="50" alt="' + data.name + '"/></a>');
			html.push('	<span>');
			html.push('	<a href="/u/' + data.id + '.html" target="_blank" usercard="' + data.id + '">' + data.name + '</a></span>');
			html.push('</li>');
		});
		html.push('</ul>');
		$div.html(html.join("")).reload();
	});
}

Base.onlineStudent = function($div, type, nums) {
	$.get("/getUsers.html?t=" + type + "&n=" + nums, function(result) {
		var result = eval("(" + result.result + ")");
		var html = []
		html.push('<h2 class="box-title"><a href = "/search/student.html?q=&p=1&c=0,0,0,0,t,0" class = "float-right" >更多 &gt;</a>在线学员</h2>');
		html.push('<ul class="clearfix">');
		$(result).each(function(index, data) {
			html.push('	<li><a href="/u/' + data.id + '.html" target="_blank" class="face-link"><img usercard="' + data.id + '" src="' + data.face + '" width="50" height="50" alt="' + data.name + '"/></a>');
			html.push('	<span>');
			html.push('	<a href="/u/' + data.id + '.html" target="_blank" usercard="' + data.id + '">' + data.name + '</a></span>');
			html.push('</li>');
		});
		html.push('</ul>');
		$div.html(html.join("")).reload();
	});
}
Guide = {
	remeber : function(g, t) {

	},
	start : function() {
		/*提醒用户注册*/
		var signup = $.cookie("guide.signup");
		if (!Env.isLogin && !signup && $("#signup").length != 0) {
			$.overlay.show(function() {
				var html = [];
				html.push("<div class='guide signup' id='signup_guide'>");
				html.push("<div class='guide-button'>");
				html.push("<a class='a1' href=\"javascript:;\" onclick=\"Guide.signup(false)\"></a>");
				html.push("<a class='a2' href=\"javascript:;\" onclick=\"Guide.signup(true)\"></a>")
				html.push("</div></div>");
				$(document.body).append(html.join(""));
				$("#signup_guide").css("left", $("#signup").offset().left - 240);
				$("#signup").addClass("guide-light")
			})
		}
	},
	signup : function(yes) {
		$.cookie("guide.signup", "1");
		if (yes) {
			location.href = Env.SECURE_DOMAIN + "/register.html";
			return;
		}
		$.overlay.hide(function() {
			$("#signup_guide").hide();
			$("#signup").removeClass("guide-light")
		})
	}
}
Guide.first = function() {
	var options = {
		effect : "mouse",
		trigger : $item
	};
	$("#interestForm").postForm(function(data) {
		$.alert.show(data, options);
	})
}
Guide.firstCancle = function() {
	$.cookie("Guide.firstCancle", "true");
}
Guide.showSuggest = function(obj) {
	var html = [];
	html.push('<div class="pop-header"><a href="javascript:;" class="close" node-type="pop-close">×</a><h3>给点建议</h3></div>');
	html.push('<div class="pop-body">');
	html.push("<textarea style='width:98%;height:60px;' id='suggest-content'></textarea>");
	html.push('</div><div class="pop-footer"><button node-type="pop-close"  class="button" >取消</button><button node-type="pop-close" class="button primary" onclick="Guide.postSuggest();">确定</button></div>');
	Pop.show({
		id : "suggest-pop",
		template : false,
		css : {
			width : 400
		},
		content : html.join("")
	});
}

Guide.postSuggest = function() {
	$.post("/suggest.html", {
		content : $("#suggest-content").val()
	}, function(data) {
		$.alert.show(data);
	})
}
/*猜用户可能感兴趣的人或事物*/
Guess = {}
Guess.follow = function($div, page, size) {
	size = $div.attr("data-size") * 1;
	page = ($div.attr("p") || 0) * 1 + 1;
	$div.attr("p", page);
	$.get("/follow/guess.html", {
		p : page,
		n : size
	}, function(data) {
		var list = data.result;
		if (list) {
			var html = [];
			html.push('<h2 class="box-title"><a href="javascript:void(0)"  id="member-guess-box" class="float-right">换一换</a>您可能感兴趣的人</h2>');
			for (var i = 0; i < list.length; i++) {
				html.push('<dl class="user-box clearfix" id="member-guess-index' + i + '">')
				var user = list[i];
				html.push('<dt><img  height="30" usercard="' + user.userId + '" width="30"  /></dt>');
				html.push('<dd><span class="name"><a href="/u/' + user.userId + '.html" usercard="' + user.userId + '" title="' + user.name + '">' + user.name + '</a></span>')
				html.push('<button class="button" node-type="follow" uid="' + user.userId + '"  item="#member-guess-index' + i + '">+关注</button></dd>');
				html.push('</dl>');
			}
			$div.fadeOut().html(html.join("")).fadeIn().reload();
			$("#member-guess-box").click(function() {
				Guess.follow($div);
			});
		}
	});
};

Guess.demand = function($div, page, size) {
	size = $div.attr("data-size") * 1;
	page = ($div.attr("p") || 0) * 1 + 1;
	$div.attr("p", page);
	$.get("/tutor/guessDemand.html", {
		p : page,
		n : size
	}, function(data) {
		var list = data.result;
		if (list) {
			var html = [];
			html.push('<h2 class="box-title"><a href="javascript:void(0)" id="tutorDemand-guess-reload" class="float-right">换一换</a>需要辅导的学员</h2>');
			html.push('<ul id="guess-demand-ul">');
			for (var i = 0; i < list.length; i++) {
				var user = list[i];
				var price = ["面议", "30以下", "30-60", "60-100", "100以上"];
				html.push('	<li id="guess-demand-li' + user.demandId + '" userid="' + user.userId + '" demandid="' + user.demandId + '"><a href="/u/' + user.userId + '.html" target="_blank" class="face-link"><img usercard="' + user.userId + '" id="face' + user.userId + '" src="' + user.face + '" width="75" height="75" alt=""/></a>');
				html.push('	<span><a href="/u/' + user.userId + '.html" target="_blank" usercard="' + user.userId + '"></a></span>');
				html.push(' <div id="guess-demand-div' + user.demandId + '" style="display:none">');
				html.push(' 		<div><span>需要辅导的科目：<b>' + user.memo + '</b></span');
				html.push(' 		<span>详细描述：<b>' + user.intro + '</b></span>');
				html.push(' 		<span>价格：<b>' + price[user.price] + '</b></span>');
				html.push(' <a href="javascript:;" class="tag-button">辅导</a></div>');
				html.push(' </div>');
				html.push(' </li>');
			}
			html.push('</ul>');
			$div.fadeOut().html(html.join("")).fadeIn().reload();
			//触发鼠标移动事件
			$("#guess-demand-ul li").mouseenter(function(index) {
				var $this = $(this);
				$("#guess-demand-ul li.active").removeClass('active')
				$("#demand-guess-box").html($("#guess-demand-div" + $this.attr("demandid")).html()).show().find(".tag-button").click(function() {
					var userid = $this.attr("userid"), name = $this.find("a[usercard=" + userid + "]").text();
					IM.addUser($this, userid, name);
					return false;
				});
				;
				$this.addClass('active');
			}).first().mouseenter();
			$("#tutorDemand-guess-reload").click(function() {
				Guess.demand($div);
			});
		}
	});
};

Guess.course = function($div, page, size) {
	size = $div.attr("data-size") * 1;
	page = ($div.attr("p") || 0) * 1 + 1;
	$div.attr("p", page);
	$.post("/course/guess.html", {
		p : page,
		n : size
	}, function(result) {
		var list = result.result;
		if (list) {
			var html = [];
			html.push('<h2 class="box-title"><a href="javascript:void(0)" id="guess-course-box" class="float-right">换一换</a>您可能感兴趣的课程</h2>');
			for (var i = 0; i < list.length; i++) {
				html.push('<dl class="course-box">')
				var course = list[i];
				html.push('<dt><img  height="50"   width="50" src="' + course.cover + '"/></dt>')
				html.push('<dd><span class="name"><a href="/course/' + course.courseId + '-' + Env.VERSION + '.html"  title="' + course.name + '" target="_blank">' + course.name + '</a></span>')
				if (course.type == 2 || course.type == 4)
					html.push('分享时间：' + (course.publishTime * 1000).toDate().format("yyyy-mm-dd") + '</dd>');
				else {
					if (course.startTime != 0)
						html.push('开课时间：' + (course.startTime * 1000).toDate().format("yyyy-mm-dd  HH:mm") + '</dd>');
					else
						html.push('开课时间：' + course.openTime + '</dd>');
				}
				html.push('</dl>');
			}
			$div.fadeOut().html(html.join("")).fadeIn().reload();
			$("#guess-course-box").click(function() {
				Guess.course($div);
			});
		}
	});
}
/*判断用户是有已有身份，例如从QQ直接登录时需要选择身份*/
$(function() {
	if (Env.isLogin && (Env.USER_TYPE == 0)) {
		var p = Pop.show({
			id : "user-type-select-pop",
			url : "/static/user.type.select.htm",
			template : false,
			effect : "drop",
			className : "pop pop-tip",
			afterShow : function($pop, options) {
				$pop.find("button").click(function() {
					$.post("/selectUserType.html", {
						type : this.value
					}, function(data) {
						location.reload();
					})
				})
			}
		})
	}
})

