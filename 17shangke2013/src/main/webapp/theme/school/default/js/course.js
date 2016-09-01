/**
 *
 * 功能描述：课程相关<br>
 *
 *
 * 日 期：2011-8-2 13:21:54<br>
 *
 * 作 者：Dreamall<br>
 *
 * 版权所有：©mosai<br>
 *
 * 版 本 ：v0.5<br>
 *
 * 联系方式：dream-all@163.com<br>
 *
 * 修改备注：{修改人|修改时间}<br>
 *
 */
Course = {};
Kiss.addEventListener({
	/*课程审核*/
	"course-check" : function(obj) {
		var $this = $(obj);
		$(obj).click(function() {
			var $this = $(this), type = $this.attr("courseType"), agree = $this.attr("agree"), id = $this.attr("courseId");
			var data = {
				id : id,
				agree : agree == "true" ? 1 : 0,
				type : type,
				newprice : -1,
				reason : ""
			};
			if (agree == "true") {
				switch(type) {
					case "1":
						//在线课程
						break;
					case "2":
						//视频课程
						break;
					case "3":
						//线下课程
						//检查优惠价格
						var newPrice = $("#newPrice").val();
						if (window.isEmpty(newPrice)) {
							$.alert.warn("请输入优惠价格");
							return;
						}
						data.newprice = newPrice;
						break;
					case "4":
					//转发视频
				}
			} else {
				//检查原因
				var reason = $("#check_refuse_input").val()
				if (window.isBlank(reason)) {
					$.alert.warn("请录入驳回原因")
					return;
				}
				data.reason = reason;
			}
			$.post("/manager/business/course.check.html", data, function(result) {
				$.alert.show(result);
			})
		});
	},
	"course-item" : function(obj) {
		$(obj).each(function() {
			var $item = $(this), cid = $item.attr("cid"), ctype = $item.attr("ctype");
			$item.find("[node-type]").click(function() {
				var $this = $(this), at = $this.attr("node-type");
				switch(at) {
					case "course-cancle":
						Course.cancle(cid, ctype, $this);
						break;
					case "course-close":
						Course.close(cid, ctype, $this);
						break;
					case "course-edit":
						break;
				}
			});
		})
	}
});
/**
 * 按步骤进行课程，如果是修改课程可以直接跳到某一步骤
 *
 * @param n  课程步骤
 * @param cid 课程编号
 *
 */
Course.step = function(n, cid, back) {
	switch (n) {
		case "view":
			location.href = Env.SITE_DOMAIN + "/course/manager/{"+cid+"}/edit.html"
			break;
		case "courseware":
			if (back || Course.saveCourse())
				location.href = Env.SITE_DOMAIN + "/course/manager/{"+cid+"}/courseware.html";
			break;
		case "exercise":
			location.href = Env.SITE_DOMAIN + "/course/manager/{"+cid+"}/exercise.html"
			break;
		case "preview":
			location.href = Env.SITE_DOMAIN + "/course/manager/{"+cid+"}/preview.html"
			break;
		case "publish":
			$.formSubmit(Env.SITE_DOMAIN + "/course/manager/{"+cid+"}/publish.html");
			location.href = Env.SITE_DOMAIN + "/course/manager/{"+cid+"}/publish.html"
			break;
	}
}
/**
 * 保存课程
 */
Course.saveCourse = function(next) {
	$("#courseForm").postForm({
		handleForm : function(request) {
			if (!Course.edit) {
				if (!window.LOCAL_COURSE) {
					request.addParameter("startTime", request.getParameter("planStartDate").toSecondsTime());
				}
			}
			return true;
		},
		success : function(result) {
			if (result.result == 1) {
				if (!next)
					location.href = Env.SITE_DOMAIN + "/course/manager/courseware.html?i=" + result.courseId;
				else
					location.href = Env.SITE_DOMAIN + "/course/" + next + ".html?i=" + result.courseId;
			} else {
				$.alert.error(result)
			}
		}
	});
}
Course.uploadVideo = function(obj) {
	if (isEmpty(obj.value)) {
		return;
	}
	if (obj.value.toLowerCase().is("\.(flv|f4v|mp4)$")) {
		$("#video_upload").hide();
		$("#uploadProgress").show();
		var $bar = $("#uploadProgress_bar");
		var progressId = window.setInterval(function() {
			$.get("/upload/status.html?flag=video", function(data) {
				$bar.css("width", data + "%").text(data + "%");
			})
		}, 1500);
		$("#videoUploadForm").data("change", true).postForm(function(data) {
			window.clearInterval(progressId);
			$bar.css("width", "100%").text("上传成功!");
			/*转向表单填写页面*/
			window.setTimeout(function() {
				$.formSubmit("/course/manager/create.html", {
					type : "video",
					url : data.url,
					c : data.cover
				});
			}, 1500);
		});
	} else {
		$.alert.warn("为了提高您上传文件的的效率<br/>强烈建议您先将文件格式转换flv,f4v");
		obj.value = "";
	}
}
/*
 * 保存视频课程
 */
