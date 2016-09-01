/***
 * 图片服务器  不能修改
 */
;(function($) {
	$.image = {
		/*根据指定的条件对图片进行处理*/
		resolve : function(path, options) {
			/*引用图片*/
			if (path.is(":externalURL")) {
				return path;
			}
			var v = "";
			if (options) {
				v = "_"
				/*0代表保留原值,缩放*/
				if (options.height && options.width) {
					v += "s" + options.width + "," + options.height;
				} else if (options.height) {
					v += "s" + 0 + "," + options.height;
				} else if (options.width) {
					v += "s" + options.width + "," + 0;
				}

				/*旋转*/
				if (options.rotate) {
					v += "r" + options.rotate;
				}
			}
			return path.replaceLast(".", v + ".");
		}
	}
})(jQuery);
var flint = {
	debug : true,
	version : 1.0,
	OK : 200
};
var CONFIG = {
	/*
	 * 弹出消息,result为后台消息 result{status:1,message:"",code:"",result:"",errors:[]}
	 */
	__RESULT : {
		INFO : 2,
		SUCCESS : 1,
		WARN : 0,
		FAILURE : -1,
		EXCEPTION : -2,
		SESSION_TIME_OUT : -3,
		NO_PERMISSION : 403
	},
	__SESSION : {
		TIME : "TIME_ALIVE",
		INTERVAL : 5/* 检查session的间隔时间，单位分钟 */
	}
}
window.HPST = "testKey";
/* 框架环境变量 ,是否开启调试 */
window.DEBUG = flint.debug;
/* 当前框架版本号 */
window.FLINT_VERSION = 1.0;

/*收藏类型常量定义*/
CONFIG.__COLLECT_TYPE = window.COLLECT_TYPE = {
	COURSE : 1,
	QUESTION : 2,
	BOOK : 3,
	EXERCISE : 4,
	MBLOG : 5,
	SHOW : 6
};

/**
 * 加载用户登录界面
 */
$.showLogin = function() {
	location.href = Env.SECURE_DOMAIN + "/login.html?url=" + encodeURIComponent(document.URL);
	return;
	/*需要做跨域处理才能访问*/
	Pop.show({
		id : "login-window",
		url : Env.SECURE_DOMAIN + "/login.html?window=true&url=" + encodeURIComponent(document.URL),
		iframe : true,
		effect : "drop",
		css : {
			width : "450px",
			height : "400px"
		}
	});

}

Kiss.define(function(context) {
	var $area = $(context);
	var u = [];
	$("a[usercard],img[usercard]", context).each(function() {
		u.push($(this).attr("usercard"));
	});
	u = u.unique();
	trace("this area has users:" + u.toString());
	if (u.length > 0) {
		$.post("/ping.user.html", {
			u : u.toString(),
			gid : Env.GROUP_ID || 0/*学习圈的编号，用户可以在学习圈设置自己特有的名称*/
		}, function(result) {
			result = eval("(" + result.result + ")");
			$(result).each(function(index, data) {
				$area.find("a[usercard='" + data.id + "']").text(data.name).addClass("user status" + data.status).data({
					follow : data.follow,
					fans : data.fans
				});
				$area.find("img[usercard='" + data.id + "']").attr("online", data.online == 0 ? false : true).each(function() {
					var face = $(this);
					var w = face.attr("width"), h = face.attr("height");
					face.attr("alt", data.name).data({
						follow : data.follow,
						fans : data.fans
					}).after('<i class="credit mark  level' + isEmpty(data.credit, 1) + '" title="信誉等级"></i>').parent().addClass("face-credit").css({
						width : w,
						height : h
					});
					face.attr("src", $.image.resolve(data.face, {
						width : w,
						height : h
					}));
				});
			});
		});
	}
	try {
		$area.find("a[usercard],img[usercard]").each(function() {
			var $usercard = $(this), usercard = $usercard.attr("usercard");
			$(this).popover({
				container : document.body,
				id : "u" + usercard,
				show : function(popover, fn) {
					var follow = $usercard.data("follow"), fans = $usercard.data("fans");
					$.post("/ping.user.html", {
						u : usercard,
						full : true
					}, function(data) {
						if (data.status == -2) {
							popover.$inner.html("获取用户信息失败");
							return;
						}
						data = data.result;
						var popup = [];
						data.face = $.image.resolve(data.face, {
							width : 40,
							height : 40
						});
						popup.push('<div  class="usercard" style="width:320px">');
						popup.push('      <dl>');
						popup.push('        <dt><img src="' + data.face + '"" width="40" height="40" alt="' + data.name + '" id="face' + usercard + '"   online="' + (data.online == 0 ? false : true) + '"></dt>');
						popup.push('        <dd>');
						popup.push('             <h5><a href="' + Env.SITE_DOMAIN + '/u/' + usercard + '.html" target="_blank">' + data.name + '</a></h5>');
						popup.push('             <div>擅长：' + (isEmpty(data.expert) ? "未设置" : data.expert.replaceAll(",", "，") ) + '</div>');
						popup.push('             <div><strong>0</strong>个回答，<strong>0</strong>个课程，<strong>0</strong>个学生</div>');
						popup.push('        </dd>');
						popup.push('      </dl>');
						popup.push('      <div  class="usercard-intro">' + isEmpty(data.experience, "暂无简介") + '</div>');
						/*用户购买后开放*/
						if (false) {
							popup.push('      <div class="usercard-extend">');
							popup.push('         <div class="user-tutor">');
							popup.push('              <div class="user-tutor-title">Ta提供的辅导</div>');
							popup.push('              <ul>');
							popup.push('                 <li>语文(40元/时)</li>');
							popup.push('                 <li>语文(40元/时)</li>');
							popup.push('                 <li>语文(40元/时)</li>');
							popup.push('              </ul>');
							popup.push('         </div>');
							popup.push('      </div>');
						}
						if (data.userId != Env.USER_ID) {
							popup.push('<div class="link-section">');
							popup.push('<span class="pull-left gray-light"><strong>161359</strong>人关注他</span>');
							popup.push('<a href="javascript:;" onclick="IM.addUser(this,' + usercard + ',\'' + data.name + '\');return false;">聊天</a><button class="btn btn-mini" type="button" onclick="MC.showForm(' + usercard + ',\'' + data.name + '\');">@私信</button>');
							if (!follow)
								popup.push('<button  uid="' + usercard + '" class="btn btn-mini btn-success" node-type="follow">+关注</button>');
							else
								popup.push('<button   uid="' + usercard + '"  class="btn btn-mini" node-type="follow"  focus="true" ><i class="icon icon-follow1"></i>取消关注</button>');
							popup.push('</div>');
						}
						popup.push('</div>');
						popover.$tooltip.attr("id", 'u' + usercard);
						popover.$inner.html(popup.join("")).reload();
						fn();
					});
				}
			});
		});
	} catch(e) {
	}
});

