Searcher = {};

/*$(function() {
 var url = parserURL(document.URL);
 var param = url.param;
 if(param.scroll == "true") {
 window.scrollBy(0, document.getElementById("searchDiv").offsetTop);
 }
 $(document).click(function() {
 $("#searchboxlist").hide()
 });
 });*/

Searcher.search = function(type, keyword, ajax) {
	if(isEmpty(keyword))
		keyword = "";
	var url = URL(document.URL);
	var param = url.param;
	var path = url.path;
	param.q = encodeURIComponent(keyword);
	var pm = []
	for(var i in param) {
		pm.push(i + "=" + param[i]);
	}
	if(pm.length > 0) {
		path += "?" + pm.join("&");
	}
	if(!ajax)
	  location.href = path;
	 else
	   ajax(keyword);
}

Searcher.searchCourse = function(keyword, ajax) {
	Searcher.search(1, keyword, ajax);
}

Searcher.searchQuestion = function(keyword, ajax) {
	Searcher.search(2, keyword, ajax);
}

Searcher.searchTeacher = function(keyword, ajax) {
	Searcher.search(3, keyword, ajax);
}

Searcher.searchShow = function(keyword, ajax) {
	Searcher.search(4, keyword, ajax);
}
/**
 * 顶部检索
 */
Searcher.topSearch = {
	start : function(event) {
		var keyword = $("#topsearch").val();
		if(!isEmpty(keyword)) {
			$("#searchboxlist").show().find(".keyword").text(keyword);
			$("#searchboxlist").find(".search_title a").each(function(index) {
				$(this).attr("href", $(this).attr("link") + "?q=" + keyword)
			});
			Searcher.topSearch.timeid = window.setTimeout(Searcher.topSearch.search, 2000);
		} else {
			$("#searchboxlist").hide();
			$("#topsearch_course").html("");
			$("#topsearch_teacher").html("");
		}
	},
	stop : function(event) {
		window.clearTimeout(Searcher.topSearch.timeid);
	},
	search : function() {
		var keyword=encodeURIComponent($("#topsearch").val());
		/*加载课程*/
		$.get("/topsearch/course.html?q=" + keyword, function(data) {
			var result = data.result.result;
			var html = [];
			for(var i = 0; i < result.length; i++) {
				html.push(Searcher.topSearch.courseTemplate(result[i]));
			}
			$("#topsearch_course").html(html.join("") + "<div class='clear'></div>").reload();
			$("#topsearch_course").slideDown();
		})
		/*加载老师*/
		$.get("/topsearch/teacher.html?q=" + keyword, function(data) {
			var result = data.result.result;
			var html = [];
			for(var i = 0; i < result.length; i++) {
				html.push(Searcher.topSearch.teacherTemplate(result[i]));
			}
			$("#topsearch_teacher").html(html.join("")).reload();
			$("#topsearch_teacher").slideDown();
		})
	}
}

Searcher.topSearch.courseTemplate = function(param) {
	return '<dl><dt><img src="' + $.image.resolve(param.cover, {
		width : 60,
		height : 45
	}) + '" width="60" height="45"  alt="' + param.name + '"/></dt><dd><a href="/course/' + param.courseId + '-'+Env.VERSION+'.html" class="course-title">' + param.name + '</a>主讲老师:<a href="#" usercard="' + param.teacher + '" >' + param.teacher + '</a></dd></dl>';
};
Searcher.topSearch.teacherTemplate = function(param) {
	var k = param.expert.split(",");
	var html = [];
	for(var i = 0; i < k.length; i++)
	html.push('<u class="k' + k[i] + '"></u>');
	return '<dl><dt><a href="/u/' + param.userId + '.html" target="_blank"><img src="' + param.face + '"  width="40" height="40" alt="" /></a></dt><dd><a href="/u/' + param.userId + '.html" target="_blank"><strong>' + (param.name) + '</a><br />擅长：' + html.join("") + '</dd></dl>';
}