<#include "/conf/config.ftl" />
<#--教室内部专用文件选择器-->
<table class="table table-striped ">
	<thead>
		<tr>
			<th>文件名</th>
			<th>大小</th>
			<th>时间</th>
		</tr>
	</thead>
	<tbody>
		<#list result.result as d>
		<#assign isConvert=false />
		<tr id="doc${d.doc.docId}"  onmouseover="DM.mouse(this,'d${d.doc.docId}','over')"  onmouseout="DM.mouse(this,'d${d.doc.docId}','out')"  style="cursor:pointer">
			<td><img src="${IMAGE_DOMAIN}/theme/base/images/filetype/s/${d.extend}.png"  width="16" height="16" alt="${d.name}" /><span id="dn${d.doc.docId}"> ${d.name} 
               <#if !helper.isImage("a."+d.extend)>
                <#if (d.metadata?exists)>
				   <#if d.metadata=="EXCEPTION"> 
                       <span style="color: red">(文档转换失败)</span>
                   <#else>
				    <#assign isConvert=true />
                    </#if>
				<#else><span style="color: green">(文档正在转换中)</span>
				</#if></#if> </span><span class="con_mousehover" id="d${d.doc.docId}" style="visibility:hidden"> [
				<#if helper.isImage("a."+d.extend) > <a href="javascript:void(0)" onclick="parent.abcbox.loadImage(${d.doc.docId},1,'${d.http!}');return false;">插入图片</a>|
				<#else>
				<#if isConvert>
				<#assign metadata=Q.toJSON(d.metadata)/> <a href="javascript:void(0)" onclick="parent.abcbox.loadDocument(${d.doc.docId},${metadata.pages},'${d.extend}');return false;">载入文档</a>| </#if>
				</#if> <!--<a href="javascript:void(0)" onclick="DM.share('${d.doc.docId}');return false;">共享</a>|  --><a href="javascript:void(0)" onclick="DM.showRename('${d.doc.docId}',true,true);return false;">重命名</a> | <a href="javascript:void(0)" onclick="DM.memo('${d.doc.docId}');return false;">备注</a> | <a href="javascript:void(0)" onclick="DM.del(this,'${d.doc.docId}','doc${d.doc.docId}');return false;">删除</a> ] </span></td>
			<td width="80">${helper.fileSize(d.size)}</td>
			<td width="120">${flint.timeToDate(d.createTime,"yyyy-MM-dd HH:mm:ss")}</td>
		</tr>
		<tr class="active" id="rn${d.doc.docId}" style="display:none">
			<td>
			<input type="text" name="name" id="dname${d.doc.docId}" class="input"value="${d.name}" style="width:70%" maxlength="20"/>
			<span class="con_mousehover">[ <a href="javascript:void(0)" onclick="DM.rename('${d.doc.docId}',true);return false;">确定</a> | <a href="javascript:void(0)" onclick="DM.showRename('${d.doc.docId}',false,true);return false;">取消</a> ] </span></td>
			<td width="80">${helper.fileSize(d.size)}</td>
			<td width="120">${flint.timeToDate(d.createTime,"yyyy-MM-dd HH:mm:ss")}</td>
		</tr>
		</#list>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="3"><@flint.simplepage  result,"" /></td>
		</tr>
	</tfoot>
</table>
