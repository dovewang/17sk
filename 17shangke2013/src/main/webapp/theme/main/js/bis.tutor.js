Tutor = {}

$(function() {
	//$(".tutor-item [action-type='candidate-add']").click(Tutor.addCandidate)
})

Kiss.addEventListener({
    "tutor-delete" : function(obj) {
        var $this = $(obj);
        $(obj).unbind("click").click(function() {
            Tutor.delTutor($this, $this.attr("id"));
        });
    },
    "tutorDemand-delete" : function(obj) {
        var $this = $(obj);
        $this.unbind("click").click(function() {
           Tutor.delTutorDemand($this, $this.attr("id"));
        });
    },
    "tutor-edit" : function(obj) {
        var $this = $(obj);
        $this.click(function(event) {
            Tutor.editTutor($this.attr("id"));
        });
    },
    "tutor-editTutorDemand" : function(obj) {
        var $this = $(obj);
        $this.click(function() {
           Tutor.editTutorDemand($this.attr("id"));
        });
    }
});


/*
 * 修改辅导服务
 */
Tutor.editTutor = function(id){
   	var kind = [];
	var kind1 = $("#tutor-item-" + id + " td[id=kind1]").attr("key");
	var kind2 = $("#tutor-item-" + id + " td[id=kind2]").attr("key");
	var kind3 = $("#tutor-item-" + id + " td[id=kind3]").attr("key");
	var price = $("#tutor-item-" + id + " td[id=price]").attr("key");
	var intro = $("#tutor-item-" + id).attr("intro");
	kind.push(kind1);
	kind.push(kind2);
	kind.push(kind3);
	var $div = $("#servece-form-ftl").data("upd",true);
	$div.load("/tutor/service.form.html?kind=" + kind + "&tutorId=" + id +"&price=" + price + "&intro=" + encodeURIComponent(intro));
}

/*
 * 修改辅导需求
 */
Tutor.editTutorDemand = function(id){
   	var kind = [];
	var kind1 = $("#tutor-item-" + id + " td[id=kind1]").attr("key");
	var kind2 = $("#tutor-item-" + id + " td[id=kind2]").attr("key");
	var kind3 = $("#tutor-item-" + id + " td[id=kind3]").attr("key");
	var price = $("#tutor-item-" + id + " td[id=price]").attr("key");
	var intro = $("#tutor-item-" + id).attr("intro");
	kind.push(kind1);
	kind.push(kind2);
	kind.push(kind3);
	var $div = $("#demand-form-ftl").data("upd",true);
	$div.load("/tutor/demand.form.html?kind=" + kind + "&demandId=" + id +"&price=" + price + "&intro=" + encodeURIComponent(intro));
}

/*
 * 删除辅导服务
 */
Tutor.delTutor = function(obj,id) {
    var options={effect:"mouse",trigger:obj};
	$.alert.confirm("您确定要删除信息？", [function() {
        $.post("/tutor/delTutor.html", {
            id : id
        }, function(data) {
            if(data.status == CONFIG.__RESULT.SUCCESS) {
                $.alert.success(data.message,options);
                $("#tutor-item-" + id).slideUp();
            } else {
                $.alert.show(data,options);
            }
        });
    }],options);
}

/*
 * 删除辅导需求
 */
Tutor.delTutorDemand = function(obj,id) {
    var options={effect:"mouse",trigger:obj};
	$.alert.confirm("您确定要删除信息？", [function() {
        $.post("/tutor/delTutorDemand.html", {
            id : id
        }, function(data) {
            if(data.status == CONFIG.__RESULT.SUCCESS) {
                $.alert.success(data.message,options);
                $("#tutor-item-" + id).slideUp();
            } else {
                $.alert.show(data,options);
            }
        });
    }],options);
}

/*
 * 发布家教服务信息
 */
Tutor.postService = function(){
	var kind = $("#kind").val();
	var price = $("#price").val();
	var count = $("#tutor-tbody tr:visible").length;
	var upd = $("#servece-form-ftl").data("upd");//表单类型
	var intro = $("#intro").val();
	var v = /^\d{1,10}$|^\d{1,10}\.\d{1,2}\w?$/;
	if(count>=5&&isEmpty(upd)||count>=5&&!upd){
		$.alert.info("不允许超过5条信息!");
		return;
	}
	if(isEmpty(kind)){
		$.alert.info("请选择科目!");
		return;
	}
	if(isEmpty(price)){
		$.alert.info("请填写价格!");
		return;
	}
	if(intro.length > 50){
		$.alert.info("内容描述过多");
		return;
	}
	if(!v.test(price)){
		$.alert.info("请输入正确的价格!");
		return;
	}
	$("#serviceForm").postForm(function(data) {
		$.alert.show(data,function(){
            if(data.status > 0) {
               location.reload();
            }
        });
	});
}

/*
 * 发布家教需求信息
 */
Tutor.postDemand = function() {
	var kind = $("#kind").val();
	var count = $("#tutor-tbody tr:visible").length;
	var upd = $("#demand-form-ftl").data("upd");//表单类型
	var intro = $("#intro").val();
	if(count>=5&&isEmpty(upd)||count>=5&&!upd){
		$.alert.warn("不允许超过5条信息!");
		return;
	}
	if(isEmpty(kind)){
		$.alert.warn("请选择科目!");
		return;
	}
	if(intro.length > 50){
		$.alert.info("内容描述过多");
		return;
	}
   $("#demandForm").postForm(function(data){
        $.alert.show(data,function(){
            if(data.status > 0) {
        	   location.reload();
        	}
        });
	})
}
/*
 * 加入候选人
 */
Tutor.addCandidate = function() {
	$.post("/tutor/candidate.add.html", {
		candidate : $(this).attr("candidate")
	}, function(data) {
		$.alert.show(data);
	})
}
/*评论*/
Tutor.comment = function() {
   var com=$("#comment_content").val();
   if(isEmpty(com)){
   	 return;
   }
	var $title=$("#show-title");
   $.post("/show/comment.html",{id:$title.attr("showId"),title:$title.text(),content:com},function(data){
   	   $.alert.show(data);
   })
}
