/**
 *
 * 功能描述：后台功能模块封面JS<br>
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
Cover = {
};

/**封面删除**/
Cover.del = function(id){
	$.post("/manager/cover/del.html", {
        ids : id
    }, function(result) {
    	$.alert.show(result,function(){
			$("#manager-cover-" + id).slideUp();
		});
    });
}

/**封面上传**/
Cover.upload = function(){
	Pop.show({
		id : "cover_upload",
		url : "/manager/data/upload.cover.html",
		template : false,
		css : {
			width : 350
		},
		afterShow : function($pop, options){
			$pop.find("[node-type=add-cover]").bind("click", function(){
				var $html = "<input type='file' name='file'/>";
				$("#file-text").append($html);
			})
			$pop.find("[node-type=ok-cover]").one("click", function(){
				$("#cover-upload-form").postForm({
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
