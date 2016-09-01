$(function() {
	/*登录后的用户才创建实时消息连接*/
	try {
		if (!Env.isLogin || Env.SECURE || window.NO_COMETD) {
			return;
		}
		$(document.body).append("<iframe  id='comted_iframe' name='comted' src='about:blank' frameborder='0'  style='position: absolute;left: -100px;top: -100px;height: 1px;width: 1px;visibility: hidden'></iframe>");
		var iframe = document.getElementById("comted_iframe");
		var doc = $.iframe.getDocument(iframe);
		var head = [];
		head.push('<script src="/theme/base/js/json2.js?' + parent.Env.VERSION + '" type="text/javascript" ></script>');
		head.push('<script src="/theme/base/js/jquery.js?' + parent.Env.VERSION + '" type="text/javascript" ></script>');
		head.push('<script src="/theme/base/js/cometd-min.js?' + parent.Env.VERSION + '" type="text/javascript" ></script>');
		head.push('<script src="/theme/base/js/bis.cometd.js?' + parent.Env.VERSION + '" type="text/javascript"></script>');
		doc.open();
		doc.write("<html><head>" + head.join("") + "</head><body></body></html>")
		doc.close();
	} catch(e) {
		//alert(e.messsage)
	}

	try {
		/*添加头部不存在的校验*/
		var $gnav = $("#global-nav");
		var gnav = {
			top : 0,
			height : 0
		};
		var $pnav = $("#page-nav");
		var pnav = {
			top : 0,
			height : 0
		};
		if ($gnav.length > 0) {
			gnav.top = $gnav.offset().top;
			gnav.height = $gnav.outerHeight();
		}
		if ($pnav.length > 0) {
			pnav.top = $pnav.offset().top;
			pnav.height = $pnav.outerHeight();
		}
		var ptop = pnav.top + pnav.height;
		var gtop = gnav.top + gnav.height;
		_wall(ptop, gtop).show().addClass('fold');
		var $expand = $("#message-expand-wall");
		$expand.unbind("click").click(function() {
			if ($expand.data("open")) {
				$("#message-wall").addClass('fold');
				IM.hide();
				$expand.data("open", false)
			} else {
				$("#message-wall").removeClass('fold');
				$expand.data("open", true)
			}
		}).show();
		$expand.data("open", false)
		IM.start();
		$(window).scroll(function() {
			_wall(ptop, gtop);
		}).resize(function() {
			_wall(ptop, gtop);
		});
	} catch(e) {
		//alert(e)
	}
});
function _wall(ptop, gtop) {
	var ntop = ptop - $(window).scrollTop();
	if (ntop <= gtop) {
		ntop = gtop;
	}
	var $pfooter = $("#page-footer");
	var height = 0;
	if ($pfooter.length > 0) {
		height = $pfooter.offset().top - $(window).scrollTop() - ntop;
	} else {
		height = $(window).height() - ntop;
	}
	return $("#message-wall").css({
		"top" : ntop,
		height : height
	});
}

