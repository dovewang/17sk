<#include "/conf/config.ftl" />
<#if list?size==0>
<div class="placeholder-box">
	这里将显示您上传的课件信息
</div>
<#else>
<#list list as data>
<dl  id="dc${data.docId}" class="list-item">
	<dt class="courseimg">
		<#if helper.isImage(data.path)>
		<img src="${data.path}"  width="64" height="64" alt="${data.name}" />
		<#else>
		<img src="${IMAGE_DOMAIN}/theme/base/images/filetype/${data.type}.png"  width="64" height="64" alt="${data.name}" />
		</#if>
		</dd>
		<dd class="float-left">
			<strong id="dn${data.docId}">${data.name}</strong>
			<span id="rn${data.docId}" style="display: none" >
				<input type="text" name="name" maxlength="20" value="${data.name}" class="input" id="dname${data.docId}"/>
				<a href="javascript:void(0)" onclick="DM.rename('${data.docId}');return false;">确定</a> | <a href="javascript:void(0)" onclick="DM.showRename('${data.docId}',false);return false;">取消</a></span>
			<br />
			上传时间：${data.uploadTime} | 文件类型：${data.type} | 文件大小：${helper.fileSize(data.size)}
			<br />
			<a href="#">预览</a> | <a href="javascript:void(0)" onclick="DM.showRename('${data.docId}',true);return false;">重命名</a> | <a href="javascript:void(0)" onclick="DM.del('${data.docId}','dc${data.docId}');return false;">删除</a>
		</dd>
</dl>
</#list>
</#if>