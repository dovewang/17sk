/**
 *
 * 功能描述：问题模块<br>
 *
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
	MAX_LENGTH:window.QUESTION_MAX_LENGTH || 100,
	TEXT_MAX_LENGTH : 20000,
	refresh:function(){
		var len = $("#publish_editor").val().trim().length;
		if(len > Question.MAX_LENGTH) {
			$("#feedback-prompt").html('请文明发言，已经超过<strong  style="color:red" >' + (len - Question.MAX_LENGTH) + '</strong>字');
			$("#question_post_btn").attr("disabled","disabled").addClass("disabled");
		} else {
			$("#feedback-prompt").html('请文明发言，您还可以输入<strong>' + (Question.MAX_LENGTH- len) + '</strong>字');
			 $("#question_post_btn").attr("disabled",false).removeClass("disabled");
		}
		if(len==0)$("#question_post_btn").attr("disabled","disabled").addClass("disabled");
	}
};

$(function() {
	$("#publish_editor").keydown(function(event) {
		var len = $(this).val().length;
		if(len == Question.MAX_LENGTH && event.keyCode != 8) {
			event.preventDefault();
		}
	}).keyup(function(event) {
		Question.refresh();
	});
})

/**
 * 绑定问题的操作方法
 */
Kiss.addEventListener({
	"question-delete" : function(obj) {
		var $this = $(obj), qid = $(obj).attr("qid");
		$this.unbind("click").click(function() {
			Question.del(obj, qid);
		});
	},
	"help-extend" : function(obj) {
		var $this = $(obj);
		$(obj).click(function() {
			Question.helpGroup($this);
		});
	},
	"question-close" : function(obj) {
		var $this = $(obj), qid = $(obj).attr("qid");
		$this.click(function() {
			Question.close(obj, qid);
		});
	},
	"question-favor" : function(obj) {
		var $this = $(obj), qid = $(obj).attr("qid");
		$this.click(function(){
			favorToggle(event, $this, qid, COLLECT_TYPE.QUESTION);
		});
	}
});


/**
 * 提问
 */
Question.post = function() {
	var $editor = $("#publish_editor");
	var text = $editor.val();
	if(isEmpty(text) || text.length > Question.MAX_LENGTH) {
		$editor.addClass("error");
		$editor.fadeTo(300, 0.3).fadeTo(300, 1, function() {
			$editor.removeClass("error")
		});
		return;
	}
	var intro = $("#intro").val()||"";
	if (intro.length > Question.TEXT_MAX_LENGTH) {
		$.alert.info("补充内容过长!");
		return;
	}
	var knowledge = Knowledge.get() || {
		data : "0,0,0,0,0,0",
		text : ""
	};
	var kpath = (knowledge.data || "0,0,0,0,0,0").split(",");
	var chk_value =[];    
	$('div[id=expert-user-box] li[class=selected]').each(function(){    
	   chk_value.push($(this).attr("uid"));  
	});
	$.post(Env.SITE_DOMAIN + "/question/post.html?atUser="+chk_value, {
		title : text,
		intro : intro,
		price : 0,
		grade : kpath[0],
		kind : kpath[1] || 0,
		k1 : kpath[2] || 0,
		k2 : kpath[3] || 0,
		k3 : kpath[4] || 0,
		k4 : kpath[5] || 0,
		k5 : kpath[6] || 0,
		tag : knowledge.tag || "",
		anonymous : 0
	}, function(data) {
		if(data.status > 0) {
			$editor.addClass("postsuccess").val("");
			$("#intro").val("");
			$("#intro").hide();
			setTimeout(function() {
				$editor.removeClass("postsuccess");
				$(".images li.selected").removeClass('selected');
				location.reload();
			}, 1000);
		} else {
			Alert.show(data);
		}
	})
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
Question.showFastAnswer = function(qid, isItem) {
	var $q = $("#qitem" + qid);
	if($q.data("load") != "true") {
		var $div = $("<div class='box' id='qfast_answer_" + qid + "'  item=" + ( isItem ? "true" : "false") + " style='display:none'></div>");
		var html = [];
		html.push('<textarea name="intro" style="width:100%;height:120px;"  id="fastanswer_' + qid + '"  placeholder="输入详细内容" class="editor" ></textarea>');
		html.push('<div style="padding:8px;"><button class="button green small float-right" onclick="Question.fastAnswer(' + qid + ');">回答</button></div>');
		$div.html(html.join(""));
		$q.append($div);
		$q.data("load", "true");
		$div.find(".editor").xheditor(CONFIG.__EDITOR_CONFIG);
		$div.slideDown();
	} else {
		$("#qfast_answer_" + qid).slideToggle();
	}
}
/**
 * 快速解答
 * @param qid
 */
Question.fastAnswer = function(qid) {
	var answer = $("#fastanswer_" + qid).val();
	var title = $("#questiontitle_" + qid).text();
	if(answer.is(":htmlBlank")) {
		$.alert.info("请输入答案内容", function() {
			$("#fastanswer_" + qid).focus();
		});
		return;
	}
	if (answer.length > Question.TEXT_MAX_LENGTH) {
		$.alert.info("回答内容过长!");
		return;
	}
	$.post("/question/fastAnswer.html", {
		questionId : qid,
		title : title,
		price : $("#qfast_answer_price" + qid).val(),
		content : answer
	}, function(data) {
		$.alert.show(data, function() {
			var isItem = $("#qfast_answer_" + qid).slideUp().attr("item");
			if(isItem == "true") {
				location.reload();
				return;
			}
			var $c = $("#qanswer_count" + qid);
			var $s = $c.find("i")
			if($s.length > 0) {
				$s.text($s.text() * 1 + 1);
			} else {
				$c.append("(<i>1</i>)");
			}
		})
	})
}
/*=========用户问题编辑=================*/
/**
 * 显示问题编辑界面
 */
Question.edit = function(qid) {
	var $q = $("#qitem" + qid);
	if($q.data("load") != "true") {
		var $div = $("<div id='qide_" + qid + "'  style='display:none'></div>");
		$div.load("/question/edit.html?questionId=" + qid, function() {
			$q.append($div);
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
	if(isEmpty(text)){
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
			if(data.status > 0) {
				$("#qide_" + qid).slideUp();
			}
		});
	})
}
/*
 *删除问题
 *@param qid 问题编号
 */
Question.del = function(obj, qid) {
	var options={effect:"mouse",trigger:$(obj)};
	$.alert.confirm("确定要删除吗？", [function() {
		$.post("/question/del.html", {
			questionId : qid
		}, function(data) {
			if (data.status > 0) {
				$.alert.success(data.message,options);
				$("#qitem" + qid).slideUp();
			} else {
				$.alert.show(data,options);
			}
		});
	}],options);
}
/**
 *关闭问题
 *@param qid 问题编号
 */
Question.close = function(obj, qid) {
	var options={effect:"mouse",trigger:$(obj)};
	$.alert.confirm("确定要关闭问题吗？", [function() {
		$.post("/question/close.html", {
			questionId : qid
		}, function(data) {
			if (data.status > 0) {
				$.alert.success(data.message,options);
			} else {
				$.alert.show(data,options);
			}
		});
	}],options);
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
		title : $("#questiontitle_"+qid).text()
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