IM = {
	users : {},
	start : function() {
		$("#aImSenderChoose").click(function() {
			$("#im-key").toggle();
		});
		$("#im-key a").click(function() {
			Env.IM_SENDER_KEY = $(this).attr("im-key");
			$("#im-key li").removeClass('active');
			$(this).parent().addClass('active');
			$("#im-key").hide();
		});
		//还原上一页面的状态
		$("#im-poster-area").keydown(function(event) {
			var key = (Env.IM_SENDER_KEY || 1) * 1;
			if ((key == 1 && event.keyCode == 13) || (key == 2 && event.ctrlKey && event.keyCode == 13)) {
				event.preventDefault();
				IM.send()
			}
		});
		/*判断Cookie不是属于本用户*/
		if (isEmpty($.cookie("message.user")) || $.cookie("message.user") != Env.USER_ID) {
			$.cookie("message.chat", null);
			$.cookie("message.count", null);
		}
		/*获取用户在聊天的用户，最近1周联系的用户*/
		if (isEmpty($.cookie("message.chat"))) {
			$.post("/im/recent.users.html", function(data) {
				var result = data.result;
				IM.makeChat(JSON.stringify(result));
				//把返回值存储进Cookie
				$.cookie("message.chat", JSON.stringify(result));
			})
		} else {
			IM.makeChat($.cookie("message.chat"));
		}
		/*获取用户是否有未读的消息*/
		if (isEmpty($.cookie("message.count"))) {
			var system_count = 0, question_count = 0, fans_count = 0;
			$.post("/im/getMessageCount.html", function(data) {
				var result = data.result;
				for (var i = 0; i < result.length; i++) {
					var s = result[i];
					if (s.type == 'M2') {//系统消息类型
						system_count = system_count + s.count;
					}
					if (s.type == '@2') {//at问题类型
						question_count = question_count + s.count;
					}
					if (s.type == '@8') {//at粉丝
						fans_count = fans_count + s.count;
					}
				}
				var json_message = {
					fans_count : fans_count,
					question_count : question_count,
					system_count : system_count
				};
				$.cookie("message.count", JSON.stringify(json_message));
				$("#rt_message").data("message", json_message);
				//生成消息
				MC.showMessageDiv();
				/* 消息下拉事件*/
				MC.dropMessageDiv();
			})
		} else {
			$("#rt_message").data("message", JSON.parse($.cookie("message.count")));
			MC.showMessageDiv();
			/* 消息下拉事件*/
			MC.dropMessageDiv();
		}
		/* 判断下次登录的用户是不是本人*/
		$.cookie("message.user", Env.USER_ID);

	},
	makeChat : function(data) {
		var data = JSON.parse(data)
		if (!isEmpty(data) && data.length > 0) {
			for (var i = data.length - 1; i >= 0; i--) {
				var u = data[i];
				IM.addToWall(u.user_id, u.face || "/theme/main/default/images/noface_s.jpg", u.name, u.online);
				IM.getUsers(u.user_id);
				/*通知用户存在未读信息*/
				if (u.notread_count > 0) {
					IM.flicker(u.user_id);
				}
			}
		}
	},
	send : function() {
		var media = $("#im_media").val();
		var $v = $("#im-poster-area");
		var value = $v.val();
		if (isEmpty(media) && isEmpty(value) || value.length > MC.MAX_LENGTH || isEmpty(value.trim())) {
			$v.addClass("error");
			$v.fadeTo(300, 0.3).fadeTo(300, 1, function() {
				$v.removeClass("error")
			});
			return;
		}
		var cim = $("#im-box").attr("cim")
		IM.post({
			shower : cim,
			message : value.replaceAll("\n", "<br/>") + ( media ? "<br/><img src='" + $.image.resolve(media, {
				height : 120,
				width : 120
			}) + "'   onclick='IM.preview(this)'  preview='" + media + "'/>" : ""),
			sender : Env.USER_ID,
			name : Env.USER_NAME,
			face : Env.face,
			type : 0,
			receiver : cim,
			dateline : window.parseInt(new Date(Date.now()) / 1000)
		});
		$v.val("");
		if (!isEmpty(media))
			IM.deleteImage();
	},
	post : function(message) {
		//接收时显示到发送人的消息框
		message.shower = message.sender;
		//发送消息
		//MC.publish("/pv/" + message.receiver, message);
		$.post("/im/post.html", message, function(data) {
			//插入消息
			message.shower = message.receiver;
			message.message = data.message;
			//后台替换表情及过滤词后返回的信息
			if (message.type == 30) {
				message.type = 0;
				message.message = "已向" + $("#im-header span").html() + "发送请求，请等待回复。"
			}
			if (message.type == 0 || message.type == 30)
				IM.insertMessage(message);

		})
	},
	insertMessage : function(message, mark) {
		if (message.type == 30) {
			message.message = message.name + "邀请您进入教室实时交流，您是否接受？<span id='m" + message.id + "'><a href='javascript:;' style='color:blue' onclick='IM.acceptRoom(" + message.id + "," + message.sender + ",true,\"" + message.message + "\")'>接受</a>|<a href='javascript:;' style='color:blue' onclick='IM.acceptRoom(" + message.id + "," + message.sender + ",false)'>拒绝</a></span>";
		} else if (message.type == 31) {
			var m = eval("(" + message.message.replaceAll('&quot;','"') + ")");
			if (m.accept) {
				message.message = message.name + "已进入教室，是否为您连接……";
				$.alert.confirm(message.message, [
				function() {
					$.formSubmit(m.room, {}, "_blank");
				}]);
			} else {
				message.message = message.name + "拒绝了您进入教室的请求";
			}
		} else if (message.type == 32) {
			var sp = eval("(" + message.message + ")");
			message.message = "解答了提问:<font style='color:#f90'>" + sp.title + "</font>，详细内容请点击<a href='/qitem-" + sp.questionid + ".html#depict_" + sp.answerid + "' target='_blank' style='color:blue'>查看</a>";
		} else if (message.type == 33) {//在线回答邀请
			var sp = eval("(" + message.message + ")");
			message.message = "邀请您进入教室实时交流你所提交的问题:" + sp.title + "，您是否接受？<span id='m" + message.id + "'><a href='javascript:;' style='color:blue' onclick='IM.questionAcceptRoom(" + message.id + ",true,\"" + sp.roomid + "\")'>接受</a>|<a href='javascript:;' style='color:blue' onclick='IM.questionAcceptRoom(" + message.id + ",false)'>拒绝</a></span>";
		} else if (message.type == 35) {//提问者选择了你的答案
			message.message = "您回答的问题:<font style='color:#f90'>" + message.message + "</font>，答案已被本人采纳!";
		}
		/**生成消息**/
		/**/
		 var color=message.sender==Env.USER_ID?'style="color:green"':'style="color:blue"';
		 $("#im-message" + message.shower).append('<dl '+color+'><dt>' + message.name + '&nbsp;&nbsp;<span>' + (message.dateline * 1000).toDate().format("yyyy-mm-dd HH:MM:ss") + '</span></dt><dd>' + message.message + '</dd></dl>');
		 $("#im-message" + message.shower).scrollTop($("#im-message" + message.shower).prop("scrollHeight"));
		 /*是否标记为已读*/
		if (mark && message.receiver == Env.USER_ID) {
			$.post("/user/readMessage.html", {
				type : 0,
				ids : message.id
			})
		}

	},
	getUsers : function(userId) {
		if (IM.users["u" + userId]) {
			return true;
		} else {
			IM.users["u" + userId] = true;
		}
	},
	addUser : function(obj, userId, name) {
		/*判断是否为本人*/
		if (userId == Env.USER_ID) {
			$.alert.info("你不能和自己聊天!");
			return;
		}
		/*响应聊天窗口事件**/
		var $expand = $("#message-expand-wall");
		if (!$expand.data("open")) {
			$expand.trigger("click");
		}
		/*如果已经存在的直接打开聊天窗口*/
		if (IM.getUsers(userId)) {
			IM.open(userId, name, $("#im" + userId));
			return;
		}
		var face = $("#face" + userId);
		var offset = face.offset();
		var cf = face.clone().attr("id", "face" + userId + "-x");
		cf.css({
			"position" : "absolute",
			left : offset.left,
			top : offset.top
		});
		$(document.body).append(cf);
		cf.animate({
			top : $("#message-wall").offset().top + 7,
			left : $("#message-wall").offset().left + 10,
			"z-index" : 10000,
			height : 30,
			width : 30
		}, 600, function() {
			var src = cf.attr("src");
			var online = cf.attr("online");
			cf.remove();
			IM.addToWall(userId, src, name, online);
			IM.open(userId, name, $("#im" + userId));
		})
	},
	flicker : function(userId) {
		if (isEmpty(userId)) {
			return;
		}
		var $this = $("#im" + userId + " img");
		if (!($this.is(":animated"))) {
			$this.bind('message.new', function(event) {
				var p = $(this).css('position', 'relative');
				if (!window["m" + userId]) {
					var i = 0;
					window["m" + userId] = window.setInterval(function() {
						p.animate({
							top : 5 * ((i % 2 == 0) ? 1 : -1)
						}, 100);
						if (!window["m" + userId]) {
							p.css("top", 0);
						}
						i++;
					}, 100);
				}
			}).trigger('message.new');
		}
		/*响应聊天窗口事件**/
		var $expand = $("#message-expand-wall");
		if (!$expand.data("open")) {
			$expand.trigger("click");
		}
	},
	addToWall : function(userId, face, name, online) {
		if ($("#im" + userId).length == 1) {
			return;
		}
		var html = [];
		html.push('<li id="im' + userId + '" online="' + isEmpty(online, false) + '"><div class="message-aside">');
		html.push('<div class="user-head">');
		html.push('<img  src="' + face + '" width="30" height="30" alt="' + isEmpty(name, "") + '">');
		html.push('</div></div></li>');
		$("#im-user-list").prepend(html.join(""));
		$("#im" + userId).click(function() {
			IM.open(userId, name, $(this));
			var tid = window["m" + userId];
			if (tid) {
				window.clearInterval(tid);
				window["m" + userId] = undefined;
			}
		});
	},
	notice : function(message) {
		IM.addToWall(message.sender, message.face, message.name);
		IM.flicker(message.sender);
		/*以前打开过的直接插入消息*/
		if ($("#im-message" + message.sender).length != 0) {
			IM.insertMessage(message);
		} else {
			$.cookie("message.chat", null);
		}
	},
	open : function(userId, name, $this) {
		var $ibox = $("#im-box");
		var opened = $ibox.attr("opened");
		var cim = $ibox.attr("cim");
		if (opened == "true" && userId == cim) {
			return;
		}
		$("#im-message div").hide();
		var cm = $("#im-message" + userId);
		if (cm.length == 0) {
			$("#im-message").append('<div id="im-message' + userId + '" class="im-message"></div>');
			cm = $("#im-message" + userId);
		}
		if (cm.attr("loaded")) {
			cm.fadeIn(function() {
				cm.scrollTop(cm.prop("scrollHeight"));
			})
		} else {
			$.post("/im/now.html", {
				friend : userId
			}, function(data) {
				var result = data.result.listResult;
				for (var i = 0; i < result.length; i++) {
					var ms = result[i], n = name;
					if (Env.USER_ID == ms.sender) {
						n = Env.USER_NAME;
					}
					/*只有接受者显示该信息*/
					if ((ms.type == 30 && ms.receiveStatus == 1) || (ms.type == 31 && ms.receiveStatus == 1)) {//邀请
						continue;
					} else if (ms.type == 33 && ms.receiveStatus == 1) {//在线即时视频回答
						continue;
					}
					IM.insertMessage({
						id : ms.messageId,
						shower : userId,
						message : ms.message,
						name : n,
						type : ms.type,
						receiver : ms.receiver,
						sender : ms.sender,
						dateline : ms.sendTime
					}, ms.receiveStatus == 0)
				}
				cm.attr("loaded", "true").show();
			})
		}
		var head = $("#im" + userId).position();
		if ($(window).height() - head.top < $ibox.outerHeight()) {
			var off = $(window).height() - $ibox.outerHeight() - 35;
			$ibox.css("top", off);
			$ibox.find(".arrow").css("top", head.top - off)
		} else {
			$ibox.css("top", head.top);
			$ibox.find(".arrow").css("top", -2)
		}
		$("#im-header span").html(name);
		$ibox.attr("cim", userId).attr("opened", "true").show();
		/**设置是否在线状态**/
		var online = $("#im" + userId).attr("online");
		var $room = $("#im-enter-room");
		$room.attr("uid", userId);
		//生成独立的room
		$room.html(online == "false" ? "对方不在线" : "进入教室");
		if (online == "false") {
			$room.attr("setting", "{id:'create-im-room',effect:'mac',css:{width:500},onShow : function($pop, options){return false;}}");
		} else {
			$room.attr("setting", "{id:'create-im-room',effect:'mac',css:{width:500}}");
		}
		$.cookie("message.chat", null);
		//刷新聊天的Cookie
	},
	hide : function() {
		$("#im-box").attr("opened", "false").hide();
	},
	/*
	 * 上传图片
	 */
	upload : function() {
		$("#imForm").data("change", true).postForm(function(data) {
			var path = $.image.resolve(data.msg, {
				height : 120,
				width : 120
			});
			$("#im_media_div").html("<i class='arrow'><em>◆</em><span>◆</span></i><img src='" + path + "'  height=\"120\" width=\"120\" /><a href='javascript:void(0)' class='float-right' onclick='IM.deleteImage()'>删除</a>").show();
			$("#im_media").val(data.msg);
		});
	},
	deleteImage : function() {
		$("#im_media_div").html("").hide();
		$("#im_media").val("");
	},
	preview : function(obj) {
		window.open($(obj).attr("preview"));
	},
	inviteToRoom : function(link) {
		Pop.hide($("#create-im-room"))
		var cim = $("#im-box").attr("cim")
		IM.post({
			shower : cim,
			message : link,
			sender : Env.USER_ID,
			name : Env.USER_NAME,
			face : Env.face,
			receiver : cim,
			type : 30,
			dateline : window.parseInt(new Date(Date.now()) / 1000)
		});
	},
	acceptRoom : function(messageId, userId, accept, room) {
		var roomString = accept ? ",room:\"" + room + "\"" : "";
		var online = $("#im-box").attr("online");
		if (accept) {
			if (online == 0) {
				$.alert.warn("对方已不在线!");
				$("#m" + messageId).text("您已拒绝")
				return;
			}
			$.formSubmit(room, {}, "_blank");
			$("#m" + messageId).text("您已接受")
		} else {
			$("#m" + messageId).text("您已拒绝")
		}
		IM.post({
			shower : userId,
			message : "{accept:" + accept + roomString + "}",
			sender : Env.USER_ID,
			name : Env.USER_NAME,
			face : Env.face,
			receiver : userId,
			type : 31,
			dateline : window.parseInt(new Date(Date.now()) / 1000)
		});
	},
	questionAcceptRoom : function(messageId, accept, roomid) {
		if (accept) {
			$.formSubmit("/abc/abc.html?roomId=" + roomid, {}, "_blank");
			$("#m" + messageId).text("您已接受")
		} else {
			$("#m" + messageId).text("您已拒绝")
		}
	}
}
MC = {

};
MC.publish = function(ch, data) {
	try {
		comted.MC.publish(ch, data);
	} catch(e) {
	}
}
/*消息回调*/
MC.cometdCallback = function(type, message) {
	switch(type) {
		case "pv":
			MC.pv(message);
			break;
		case "sc":
			MC.sc(message);
			break;
		case "sys":
			MC.sys(message);
			break;
	}
}
/**
 * 私信调整为IM实现方式
 */
