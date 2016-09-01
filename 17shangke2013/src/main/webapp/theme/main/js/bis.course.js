/**
 *
 * 功能描述：课程相关<br>
 *
 * 日 期：2011-8-2 13:21:54<br>
 *
 * 作 者：Dreamall<br>
 *
 * 版权所有：©mosai<br>
 *
 * 版 本 ：v0.7<br>
 *
 * 联系方式：dream-all@163.com<br>
 *
 * 修改备注：{修改人|修改时间}<br>
 *
 */
Course = {
	TYPE : {
		ONLINE : 1, //在线课程
		VIDEO : 2, //视频课程
		LOCAL : 3, //视频课程
		FORWARD : 4//转发视频
	}
};
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
					case Course.TYPE.ONLINE:
						//在线课程
						break;
					case Course.TYPE.VIDEO:
						//视频课程
						break;
					case Course.TYPE.LOCAL:
						//线下课程
						//检查优惠价格
						var newPrice = $("#newPrice").val();
						if (window.isEmpty(newPrice)) {
							$.alert.warn("请输入优惠价格");
							return;
						}
						data.newprice = newPrice;
						break;
					case Course.TYPE.FORWARD:
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
					case "course-publish":
						Course.publish(cid);
						break;
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
			location.href = "/course/manager/{" + cid + "}/edit.html"
			break;
		case "courseware":
			if (back || Course.saveCourse())
				location.href ="/course/manager/{" + cid + "}/courseware.html";
			break;
		case "exercise":
			location.href = "/course/manager/{" + cid + "}/exercise.html"
			break;
		case "publish":
			$.post("/course/manager/{" + cid + "}/publish.html", function(result) {
				$.alert.show(result);
			})
			break;
	}
}
/**
 * 保存课程
 */
Course.saveCourse = function(edit, local) {
	$("#courseForm").postForm(function(result) {
		$.alert.show(result, function() {
			if (result.status == 1) {
				if (edit)
					location.href = "/course/manager/" + result.courseId + "/edit.html";
			}
		})
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
				$.formSubmit("/course/manager/2/create.html", {
					url : data.url,
					c : data.cover
				});
			}, 1500);
		});
	} else {
		$.alert.warn("为了提高您上传文件的的效率<br/>强烈建议您先将文件格式转换flv,f4v,mp4");
		obj.value = "";
	}
}
/*
 * 保存视频课程
 */
Course.saveVideo = function(edit) {
	if (!$.form.isChange($("#videoForm"))) {
		return;
	}
	$("#videoForm").postForm({
		success : function(result) {
				$.alert.show(result, function() {
					if (result.status == 1&&edit) {
						location.href =  "/course/manager/" + result.courseId + "/edit.html";
					}
				});
		}
	});
}
/**
 * 保存线下课程
 */
Course.saveLocal = function(edit) {
	Course.saveCourse(edit, true);
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
/*选择方式或普通上传放肆*/
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
/*编辑模式的图片,使用美图秀秀*/
Course.editCoverBefore=function(data,id){
	var size = data. size;
  if(size > 2 * 1024 * 1024)
  { 
    alert("图片不能超过2M"); 
    return false; 
  }
  
  if(data.height!=225&&data.width!=300){
	alert("请您先将图片裁剪为300*225");  
	return  false; 
  }
  return true;
}
Course.editCoverAfter=function(message){
	message = eval("(" + message + ")");
	if (!message.err || message.err == "") {
		$("#courseCoverInput").val(message.msg);
		$("#courseCoverDiv").html("<img src='" + message.msg + "'/>");
		Pop.hide($("#cover-upload-pop"));
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
				if (data.status == 1) {
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
/**
 * 关闭课程
 * @param  cid {Number} 课程编号
 * @param type {Number} 课程类型
 * @param $this 当前点击的链接对象
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
/**
 * 取消课程
 *  *@param  cid {Number} 课程编号
 * @param type {Number} 课程类型
 * @param $this 当前点击的链接对象
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
/**
 *课程发布
 * @param {Number} cid 课程编号
 */
Course.publish = function(cid) {
	$.post("/course/manager/" + cid + "/publish.html", function(result) {
		$.alert.show(result);
	});
}
/*学校相关信息处理*/
School = window.School || {};
/**
 * 学校选择，该方法只限管理员使用，允许发布线下课程的时候选择学校
 *  @param {Object}选择的对象
 */
School.select = function(obj) {
	var $obj = $(obj), sid = $obj.attr("sid"), sname = $obj.attr("sname");
	$("#schoolId").val(sid);
	$("#schoolName").val(sname);
	$obj.trigger("pop-close");
	/*当学校的值发生变化时需要重置学校上课地址选择*/
	$("#addressId").val("");
	$("#address_text").val("请您选择上课地址");
	$("#address_city").val("");
}
/**
 *学校地址选择
 *
 */
School.viewAddress = function() {
	Pop.show({
		id : "school-select-pop",
		template : false,
		url : "/school/address.html?id=" + $("#schoolId").val(),
		css : {
			width : "700px"
		}
	});
}
/**
 *保存上课地址
 */
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
				$("#address_text").val(text);
				$("#address_city").val(data.getParameter("area"))
				Pop.hide($("#school-select-pop"));
			}
		}
	})
}
/**
 *选择一个上课地址
 * @param {Number} id
 */
School.selectAddress = function(id) {
	var $tds = $("#address" + id + " td ");
	var text = $tds.eq(0).text() + "-" + $tds.eq(1).text() + "，" + $tds.eq(2).text() + "，" + $tds.eq(3).text();
	$("#addressId").val(id);
	$("#address_text").val(text);
	$("#address_city").val($tds.eq(2).find("[node-type='city-label']").attr("city"))
	Pop.hide($("#school-select-pop"));
}
/**
 *删除一个上课地址
 * @param {Number} id
 */
School.deleteAddress = function(id) {
	$.post("/school/address.delete.html", {
		id : id
	}, function(data) {
		$("#address" + id).slideUp();
	})
}

