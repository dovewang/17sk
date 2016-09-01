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
Question = {
};

/**审核**/
Question.verify = function(qid){
	var statusval = $("input:radio[name='verify" + qid + "'][checked]").val();
	$.post("/question/verify.html", {
		id : qid,
		status : statusval,
		memo : $("#question-memo" + qid).val()
	}, function(result){
		$.alert.show(result,function(){
				location.reload();
		});
	})
}

/*
 *删除问题
 *@param qid 问题编号
 */
Question.del = function(obj, qid) {
    var options={effect:"mouse",trigger:$(obj)};
	$.alert.confirm("确定要删除吗？", [function() {
		$.post("/question/del.html", {
			questionId : qid
		}, function(data) {
			$.alert.show(data,function(){
				if (data.status > 0) {
		            $("#td_"+qid).text("已删除");
		            $(obj).hide();
		        }
			});
		});
	}],options);
}

/**
 * 重建索引
 *
 *
 */
Question.CreateIndex = function(){
    $.post("/manager/business/createquestionindexs.html", {
    }, function(data) {
        if (data.status > 0) {
            $.alert.show(data);
        } else {
            $.alert.show(data);
        }
    })
}