Kiss.addEventListener({
	editor : function(obj) {
		$(obj).editor({
			base : "/theme/kiss/editor",
			plugins : ['bold', 'italic', 'underline', 'strikethrough', 'insertorderedlist', 'insertunorderedlist', 'removeformat', 'image', 'sketchpad', "camera"],
			disabled : !Env.isLogin,
			content : "<div style='text-align:center;margin-top:30px;'>您还没有<a href='javascript:void(0)' onclick='parent.$.showLogin() '>登录</a></div>"
		})
	},
	countdown : function(obj) {
		$(obj).countdown();
	},
	favor : function(obj) {
		$(obj).live("click", function(event) {
			var $this = $(this), objectId = $this.attr("objectId"), objectType = $this.attr("objectType");
			favorToggle(event, $(obj), objectId, objectType)
		});
	},
	follow : function(obj) {
		$(obj).click(function() {
			var $this = $(this), u = $this.attr("uid"), focus = $this.attr("focus"), reload = $this.attr("reload"), item = $this.attr("item");
			if (focus == "true") {
				/*取消关注*/
				$.post("/follow/blur.html", {
					userId : u
				}, function(data) {
					$.alert.show(data);
					if (data.status == 1) {
						$this.attr("focus", "false");
						$this.text("+关注");
					}
				});
			} else {
				if (u == Env.USER_ID) {
					$.alert.info("你不能关注自己!");
					return;
				}
				/*关注*/
				$.post("/follow/focus.html", {
					userId : u
				}, function(data) {
					$.alert.show(data);
					if (data.status == 1) {
						if (!reload) {
							$this.attr("focus", "true");
							$this.html("<i class='icon icon-follow'></i>取消关注")
						} else {
							$(item).fadeOut();
							$(reload).click();
						}
					}
				});
			}
		});
	},
	"goto-room-fast" : function(obj) {
		$(obj).click(function() {
			$.post("/abc/create.html", eval("(" + $(this).data("param") + ")"), function(result) {
				if (result.status == 1) {
					location.href = Env.SITE_DOMAIN + "/abc/abc.html?roomId=" + result.roomId;
				} else {
					$.alert.show(result);
				}
			})
		});
	},
	"mygroup" : function(obj) {
		var me = $(obj);
		/*开始加载班级信息*/
		var $pop;
		me.on("pop", function() {
			$pop = me.dropover({
				id : "my-group-drop",
				className : "dropover my-group-list",
				content : me.data("group-html"),
				cache : true
			});
		}).mouseenter(function() {
			if (!me.data("loaded")) {
				var type = isEmpty(me.attr("group-type"), 0) * 1;
				$.getJSON("/user/group" + type + ".html", function(result) {
					me.addClass("active");
					result = result.groups;
					var html = [];
					for (var i = 0; i < result.length; i++) {
						var g = result[i];
						html.push('<a href="' + (type == 0 ? "/class/" : "/group/") + g.groupId + '.html" target="_blank"><dl class="clearfix" ><dt>');
						html.push('<img  src="http://www.17shangke.com/image.server/files/avatar/608.jpg" width="50" height="50" alt="">');
						html.push('</dt><dd><div>' + g.name + '</div><div><!--这里显示最新的动态--></div></dd></dl></a>');
					}
					me.data("loaded", true);
					me.data("group-html", html.join(""));
					me.trigger("pop");
				})
			} else {
				me.trigger("pop");
			}
		}).mouseleave(function() {
			me.removeClass("active")
			Pop.hide($pop);
		});
	}
});

