<#assign TITLE="订单信息-"/>
<#assign CATEGORY=""/>
<!DOCTYPE html>
<html>
	<head>
		<#include "../../meta.ftl" />
	</head>
	<body>
		<#include "../../header.ftl" />
		<div class="page-container">
			<#if order.orderType==1>
			<#include "order/question.ftl" />
			<#elseif order.orderType==2>
			<#include "order/course.online.ftl" />
			<#elseif order.orderType==3>
			<#include "order/course.local.ftl" />
			</#if>
		</div>
		<#include "../../footer.ftl"/>
	</body>
</html>