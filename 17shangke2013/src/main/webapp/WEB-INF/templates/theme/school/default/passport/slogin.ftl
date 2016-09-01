<#include "/conf/config.ftl" /> 
<script src="${IMAGE_DOMAIN}/theme/base/js/sha256.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<script src="${IMAGE_DOMAIN}/theme/base/js/flint.sec.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<div class="popup-header">
	<a href="#" class="close" action-type="close">×</a>
	<div>
		<div  style="background-image:url(${site.logo})" class="logo-background"></div>
	</div>
</div>
<div class="popup-body">
	<form name="loginForm" method="post" onsubmit="return false">
		<input id="forwardUrl" name="forwardUrl" type="hidden" value="${RequestParameters["url"]?default("/index.html")}" />
		<div id="login_message" class="warn"></div>
		<dl class="form">
			<dd >
				<label>用户名：</label><span>
					<input type="text" name="userName" id="userName"  placeholder="请输入登录邮箱" class="input"/>
				</span>
			</dd>
			<dd>
				<label>密 码：</label><span>
					<input name="password" type="password" id="password" value="" placeholder="请输入登录密码" class="input"/>
				</span>
			</dd>
			<dd>
				<label>验证码：</label><span>
					<input type="text" name="captcha" id="captcha"  placeholder="请输入验证码" class="input"/>
					<em><img  src="/captcha?${.now}" id="captcha_image" width="80" height="33"  style="cursor:pointer" title="看不清楚？换一个" onclick="Security.captcha()"/>
					<br />
					<a href="#" onclick="return Security.captcha();">看不清楚，换一张</a></em> </span>
			</dd>
		</dl>
	</form>
</div>
<div class="popup-footer" style="padding-left:80px;">
	<label >
		<input name="remeberme" type="checkbox"  class="floatLeft" id="remeberme" value="1"/>
		记住我</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="forget_pw">忘记密码</a>
	<button onclick="Security.login()" >
		登录
	</button>
</div>