window.lastScrollTop = 0;
window.direction = "down";
$(function() {
	$(window).scroll(function() {
		var st = $(this).scrollTop();
		if (st > lastScrollTop) {
			window.direction = "down";
		} else {
			window.direction = "up";
		}
		lastScrollTop = st;
	});
});
/**
 * 全局函数收藏课程
 */
function favorToggle(event, $item, objectId, objectType) {
	var options = {
		effect : "mouse",
		trigger : $item
	};
	var favor = ($item.attr("favor") || 0) * 1;
	var isFavor = $item.attr("isFavor");
	/*已收藏的，取消收藏*/
	if (favor == 1) {
		$.alert.confirm("您确定要取消收藏？", [
		function() {
			$.post(Env.SITE_DOMAIN + "/user/delFav.html?objectId=" + objectId + "&objectType=" + objectType, function(data) {
				if (data.status == 1) {
					$item.html("<i class='icon icon-like'></i>收藏");
					$.alert.success("取消收藏成功", options);
					if (isFavor) {
						$("#" + isFavor).slideUp(500);
					}
					$item.attr("favor", "0");
				} else {
					$.alert.show(data, options);
				}
			});
		}], options);
	} else {
		$.post(Env.SITE_DOMAIN + "/user/toFav.html?objectId=" + objectId + "&objectType=" + objectType, function(data) {
			if (data.status == 1) {
				$.alert.success("收藏成功", options);
			} else {
				$.alert.show(data, options);
			}
			$item.html("<i class='icon icon-like-active'></i>取消收藏");
			$item.attr("favor", "1");
		});
	}
}

/**
 * 加载表情
 */
function loadFace(obj, bindArea, div) {
	$.getJSON("/tools.face.html", function(data) {
		window.FACE_LIST = data;
		var html = [];
		var list = data.faces;
		html.push("<a class='close' href='javascript:;' style='position:absolute;right:5px;top:-5px;'>×</a><div style='padding:15px 10px'><ul class='faces_list face list'>")
		$.each(list, function(key, value) {
			html.push("<li text='" + key + "'><img src='" + value + "'></li>");
		})
		html.push("</ul><div class='clearfix'></div></div>");
		$(obj).popover({
			id : "faces_list",
			content : html.join(""),
			placement : "bottom",
			trigger : "click",
			afterShow : function($pop) {
				$pop.css("z-index", 65535).find("li").click(function() {
					var $bindArea = $("#" + bindArea);
					$bindArea.change().val($bindArea.val() + $(this).attr("text"));
					if (div) {
						div.append($(this).html());
					}
					Pop.hide($("#faces_list"));
				})
			}
		});
		obj.popover.show();
	})
}