Course.saveVideo = function() {
	$("#videoForm").postForm({
		handleForm : function(request) {
			return true;
		},
		success : function(result) {
			if (result.result == 1) {
				location.href = Env.SITE_DOMAIN + "/course/manager/preview.html?i=" + result.courseId;
			} else {
				$.alert.show(result)
			}
		}
	});
}
/**
 * 发布线下课程
 */
Course.saveLocal = function() {
	window.LOCAL_COURSE = true;
	Course.saveCourse("preview");
}

Course.uploadCover = function() {
	if (isEmpty($("#cover_file").val())) {
		return;
	}
	$("#coverUploadForm").postForm(function(data) {
		if (data.err) {
			$.alert.warn(data.err);
			return;
		}
		Cover.load(data.msg);
		$("#coverUploadForm").attr("cpath", data.path)
	});
}

Course.saveCover = function(isSelect) {
	var cpath = $("#coverUploadForm").attr("cpath");
	if (!isSelect && cpath) {
		$.post("/cover/save.html", {
			x : Cover.data.x,
			y : Cover.data.y,
			w : Cover.data.w,
			h : Cover.data.h,
			path : cpath
		}, function(data) {
			$("#courseCoverInput").val(data.path);
			$("#courseCoverDiv").html("<img src='" + data.path + "'/>");
			Pop.hide($("#cover-upload-pop"));
		})
		return;
	} else {
		var select = $(".images li.selected img")
		if (select.length > 0) {
			$("#courseCoverInput").val(select.attr("src"));
			$("#courseCoverDiv").html("<img src='" + select.attr("src") + "'/>");
			Pop.hide($("#cover-upload-pop"));
		} else {
			$.alert.warn("请您先选择封面!");
		}
	}
}
Cover = {
	options : {
		selector : {
			width : 300,
			height : 225
		},
		source : "cover-source",
		previews : [{
			id : "cover-preview",
			width : 300,
			height : 225
		}],
		updatePreview : function(c) {
			var x = c.x, y = c.y, w = c.w, h = c.h;
			$(Cover.options.previews).each(function() {
				var p = this;
				var rx = p.width / c.w;
				var ry = p.height / c.h;
				$('#' + p.id + " img").css({
					width : Math.round(rx * Cover.width) + 'px',
					height : Math.round(ry * Cover.height) + 'px',
					marginLeft : '-' + Math.round(rx * x) + 'px',
					marginTop : '-' + Math.round(ry * y) + 'px'
				});
				Cover.data = {
					x : x,
					y : y,
					w : w,
					h : h
				};
			});
		}
	},
	create : function(options) {
		Cover.options = $.extend(Cover.options, options);
		if (!isEmpty(Cover.options.cover)) {
			$("#" + Cover.options.source).html("<img src='" + Cover.options.cover + "?t=" + new Date().getTime() + "'>");
			$(Cover.options.previews).each(function() {
				$("#" + this.id).html("<img src='" + Cover.options.cover + "?t=" + new Date().getTime() + "'>");
			});
		}
		$("#" + Cover.options.source + " img").Jcrop({
			onChange : Cover.options.updatePreview,
			onSelect : Cover.options.updatePreview,
			aspectRatio : 4 / 3
		}, function() {
			var bounds = this.getBounds();
			Cover.width = bounds[0];
			Cover.height = bounds[1];
			Cover.api = this;
			var w = Cover.options.selector.width, h = Cover.options.selector.height, x = (Cover.width - w) / 2, y = (Cover.height - h) / 2;
			if (!isEmpty(Cover.options.cover)) {
				Cover.api.animateTo([x, y, x + w, y + h]);
			}
		});
	},
	load : function(cover) {
		if (Cover.api)
			Cover.api.destroy();
		Cover.create({
			cover : cover
		});
	}
};

/**
 加载文档信息
 */
Course.loadDocument = function(courseId) {
	if (!courseId) {
		courseId = $("#documentList").attr("courseId");
	}
	$("#documentList").load("/doc/list1.html?attr1=" + courseId);
}
/*
 * 课程评论
 */
