<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head><#assign SECURE=true/>
		<#include "../meta.ftl" />
	</head>
	<#assign step=RequestParameters["s"]!"1" />
	<body>
		<#include "nologin.header.ftl" />
		<div class="page-container">
			<div class="page-body register-flow  box">
				<h1>找回密码</h1>
				<div class="page-left">
					<#if step=="1">
                    <div id="recoveryMessage"  class="alert alert-error hide"></div>
					<form  id="recoveryForm"   method="post" action="${SECURE_DOMAIN}/recovery.html?s=1&g=true"  >
						<dl  class="form input-xlarge">
							<dd>
								<label>用户名：</label><span>
									<input type="text" id="userName" name="name"  />
								</span>
							</dd>
							<dd>
								<label>邮箱：</label><span>
									<input type="text" id="email" name="email" />
								</span>
							</dd>
							<dd>
								<label>&nbsp;</label><span>
									<button type="button" class="button  primary large" onclick="Security.recovery()">
										发送邮件
									</button></span>
							</dd>
						</dl>
					</form>
					<#elseif step=="2">

					</#if>
				</div>
				<div class="page-right"></div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>