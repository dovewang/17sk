/**
 *
 * 功能描述：后台功能模块问题JS<br>
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
Data = {
};

Kiss.addEventListener({
	"tree" : function(obj) {
		$(obj).each(function() {
			var $item = $(this), actionIndex = $item.attr("actionIndex");
			$item.find("[action-type='tree-item']").each(function(){
				var $this = $(this), gid = $this.attr("gid");
				$this.click(function() {
					$.get("/manager/data/getKinds.html", {
						grade : gid
					}, function(data) {
						var list = data.result;
						var html = [];
						html.push("<ul class='cc-tree-gcont'>");
						html.push("<li class='cc-tree-item'><a href='javascript:;' onclick='Data.addkind("+gid+")'>+</a></li>");
						for (var i = 0; i < list.length; i++) {
							var k = list[i];
							html.push("<li id='cc-tree-item-"+k.kind+"' onclick='Data.kindClick(this,"+gid+","+k.kind+")' class='cc-tree-item cc-hasChild-item'>"+k.name+"</li>");
						}
						html.push("</ul>");
						$this.append(html.join("")).unbind("click").addClass("cc-tree-expanded");
						$this.find("div[id="+gid+"]").data("expanded",true).attr("onclick","Data.expanded(this,"+gid+")");
					})
				});
			})
		})
	}
});

/**
 * 科目展开方法
 * @param obj
 * @param gid
 */
Data.expanded = function(obj, gid){
	var $this = $(obj), expanded = $this.data("expanded");
	if(expanded){
		$("#cc-tree-group-"+gid).removeClass("cc-tree-expanded");
		$this.data("expanded",false);
		return;
	}
	$("#cc-tree-group-"+gid).addClass("cc-tree-expanded");
	$this.data("expanded",true);
}

/**
 * 加载知识点
 * @param obj
 * @param gid
 */
Data.kindClick = function(obj, gid, kid){
	var $this = $(obj);
	$("li[id='cc-list-item'] .cc-selected").removeClass("cc-selected");
	$this.addClass("cc-selected");
	$("li[id='cc-list-item']").nextAll().remove();
	Data.knowledge(gid, kid, 0, 0);
}

/**
 * 
 */
Data.knowledge = function(gid, kid, parentid, group, obj){
	$.get("/manager/data/getKnowledge.html", {
		grade : gid,
		kind : kid,
		parentId : parentid
	}, function(data) {
		$("li[id=cc-list-item-"+(group-1)+"]").nextAll().remove();
		var list = data.result;
		var html = [];
		html.push("<li id='cc-list-item-"+group+"' class='cc-list-item'>");
		html.push("<div class='cc-cbox'><div class='cc-cbox-filter'><a href='javascript:;' onclick='Data.addknowledge("+gid+","+kid+","+parentid+");'>+</a></div>");
		html.push("<ul class='cc-cbox-cont'>");
		for (var i = 0; i < list.length; i++) {
			var k = list[i];
			html.push("<li onclick='Data.knowledge("+gid+","+kid+","+k.knowledgeId+","+($("ol > li").length)+",this)' class='cc-cbox-item"+(k.memo>0?" cc-hasChild-item":"")+"'>"+k.knowledge+"</li>");
		}
		html.push("</ul>");
		html.push("</div>");
		html.push("</li>");
		$("ol").append(html.join(""));
		if(!isEmpty(obj)){
			$("li[id=cc-list-item-"+(group-1)+"] .cc-selected").removeClass("cc-selected");
			$(obj).addClass("cc-selected");
		}
	})
}

/**
 *新增知识点
 *  gid
 *  kid
 *  parentid
 */
Data.addknowledge = function(gid, kid, parentid){
	Pop.show({
		id : "addKnowledgeView",
		url : "/manager/data/add.knowledgeView.html?gid="+gid+"&kid="+kid+"&parentid="+parentid,
		template : false,
		css : {
			width : 250
		},
		afterShow : function($pop, options){
			$pop.find("[node-type=submit-ok]").one("click", function(){
				$("#addknowledge").postForm({
					success : function(result){
						$.alert.show(result,function(){
						     location.reload();
						});
					}
				});
			})
		}
	})
}

/**
 * 新增科目
 * gid
 */
Data.addkind = function(gid){
	Pop.show({
		id : "addKindView",
		url : "/manager/data/add.kindView.html?gid="+gid,
		template : false,
		css : {
			width : 250
		},
		afterShow : function($pop, options){
			$pop.find("[node-type=submit-ok]").one("click", function(){
				$("#addKind").postForm({
					success : function(result){
						$.alert.show(result,function(){
						     location.reload();
						});
					}
				});
			})
		}
	})
}