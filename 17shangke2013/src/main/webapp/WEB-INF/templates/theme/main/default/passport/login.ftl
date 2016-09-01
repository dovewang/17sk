<!DOCTYPE html >
<html>
	<head>
		<#assign SECURE=true/>
		<#include "../meta.ftl" />
		<style type="text/css">
			#loginForm .input {
				height: 30px;
				width: 300px;
				line-height: 30px;
			}
		</style>
	</head>
	<body style="background: #ffffff">
		<#include "nologin.header.ftl" />
		<div class="page-container">
			<div class="page-body register-flow box">
				<h1>用户登录</h1>
				<div class="page-left">
					<form id="loginForm" name="loginForm" method="post" onsubmit="return false">
						<input id="forwardUrl" name="forwardUrl" type="hidden" value="${RequestParameters["url"]?default(HTTP_DOMAIN+"/index.html")}" />
						<div id="login_message" class="error hide" ></div>
						<dl class="form label60 input-middle">
							<dd >
								<label>用户名：</label><span>
									<input type="text" name="userName" id="userName"  placeholder="请输入登录邮箱" class="input" autocomplete="off"/>
								</span>
							</dd>
							<dd>
								<label>密 码：</label><span>
									<input name="password" type="password" id="password" value="" placeholder="请输入登录密码" class="input"/>
								</span>
							</dd>
							<dd>
								<label>验证码：</label><span>
									<input type="text" autocomplete="off" name="captcha" id="captcha"  placeholder="请输入验证码" class="input" style="width:200px"/>
									<em><img  src="${SITE_DOMAIN}/captcha?${.now}" id="captcha_image" width="80" height="33"  style="cursor:pointer" title="看不清楚？换一个" onclick="Security.captcha()"alt="验证码"/>
									<br />
									<a href="#" onclick="return Security.captcha();">看不清楚，换一张</a></em> </span>
							</dd>
							<dd>
								<label>&nbsp;</label><span> <label>
										<input name="remeberme" type="checkbox" id="remeberme"/>
										记住登录状态</label>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${SECURE_DOMAIN}/recovery.html">忘记密码</a> </span>
							</dd>
							<dd>
								<label>&nbsp;</label><span>
									<button  type="submit" class="button primary large" id="button" onclick="Security.login()" style="width:80px;">
										登录
									</button></span>
							</dd>
						</dl>
					</form>
					<div id="login-social">
						<p>
							您也可以使用以下账号登录
						</p>
						<ul>
							<li class="sina">
								<a href="${SITE_DOMAIN}/social/sina/authz.html?url=${RequestParameters["url"]?default(HTTP_DOMAIN+"/index.html")}">新浪微博</a>
							</li>
							<li class="qq">
								<a href="${SITE_DOMAIN}/social/qq/authz.html?url=${RequestParameters["url"]?default(HTTP_DOMAIN+"/index.html")}">QQ</a>
							</li>
						</ul>
					</div>
				</div>
				<div class="page-right">
					<div style="margin-top:30px;">
						<h2>还没有一起上课账号？</h2>
					</div>
					<div style="margin-top:30px;">
						<a href="${SECURE_DOMAIN}/register.html" class="button  large">立即注册</a>
					</div>
					<p>
						因材"挑"教，来17上课挑选您专属老师，全程真人教学，课堂视频随时回顾，为网校学员提供更加优质的学习管理服务。如果您很优秀，您可以在这里展示自己，
						开办自己的教学课堂，获取丰厚的报酬。
					</p>
				</div>
			</div>
		</div>
	</body>
</html>