<!DOCTYPE html>
<html>
	<head>
		<#assign SECURE=true/>
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
				<h1 class="clearfix">快速注册
				<div class="step float-right">
					<ul>
						<li class="first active">
							<span><font> 1. </font>填写注册信息</span><i></i>
						</li>
						<li class="last">
							<span><font> 2. </font>完成注册</span><i></i>
						</li>
					</ul>
				</div></h1>
				<div class="page-left">
					<form id="registerForm" name="registerForm" method="post" action="${SECURE_DOMAIN}/signup.html"  data-validator-url="${SECURE_DOMAIN}/v/register.json">
						<dl class="form  input-xlarge" style="width:650px">
							<dd>
								<label>邮箱地址：</label><span>
									<input type="text" autocomplete="off" id="email" name="email" class="input"/>
								</span>
							</dd>
							<dd>
								<label>昵称：</label><span>
									<input  type="text" autocomplete="off" id="userName" name="userName"  class="input" maxlength="12"/>
								</span>
							</dd>
							<dd id="suggestName" style="display:none">
								<label>&nbsp;</label><span>
									<div class="box" style="margin:0">
										<strong>您可以重新输入或选择：</strong><ul  id="suggestNameList"></ul>
									</div> </span>
							</dd>
							<dd>
								<label>注册身份：</label>
								<span> <label>
										<input type="radio"   name="userType" value="1"  checked="checked"/>
										学生</label><label>
										<input type="radio"   name="userType"  value="2"/>
										老师</label><label>
										<input type="radio"   name="userType"  value="3"/>
										家长</label> </span>
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
										我已看过并同意接受<a href="javascript:void(0)" onclick="$('#register_agreement').toggle()"  title="点击查看'用户网络服务协议'内容">用户网络服务协议</a></label></span>
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
						<a href="${SECURE_DOMAIN}/login.html" class="button  large">立即登录</a>
					</div>
					<p>
						因材"挑"教，来17上课挑选您专属老师，全程真人教学，课堂视频随时回顾，为网校学员提供更加优质的学习管理服务。如果您很优秀，您可以在这里展示自己，
						开办自己的教学课堂，获取丰厚的报酬。
					</p>
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
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
	function showSuggestName(ns) {
		var $nlist = $("#suggestNameList");
		var html = [];
		for (var i = 0; i < ns.length; i++) {
			html.push("<li sname='" + ns[i] + "'><input type='radio'  name='sname'  value='" + ns[i] + "' />" + ns[i] + "</li>");
		}
		$nlist.html(html.join("")).find("li").bind("click", function() {
			$("#userName").val($(this).attr("sname"));
			$("#suggestName").hide();
			$("#userName").blur();
		});
		if(ns.length>0)
		  $("#suggestName").show();
	}
</script>