<#include "/conf/config.ftl" />
<table width="100%" class="table table-striped table-bordered">
	<thead>
		<tr>
			<th width="200">班级名称</th>
			<th width="30%">班级简介</th>
			<th>备注</th>
			<th style="width:100px">操作</th>
		</tr>
	</thead>
	<tbody>
		<#if result.totalCount==0>
		<tr>
			<td colspan="5"><div class="placeholder-box">暂时还没有班级信息</div></td>
		</tr>
		<#else>
		<#list result.result as c>
		<tr id="classItem${c.groupId}">
			<td><a href="/admin/user.html?classid=${c.groupId}">${c.name}<a></td>
			<td>${c.intro}</td>
			<td>${c.memo!}</td>
			<td>
			<a href="/admin/class/form.html?id=${c.groupId}" pop-id="pop-classform" node-type="pop">编辑</a> | 
			<a href="javascript:;" node-type="pop-confirm" data-message="您确定要删除该信息？" ok="School.deleteClass(${c.groupId})">删除</a>
			</td>
		</tr>
		</#list>
		</#if>
	</tbody>
</table>
<@flint.pagination result,"","","" />