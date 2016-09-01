/*=====================================================
 *消息通讯接口说明:
 *@param vCallBack:回调函数名字
 *@param  vCs:参数
 *@param  vToSomeOne:String :发送对象   "all"：所有人, "teacher"：老师, "noteacher"：老师之外, "id":指定id;
 *@param  vID:String:  发送的指定ID，如果没有，请传入""空值
 *例如：Room.flash.fPopUpThings("openExcercise", "1", "all", "");
 *==========================================================*/
/*初始化设置*/
Room = {
	login:function(){
		$("#loginForm").postForm("")
	},
	position : {
		top : 60
	},
	start : function() {
		Room.flash = getFlashMovieObject("classroom");
	},
	end : function() {
		$(window).unbind("beforeunload");
		if(window.subjectType == 1) {
			$.alert.confirm("问题解答已经结束，是否对问题解答的结果进行评价？", [function() {
				location.href = "/qitem-" + window.subjectId + ".html"
			}]);
		} else if(window.subjectType == 2) {
			$.alert.confirm("课程已经结束，是否对课程进行评价？", [function() {
				location.href = "/course/" + window.subjectId + "-"+Env.VERSION+".html#!/course/comments-" + window.subjectId + ",-1.html"
			}]);
		} else if(window.subjectType == 3) {
			$.alert.confirm("秀秀观看结束，是否进行评价？", [function() {
				//location.href = "/show/item-"+window.subjectId+".html";
				history.back();
			}]);
		} else {
			$.alert.confirm("课程已经结束，是否离开教室？",[function() {
				location.href = "/index.html";
			}]);
		}
	},
	teacherEnd : function() {
		$(window).unbind("beforeunload");
		if(window.subjectType == 3) {
			$.alert.confirm("秀秀录制已完成，是否发布？", [function() {
				$.formSubmit("/show/form.html", {
					t : 1,
					roomId : window.roomid
				});
			}]);
		} else {
			$.alert.confirm("您的课程已经结束，是否离开？",[function() {
				location.href = "/index.html"
			}]);
		}
	}
}

$(function() {
	Room.start();
	$(window).on("beforeunload", function(event) {
		event.preventDefault();
		event.stopImmediatePropagation();
		event.stopPropagation();
		return "离开教室，所有信息将被清除：";
	});
});
/*
 * 调用投票
 */
function callVote() {
	$.alert.info("该功能暂不开放");
}

/*===========================================练习模块相关部分=================================================*/
/**
 *调用练习
 */

function callExer() {
	PopUp.show({
		id : "exerciseManager",
		url : "/exercise/manager.html",
		iframe : true,
		cache : true,
		position : Room.position
	});
}

/**
 * 分发练习及测验
 */
function distrubuteExercise(exerciseId, subject, time) {
	Room.flash.fPopUpThings("openExcercise", exerciseId + "," + subject + "," + time, "noteacher", "");
	/*将老师界面切换为统计交卷情况的界面*/
	PopUp.hide("exerciseManager");
	showExerciseResult(exerciseId);
}

/**
 * 练习结果
 */
function showExerciseResult(exerciseId) {
	PopUp.show({
		id : "exerciseResult",
		iframe : true,
		url : "/exercise/result.html?exerciseId=" + exerciseId + "&roomid=" + window.roomid,
		position : Room.position
	});
}

/**
 * 交卷，通知老师用户已交卷
 */
function handOnExercise(userId, exerciseId) {
	Room.flash.fPopUpThings("handExerciseToTeacher", userId + "," + exerciseId, "teacher", "");
	PopUp.hide("paper");
}

/**
 * 打开练习,回调函数
 */
function openExcercise(param) {
	var ps = param.split(",")
	PopUp.show({
		id : 'paper',
		iframe : true,
		url : '/exercise/paper.html?exerciseId=' + ps[0] + "&roomid=" + window.roomid + "&subject=" + ps[1] + "&time=" + ps[2],
		position : Room.position
	});

	//$.post("")

	/*通知老师练习已收到*/
	Room.flash.fPopUpThings("receiveExcerciseOk", "userId", "teacher", "");
}

/**
 * 学生收到练习确认
 */
