/**
 *
 * 功能描述：问题模块<br>
 *
 * 日 期：2011-7-21 13:59:54<br>
 *
 * 作 者：Spirit<br>
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
Question = {
	MAX_LENGTH : window.QUESTION_MAX_LENGTH || 100,
	TEXT_MAX_LENGTH : 20000,
	refresh : function() {
		var len = $("#question_editor").val().trim().length;
		if (len > Question.MAX_LENGTH) {
			$("#poster-count").html('请文明发言，已经超过<strong  style="color:red" >' + (len - Question.MAX_LENGTH) + '</strong>字');
			$("#question-poster-button").attr("disabled", "disabled").addClass("disabled");
		} else {
			$("#poster-count").html('请文明发言，您还可以输入<strong>' + (Question.MAX_LENGTH - len) + '</strong>字');
			$("#question-poster-button").attr("disabled", false).removeClass("disabled");
		}
		if (len == 0)
			$("#question-poster-button").attr("disabled", "disabled").addClass("disabled");
	}
};

$(function() {
	$("#question_editor").keydown(function(event) {
		var len = $(this).val().trim().length;
		if (len == Question.MAX_LENGTH && event.keyCode != 8) {
			event.preventDefault();
		}
		if (len > 0) {
			Question.clearRelatedQuestion();
		}
	}).keyup(function(event) {
		$.cookie("question", this.value);
		var len = $(this).val().trim().length;
		Question.refresh();
		/*开始自动检索*/
		if (len > 0 && len < Question.MAX_LENGTH) {
			Question.seachRelatedQuestion(this.value);
		}
	}).val($.cookie("question") || "");
	if ($("#question_editor").length > 0) {
		Question.refresh();
	}
	$("#relatedQuestionDiv").load("/question-related.html?c=${question.grade},${question.kind},1,0");
})

Kiss.addEventListener({
	"question-extend" : function(obj) {
		$(obj).click(function() {
			$("#question-extend").slideToggle();
		});
	},
	"help-extend" : function(obj) {
		var $this = $(obj);
		$(obj).click(function() {
			Question.helpGroup($this);
		});
	},
	"question-delete" : function(obj) {
		var $this = $(obj);
		$this.unbind("click").click(function(event) {
			var $this = $(this), qid = $this.attr("qid");
			Question.del($this, qid);
		});
	},
	"question-close" : function(obj) {
		var $this = $(obj);
		$this.click(function(event) {
			var $this = $(this), qid = $this.attr("qid");
			Question.close($this, qid);
		});
	},
	"question-favor" : function(obj) {
		var $this = $(obj);
		$this.click(function(event) {
			var $this = $(this), qid = $this.attr("qid");
			favorToggle(event, $this, qid, COLLECT_TYPE.QUESTION);
		});
	},
	"question-order" : function(obj) {
		var $this = $(obj), qid = $this.attr("qid"), order = $this.attr("order");
		$(obj).click(function() {
			Question.answerOrder(qid, order, $this);
		});
	}
});

/**
 *问题主页的答案列表排序
 *
 */
Question.answerOrder = function(qid, order, obj) {
	$("#question-answer-list").load("/qitem-" + qid + ".html?order=" + order + " #question-answer-list", {}, function() {
		$(".answer-header .active").removeClass("active");
		obj.addClass("active");
	});
}
/**
 *自动检索相关问题
 */
Question.searchQuestion = function() {
	$.get(Env.SITE_DOMAIN + "/question.autosearch.html", {
		q : $("#question_editor").val(),
		p : 1,
		s : 3/*只显3条相关记录*/
	}, function(html) {
		$("#searchQuestion").html(html).reload();
		$("#searchQuestion").show();
	})
}
var timeid = 0;
Question.seachRelatedQuestion = function(value) {
	if (value && value.length > 4) {
		timeid = window.setTimeout(Question.searchQuestion, 5000)
	} else {
		$("#searchQuestion").hide();
	}
}
Question.clearRelatedQuestion = function() {
	window.clearTimeout(timeid)
}
/**
 * 提问
 */
