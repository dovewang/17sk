Show = {
	TEXT_MAX_LENGTH : 1000
};

Kiss.addEventListener({
	"show-delete" : function(obj){
		var $this=$(obj);
		$this.unbind("click").click(function(event) {
			var $this=$(this),sid=$this.attr("sid");
			Show.del($this, sid);
		});
	},
	"show-edit" : function(obj){
		var $this=$(obj);
		$this.unbind("click").click(function(event) {
			var $this=$(this),sid=$this.attr("sid");
			Show.del($this, sid);
		});
	}
});


Show.del = function(obj,sid){
    var options={effect:"mouse",trigger:obj};
	$.alert.confirm("确定要删除吗？", [function() {
		$.post("/show/del.html", {
			sid : sid
		}, function(data) {
			if (data.status > 0) {
				$.alert.show(data,function(){
					location.reload();
				});
			} else {
				$.alert.show(data,options);
			}
		});
	}],options);
}


Show.upload = function(obj) {
	if(isEmpty(obj.value)) {
		return;
	}
	if(obj.value.toLowerCase().is("\.(flv|f4v|mp4)$")) {
		$("#show_upload").hide();
		$("#uploadProgress").show();
		var $bar = $("#uploadProgress_bar");
		var progressId = window.setInterval(function() {
			$.get("/upload/status.html?flag=show", function(data) {
				$bar.css("width", data + "%").text(data + "%");
			})
		}, 1500);
		$("#showUploadForm").data("change", true).postForm(function(data) {
			window.clearInterval(progressId);
			$bar.css("width", "100%").text("上传成功!");
			/*转向表单填写页面*/
			window.setTimeout(function() {
				/*使用form方式安全提交，保证数据安全性*/
				$.formSubmit("/show/form.html", {
					t : 2,
					url : data.url,
					c : data.cover
				});
			}, 1500);
		});
	} else {
		$.alert.warn("为了提高您上传文件的的效率<br/>强烈建议您先将文件格式转换flv,f4v");
		this.value = "";
	}
}
/**
 * 发布秀秀
 */
Show.post = function() {
	$("#showForm").postForm({
		handleForm : function(request) {
			//年级和科目验证
			// var knowledge = Knowledge.get();
			// if(!knowledge.data) {
				// Alert.warn("请您选择课程分类！")
				// return false;
			// } else {
				// var kpath = knowledge.data.split(",");
				// var kn = {
					// grade : kpath[0] || 0,
					// subject : kpath[1] || 0,
					// k1 : kpath[2] || 0,
					// k2 : kpath[3] || 0,
					// k3 : kpath[4] || 0,
					// k4 : kpath[5] || 0,
					// k5 : kpath[6] || 0,
					// tag : knowledge.tag || ""
				// };
				// for(var i in kn) {
					// request.addParameter(i, kn[i]);
				// }
			// }
		},
		success : function(data) {
			$.alert.show(data, function() {
				if(data.status == 1) {
					location.href = "/user/index.html#!/show/index.html"
				}
			})
		}
	});
}
/**
 * 秀秀评论
 */
Show.comment = function() {
	var com = $("#comment_content").val();
	if(isEmpty(com)) {
		$.alert.info("评论内容不能为空");
		return;
	}
	if(com.length > Show.TEXT_MAX_LENGTH){
		$.alert.info("评论内容过长");
		return;
	}
	var $title = $("#show-title");
	var showId = $title.attr("showId");
	$.post("/show/comment.html", {
		id : showId,
		title : $title.text(),
		content : com
	}, function(data) {
		$.alert.show(data, function() {
			var $c = $("#commentTab");
			var $s = $c.find("span")
			if($s.length > 0) {
				$s.text($s.text() * 1 + 1);
			} else {
				$c.append("(<span>1</span>)");
			}
			$("#commentTab").trigger("refresh");
		});
	})
}
/***
 * 秀秀投票
 */
Show.vote = function(id, support) {
	if($.cookie("v_s" + id)) {
		$.alert.warn("您已经投过票了")
		return;
	}
	$.post("/show/vote.html", {
		id : id,
		support : support
	}, function(data) {
		$.alert.show(data)
		$.cookie("v_s" + id, $.now());
	});
}

Show.getVideo = function(obj) {
	var url = $("#showURL").val();
	var $this = $(obj);
	$this.text("正在解析……").attr("disabled", true)
	$.post("/social/share/video.html", {
		url : url
	}, function(data) {
		if(data.status && data.status == 0) {
			//解析失败
			$this.text(data.message).attr("disabled", false);
		} else {
			//解析成功，绑定数据
			$this.text("重新获取").attr("disabled", false);
			var video = data.video;
			$("#title").val(video.title);
			$("#showCover").val(video.cover);
			$("#imageCover").attr("src",video.cover);
			$("#showDir").val(video.player);
			$("#intro").val(video.summary)
		}
	});
}