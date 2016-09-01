<#assign TITLE="订单确认信息-"/>
<#assign CATEGORY=""/>
<!DOCTYPE html>
<html >
	<head>
		<script type="text/javascript">
			window.history.forward(1);
		</script>
		<#include "../meta.ftl" />
		<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/main/js/bis.trade.js"></script>
		<script src="${IMAGE_DOMAIN}/theme/base/js/city-min.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
	</head>
	<body>
		<#include "../header.ftl" />
		<div class="page-container">
			<input type="hidden" id="tradeOnceKey" value="${__USER.getAttribute("tradeOnceKey")}"/>
			<div class="page-container">
				<#if     product.productType==1>
				<#include "question.ftl" />
				<#elseif product.productType==2>
				<#include "course.online.ftl" />
				<#elseif product.productType==3>
				<#include   "course.local.ftl" />
				</#if>
			</div>
			<div class="p15">
				<button class="button xlarge primary" onclick="Trade.submitOrder()">
					提交订单
				</button>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>