Question.post = function() {
	var $editor = $("#question_editor");
	var text = $editor.val();
	var intro = $("#question-content").val() || "";
	if (isEmpty(text) || text.length > Question.MAX_LENGTH) {
		$editor.addClass("error");
		$editor.fadeTo(300, 0.3).fadeTo(300, 1, function() {
			$editor.removeClass("error")
		});
		return;
	}
	if ($("#question-content")[0].editor.count("text") > Question.TEXT_MAX_LENGTH) {
		$.alert.info("补充内容过长!");
		return;
	}
	var knowledge = Knowledge.get() || {
		data : "0,0,0,0,0,0",
		text : ""
	};
	var kpath = (knowledge.data || "0,0,0,0,0,0").split(",");
	var helpers = [];
	$('#expert-user-box li.selected').each(function() {
		helpers.push($(this).attr("uid"));
	});
	$.post(Env.SITE_DOMAIN + "/question/post.html", {
		title : text,
		intro : intro,
		price : isEmpty($("#question-price").val(), 0),
		grade : kpath[0],
		kind : kpath[1] || 0,
		k1 : kpath[2] || 0,
		k2 : kpath[3] || 0,
		k3 : kpath[4] || 0,
		k4 : kpath[5] || 0,
		k5 : kpath[6] || 0,
		tag : knowledge.tag || "",
		anonymous : $("#question-anonymous").attr("checked") ? 1 : 0,
		urgent : $("#question-urgent").attr("checked") ? 1 : 0,
		helpers:helpers.join(",")
	}, function(data) {
		if (data.status == CONFIG.__RESULT.SUCCESS) {
			$editor.addClass("postsuccess").val("");
			$("#question-content").val("");
			$("#question-extend").slideUp();
			$("#help-extend").slideUp();
			$("#searchQuestion").hide();
			if ($("#question-poster").data("reload")) {
				setTimeout(function() {
					$editor.removeClass("postsuccess");
					location.reload();
				}, 1000)
			} else {
				$("#question-tab").trigger("refresh");
				setTimeout(function() {
					$editor.removeClass("postsuccess");
				}, 1000)
			}
			$(".images li.selected").removeClass('selected');
			$.cookie("question", null);
			Question.newQuestion();
			//刷新问题列表
			Mblog.insetNesestToPage();
			//刷新微博列表
		} else if (data.status == CONFIG.__RESULT.WARN) {
			$.alert.confirm("您的账户余额不足，是否立即充值？", function() {
				location.href = Env.SECURE_DOMAIN + "/account/recharge.html";
			});
		} else {
			$.alert.show(data);
		}
	})
}
/**
 *插入最新发布的问题
 */
Question.newQuestion = function() {
	var $list = $("#question_page_list");
	if ($list.length>0) {
		$.get("/question/getNewstQuestion.html", function(html) {
			$list.prepend(html).reload();
		});
	}
}
/**
 * 求助团体
 */
Question.helpGroup = function(obj) {
	$("#help-extend").slideToggle();
}
/**快速解答，使用文字方式解答
 * @param qid
 * @param  isItem  是否是详细页面
 * */
Question.showFastAnswer = function(qid, price, isItem) {
	var $q = $("#qitem" + qid);
	if ($q.data("load") != "true") {
		var $div = $("<div class='faster-answer box' id='qfast_answer_" + qid + "'  item=" + ( isItem ? "true" : "false") + " style='display:none'></div>");
		var html = [];
		if (Env.FEE_MODLE)
			html.push('<div>请输入您的解答价格：<input type="number" name="price" id="qfast_answer_price' + qid + '" value="' + price + '" min="0" max="' + price + '"/></div>');
		html.push('<div><textarea name="intro" style="width:100%;height:120px;"  id="fastanswer_' + qid + '"  placeholder="输入详细内容" node-type="editor" ></textarea></div>');
		html.push('<div class="control"><button class="button large primary" onclick="Question.fastAnswer(' + qid + ');">回答</button></div>');
		$div.html(html.join(""));
		$q.append($div);
		$q.data("load", "true");
		$div.reload().slideDown();
	} else {
		$("#qfast_answer_" + qid).slideToggle();
	}
}
/**
 * 快速解答
 * @param qid
 */
