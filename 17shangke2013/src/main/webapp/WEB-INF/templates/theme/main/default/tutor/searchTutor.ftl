<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<#include "../meta.ftl" />
		<script type="text/javascript" src="/theme/main/js/bis.tutor.js?${VERSION}"></script>
		<link href="${IMAGE_DOMAIN}/theme/main/skins/default/css/zone.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
	</head>
	<body class="zone-page">
		<#include "../user/header.ftl" />
		<div class="page-container">
			<div class="page-body">
				<div class="page-left">
					<#include "../user/left.ftl" />
				</div>
					<div class="page-main" id="page-center">
						<div class="box m15">
							<div id="servece-form-ftl" style="height:330px;"><#include "service.form.ftl" /></div>
							<#if result.totalCount==0>
							<div  class="nodata">
								没有发布任何信息
							</div>
							<#else>
							<div id="my-account">
							<h1 class="title">辅导服务明细</h1>
							<table class="table">
								<thead>
									<tr>
										<th>辅导科目1</th>
										<th>辅导科目2</th>
										<th>辅导科目3</th>
										<th>辅导价格(${MONEY_UNIT}/小时)</th>
										<th>浏览人数</th>
										<th>发布时间</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id = "tutor-tbody">
									<#list result.result as m>
									<tr node-type="tutor-item" id="tutor-item-${m.tutorId}" tutorid = "${m.tutorId}" intro="${m.intro!""}">
										<td id="kind1" key="${m.kind1}" align="right">${kindHelper.getKindLabel(m.kind1?j_string)}</td>
										<td id="kind2" key="${m.kind2}" align="right">${kindHelper.getKindLabel(m.kind2?j_string)}</td>
										<td id="kind3" key="${m.kind3}" align="right">${kindHelper.getKindLabel(m.kind3?j_string)}</td>
										<td id="price" key="${m.price}">
										<#if m.price==0>
											面议
										<#else>
											${m.price}
										</#if>
										</td>
										<td>${m.views}</td>
										<td>${flint.timeToDate(m.dateline,"yyyy-MM-dd HH:mm:ss")}</td>
										<td><a href="javascript:;" id = "${m.tutorId}" node-type="tutor-delete">删除</a>|<a href="javascript:;" id = "${m.tutorId}" node-type="tutor-edit">修改</a></td>
									</tr>
									</#list>
								</tbody>
							</table>
							</div>
							<@flint.pagination result,"","","" /> 
							</#if>
						</div>
					</div>
				<div class="shady" style="margin-bottom:27px"></div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>