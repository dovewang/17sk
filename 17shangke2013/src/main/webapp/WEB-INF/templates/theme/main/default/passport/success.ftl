<!DOCTYPE html>
<html>
<head>
<#assign SECURE=true/>
		<#include "../meta.ftl" />
		<#assign t=RequestParameters["t"]! />
		<style type="text/css">
#success {
	padding: 20px;
	font-size: 18px;
}
.p15 {
	font-size: 14px;
}
</style>
		</head><body>
<#include "nologin.header.ftl" />
<div class="page-container">
          <div class="page-body">
    <div class="page-left">
              <div class="box"> <#if t="sign">
        <h2  id="success"> <img src="${HTTP_DOMAIN}/theme/main/default/images/success.png" width="48" height="48">恭喜您，注册成功！</h2>
        <div class="p15"> 我们已向您注册的邮箱发送了一封邮件，<br/>
                  请查收并激活您的邮箱。验证邮箱即可获赠20个
                  ${MONEY_UNIT}
                  <br/>
                  <span style="color:#f90"> 没收到？</span><a class="button primary xlarge" href="${SECURE_DOMAIN}/email.reactive.html">重新验证</a> </div>
        <#elseif  t="recovery">
        <div class="p15"> 我们已向您注册的邮箱（
                  ${RequestParameters["mail"]!}
                  ）发送了一封邮件，请及时查看重置您的密码。 </div>
        <#elseif  t="active">
        <h2  id="success"> <img src="${HTTP_DOMAIN}/theme/main/default/images/success.png" width="48" height="48">恭喜您，邮箱验证成功！</h2>
        <#elseif  t="password.reset">
        <h2  id="success"> <img src="${HTTP_DOMAIN}/theme/main/default/images/success.png" width="48" height="48">恭喜您，密码重置成功！</h2>
        </#if>
        <div> <a href="${HTTP_DOMAIN}/index.html">返回首页</a> </div>
      </div>
            </div>
    <div class="page-right"></div>
  </div>
        </div>
<#include "../footer.ftl"/>
</body>
</html>