;(function() {
	Mblog = {
		maxLength : window.Env.MBLOG_MAX_LENGTH || 140,
		refresh : function() {
			var len = $("#mblog_editor").val().trim().length;
			if (len > Mblog.maxLength) {
				$("#poster-count").html('请文明发言，已经超过<strong  style="color:red" >' + (len - Mblog.maxLength) + '</strong>字');
				$("#mblog-poster-button").attr("disabled", "disabled").addClass("disabled");
			} else {
				$("#poster-count").html('请文明发言，您还可以输入<strong>' + (Mblog.maxLength - len) + '</strong>字');
				$("#mblog-poster-button").attr("disabled", false).removeClass("disabled");
			}
			if (len == 0)
				$("#mblog-poster-button").attr("disabled", "disabled").addClass("disabled");
		}
	};

	$(function() {
		$("#mblog_editor").focus(function() {
			window.mblog_change = window.setInterval(Mblog.refresh, 1000);
		}).blur(function() {
			window.clearInterval(window.mblog_change)
		}).keydown(function(event) {
			var len = $(this).val().length;
			if (len == Mblog.maxLength && event.keyCode != 8) {
				event.preventDefault();
			}
		}).keyup(function(event) {
			$.cookie("mblog", this.value);
			Mblog.refresh();
		}).val($.cookie("mblog") || "");
		if ($("#mblog_editor").length > 0) {
			Mblog.refresh();
			var $pwrap = $("#poster-wrap");
			$pwrap.data("poster", "mblog-poster")
			$pwrap.find("a[action-type='poster']").click(function() {
				var $this = $(this);
				if (!$this.hasClass("active")) {
					var ps = $this.attr("poster"), p = $this.parent(), c = $pwrap.data("poster");
					p.find("a").removeClass("active");
					$this.addClass("active");
					trace(c)
					$("#" + c).hide();
					$("#" + ps).show();
					$pwrap.data("poster", ps);
				}
			})
		}
	});

	Kiss.addEventListener("feed-item", function(item) {
		var $item = $(item), feedid = $item.attr("feedid");
		$item.find("[action-type]").each(function() {
			var $this = $(this), at = $this.attr("action-type");
			switch(at) {
				case "feed-show-comment":
					$this.click(function() {
						Mblog.showComments(feedid);
					});
					break;
				case "feed-delete":
					$this.click(function(evt) {
						Mblog.del(this, feedid);
					});
					break;
				case "feed-favor":
					$this.click(function(event) {
						favorToggle(event, $this, feedid, COLLECT_TYPE.MBLOG);
					});
					break;
			}
		});
	});

	Mblog.loadAll = function(p, g) {
		if (!p)
			p = 1;
		if (!g)
			g = 0;
		$("#mblog_list").load("/m/list" + isEmpty(Env.GROUP_ID, 0) + "-0.html?page=" + p + "&g=" + g);
		$("#mblog_new_count").hide();
		window.__MBLOG_NEW_COUNT = 0;
	}
	/*
	 * 发布微博
	 *
	 */
	Mblog.post = function() {
		var $editor = $("#mblog_editor");
		var message = $editor.val();
		var media = $("#mblog_media").val() || "";
		if (isEmpty(message) || message.length > Mblog.maxLength) {
			$editor.addClass("error");
			$editor.fadeTo(300, 0.3).fadeTo(300, 1, function() {
				$editor.removeClass("error")
			});
			return;
		}
		$("#poster-button").attr("disabled", "disabled").addClass("disabled");
		$.post("/m/post.html", {
			message : message,
			media : media,
			gid : isEmpty(Mblog.groupid, 0)
		}, function(id) {
			if (id > 0) {
				$("#mblog_media_div").html("").hide();
				$("#mblog_media").val("");
				$.cookie("mblog", null);
				$editor.addClass("postsuccess").val("");
				Mblog.insert(id);
				setTimeout(function() {
					$editor.removeClass("postsuccess")
				}, 1000);
			} else {
				$.alert.show("发布失败");
			}
		});
	}
	/**
	 *插入最新发布的微博
	 */
	Mblog.insert = function(id) {
		/*有可能一条微博都未发，这里先删除placeholder-box*/
		var $page = $("#mblog_page_list");
		if($page.hasClass("placeholder-box")){
			$page.removeClass("placeholder-box").empty();
		}
		$.get("/m/new/item-" + id + ".html", function(html) {
			var item = $(html).hide();
			$page.prepend(item).reload();
			item.slideDown();
		});
	}
	/*
	 * 删除微博信息
	 */
	Mblog.del = function(obj, id) {
		var options = {
			effect : "mouse",
			trigger : $(obj)
		};
		$.alert.confirm("您确定要删除信息？", [
		function() {
			$.post("/m/delete.html", {
				id : id
			}, function(data) {
				$.alert.show(data, function() {
					if (data.status == 1) {
						$("#feed_" + id).slideUp();
					}
				},options);
			});
		}], options);
	}
	/**
	 * 转发微博
	 */
	Mblog.forward = function(id) {

	}
	/*
	 * 上传图片
	 */
	Mblog.uploadImage = function(obj) {
		$("#imageForm").data("change", true).postForm(function(data) {
			 $(obj).popover({id:"mblog_media_div",placement:"bottom",trigger:"static",content:"<div style='width:140px;'><img src='" + data.msg + "'  height=\"120\" width=\"120\" /></div><div class='text-right'><a href='javascript:;'  onclick='Mblog.deleteImage()'>删除</a></div>"})
			$("#mblog_media").val(data.msg);
			/*修复页面上传相同的图片*/
			$("#imageForm")[0].reset();
		});
	}
	/**
	 * 删除上传的图片
	 */
	Mblog.deleteImage = function() {
		$("#mblog_media_div").html("").hide();
		$("#mblog_media").val("");
	}
	/**
	 * 图片查看
	 */
	Mblog.showImage = function(id, show) {
		if (show) {
			$("#media_s_" + id).hide();
			$("#media_" + id).show();
		} else {
			$("#media_s_" + id).show();
			$("#media_" + id).hide();
		}
	}

	Mblog.rotateLeft = function(id) {
		$("#img_container" + id).rotateLeft();
	}
	Mblog.rotateRight = function(id) {
		$("#img_container" + id).rotateRight();
	}
	/*
	 * 显示微博评论信息
	 */
	Mblog.showComments = function(id) {
		var $feedc = $("#feedc_" + id);
		if ($feedc.attr("loaded") != "true") {
			$feedc.load("/m/comments.html?id=" + id, function() {
				$feedc.show();
				$feedc.attr("loaded", "true");
			});
		} else {
			$feedc.toggle();
		}
	}
	/*
	 * 删除微博评论信息
	 */
	Mblog.delComment = function(obj, id, mid) {
		var options = {
			effect : "mouse",
			trigger : $(obj)
		};
		$.alert.confirm("您确定要删除信息？", [
		function() {
			$.post("/m/delComment.html", {
				id : id,
				mid : mid
			}, function(data) {
				$.alert.show(data, function() {
					if (data.status == 1) {
						$("#cnums" + mid).text(($("#cnums" + mid).text() || 0) * 1 - 1);
						$("#comment" + id).slideUp();
					}
				}, options);
			});
		}], options);
	}
	/**
	 * 发布微博评论
	 */
	Mblog.postComments = function(id) {
		var com = $("#newcom_" + id).val();
		if (isEmpty(com)) {
			return;
		} else {
			$.post("/m/comment.html", {
				id : id,
				text : com
			}, function(data) {
				var $numsSpan = $("#cnums" + id);
				if ($numsSpan.length == 0) {
					$("#comments_link" + id).append("(<i id='cnums" + id + "'></i>)");
					$numsSpan = $("#cnums" + id);
				}
				$numsSpan.text(($numsSpan.text() || 0) * 1 + 1);
				$("#feedc_" + id).attr("loaded", false);
				Mblog.showComments(id);
			});
		}
	}
	/**
	 * 回复默认的评论
	 */
	Mblog.replyComment = function(id, mid, usercard) {
		var name = $("#comment" + id + " a[usercard='" + usercard + "']").text();
		if (!$("#newcom_" + mid).val().startsWith("TO" + name + "："))
			$("#newcom_" + mid).val("TO" + name + "：" + $("#newcom_" + mid).val())
	}
})(window);