MC.pv = function(message) {
	message = message.data;
	switch(message.type) {
		case 6 :
			//At消息
			var $message = $("#message_nums");
			var num = ($message.find("i").text() || 0) * 1 + 1;
			//设定消息值
			$message.html("消息(<font style='color:red'><i>" + num + "</i></font>)");
			if (message.objecttype == 2) {//问题
				MC.resetMessageCookie("question_count");
			} else if (message.objecttype == 8) {//粉丝
				MC.resetMessageCookie("fans_count");
			}
			break;
		default :
			//私人聊天信息
			var $ibox = $("#im-box");
			var opened = $ibox.attr("opened"), cim = $ibox.attr("cim");
			/*如果窗口打开*/
			if (opened == "true") {
				/*正在和当前人聊天*/
				if (message.sender == cim) {
					IM.insertMessage(message, true);
					return;
				}
			}
			IM.notice(message);
			break;
	}
}
/* 接收到消息重置消息Cookie
 * type 类型
 * */
MC.resetMessageCookie = function(type) {
	var data = $("#rt_message").data("message");
	data[type] = data[type] + 1;
	$("#rt_message").data("message", data);
	$.cookie('message.count', JSON.stringify(data));
}
/**
 * 生成消息显示框
 */
MC.showMessageDiv = function() {
	var data = $("#rt_message").data("message");
	var messageCount = data.question_count + data.system_count + data.fans_count;
	$("#rt_message").html("<a href='/user/message.html' id='message_nums'>消息" + (messageCount > 0 ? "(<font style='color:red'><i>" + messageCount + "</i></font>)" : "<i id='message_nums'></i>") + "</a>");
}
/**
 * 生成消息下拉框
 */
