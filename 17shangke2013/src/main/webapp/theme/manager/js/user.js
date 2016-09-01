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
User = {
};

/**
 * 重建索引
 *
 *
 */
User.CreateIndex = function(){
	$.post("/manager/business/createuserindexs.html", {
	}, function(data) {
		if (data.status > 0) {
			$.alert.show(data);
		} else {
			$.alert.show(data);
		}
	})
}

/**
 * 加锁
 *
 *
 */
User.freeze = function(obj,uid){
	$.post("/manager/business/updateuserstatic.html", {
		userid : uid,
		status : 1
	}, function(data) {
		if (data.status > 0) {
		    $.alert.show(data,function(){
		        location.reload();
		    })
		} else {
			$.alert.show(data);
		}
	});
}

/**
 * 解锁
 *
 *
 */
User.unfreeze = function(uid){
	$.post("/manager/business/updateuserstatic.html", {
		userid : uid,
		status : 2
	}, function(data) {
		if (data.status > 0) {
			$.alert.show(data,function(){
                location.reload();
            })
		} else {
			$.alert.show(data);
		}
	});
}


/**
 * 系统消息
 * 
 */
User.systemMessage = function() {
    var subject = $("#subject").val();
    var content = $("#content_editor").val();
    if (isEmpty(subject)) {
        $.alert.warn("请输入标题！");
        return;
    }
    $.post("/manager/data/submitmessage.html", {
        content : content,
        subject : subject
    }, function(data) {
        if (data.status > 0) {
        	$.cookie('message.count', null);//刷新个人的消息cookie（后台发送消息订阅不到）
            $.alert.show(data,function(){
                location.reload();
            })
        } else {
            $.alert.show(data);
        }
    })
}