Question.fastAnswer = function(qid) {
	var answer = $("#fastanswer_" + qid).val().trim();
	var title = $("#questiontitle_" + qid).text();
	if (answer.is(":htmlBlank")) {
		$.alert.info("请输入答案内容", function() {
			$("#fastanswer_" + qid).focus();
		});
		return;
	}
	if ($("#fastanswer_" + qid)[0].editor.count("text") > Question.TEXT_MAX_LENGTH) {
		$.alert.info("回答内容过长!");
		return;
	}
	$.post("/question/fastAnswer.html", {
		questionId : qid,
		content : answer,
		title : title,
		price : $("#qfast_answer_price" + qid).val()
	}, function(data) {
		$.alert.show(data, function() {
			$("#qfast_answer_" + qid).slideUp();
			var isItem = $("#qitem" + qid).attr("item");
			if (isItem == "true") {
				location.reload();
				return;
			}
			var $c = $("#qanswer_count" + qid);
			var $s = $c.find("em")
			if ($s.length > 0) {
				$s.text($s.text() * 1 + 1);
			} else {
				$c.append("(<em>1</em>)");
			}
		})
	})
}
/**
 * 显示问题编辑界面
 */
Question.edit = function(qid) {
	var $q = $("#qitem" + qid);
	if ($q.data("load") != "true") {
		var $div = $("<div  class='faster-answer box' id='qide_" + qid + "'  style='display:none'></div>");
		$.get("/question/edit.html?questionId=" + qid, function(html) {
			$div.html(html);
			$q.append($div).reload();
			$q.data("load", "true");
			$div.slideDown();
		});
	} else {
		$("#qide_" + qid).slideToggle();
	}
}
/**
 * 更新问题
 */
Question.update = function(qid) {
	var $intro = $("#qintro_" + qid);
	var text = $intro.val();
	if (isEmpty(text)) {
		$.alert.info("编辑内容不可以为空!");
		return;
	}
	if (text.length > Question.TEXT_MAX_LENGTH) {
		$.alert.info("编辑内容内容过长!");
		return;
	}
	$.post("/question/update.html", {
		questionId : qid,
		title : $("#qtitle_" + qid).val(),
		intro : text,
		price : $("#qprice_" + qid).val() || 0
	}, function(data) {
		$.alert.show(data, function() {
			if (data.status > 0) {
				$("#qide_" + qid).slideUp();
			}
		});
	})
}
/*
 *删除问题
 *@param {Node} obj 点击的节点
 *@param {Number} qid 问题编号
 * close {Boolean}为true代表要关闭当前窗口
 */
Question.del = function(obj, qid, close) {
	var options = {
		effect : "mouse",
		trigger : $(obj)
	};
	$.alert.confirm("确定要删除吗？", [
	function() {
		$.post("/question/del.html", {
			questionId : qid
		}, function(data) {
				$.alert.show(data,function(){
					if (data.status == 1) {
					$("#qitem" + qid).slideUp("slow", function() {
					if (close)
						window.close();
				    });
					}
				},options);
		});
	}], options);
}
/**
 *关闭问题
 *@param qid 问题编号
 */
Question.close = function(obj, qid) {
	var options = {
		effect : "mouse",
		trigger : $(obj)
	};
	$.alert.confirm("确定要关闭问题吗？", [
	function() {
		$.post("/question/close.html", {
			questionId : qid
		}, function(data) {
			$.alert.show(data,function(){
			    if (data.status == 1) {
					$(obj).hide();
					$("#question_class_" + qid).html("<i class='status3'></i>&nbsp;已关闭");
				}
			}, options);
		});
	}], options);
}
/**
 * @param qid 问题编号
 * @param aid答案编号
 */
