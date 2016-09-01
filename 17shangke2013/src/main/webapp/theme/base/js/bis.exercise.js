Exercise = {
	EN : ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'],
	END : "&#8718;"/*填空题答案分分割副*/
};

Exercise.search = function() {
	var $active = $("#eTab  li.active");
	var $href = $active.find("a");
	var href = $href.attr("href");
	if (href) {
		if (!$href.attr("oldhref")) {
			$href.attr("oldhref", href);
		}
		var ohref = $href.attr("oldhref") || href;
		var v = $("#q").val();
		if (!isEmpty(v)) {
			$href.attr("href", ohref + "&q=" + encodeURIComponent(v)).trigger("refresh");
		} else {
			$href.attr("href", ohref).trigger("refresh");
		}
	}

}
/**
 * 添加一个选项
 * @param fill 是否是填空
 */
Exercise.addOption = function(fill) {
	var $lastOptions = $("#exercise_answerArea input:last");
	if (fill) {
		/*填空题*/
		var newOption = $($("#exercise_answerArea  input")).index($lastOptions) + 2;
		$lastOptions.parent().after("<span>" + newOption + ".<input class=\"input fill\" name=\"option\" type=\"" + type + "\" value=\"\" /></span>");
		return;
	}
	var type = $lastOptions.attr("type");
	var lastValue = $lastOptions.val();
	if (lastValue == 'Z') {
		return;
	}
	var newOption = Exercise.EN[Exercise.EN.indexOf(lastValue) + 1];
	$lastOptions.parent().after("<span>" + newOption + ".<input name=\"option\" type=\"" + type + "\" value=\"" + newOption + "\" /></span>");

}
/**
 * 保存练习
 */
Exercise.save = function(callback) {
	var type = $("#exerciseForm input[name='type']").val() * 1;
	if (isEmpty($("#exercise_content").val())) {
		$.alert.warn("请描述您的问题");
		return;
	}
	switch(type) {
		case 1:
		//单选
		case 2:
		//多选
		case 3:
			//判断
			var options = [];
			var answers = [];
			$("#exercise_answerArea input").each(function() {
				var value = $(this).val();
				options.push(value);
				if ($(this).attr("checked")) {
					answers.push(value);
				}
			})
			if (answers.length == 0) {
				$.alert.warn("请您设置选择答案");
				return;
			}
			$("#exercise_picks").val(options.toString());
			$("#exercise_answer").val(answers.toString());
			break;
		case 4:
			//填空
			var answers = [];
			$("#exercise_answerArea input").each(function() {
				var value = $(this).val();
				if (isEmpty(value)) {
					$.alert.warn("请您输入填空的内容");
					answers = [];
					return false;
				}
				answers.push(value);
			})
			if (answers.length == 0) {
				return;
			}
			$("#exercise_answer").val(answers.join(Exercise.END));
			break;
		case 5:
			//简答计算等
			break;
	}

	var analyse = $("#analyse").val();
	if (isEmpty(analyse)) {
		$.alert.warn("请简单描述您对答案的评析");
		return;
	}
	var knowledge = Knowledge.get();
	if (!knowledge.data) {
		$.alert.warn("请您先设置分类！")
		return false;
	}

	$("#exerciseForm").postForm({
		after : function(request) {
			var kpath = knowledge.data.split(",");
			var kn = {
				grade : kpath[0],
				kind : kpath[1] || 0,
				k1 : kpath[2] || 0,
				k2 : kpath[3] || 0,
				k3 : kpath[4] || 0,
				k4 : kpath[5] || 0,
				k5 : kpath[6] || 0,
				tag : knowledge.tag || ""
			};
			for (var i in kn) {
				request.addParameter(i, kn[i]);
			}
		},
		success : function(data) {
			PopUp.hide("exercise_popup");
			$.alert.show(data, function() {
				if (callback) {
					callback();
				}
			});
		}
	});
}
/**
 * 删除一道练习
 */
