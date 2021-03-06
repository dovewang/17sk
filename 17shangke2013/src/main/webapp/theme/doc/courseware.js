Courseware = {
	index : 0
};
/**
 * 添加新的课程
 */
Courseware.newLesson = function(show) {
	$(this).hasClass("add")?$("#newLesson").show():$("#newLesson").hide();
}

Courseware.addLesson=function(){
	$.post("/courseware/lesson.add.html",{groupId:Env.GROUP_ID,lesson:$("#lesson_name").val(),teacher:$("#lesson_teacher").attr("uid")},function(data){
		$("#lessonId").val(data.id);
		Courseware.addChapter();
	});
}
/*添加一个课程到草稿箱*/
Courseware.addChapter=function(){
	$.post("/courseware/chapter.draft.html",{title:$("#chapter_name").val(),
	  lessonId:$("#lessonId").val(),classindex:$("#scheduleIndex").val(),classtime:$("#classtime").data("value").toSecondsTime(),content:$("#chapter_content").val()},function(data){
		$("#chapterId").val(data.id);
	});
}
/*发布课程*/
Courseware.publish=function(obj){
	$.post("/courseware/chapter.update.html",{title:$("#chapter_name").val(),
	  lessonId:$("#lessonId").val(),classindex:$("#scheduleIndex").val(),classtime:$("#classtime").data("value").toSecondsTime(),content:$("#chapter_content").val(),chapterId:Q.isEmpty($("#chapterId").val(),0),status:1},function(data){
		 $.alert.show(data,function(){
			 if(data.status==1)
			   $(obj).trigger("pop-close");
		 })
	});
}

/*添加课程资料*/
Courseware.add=function(obj){
	/*先检查是否新增加的科目，如果是新增加的科目，点击到这个模块时，将自动保存提交课程的基本信息*/
	var nl=$("#newLesson");
	if(nl.attr("draft")!="true"&&Q.isEmpty($("#chapterId").val())){
		if(nl.is(":visible")){
			  Courseware.addLesson();//增加课程信息
		} else{
			  Courseware.addChapter();//增加章节信息
	    }
		$("#newLesson").attr("draft","true");		
		document.getElementById("timetable").timetable.refresh();
	}
	var me=$(obj),type=me.data("type");
	/*根据模版对应生成*/
	var template=$("#template_chapter_resource_"+type).clone();
	var div=$("#chapterResource_"+type);
	var chapterResource=$('<div id="chapterResource"></div>')
	div.append(chapterResource);
	template.find("[tid]").each(function(index, element) {
        $(this).attr("id",$(this).attr("tid")).removeAttr("tid");
    });
	chapterResource.append(template);
	template.find("div.tab-header").tabnavigator();
	switch(type){		
		case "video":  	    
		case "audio":
		case "image":
		case "document":
		case "text":
	}
}


Courseware.upload = function() {
	var chapterId=$("#chapterId").val();
	$("#chapterResouceForm_lessonId").val($("#lessonId").val());
	$("#chapterResouceForm_chapterId").val(chapterId);
	$("#chapterResouceForm").postForm(function(result) {
		$.alert.show(result, function() {
			  Courseware.resource(chapterId);
		});
	});
}

Courseware.external=function(type){
	var out=$("#resource_tab_out");
	var name=out.find("input[name=name]:first").val();
	var url=out.find("input[name=url]:first").val();
	var chapterId=$("#chapterId").val();
	$.post("/courseware/resource/reference.html",{lessonId:$("#lessonId").val(),chapterId:chapterId,type:type,name:name,url:url},function(result){
		$.alert.show(result, function() {
			   Courseware.resource(chapterId);
		});
	});
}

Courseware.text=function(){
	var form=$("#chapter_resource_text");
	var title=form.find("input[name=title]:first").val();
	var text=form.find("textarea[name=text]:first").val();
	var chapterId=$("#chapterId").val();
	$.post("/courseware/resource/text.html",{lessonId:$("#lessonId").val(),chapterId:chapterId,title:title,text:text},function(result){
		$.alert.show(result, function() {
			 Courseware.resource(chapterId);
		});
	});
}
Courseware.resource=function(id){
	$("#chapterResource-container").load("/courseware/chapter.resource/"+id+".html");
}


Courseware.view=function(obj,id){
	var pop=$("#chapterWindow").data("window");
	Pop.update(pop,{url:"/courseware/chapter/"+id+".html?g="+Env.GROUP_ID+"&view="+$(obj).attr("role")})
}