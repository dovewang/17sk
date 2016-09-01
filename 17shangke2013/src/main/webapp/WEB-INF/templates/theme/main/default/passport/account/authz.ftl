<!DOCTYPE html>
<html>
	<head>
		<#assign SECURE=true/>
		<#include "/base.ftl" />
		<link href="${SITE_DOMAIN}/theme/main/default/css/default.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
	</head>
	<body style="background:none">
		<#assign fee=(RequestParameters["fee"]!0)?number?int/>
		<#assign url=RequestParameters["url"]! />
        <#assign isParent=RequestParameters["parent"]! />
		<div class="text-right">
			<form id="authorizeForm" action="${SECURE_DOMAIN}/account/authorize.html" method="post"  onsubmit="return false">
				可用余额 ：<span class="money">${__USER.getAttribute("balance")!}</span>${MONEY_UNIT}<span> <#if  (fee>__USER.getAttribute("balance"))>
					(<a href="#" onclick="Security.balanceMore()">余额不足，立即充值</a>)</#if> </span>&nbsp;&nbsp;
				<input type="hidden" id="url" name="url" value="${url}">
				请输入支付密码：
				<input type="password"  id="password"   name="${__USER.getAttribute("authorizeOnceKey")!}" style="width:80px;"/>
				<button class="button primary large" type="button" onclick="Security.authorize(${isParent})">
					确定
				</button>
				<a href="#" onclick="Security.loseAccount()" >忘记支付密码</a>
			</form>
		</div>
	</body>
</html>