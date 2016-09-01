<#assign TITLE="订单信息-"/>
<#assign CATEGORY=""/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<#include "../meta.ftl" />
	</head>
	<body>
		<#include "../header.ftl" />
		<div class="page-container">
			<#if order.orderType==1>
			<#include "detail/order/question.ftl" /> 
			<#elseif order.orderType==2>
			<#include "detail/order/course.online.ftl" /> 
			<#elseif order.orderType==3>
			<#include "detail/order/course.local.ftl" />
			</#if>
			<iframe src="${SECURE_DOMAIN}/account/authz.html?parent=true&fee=${order.price}&url=${(SITE_DOMAIN+"/trade/prepay.html?orderId="+order.orderId+"&fee="+order.price)?url("utf-8")}" scrolling="no" frameborder="0" width="100%" height="50px"></iframe>		
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>