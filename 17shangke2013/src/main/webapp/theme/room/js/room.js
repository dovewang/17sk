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
	start : function() {
		Room.flash = getFlashMovieObject("classroom");
	},
	end : function() {
		$(window).unbind("beforeunload");
		location.href = "/abc/end.html?roomId=" + window.roomid
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
	parent.jQuery.alert.warn("该功能暂不开放");
}

/*===========================================练习模块相关部分=================================================*/
/**
 *调用练习
 */

function callExer() {
	parent.ABC.run("exerciseManager");
}

/**
 * 分发练习及测验
 */
function distrubuteExercise(exerciseId, subject, time) {
	Room.flash.fPopUpThings("openExcercise", exerciseId + "," + subject + "," + time, "noteacher", "");
	/*将老师界面切换为统计交卷情况的界面*/
	parent.ABC.close("exerciseManager");
	showExerciseResult(exerciseId);
}

/**
 *练习完成结果 
 */
function showExerciseResult(exerciseId) {
	parent.ABC.run("exerciseResult",{exerciseId:exerciseId,roomId:window.roomid})
}


/**
 * 打开练习,回调函数
 */
function openExcercise(param) {
	var ps = param.split(",")
	parent.ABC.run("paper", {
		exerciseId : ps[0],
		roomId : window.roomid,
		subject : ps[1],
		time : ps[2]
	});
	/*通知老师练习已收到*/
	Room.flash.fPopUpThings("receiveExcerciseOk", Env.USER_ID, "teacher", "");
}

/**
 * 学生收到练习确认
 */
function receiveExcerciseOk(userId) {
	/*判断是否是打开了练习结果查看页面*/
	var $exerciseResult = $("#exerciseResult");
	if ($exerciseResult.attr("open") == "open") {
		parent.exerciseResultIframe.newUser(userId);	
	}
}

/**
 *提交练习 
 */
function handOnExercise(exerciseId) {
	Room.flash.fPopUpThings("handExerciseToTeacher", Env.USER_ID + "," + exerciseId+","+Env.USER_NAME, "teacher", "");
}

/**
 * 将练习提交给老师的回调函数
 */
function handExerciseToTeacher(param) {
	var p=param.split(",");
	parent.exerciseResultIframe.handExercise(p[0],p[1]);
	Room.flash.fPopUpThings("handExerciseResult", "", "id", p[0]);
}

/*老师收到练习后的结果，成功提交*/
function handExerciseResult(message){
		parent.jQuery.alert.success("您的练习已成功提交，正在等待老师批阅，请耐心等待！");
		parent.ABC.close("paper");
}


/*========================文档模块相关部分================================*/
/*
 *  打开文档管理器
 */
function callDocCon() {
	parent.ABC.run("documentManager");
}

/**
 *  文档管理器回调函数
 *  function fAddPic(vURL:String)   插入图片
 *  function fAddBack(vURL:String)   插入背景
 *  function fOpenDoc(vURL:String, vPage:String = "", vCallBack:String = ""):void 其中：vCallBack(var1, var2); var1:上面传过来的页数。var2: swf里对应的黑板的id。
 */
window.doc = {};
function loadDocument(docId, pages, type) {
	if (!pages)
		pages = 1;
	window.bid = 0;
	window.curdoc = docId;
	parent.ABC.close("documentManager");
	/*已加载完成的页索引*/
	window.loaded_index = 0;
	/*正在加载中的索引*/
	window.loading_index = 1;
	window.timeid = window.setInterval(function() {
		if (window.loaded_index == pages) {
			window.clearInterval(window.timeid);
			if (pages != 1)
				Room.flash.fChangeBoard(window.bid);
		} else {
			if (window.loading_index == 1) {
				Room.flash.fOpenDoc("/doc/load.html?id=" + docId + "&page=" + window.loading_index, "" + window.loading_index, "loadDocumentCallback");
				window.loading_index++;
			}
			/*加载完成，继续加载下一页*/
			else if (window.loaded_index == window.loading_index - 1) {
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
	if (index == 1) {
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
	parent.ABC.close("documentManager");
}

/**
 * 添加一个背景
 */
function loadBackground(docId, pages, path) {
	Room.flash.fAddBack(path);
	parent.ABC.close("documentManager");
}

/**
 * 课程结束了，通知学生端对课程进修评价
 */
function fTimeOver() {
	if (window.replay) {
		/*将信息记录到用户问题查看表*/
		classover("replay");
	} else {
		Room.flash.fPopUpThings("classover", Env.USER_ID, "noteacher", "");
		if (window.subjectType == 3) {
			parent.jQuery.alert.confirm("秀秀录制已完成，是否发布？", "<button node-type='pop-close' class='button cancle'>取消</button><button node-type='pop-close'  class='button ok'>确定</button>", [
			function() {
			},
			function() {
				$(window).unbind("beforeunload");
				parent.jQuery.formSubmit("/show/form.html", {
					t : 1,
					roomId : window.roomid,
					subject : window.subject
				});
			}]);
		} else {
			window.setTimeout(Room.end, 1000);
		}
	}
}

/**
 * 课程结束是调用的方法
 */
function classover(param) {
	Room.end();
}