function receiveExcerciseOk(param) {
	/*判断是否是打开了练习结果查看页面*/
	var $exerciseResult = $("#exerciseResult");
	if($exerciseResult.attr("open") == "open") {
		var html = [];
		html.push('<tr>');
		html.push('<td><a href="/u/${e.userId}.html" usercard="${e.userId}">${e.userId}</a> <span>[ <a href="javascript:void(0)" onclick="markExercise(${e.id},${e.exerciseId},true)">自动阅卷</a> | <a href="javadcript:void(0)" onclick="markExercise(${e.id},${e.exerciseId},false)">手工阅卷</a> ] </span></td>');
		html.push('<td>已收到练习，正在做题</td>');
		html.push('<td>&nbsp;</td>');
		html.push('<td>&nbsp;</td>');
		html.push('<td>&nbsp;</td>');
		html.push('<td>&nbsp;</td>');
		html.push('</tr>');
		$("#exercise_status").append(html.join(""));
	}

}

/**
 * 将练习提交给老师的回调函数
 */
function handExerciseToTeacher(param) {
	$.alert.info("您的练习已成功提交，正在等待老师批阅，请耐心等待！")
}

/*========================文档模块相关部分================================*/
/*
 *  打开文档管理器
 */
function callDocCon() {
	PopUp.show({
		id : "documentManager",
		url : "/doc/manager.html",
		iframe : true,
		cache : true,
		position : Room.position
	})
}

/**
 *  文档管理器回调函数
 *  function fAddPic(vURL:String)   插入图片
 *  function fAddBack(vURL:String)   插入背景
 *  function fOpenDoc(vURL:String, vPage:String = "", vCallBack:String = ""):void 其中：vCallBack(var1, var2); var1:上面传过来的页数。var2: swf里对应的黑板的id。
 */
window.doc = {};
function loadDocument(docId, pages, type) {
	if(!pages)
		pages = 1;
	window.bid = 0;
	window.curdoc = docId;
	PopUp.hide("documentManager");
	/*已加载完成的页索引*/
	window.loaded_index = 0;
	/*正在加载中的索引*/
	window.loading_index = 1;
	window.timeid = window.setInterval(function() {
		if(window.loaded_index == pages) {
			window.clearInterval(window.timeid);
			if(pages != 1)
				Room.flash.fChangeBoard(window.bid);
		} else {
			if(window.loading_index == 1) {
				Room.flash.fOpenDoc("/doc/load.html?id=" + docId + "&page=" + window.loading_index, "" + window.loading_index, "loadDocumentCallback");
				window.loading_index++;
			}
			/*加载完成，继续加载下一页*/
			else if(window.loaded_index == window.loading_index - 1) {
				Room.flash.fOpenDoc("/doc/load.html?id=" + docId + "&page=" + window.loading_index, "" + window.loading_index, "loadDocumentCallback");
				window.loading_index++;
			}
		}
	}, 500);
}

/**
 * 加载文档回调函数
 */
function loadDocumentCallback(index, id) {
	if(index == 1) {
		/*设定激活的白板*/
		window.bid = id;
	}
	window.loaded_index = index;
}

/**
 * 载入一个图片
 */
function loadImage(docId, pages, path) {
	Room.flash.fAddPic(path);
	PopUp.hide("documentManager");
}

/**
 * 添加一个背景
 */
function loadBackground(docId, pages, path) {
	Room.flash.fAddBack(path);
	PopUp.hide("documentManager");
}

/**
 * 课程结束了，通知学生端对课程进修评价
 */
function fTimeOver() {
	if(window.replay) {
		/*将信息记录到用户问题查看表*/
		classover("replay");
	} else {
		Room.flash.fPopUpThings("classover", "userId", "noteacher", "");
		Room.teacherEnd();
	}
}

/**
 * 课程结束是调用的方法
 */
function classover(param) {
	/*转到相关问题及课程评价页面*/
	$.post("/room/classover.html", {
		roomId : window.roomid,
		subjectId : window.subjectId,
		subjectType : window.subjectType,
		realtime : (param != "replay")
	}, function(data) {
		Room.end();
	});
}