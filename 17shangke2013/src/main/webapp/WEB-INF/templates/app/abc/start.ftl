<!DOCTYPE html >
<html>
	<head>
		<#include "head.ftl" />
		<style type="text/css">
			#loginForm .input {
				height: 30px;
				width: 300px;
			}
			body {
				background: url(/theme/room/images/start.jpg)
			}
		</style>
	</head>
	<body>
		<div class="page-container">
			<div>
				<div id="room-start">
					<#if (room.subject?length>20)> <h1 class="longText">${room.subject}</h1>
					<#else> <h1>${room.subject}</h1>
					</#if> <h2><a href="/u/${room.host}.html" usercard="${room.host}"></a> ${flint.timeToDate(room.createTime,"yyyy年MM月dd日")}</h2>
					<p>
						<#if ( room.price>0 ) >
						您需要为该课程支付费用：<span class="money">${room.price}</span>${MONEY_UNIT}
						<div>
							<iframe src="${SECURE_DOMAIN}/account/authz.html?fee=${room.price}&parent=true&url=${SITE_DOMAIN}/abc/login.html?roomId=${room.roomId}" scrolling="no" frameborder="0" width="600px" height="50px"></iframe>
						</div>
						<#else>
						该课程免费
						</#if>
					</p>
				</div>
				<#if room.status==1>
				<#assign code=RequestParameters["e"]!"0"/>
				<#if code!="0">
				<div id="login_message" class="alert alert-error" >
					<#if code=="-1">
					进入教室需要密码
					<#elseif code=="-2">
					教室登录密码错误
					<#elseif code=="-3">
					很抱歉，您没有安排该课程，所有您不能进入教室哦
					<#elseif code=="-4">
					很抱歉，用户已在另一位置登录该教室
					<#elseif code=="-5">
					很抱歉，该教室的容量已满
					<#elseif code=="-6">
					用户身份不符
					<#elseif code="auth.error">
					授权码不存在，验证失败，请您重新授权！
					</#if>
				</div>
				</#if>
				<#if (room.price==0)>
				<#if ((room.password?exists)&&(room.password!=""))>
				<form id="loginForm" name="loginForm" method="post"   action="/abc/login.html">
					<input type="hidden" name="roomId" value="${room.roomId}" />
					<dl class="form label60 input-middle">
						<dd>
							<label>密 码：</label><span>
								<input name="v" type="password" id="password" value="" placeholder="请输入登录密码" class="input"/>
							</span>
						</dd>
						<dd>
							<label>&nbsp;</label><span>
								<button  type="submit" class="button primary large" id="button"  style="width:80px;">
									登录
								</button></span>
						</dd>
					</dl>
				</form>
				<#else> <a class="button primary large" href="/abc/login.html?roomId=${room.roomId}">继续</a>
				</#if>
				</#if>
			</div>
			<#else>
			课程已结束
			</#if>
		</div>
	</body>
</html>
<script type="text/javascript">
	self.moveTo(0, 0)
	self.resizeTo(screen.availWidth, screen.availHeight);

</script>