Course.postComment = function() {
	$("#commentForm").postForm({
		after : function(request) {
			if (isEmpty(request.getParameter("content"))) {
				request.setParameter("content", $("#cs" + request.getParameter("courseScore")).text())
			}
		},
		success : function(data) {
			$.alert.show(data, function() {
				if (data.status > 0) {
					$("#commentPostDiv").slideUp();
					$("#commentAll").trigger("refresh")
				}
			});
		}
	});
}
/**
 * 保存课堂及课后练习
 */
Course.postExercise = function(form, type) {
	$(form).postForm(function(data) {
		$.alert.show(data, function() {
			if (data.status > 0) {
				$("#subjectExercise" + type).trigger("refresh");
			}
		});
	});
}
/*
 * 关闭课程
 */
Course.close = function(cid, type, $this) {
	Pop.show({
		id : "course_close",
		url : "/static/course.close.htm",
		template : false,
		css : {
			width : 350
		},
		afterShow : function($pop, options) {
			$pop.find("[node-type=course-close-ok]").one("click", function() {
				Pop.hide($pop);
				$.post("/course/manager/close.html", {
					id : cid,
					reason : $("#couse_close_reason").val()
				}, function(result) {
					$.alert.show(result, function() {
						if (result.status == 1) {
							$this.text("已关闭").unbind("click");
						}
					});
				})
			})
		}
	})
}
/*
 * 取消课程
 */
Course.cancle = function(cid, type, $this) {
	Pop.show({
		id : "course_cancle",
		url : "/static/course.cancle.htm",
		template : false,
		css : {
			width : 350
		},
		afterShow : function($pop, options) {
			$pop.find("[node-type=course-cancle-ok]").one("click", function() {
				Pop.hide($pop);
				$.post("/course/manager/cancle.html", {
					id : cid,
					reason : $("#couse_cancle_reason").val()
				}, function(result) {
					$.alert.show(result, function() {
						if (result.status == 1) {
							$this.text("已取消").unbind("click");
						}
					});
				})
			})
		}
	})
}
/**
 * 从土豆，优酷等视频网站获取视频信息
 */
Course.getVideo = function(obj) {
	var url = $("#url").val();
	/*非转发网站地址*/
	if (!url.is(":externalURL")) {
		return;
	}
	var $this = $(obj);
	$this.text("正在解析……").attr("disabled", true)
	$.post("/social/share/video.html", {
		url : url
	}, function(data) {
		if (data.status && data.status == 1) {
			//解析成功，绑定数据
			$this.text("重新获取").attr("disabled", false);
			var video = data.video;
			$("#videoName").val(video.title);
			$("#aim").val(video.title);
			$("#videoCover").val(video.cover);
			$("#coverImage").html("<img src='" + video.cover + "' width='300' height='225'/>");
			$("#videoDir").val(video.player);
			$("#intro").val(video.summary)
		} else {
			$this.text("很抱歉，目前暂不支持该地址解析").attr("disabled", false);
		}
	});
}

Course.localJoinCheck = function() {
	if (isEmpty($("#order_username").val())) {
		$.alert.warn("请输入您的姓名");
		return;
	}
	if (isEmpty($("#order_phone").val())) {
		$.alert.warn("请您输入电话号码");
		return;
	}
	$("#joinForm").submit();
}
School = window.School || {};
School.saveAddress = function() {
	var data;
	$("#addressForm").postForm({
		handleForm : function(request) {
			data = request;
			return true;
		},
		success : function(result) {
			if (result.status == 1) {
				$("#addressId").val(result.id);
				var text = data.getParameter("main") + "-" + data.getParameter("branch") + "，" + City.getLabel(data.getParameter("area")) + "，";
				text += data.getParameter("contact") + "/" + data.getParameter("phone");
				$("#address_text").text(text);
				$("#address_city").val(data.getParameter("area"))
				$("#address-button").trigger("pop-close");
			}
		}
	})
}

School.selectAddress = function(id) {
	var $tds = $("#address" + id + " td ");
	var text = $tds.eq(0).text() + "-" + $tds.eq(1).text() + "，" + $tds.eq(2).text() + "，" + $tds.eq(3).text();
	$("#addressId").val(id);
	$("#address_text").text(text);
	$("#address_city").val($tds.eq(2).find("[node-type='city-label']").attr("city"))
	$("#address-button").trigger("pop-close");
}

School.editAddress = function(id) {

}

School.deleteAddress = function(id) {
	$.post("/school/address.delete.html", {
		id : id
	}, function(data) {
		$("#address" + id).slideUp();
	})
}