MC.dropMessageDiv = function() {
	var $pop;
	var $this = $("#rt_message");
	$this.mouseenter(function() {
		var data = $this.data("message");
		var html = [];
		var system_count = data.system_count, question_count = data.question_count, fans_count = data.fans_count;
		if (system_count == 0 && question_count == 0 && fans_count == 0) {
			return;
		}
		if (fans_count > 0) {
			html.push("<a href='/follow/index.html#!/follow/fans.html?id=" + Env.USER_ID + "'>您有(<font style='color:red'>" + fans_count + "</font>)位新粉丝</a>");
		}
		if (question_count > 0) {
			html.push("<a href='/user/index.html#!/question/at.html'>您有(<font style='color:red'>" + question_count + "</font>)条求助消息</a>");
		}
		if (system_count > 0) {
			html.push("<a href='/user/message.html?type=2'>您有(<font style='color:red'>" + system_count + "</font>)条系统消息</a>");
		}
		$this.addClass("active")
		$pop = $this.dropover({
			id : "message-drop",
			content : html.join(""),
			className : "dropover",
			css : {
				width : 150
			},
			cache : false
		});
	}).mouseleave(function() {
		$this.removeClass("active")
		Pop.hide($pop)
	});
}

MC.sc = function(message) {
	var data = message.data;
	switch(data.type) {
		case "mblog":
			if (data.poster != Env.USER_ID) {
				window.__MBLOG_NEW_COUNT = (window.__MBLOG_NEW_COUNT == undefined ? 0 : window.__MBLOG_NEW_COUNT) + 1;
				$("#mblog_new_count").html("<a href='javascript:void(0)' onclick='Mblog.loadAll()'>有" + window.__MBLOG_NEW_COUNT + "条新微博，点击查看</a>").show();
			}
			break;
		case "accounce":
			break;
	}
}