Kiss.define(function(context) {
	var $area = $(context);
	$area.find(".select-box").each(function() {
		var $sb = $(this);
		var max = $sb.attr("max") * 1;
		var $target = $("#" + $sb.attr("action-target"));
		$sb.bind("reset.value", function() {
			var value = [];
			var text = [];
			$sb.find("a.active").each(function() {
				value.push($(this).attr("action-data"));
				text.push($(this).text());
			});
			$sb.data("selected-count", value.length);
			$target.data("text", text.join(","))
			$target.val(value.join(",")).change();
		}).find("a[action-type='select-item']").click(function() {
			if ($(this).hasClass("active")) {
				$(this).removeClass("active");
				$sb.trigger("reset.value");
			} else {
				if ($sb.data("selected-count") != max || max == 1) {
					if (max == 1) {
						$sb.find("a.active").removeClass("active");
					}
					$(this).addClass("active");
					$sb.trigger("reset.value");
				} else {
					$.alert.warn("对不起，您最多只能选择" + max + "项！")
				}
			}
		});
		var defaultValue = $target.val();
		var d = [];
		if (!isEmpty(defaultValue)) {
			var dv = defaultValue.split(",")
			for (var i = 0; i < dv.length; i++)
				d.push("a[action-data='" + dv[i] + "']");
			$sb.find(d.join(",")).addClass("active");
			$sb.trigger("reset.value");
		}
	});

	$area.find("[node-type='city-selector']").each(function() {
		var $this = $(this), city = isEmpty($this.attr("city"), "000000") * 1, name = $this.attr("node-name");
		var pv = (city % 10000 == 0) ? city : parseInt(city / 10000) * 10000;
		var cv = (city % 100 == 0) ? city : parseInt(city / 100) * 100;
		var select = [];
		select.push("<input type='hidden' name='" + name + "' value='" + city + "'>");
		select.push("<select>");
		select.push("<option value='000000'>请选择</option>");
		for (var i = 0; i < City.length; i++) {
			select.push("<option value='" + City[i].code + "'>" + City[i].name + "</option>");
		}
		select.push("</select>");
		select.push("<select>");
		select.push("<option value='000000'>请选择</option>");
		select.push("</select>");
		select.push("<select>");
		select.push("<option value='000000'>请选择</option>");
		select.push("</select>");
		$this.html(select.join(""));
		var $select = $this.find("select"), $input = $this.find("input[name='" + name + "']");
		$select.eq(0).change(function() {
			var s = [], v = this.value, c;
			if (v == "000000")
				return;
			c = City[this.selectedIndex - 1];
			s.push("<option value='000000'>请选择</option>");
			for (var k = 0; k < c.areas.length; k++) {
				var tp = c.areas[k]
				s.push("<option value='" + tp.code + "'>" + tp.name + "</option>");
			}
			$(this).next().html(s.join("")).data("cityIndex", this.selectedIndex - 1).next().html("<option value='000000'>请选择</option>");
			$input.val(v);
		}).find("option[value=" + pv + "]").attr("selected", true).change();
		$select.eq(1).change(function() {
			var s = [], v = this.value, c, $s = $(this);
			if (v == "000000")
				return;
			c = City[$s.data("cityIndex")].areas[this.selectedIndex - 1];
			s.push("<option value='000000'>请选择</option>");
			for (var k = 0; k < c.citys.length; k++) {
				var tp = c.citys[k]
				s.push("<option value='" + tp.code + "'>" + tp.name + "</option>");
			}
			$(this).next().html(s.join("")).next().html("<option value='000000'>请选择</option>");
			$input.val(v);
		}).find("option[value=" + cv + "]").attr("selected", true).change();
		$select.eq(2).change(function() {
			$input.val(this.value);
		}).find("option[value=" + city + "]").attr("selected", true);

	});
});

$.city = {
	pinyin : function() {
		var A = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
		var b = [];
		for (var i = 0; i < City.length; i++) {
			var index = A.indexOf(City[i].jp.substring(0, 1));
			if (!b[index])
				b[index] = [];
			b[index].push(City[i]);
		}
		return b;
	},
	intial : function() {
		var html = [];
		html.push('<a href="javascript:;" note-type="popup-close" class="close">×</a>');
		html.push('<div class="filter-box box>')
		var list = $.city.pinyin();
		for (var i = 0; i < list.length; i++) {
			var s = list[i];
			if (s && s.length > 0) {
				html.push('<dl>');
				html.push('<dt>A</dt>');
				html.push('<dd>');
				for (var j = 0; j < s.length; j++) {
					var c = s[j];
					html.push('<a href="javascript:;">' + c.name + '</a>')
				}
				html.push('</dd>');
				html.push('</dl>');
			}
		}
		html.push('</div')
		return html.join("");
	},
	show : function(obj) {
		PopUp.show({
			id : "city-select",
			cache : true,
			content : $.city.intial()
		})
	}
}

$.createRoom = function(fast, callback) {
	if ($("#password").val() != $("#repassword").val()) {
		$.alert.warn("您两次输入的密码不一致!");
		return;
	}
	$("#createRoomForm").postForm(function(data) {
		var link = Env.SITE_DOMAIN + "/abc/abc.html?roomId=" + data.roomId;
		if (callback) {
			eval(callback)(link);
			return;
		}
		/*快速创建*/
		if (fast) {
			location.href = link;
			return;
		}
		Pop.update($("#createRoom-pop").data("pop"), {
			content : "<div class='pop-header'><h3>邀请连接</h3><a node-type='pop-close' class='close' href='javascript:;'>×</a></div><div class='pop-body' node-type='pop-body'><textarea row='3' style='width:98%' id='room-link-text'>" + link + "</textarea></div><div class='pop-footer'><button onclick='$.linkCopy(this,\"" + link + "\")'  class='button large '>复制连接</button></div>"
		});
	});
}

$.linkCopy = function(obj, text) {
	if (window.clipboardData) {
		window.clipboardData.setData("text", text);
	} else {
		$("#room-link-text").focus().select();
		$.alert.warn("您的浏览器不支持复制，请按Ctrl+C复制");
	}
}