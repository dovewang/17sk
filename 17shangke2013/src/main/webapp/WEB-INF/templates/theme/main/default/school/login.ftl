<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<#include "../meta.ftl" />
	</head>
	<body>
		<#include "../nologin.header.ftl" />
		<div class="page-container">
			<div class="page-body register-flow box">
				<div class="page-left">
					<form id="registerForm" name="registerForm" method="post" action="${SECURE_DOMAIN}/signup.html" >
						<dl class="form  input-xlarge" style="width:650px">
							<dd>
								<label>用户名：</label><span>
									<input type="text" id="email" name="email" />
								</span>
							</dd>
							<dd>
								<label>密码：</label>
								<span>
									<input type="password" id="password" name="password" class="password"/>
								</span>
							</dd>
							<dd>
								<label>&nbsp;</label>
								<span><label><input name="accept" type="checkbox"  id="accept" class="accept" />记住用户名</label></span>
							</dd>
							<dd>
								<#include "/agreement/register.ftl" />
							</dd>
							<dd>
								<label>&nbsp;</label>
								<span>
									<button type="button" class="button primary large"    onclick="Security.sign()" >
										马上注册
									</button></span>
							</dd>
						</dl>
					</form>
				</div>
				<div class="page-right">
					<div style="margin-top:30px;">
						<h2>已有一起上课账号？</h2>
					</div>
					<div style="margin-top:30px;">
						<a href="/login.html" class="button  large">立即登录</a>
					</div>
					<p>
						因材"挑"教，来17上课挑选您专属老师，全程真人教学，课堂视频随时回顾，为网校学员提供更加优质的学习管理服务。如果您很优秀，您可以在这里展示自己，
						开办自己的教学课堂，获取丰厚的报酬。
					</p>
				</div>
			</div>
		</div>
	<#include "../footer.ftl"/>
	</body>
</html>