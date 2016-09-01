/**
 *
 * 功能描述:文档管理器
 *
 * 日 期：2011-12-7 13:21:54<br>
 *
 * 作 者：Dreamall<br>
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
DM = {};

DM.refresh = function() {
	$("#documentAll").trigger("refresh");
}
/*激活单行的操作*/
DM.mouse = function(li, id, overOrout) {
	document.getElementById(id).style.visibility = (overOrout == "over") ? "visible" : "hidden";
	li.className = (overOrout == "over") ? "active" : "";
	li.setAttribute("class", li.className);
}
/*共享*/
DM.share = function(docId) {
}
/*显示修改名称的层*/
DM.showRename = function(docId, show, tr) {
	if (show) {
		$("#rn" + docId).show();
		if (tr) {
			$("#doc" + docId).hide();
		} else {
			$("#dn" + docId).hide();
		}
	} else {
		$("#rn" + docId).hide();
		if (tr) {
			$("#doc" + docId).show();
		} else {
			$("#dn" + docId).show();
		}
	}
}
/*改变文件名*/
DM.rename = function(docId, tr) {
	var name = $("#dname" + docId).val();
	if (isEmpty(name)) {
		$.alert.warn("文件名不能为空！")
		return;
	}
	$.post("/doc/rename.html", {
		id : docId,
		name : name
	}, function(data) {
		$.alert.show(data, function() {
			if (data.status > 0) {
				$("#dn" + docId).text(name);
				DM.showRename(docId, false, tr);
			}
		});
	});
}
/*添加备注*/
DM.memo = function(docId) {
}
/*删除文件*/
DM.del = function(obj,docId, slideId) {
	var options = {
			effect : "mouse",
			trigger : $(obj)
		};
	$.alert.confirm("您确定要删除该文档？", [
	function() {
		$.post("/doc/delete.html",{id:docId}, function(data) {
			$.alert.show(data, function() {
				if (slideId && data.status > 0) {
					$("#" + slideId).slideUp();
				}
			},options);
		});
	}],options);
}

DM.upload = function(callback,obj) {
	if (isEmpty($("#doc_file").val())) {
		$.alert.warn("请您选择上传文件")
		return;
	}
	if (isEmpty($("#name").val())) {
		$.alert.warn("文件名不能为空！")
		return;
	}
	if(!$("#doc_file").val().toLowerCase().is(/\.(gif|jpg|jpeg|bmp|png|ppt|pptx)$/)){
		$.alert.warn("您上传的文档格式不符合要求！");
		return;
	}

	$("#documentForm").postForm(function(data) {
		$.alert.show(data, function() {
			if (data.status ==1) {
				if (callback) {
					eval(callback + "()");
				}
				$(obj).trigger('pop-close');
			}
		})
	});
}
/*添加文件标签*/
DM.addTag = function() {

}
/*文档检索，只按文件名检索*/
DM.search = function() {
	var $active = $("#docTab  li.active");
	var $href = $active.find("a");
	var href = $href.attr("href");
	if (href) {
		if (!$href.attr("oldhref")) {
			$href.attr("oldhref", href);
		}
		var ohref = $href.attr("oldhref") || href;
		var v = $("#q").val();
		if (!isEmpty(v)) {
			$href.attr("href", ohref + "&q=" + encodeURIComponent(v)).trigger("refresh");
		} else {
			$href.attr("href", ohref).trigger("refresh");
		}
	}

}
/*将文件拖拽到相关的标签*/
DM.dragToTag = function() {
}