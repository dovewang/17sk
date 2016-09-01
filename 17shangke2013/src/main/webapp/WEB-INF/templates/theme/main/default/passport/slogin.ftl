<!DOCTYPE html>
<html>
	<head>
		<#assign SECURE=true/>
		<#include "../meta.ftl" />
		<style type="text/css">
			#loginForm .input {
				height: 25px;
				width: 300px;
				line-height: 25px;
			}
		</style>
	</head>
	<body style="background: #ffffff">
		<div class="pop-header" style="position:relative;">
			<h3>用户登录</h3>
			<div style="position:absolute;right:15px; bottom:10px; width:150px;">
				还没有17账号？<a href="${SECURE_DOMAIN}/register.html" class="parent-link a1">立即注册</a>
			</div>
		</div>
		<div class="pop-body">
			<form id="loginForm" name="loginForm" method="post" onsubmit="return false">
				<input id="forwardUrl" name="forwardUrl" type="hidden" value="${RequestParameters["url"]?default(HTTP_DOMAIN+"/index.html")}" />
				<div id="login_message" class="error"></div>
				<dl class="form label60 input-middle">
					<dd >
						<label>用户名：</label><span>
							<input type="text" name="userName" id="userName"  autocomplete="off" placeholder="请输入登录邮箱" class="input"/>
						</span>
					</dd>
					<dd>
						<label>密 码：</label><span>
							<input name="password" type="password" id="password" value="" placeholder="请输入登录密码" class="input"/>
						</span>
					</dd>
					<dd>
						<label>验证码：</label><span>
							<input type="text" autocomplete="off" name="captcha" id="captcha"  placeholder="请输入验证码" class="input" style="width:120px"/>
							<em><img  src="${SITE_DOMAIN}/captcha?${.now}" id="captcha_image" width="80" height="33"  style="cursor:pointer" title="看不清楚？换一个" onclick="Security.captcha()"/>
							<br />
							<a href="#" onclick="return Security.captcha();">看不清楚，换一张</a></em> </span>
					</dd>
					<dd>
						<label>&nbsp;</label><span> <label>
								<input name="remeberme" type="checkbox" id="remeberme"/>
								记住登录状态</label>&nbsp;&nbsp;&nbsp;&nbsp;<a class="parent-link" href="${SECURE_DOMAIN}/recovery.html">忘记密码</a> </span>
					</dd>
					<dd>
						<label>&nbsp;</label><span>
							<button  type="submit" class="button primary large" id="button" onclick="Security.login(true)">
								登录
							</button></span>
					</dd>
				</dl>
			</form>
			<div id="login-social" style="margin-top:5px">
				<p>
					您也可以使用以下账号登录
				</p>
				<ul>
					<li class="sina">
						<a class="parent-link" href="${SITE_DOMAIN}/social/sina/authz.html?url=${RequestParameters["url"]?default(HTTP_DOMAIN+"/index.html")}">新浪微博</a>
					</li>
					<li class="qq">
						<a class="parent-link" href="${SITE_DOMAIN}/social/qq/authz.html?url=${RequestParameters["url"]?default(HTTP_DOMAIN+"/index.html")}">QQ</a>
					</li>
				</ul>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
	$("a.parent-link").click(function(event) {
		event.preventDefault();
		window.parent.location.href = this.href;
	})
</script>