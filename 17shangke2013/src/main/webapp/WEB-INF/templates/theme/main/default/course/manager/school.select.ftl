<div class="pop-header">
	<a node-type='pop-close' class='close' href='javascript:;'>×</a>
	<h3>学校选择</h3>
</div>
<div class="pop-body" id="room-create-div">
	<table width="98%" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>#</th>
				<th>学校名称</th>
				<th>域名</th>
				<th>状态</th>
				<th>注册时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<#list result.result as school>
			<tr>
				<td>${school.schoolId}</td>
				<td>${school.name}</td>
				<td><a href="http://${school.domain}.17shangke.com/about.html" target="_blank"> ${school.domain}
				.17shangke.com</a></td>
				<td><#if school.status==1>正常<#elseif school.status==-1>冻结<#elseif school.status==-4>审核不通过</#if></td>
				<td>${(school.createTime*1000)?number_to_datetime?string("yyyy-MM-dd HH:mm")}</td>
				<td><a href="javascript:;"  sid="${school.schoolId}" sname="${school.name}" onclick="School.select(this)" node-type="pop-close-trigger">选择</a></td>
			</tr>
			</#list>
		</tbody>
	</table>
</div>