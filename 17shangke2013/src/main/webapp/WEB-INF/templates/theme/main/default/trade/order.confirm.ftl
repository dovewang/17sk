<#assign TITLE="订单信息-"/>
<#assign CATEGORY=""/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<#include "../meta.ftl" />
		<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/main/js/bis.trade.js"></script>
	</head>
	<body>
		<#if order.payType=2>
		<#assign MONEY_UNIT="元"/>
		</#if>
		<#include "../header.ftl" />
		<div class="page-container">
			<#if order.orderType==1>
			<#include "detail/order/question.ftl" />
			<#elseif order.orderType==2>
			<#include "detail/order/course.online.ftl" />
			<#elseif order.orderType==3>22222222222222222
			<#include "detail/order/course.local.ftl" />
			</#if>
			<#--线上支付，需要输入支付密码-->
			<#if order.payType=1>
			<iframe src="${SECURE_DOMAIN}/account/authz.html?fee=${order.price}&url=${SITE_DOMAIN}/trade/doConfirm.html?orderId=${order.orderId}" scrolling="no" frameborder="0" width="100%" height="50px"></iframe>
			<#else>
			<div class="p15">
				<button class="button large primary" onclick="Trade.comfirmOrder(${order.orderId})">
					订单确认
				</button>
				<button class="button large" onclick="Trade.cancleOrder(${order.orderId})">
					取消订单
				</button>
			</div>
			</#if>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>