MC.sys = function(message) {
	var data = message.data;
	switch(data.event) {
		case "system_message":
			var $message = $("#message_nums");
			var num = ($message.find("i").text() || 0) * 1 + 1;
			//设定消息值
			$message.html("消息(<font style='color:red'><i>" + num + "</i></font>)");
			MC.resetMessageCookie("system_count");
			break;
		case "user.status":
			var status = data.status, userid = data.id;
			if (status == 1) {
				/*上线*/
				$("a[usercard='" + userid + "']").removeClass("user status" + 0).addClass("user status" + 1);
				$("img[usercard='" + userid + "']").attr("online", true);
				//页面
				$("img[id='face" + userid + "']").attr("online", true);
				//名片
				$("#im" + userid).attr("online", true);
				//聊天窗口
				var $room = $("#im-enter-room[uid=" + userid + "]");
				$room.attr("setting", "{id:'create-im-room',effect:'mac',css:{width:500}}").html("进入教室");
			} else if (status == 0) {
				/*离线*/
				$("a[usercard='" + userid + "']").removeClass("user status" + 1).addClass("user status" + 0);
				$("img[usercard='" + userid + "']").attr("online", false);
				$("img[id='face" + userid + "']").attr("online", false);
				$("#im" + userid).attr("online", false);
				//聊天窗口
				var $room = $("#im-enter-room[uid=" + userid + "]");
				$room.attr("setting", "{id:'create-im-room',effect:'mac',css:{width:500},onShow : function($pop, options){return false;}}").html("对方不在线");
			}
			break;
	}
}
/*======================================================*
 * 发送私信相关方法
 *======================================================*/