Question.selectAnswer = function(obj, qid, aid, type) {
	$.post("/question/selectAnswer.html", {
		qid : qid,
		aid : aid,
		type : type,
		title : $("#questiontitle_" + qid).text()
	}, function(data) {
		if (data.status > 0) {
			if (data.orderId) {

			} else {
				if (data.status == 1)
					$(obj).text("已采纳").attr("onclick", "");
				//设置onclick事件为null
				var $numsSpan = $("#cnums5" + aid);
				$numsSpan.text(($numsSpan.text() || 0) * 1 + 1);
			}
		} else {
			$.alert.show(data);
		}
	});
}

Question.fullscreen = function(obj) {
	var $this = $(obj);
	var qid = $this.attr("qid");
	var $item = $("#qitem" + qid);
	var full = $this.attr("full");
	if (full == "true") {
		var h = $item.data("height");
		var w = $item.data("width");
		$item.removeClass('fullscreen').height(h).width(w);
		$this.text("全屏阅读").attr("full", "false");
		$item.removeAttr("style");
	} else {
		var h = $item.data("height") || $item.height();
		var w = $item.data("width") || $item.width();
		$item.data("height", h);
		$item.data("width", w);
		$item.addClass('fullscreen').height($(document.body).height() - 35).width($(document.body).width() - 50);
		$this.text("正常阅读").attr("full", "true");
	}
}
$(function() {
	$(window).resize(function() {
		Question.fullscreen($("#fullscreen"), $("#fullscreen").attr("full"));
	})
})

Question.viewAnswer = function(aid, fee) {
	var title = $("#question_title").html();
	var url = encodeURIComponent(Env.SITE_DOMAIN + '/question/answer.html?answerId=' + aid + '&title=' + title);
	$("#auth-box" + aid).html('<iframe src="' + Env.SECURE_DOMAIN + '/account/authz.html?fee=' + fee + '&url=' + url + '" scrolling="no" frameborder="0" width="100%" height="50px"></iframe>').hide();
	setTimeout(function() {
		$("#noview" + aid).slideUp();
		$("#auth-box" + aid).slideDown();
	}, 500)
}

Question.loadAnswer = function(id, html) {
	$("#auth-box" + id).remove();
	$("#noview" + id).replaceWith(html);
	$("#answer_show_" + id).css("display", "block");
}
Question.search = function(keyword) {
	var $active = $("#question_filter .tab-subheader li.active a");
	var href = $active.attr("href");
	href = href.substring(href.indexOf("#!") + 2);
	var start = href.indexOf("q=");
	var end = -1;
	if (start != -1) {
		end = href.substring(start).indexOf("&");
		href = href.substring(0, start) + "q=" + keyword + href.substring(start + 3, end);
	} else {
		href += "&q=" + keyword;
	}
	$active.attr("href", "#!" + href);
	$active.trigger("refresh");
}
/*
 * 只检索带答案的数据
 */
Question.answerSearch = function() {
	var value = $("#question-search-input").val()
	if (isEmpty(value))
		return;
	$("#search-tab").attr("remote", "true").attr("href", "#!/question/list.html?c=0,0,2,0&q=" + value).attr("link", "#!/question/list.html").trigger("refresh")
}
/*=========用户问题评论模块=================*/

Answer = {};

/**
 * 绑定问题评论的操作方法
 */
Kiss.addEventListener("answer-item", function(item) {
	var $item = $(item), answerId = $item.attr("answerid"), answer = $item.attr("answer"), questionId = $item.attr("questionId");
	$item.find("[action-type]").each(function() {
		var $this = $(this), at = $this.attr("action-type");
		switch(at) {
			case "answer-show-comment":
				$this.click(function() {
					Answer.showComments(answerId, answer);
				});
				break;
			case "answer-delete":
				$this.click(function(evt) {
					Answer.del(this, questionId, answerId, answer);
				});
				break;
			case "answer-edit":
				$this.click(function(event) {
					Answer.edit(answerId);
				});
				break;
		}
	});
});

/*
 * 显示问题评论信息
 */
