/**
 * 教室的父页面
 */
ABC = {
	guide : function() {
	}
};
ABC.vote = function() {

}
ABC.run = function(module, param) {
	switch(module) {
		case "documentManager":
			Pop.show({
				id : "documentManager",
				url : "/doc/manager.html",
				cache : true,
				iframe : true,
				template:false
			});
			break;
		case "exerciseManager":
			Pop.show({
				id : "exerciseManager",
				url : "/exercise/manager.html",
				cache : true,
				iframe : true,
				template:false
			});
			break;
		case "exerciseResult":
			Pop.show({
				id : "exerciseResult",
				iframe : true,
				template:false,
				url : "/exercise/result.html?exerciseId=" + param.exerciseId + "&roomid=" + param.roomId
			});
			break;
		case "paper":
			Pop.show({
				id : 'paper',
				iframe : true,
				template:false,
				url : '/exercise/paper.html?exerciseId=' + param.exerciseId + "&roomid=" + param.roomId + "&subject=" + param.subject + "&time=" + param.time
			});
	}
}

ABC.close = function(module) {
	Pop.hide($("#" + module));
}
