<!DOCTYPE html>
<html>
	<head>
		<#include "../header.ftl"/>
	</head>
	<body>
		<#include "../top.ftl"/>
		<div id="page-main">
			<div class="tabbable tabs-left" style="padding-left:10px; height:100%">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#school_A" data-toggle="tab">学校管理</a>
					</li>
					<li>
						<a href="#school_B" data-toggle="tab">主题管理</a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane" id="school_A" style="display:block">
						<div style="padding:10px;">
							<a class="btn btn-danger" node-type="pop" href="/manager/site/view.html?schoolId=0&add=true" id="schoolView" title="注册新的学校" pop-id="pop-schoolForm">注册新的学校</a>
						</div>
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
									<td><a href="http://${school.domain}.17shangke.com" target="_blank"> ${school.domain}
									.17shangke.com</a></td>
									<td><#if school.status==1>正常<#elseif school.status==-1>冻结<#elseif school.status==-4>审核不通过</#if></td>
									<td>${(school.createTime*1000)?number_to_datetime?string("yyyy-MM-dd HH:mm")}</td>
									<td>
									<a href="/manager/site/view.html?schoolId=${school.schoolId}" node-type="pop" pop-id="pop-schoolForm"><i class="icon-edit"></i>编辑</a>
									<#if school.status==-1>
										<a href="javascript:;" domain="${school.domain}" onclick="School.unfreeze(this)"><i class="icon-lock"></i><font style="color:red">解冻</font></a>
									<#else>
										<a href="javascript:;" domain="${school.domain}" onclick="School.freeze(this)"><i class="icon-lock"></i>冻结</a>
									</#if>
									<a href="javascript:;" domain="${school.domain}" onclick="School.refresh(this)"><i class="icon-refresh"></i>刷新</a>
									<a href="javascript:;" domain="${school.domain}" sid="${school.schoolId}" onclick="School.verify(this)"><i class="icon-refresh"></i>审核</a>
									</td>
								</tr>
								</#list>
							</tbody>

						</table>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>