Answer.showComments = function(id, answer) {
	var $answerc = $("#answer_view_" + id);
	if ($answerc.attr("loaded") != "true") {
		$answerc.load("/answer/loadAnswerComment.html?id=" + id + "&answer=" + answer, function() {
			$answerc.show();
			$answerc.attr("loaded", "true");
		});
	} else {
		$answerc.toggle();
	}
}
/**
 * 发布问题答案评论
 * @param id 答案ID
 * @param score 答案分数
 */
Answer.postComments = function(id) {
	var com = $("#newcom_" + id).val();
	var answer = $("#commentForm" + id).attr("answer");
	var score = $("input[name='anwerScore" + id + "']:checked").val();
	var $numsSpan = null;
	if (isEmpty(score)) {
		$.alert.warn("请选择选项!");
		return;
	}
	if (com.length > Question.MAX_LENGTH) {
		$.alert.info("评论内容过长!");
		return;
	}
	if (isEmpty(com)) {
		switch (score) {
			case "-1" :
				com = "作用不大";
				break;
			case "0" :
				com = "有用";
				break;
			case "1" :
				com = "一般";
				break;
			case "2" :
				com = "非常有用";
				break;
		}
	}
	$.post("/answer/answerComment.html", {
		aid : id,
		text : com,
		score : score
	}, function(data) {
		$.alert.show(data, function() {
			if (data.status > 0) {
				switch (score) {
					case "-1" :
						$numsSpan = $("#cnums4" + id);
						break;
					case "0" :
						$numsSpan = $("#cnums3" + id);
						break;
					case "1" :
						$numsSpan = $("#cnums2" + id);
						break;
					case "2" :
						$numsSpan = $("#cnums1" + id);
						break;
				}
				$numsSpan.text(($numsSpan.text() || 0) * 1 + 1);
				$("#answer_view_" + id).attr("loaded", false);
				Answer.showComments(id, answer);
			}
		});
	});
}
/**
 * 回复答案的评论
 */
Answer.replyComment = function(id, mid, usercard) {
	var name = $("#answercomment" + id + " a[usercard='" + usercard + "']").text();
	if (!$("#newcom_" + mid).val().startsWith("TO" + name + "："))
		$("#newcom_" + mid).val("TO " + name + "：" + $("#newcom_" + mid).val())
}
/*
 * 删除答案信息
 */
Answer.del = function(obj, questionId, answerId, answer) {
	var options = {
		effect : "mouse",
		trigger : $(obj)
	};
	$.alert.confirm("您确定要删除信息？", [
	function() {
		$.post("/question/answer.delete.html", {
			qid : questionId,
			answer : answer,
			id : answerId
		}, function(data) {
			if (data.status == CONFIG.__RESULT.SUCCESS) {
				$.alert.success(data.message, options);
				$("#answer-item-" + answerId).slideUp();
			} else {
				$.alert.show(data, options);
			}
		});
	}], options);
}
/**
 * 显示答案编辑界面
 */
Answer.edit = function(aid) {
	var memocount = $("#cnums5" + aid).text();
	if (memocount > 0) {
		$.alert.warn("答案已被采纳，不允许再编辑!");
		return;
	}
	var $a = $("#answer-item-" + aid);
	if ($a.data("load") != "true") {
		var $div = $("<div id='aide_" + aid + "'  style='display:none'></div>");
		$div.load("/question/editAnswer.html?answerId=" + aid, function() {
			$a.append($div);
			//追加一个div对象
			$a.data("load", "true");
			$div.slideDown();
		});
	} else {
		$("#aide_" + aid).slideToggle();
	}
}
/**
 * 更新答案
 */
Answer.update = function(aid) {
	var content = $("#aintro_" + aid).val();
	$.post("/question/updateAnswer.html", {
		aid : aid,
		content : content
	}, function(data) {
		$.alert.show(data, function() {
			$("#answer-item-" + aid + " .viewed").html(content);
			if (data.status > 0) {
				$("#aide_" + aid).slideUp();
			}
		});
	})
}