Exercise.delQ = function(questionId) {
	$.alert.confirm("您确定要删除这道习题，删除后不可恢复？", [
	function() {
		$.post("/q/delete.html", {
			questionId : questionId
		}, function(data) {
			$.alert.show(data, function() {
				if (data.status > 0) {
					$("#eq_" + questionId).slideUp();
				}
			});
		});
	}]);
}
/*
 *
 */
Exercise.analyseQ = function(questionId) {
}
/**
 * 添加一道习题到练习中
 */
Exercise.add = function(exerciseId, questionId, type) {
	if (exerciseId == 0) {
		$.alert.warn("练习没有定义");
		return;
	}

	$.post("/exercise/add.html", {
		exerciseId : exerciseId,
		questionId : questionId,
		displayOrder : 0,
		score : 5
	}, function(data) {
		$.alert.show(data, function() {
			if (data.status > 0) {
				if (type)
					eval("loadE" + type + "()");
			}
		});
	})
}
/**
 * 排序
 */
Exercise.order = function(exerciseId, questionId, downOrUp, type) {
	$.post("/exercise/order.html", {
		exerciseId : exerciseId,
		questionId : questionId,
		order : downOrUp
	}, function(data) {
		if (data.status > 0) {
			if (type)
				eval("loadE" + type + "()");
		}
	})
}
/*设定为不确定答案*/
Exercise.setUncertain = function(obj, qid) {
	$("#eq_" + qid).addClass("error");
	//$(obj).text("答案确定");
}
/*设定为确定答案*/
Exercise.setCertain = function(obj, qid) {
	$("#eq_" + qid).addClass("error");
	//$(obj).text("答案确定");
}
/*** 检查试卷*/
Exercise.checkPaper = function(form) {
	var result = true;
	$("#" + form + " .question-box").each(function() {
		var $this = $(this), id = $this.attr("qid"), type = $this.attr("qtype") * 1;
		$this.removeClass("error");
		switch(type) {
			case 1:
			case 2:
			case 3:
				if ($this.find("input:checked").length == 0) {
					result = false;
					$this.addClass("error");
				}
				break;
			case 4:
				$this.find("input").each(function() {
					if (isEmpty($(this).val())) {
						$this.addClass("error");
						return false;
					}
				});
				break;
			case 5:
				$this.find("textarea").each(function() {
					if (isEmpty($(this).val())) {
						$this.addClass("error");
						return false;
					}
				});
				break;
		}
	});
	return result;
}
/**
 * 交卷
 * @param announce:是否通知分发试卷的人  true|false
 */
Exercise.handOn = function(announce) {
	if (Exercise.checkPaper('exerciseForm')) {
		$("#exerciseForm").postForm(function(data) {
			$.alert.show(data, function() {
				if (announce)
					parent.abcbox.handOnExercise($("#exerciseId").val());
			});
		});
	}
}
/*阅卷*/
Exercise.mark = function(id, eid, auto) {
	$.post("/exercise/mark.html", {
		id : id,
		exerciseId : eid
	}, function(data) {
		$.alert.show(data);
	})
}
function newUser(userId) {
	var html = [];
	html.push('<tr id="u' + userId + '">');
	html.push('<td><a href="/u/' + userId + '.html" usercard="' + userId + '"></a></td>');
	html.push('<td>已收到练习，正在做题</td>');
	html.push('<td>&nbsp;</td>');
	html.push('<td>&nbsp;</td>');
	html.push('<td>&nbsp;</td>');
	html.push('<td>&nbsp;</td>');
	html.push('</tr>');
	$("#exercise_status").append(html.join(""));
}

function handExercise(userId, exerciseId, handTime) {
	var $user = $("#u" + userId + " td");
	$user.eq(0).append('<span>[ <a href="javascript:void(0)" onclick="markExercise(${e.id},${e.exerciseId},true)">自动阅卷</a> | <a href="javadcript:void(0)" onclick="markExercise(${e.id},${e.exerciseId},false)">手工阅卷</a> ] </span>')
	$user.eq(1).html('<span style="color:red">已提交练习</span>')
}