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
Show = {
};

/**
 * 重建索引
 *
 *
 */
Show.CreateIndex = function(){
    $.post("/manager/business/createshowindexs.html", {
    }, function(data) {
        if (data.status > 0) {
            $.alert.show(data);
        } else {
            $.alert.show(data);
        }
    })
}

/**删除**/
Show.Del = function(sid){
    var memo = $("#show-memo" + sid).val();
    if(isEmpty(memo)){
        $.alert.warn("请填写原因!");
        return;
    }
    $.post("/manager/business/delShow.html", {
        id : sid,
        memo : $("#show-memo" + sid).val()
    }, function(result){
        $.alert.show(result,function(){
                location.reload();
        });
    })
}