MC.showForm = function(u, name) {
	if (u == Env.USER_ID) {
		$.alert.info("你不能私信给自己!");
		return;
	}
	Pop.show({
		id : 'privateMessage',
		url : "/user/messageForm.html",
		css : {
			width : "550px"
		},
		afterShow : function() {
			$("#messageForm_username").val(name);
			$("#messageForm_receiver").val(u);
			return true;
		}
	});
}

MC.messagelistShowForm = function(event, u, name) {
	event = $.event.fix(event);
	event.preventDefault();
	event.stopPropagation();
	Pop.show({
		id : 'privateMessage',
		url : "/user/messageForm.html",
		css : {
			width : "550px"
		},
		afterShow : function() {
			$("#messageForm_username").val(name);
			$("#messageForm_receiver").val(u);
			return true;
		}
	});
}

MC.MAX_LENGTH = 100;
MC.countWord = function(obj, infoId) {
	var len = $(obj).val().length;
	if (len > MC.MAX_LENGTH) {
		$("#" + infoId).html('已经超过<strong  style="color:red" >' + (len - MC.MAX_LENGTH) + '</strong>字');
	} else {
		$("#" + infoId).html('您还可以输入<strong style="color:#49C146">' + (MC.MAX_LENGTH - len) + '</strong>字');
	}
}

