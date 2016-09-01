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
School = {
};

/**
 * 冻结
 *
 */
School.freeze = function(obj){
	var options={effect:"mouse",trigger:$(obj)};
	var domain = $(obj).attr("domain");
	$.alert.confirm("确定要冻结该学校吗？", [function() {
		$.post("/manager/site/frozen.html", {
			domain : domain
		}, function(data) {
			$.alert.show(data,function(){
				if (data.status > 0) {
					$(obj).attr("onclick","School.unfreeze(this)").empty().html("<i class='icon-lock'></i><font style='color:red'>解冻</font>");
				}
			});
		});
	}],options);
}

/**
 * 解冻
 *
 */
School.unfreeze = function(obj){
	var options={effect:"mouse",trigger:$(obj)};
	var domain = $(obj).attr("domain");
	$.alert.confirm("确定要解冻该学校吗？", [function() {
		$.post("/manager/site/unfrozen.html", {
			domain : domain
		}, function(data) {
			$.alert.show(data,function(){
				if (data.status > 0) {
					$(obj).attr("onclick","School.freeze(this)").empty().html("<i class='icon-lock'></i>冻结");
				}
			});
		});
	}],options);
}

/**
 * 学校审核
 *
 */
School.verify = function(obj){
	var sid = $(obj).attr("sid");
	var domain = $(obj).attr("domain");
	Pop.show({
		id : "school_verify" + sid,
		url : "/manager/site/verify.school.html?sid=" + sid,
		template : false,
		css : {
			width : 250
		},
		afterShow : function($pop, options){
			$pop.find("[node-type=action-type]").one("click", function(){
				var verify = $("input[name=verify]:checked").val();
				var memo = $("#memo").val();
				$.post("/manager/site/verify.html", {
					schoolId : sid,
					verify : verify,
					domain : domain,
					memo : memo
				}, function(data) {
					$.alert.show(data,function(){
						if (data.status > 0) {
							location.reload();
						}
					});
				});
			})
		}
	})
}

/**
 * 刷新学校缓存
 *
 */
School.refresh = function(obj){
	var options={effect:"mouse",trigger:$(obj)};
	var domain = $(obj).attr("domain");
	$.alert.confirm("确定刷新该学校的缓存信息吗？", [function() {
		$.post("/manager/site/refresh.html", {
			domain : domain
		}, function(data) {
			$.alert.show(data);
		});
	}],options);
}