/*学习圈及班级管理*/
;(function() {
	Group = {
		id : 0,
		type : 0, /*0代表学习圈，1，代表班级*/
		members : function(page, size, div) {
			$.get("/group/" + Env.GROUP_ID + "/member.html", {
				page : page,
				size : size
			}, function(result) {
				var html = [];
				$(result.members).each(function(index) {
					html.push('<a target="_blank"   href="/u/' + this + '.html">');
					html.push('<img  usercard="' + this + '" src="/theme/school/default/images/noface_s.jpg" height="30" width="30"></a>');
				});
				$(div).html(html.join("")).reload();
			})
		},
		announce : function(obj) {
			$.post("/group/" + Env.GROUP_ID + "/announce.html", {
				announce : obj.value
			}, function(result) {
				if (result.status != 1) {
					$.alert.show(result);
				}
			})
		}
	}

	Kiss.addEventListener("group-members", function(item) {
		Group.members(1, 14, item);
	});
})(window);
