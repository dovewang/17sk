<!DOCTYPE html>
<html>
	<head><#assign SECURE=true/>
		<#include "../meta.ftl" />
		<style type="text/css">
			#registerForm .input {
				height: 30px;
				width: 300px;
				line-height: 30px;
			}
		</style>
	</head>
	<body>
		<#include "nologin.header.ftl" />
		<div class="page-container">
			<div class="page-body register-flow box">
				<h1 class="clearfix">学校注册
				<div class="step float-right">
					<ul>
						<li class="first active">
							<span><font> 1. </font>注册账号</span><i></i>
						</li>
						<li>
							<span><font> 2. </font>验证邮箱</span><i></i>
						</li>
                        <li>
							<span><font> 3. </font>填写认证资料</span><i></i>
						</li>
                        <li>
							<span><font> 5. </font>认证审核</span><i></i>
						</li>
                        <li class="last">
							<span><font> 6. </font>申请成功</span><i></i>
						</li>
					</ul>
				</div></h1>
				<div class="page-left">
					<form id="registerForm" name="registerForm" method="post" action="${SECURE_DOMAIN}/signup.html" >
                    <input type="hidden"   name="userType" value="4" />
						<dl class="form  input-xlarge" style="width:650px">
							<dd>
								<label>邮箱地址：</label><span>
									<input type="text" id="email" name="email" class="input"/>
								</span>
							</dd>
							<dd>
								<label>昵称：</label><span>
									<input  type="text" id="userName" name="userName"  class="input"/>
								</span>
							</dd>
							<dd id="suggestName" style="display:none">
								<label>&nbsp;</label><span>
									<div class="box" style="margin:0">
										<strong>该用户名已被注册，请重新输入或选择：</strong><ul  id="suggestNameList"></ul>
									</div> </span>
							</dd>
							<dd>
								<label>设置密码：</label>
								<span>
									<input type="password" id="password" name="password"  class="password input"/>
								</span>
							</dd>
							<dd>
								<label>确认密码：</label><span>
									<input type="password" id="password1" name="password1"  class="password input" />
								</span>
							</dd>
							<dd>
								<label>&nbsp;</label>
								<span><label>
										<input name="accept" type="checkbox"  id="accept" class="accept" checked="checked" disabled="disabled" />
										我已看过并同意接受<a href="javascript:void(0)" onclick="$('#register_agreement').toggle()"  title="点击查看'用户网络服务协议'内容">学校网络服务协议</a></label></span>
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
				
				</div>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/main/js/user-v.js?${VERSION}"></script>