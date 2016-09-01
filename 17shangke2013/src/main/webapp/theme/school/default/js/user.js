MC = {
	
}

/***获取页面信息***/
MC.getMessagePage = function(type, anotherid) {
	var $div = $("#message-privateview");
	$.get("/user/message.html?type="+type+"&anotherid=" + anotherid, function(html) {
		$("#message-tab li").removeClass("active");
		$("#message-tab li:eq(" + (type == "9" ? "0" : type) + ")").addClass("active");
		var $html = $(html).find("#message-privateview").children();
		$div.html($html).reload();
	});
}

/***删除某条信息***/
MC.del = function(id, url, index) {
	$.post(url, {
		ids : id
	}, function(data) {
		$.alert.show(data, function() {
			if (data.status > 0) {
				if (!isEmpty(index)) {
					$("#messageInfo_dd_" + id).slideUp();
					if ($("#messageInfo_dl_" + index + " dd:visible").length == 2) {
						$("#messageInfo_dl_" + index).slideUp();
					}
				} else {
					$("#messageInfo_dl_" + id).slideUp();
				}
			}
		});
	});
}

/***删除我和某人的信息***/
MC.delAll = function(other) {
	$.post("/user/deleteMessage.html", {
		other : other
	}, function(data) {
		$.alert.show(data, function() {
			if (data.status > 0) {
				$("#mymessageList_" + other).slideUp();
			}
		});
	});
}

MC.deleteAnnounce = function(id) {
	$.alert.confirm("您确定要删除该信息？", [
	function() {
		$.post("/admin/deleteAnnounce.html", {
			messageId : id
		}, function(data) {
			$.alert.show(data, function() {
				if (data.status > 0) {
					$("#announceItem" + id).slideUp();
				}
			});
		});
	}]);
}

