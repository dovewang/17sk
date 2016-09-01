Knowledge = {
	max : 3
};
/**
 * 设置默认的知识点
 */
Knowledge.setDefault = function(kpath, text, tag) {
	$("#setKnowledgeDiv").text(text).data("knowledge", kpath);
}

Knowledge.loadKind = function(obj) {
	var grade = $(obj).val();
	$.get(Env.SITE_DOMAIN + "/kind.html", {
		grade : grade
	}, function(json) {
		var html = [];
		for( i = 0; i < json.result.length; i++) {
			html.push('<option value="' + json.result[i].kind + '">' + json.result[i].name + '</option>');
		}
		$("#knowledge_kind").html(html.join(""));
		$("#knowledge_text").text($(obj).find("option:selected").text()).data("knowledge", grade);
	});
	$("#knowledge_kind").nextAll().hide();
	$("#knowledge_tag_span").hide();
}

Knowledge.loadKnowledge = function(obj, parentId, depth) {
	$("#knowledge_tag_span").show();
	if(depth == Knowledge.max) {
		Knowledge.set(depth);
		return;
	}
	var parentId = (depth == 1 ? parentId : $("#knowledge_subs" + (depth - 1)).val());
	$.get(Env.SITE_DOMAIN + "/knowledge.html", {
		grade : $("#knowledge_grade").val(),
		kind : $("#knowledge_kind").val(),
		parentId : parentId
	}, function(json) {
		if(json.knowledge.length > 0) {
			var html = [];
			html.push('<select id="knowledge_subs' + depth + '" size="10" onchange="Knowledge.loadKnowledge(this,' + parentId + ',' + (depth + 1) + ')" >');
			for(var i = 0; i < json.knowledge.length; i++) {
				html.push('<option value="' + json.knowledge[i].knowledgeId + '">' + json.knowledge[i].knowledge + '</option>');
			}
			html.push('</select>');
			if($('#knowledge_subs' + depth).length == 0) {
				$(obj).after(html.join(""));
			} else {
				$('#knowledge_subs' + depth).html(html.join("")).show();
			}
		} else {
			$(obj).nextAll().hide();
		}

		Knowledge.set(depth);
	});
}

Knowledge.set = function(depth) {
	var text = $("#knowledge_grade option:selected").text() + ">" + $("#knowledge_kind option:selected").text();
	var data = $("#knowledge_grade").val() + "," + $("#knowledge_kind").val();
	for(var d = 2; d <= depth; d++) {
		text += ">" + $("#knowledge_subs" + (d - 1) + " option:selected").text();
		data += "," + $("#knowledge_subs" + (d - 1)).val();
	}
	$("#knowledge_text").text(text).data("knowledge", data);
}

Knowledge.get = function() {
	return {
		text : $("#knowledge_text").text(),
		tag : $("#knowledge_tag").val(),
		data : $("#knowledge_text").data("knowledge") || $("#setKnowledgeDiv").data("knowledge")
	}
}; 

Kiss.addEventListener(
	"kownleadge-select", function(obj) {
		$(obj).click(function() {
			 $(obj).popover({
			   id : "knowledge-select-window",
			   url : Env.SITE_DOMAIN + "/kindView.html",
			   cache : true,
			   theme:"top",
			   trigger:$(obj),
			   afterShow:function($pop,options){
			   		$pop.css("z-index",65535);
			   		$pop.find("button[node-type=knowledge-action-submit]").unbind("click").click(function() {
			   			var $s = $("#help-extend");
						var knowledge = Knowledge.get() || {
							data : "0,0,0,0,0,0",
							text : ""
						};
						var kpath = (knowledge.data || "0,0,0,0,0,0").split(",");
						var grade = isEmpty(kpath[0],0),kind = isEmpty(kpath[1],0);
						$.get(Env.SITE_DOMAIN + "/user/getExpertUser.html",{
							grade : grade,
							kind : kind
						},function(data){
							var list = data.result;
							var html = []
							html.push('<div id="expert-user-box" class="box expert-user-box">');
							if(list.length > 0){
								html.push('<ul class="images">');
								for (var i = 0; i < list.length; i++) {
									var user = list[i];
									html.push('	<li uid="' + user.userId + '"><a href="javascript:;"><i></i><img usercard="' + user.userId + '" src="' + user.face + '" width="58" height="58"/></a>');
									html.push('	<span><div style="padding-left:10px;overflow:hidden;width:65px;white-space:nowrap;word-spacing:normal;word-break:none;text-overflow:ellipsis;">' + user.name + '</div></span>');
									html.push('</li>');
								};
								html.push('</ul>');
							}else{
								html.push('<span>很抱歉，没有查询到该老师</span>');
							}
							html.push('</div>');
							$s.empty().html(html.join(""));
							/* 绑定选择事件*/
							$(".images a").click(function(){
								if($(this).data("open") == undefined || !$(this).data("open")){
					      			$(this).parent().addClass("selected");
					      			$(this).data("open",true);
					      		}else{
					      			$(this).parent().removeClass("selected");
					      			$(this).data("open",false);
					      		}
					        });
							Pop.hide($pop);
							$s.slideDown();
						});
					})
			   }
			 });
	});
})