MC.wordCheck = function(event, obj) {
	var len = $(obj).val().length;
	if (event.keyCode == 8) {
		return true;
	};
	if (len >= MC.MAX_LENGTH) {
		return false;
	}
}

MC.send = function() {
	if ($("#messageForm_receiver").val() == Env.USER_ID) {
		$.alert.warn("您不能给自己发消息!");
		return;
	}
	var $editor = $("#message_editor");
	var message = $editor.val();
	if (isEmpty(message) || message.length > MC.MAX_LENGTH) {
		$editor.addClass("error");
		$editor.fadeTo(300, 0.3).fadeTo(300, 1, function() {
			$editor.removeClass("error")
		});
		return;
	}
	$("#messageForm").postForm(function(data) {
		if (data.status > 0) {
			$editor.addClass("postsuccess2").val("");
			setTimeout(function() {
				$editor.removeClass("postsuccess2");
				Pop.hide($("#privateMessage"));
			}, 1000);
		} else {
			$.alert.show(data);
		}
	});
}
/***获取页面信息***/
MC.getMessagePage = function(type, anotherid) {
	var $div = $("#message-privateview");
	$.get("/user/message.html?type=" + type + "&anotherid=" + anotherid, function(html) {
		$("#message-tab li").removeClass("active");
		$("#message-tab li:eq(" + (type == "9" ? "0" : type) + ")").addClass("active");
		var $html = $(html).find("#message-privateview").children();
		$div.html($html).reload();
	});
}
/***删除私人聊天信息(接收者删除和发送者删除)***/
MC.delPrivate = function(id, index, isReceiver) {
	var url = "";
	if (isReceiver) {
		url = "/user/deleteMessageReceiveStatus.html";
	} else {
		url = "/user/deleteMessageSendStatus.html";
	}
	$.post(url, {
		id : id
	}, function(data) {
		$.alert.show(data, function() {
			if (data.status == 1) {
				/*只有一条时直接全隐藏*/
				if ($("#messageInfo_dl_" + index).find(".R_msg:visible").length == 1) {
					$("#messageInfo_dl_" + index).slideUp();
					$("#messageInfo_dl_line_" + index).slideUp();
				} else {
					$("#messageInfo_content_" + id).slideUp();
					$("#messageInfo_line_" + id).slideUp();
				}
			}
		});
	});
}
/***删除某条信息***/
MC.del = function(id) {
	$.post("/user/deleteMessageReceiveStatus.html", {
		id : id
	}, function(data) {
		$.alert.show(data, function() {
			if (data.status == 1) {
				$("#messageInfo_dl_" + id).slideUp();
			}
		});
	});
}
/***删除我和某人的所有聊天信息***/
MC.delAll = function(other) {
	$.post("/user/deleteMessage.html", {
		other : other
	}, function(data) {
		$.alert.show(data, function() {
			if (data.status == 1) {
				$("#mymessageList_" + other).slideUp();
			}
		});
	});
}

MC.deleteAnnounce = function(id) {
	$.alert.confirm("您确定要删除该信息？", [
	function() {
		$.post("/admin/deleteAnnounce.html", {
			messageId : id
		}, function(data) {
			$.alert.show(data, function() {
				if (data.status > 0) {
					$("#announceItem" + id).slideUp();
				}
			});
		});
	}]);
}