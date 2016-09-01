<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head><#assign SECURE=true/>
		<#include "../meta.ftl" />
	</head>
	<body>
		<#include "nologin.header.ftl" />
		<div class="page-container">
			<div class="page-body register-flow  box">
				<h1>重设密码</h1>
				<div class="page-left">
					<form  id="recoveryForm"   method="post" action="${SECURE_DOMAIN}/password.reset.html"  >
						<input type="hidden" name="id" value="${RequestParameters["id"]!}" />
						<input type="hidden" name="ac" value="${RequestParameters["ac"]!}" />
						<dl  class="form input-xlarge">
							<dd>
								<label><em class="required">*</em> 新&nbsp;密&nbsp;码：</label><span>
									<input type="password" id="newpassword" name="newpassword"   class="input password"/>
								</span>
							</dd>
							<dd>
								<label><em class="required">*</em> 密码确认：</label><span>
									<input type="password" id="repeartpassword" name="repeartpassword"   class="input password"/>
								</span>
							</dd>
							<dd>
								<label>&nbsp;</label><span>
									<button  class="button  primary large"  type="button"   onclick="Security.resetPassword()">
										重置密码
									</button></span>
							</dd>
						</dl>
					</form>
				</div>
				<div class="page-right"></div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>
<script type="text/javascript">
	Validation.add("recoveryForm", {
		newpassword : {
			info : "请您输入新密码",
			required : "请您输入新密码"
		},
		repeartpassword : {
			info : "请您确认新密码",
			required : "请您确认新密码"
		}
	});

</script>