<#include "/conf/config.ftl" />
<!DOCTYPE html PUBLIC>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${SITE_NAME}</title>
		<link href="${STATIC_DOMAIN+"/"+THEME_PATH}/css/global.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}" />
		<link href="${STATIC_DOMAIN+"/"+THEME_PATH}/css/login.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
		<script  type="text/javascript">
         window.Env= {VERSION:"${VERSION}",SITE_DOMAIN:"${SITE_DOMAIN}",SITE_NAME:"${SITE_NAME}",SECURE_DOMAIN:"${SECURE_DOMAIN}",THEME_PATH:"${THEME_PATH}"}
		</script>
	</head>
	<body style="overflow:hidden">
		<div id="login_wrap">
			<div id="login_con">
				<form name="loginForm" method="post"  onsubmit="return false;">
					<input id="forwardUrl" name="forwardUrl" type="hidden" value="${RequestParameters["url"]?default(SITE_DOMAIN+"/index.html")}"  />
					<div id="logobox">
						<div id="loginlogo" style="background-image:url(${site.logo})"></div>
						<div id="loginlogo_ex"></div>
						<div class="clear"></div>
					</div>
					<div id="loginbox">
						<div id="inputtext_box">
							<div class="warn_text" id="login_message"></div>
							<div id="inputlist_box">
								<ul>
									<li>
										<label>用户名：</label><span class="s1">
											<input type="text" name="userName" id="userName"  placeholder="请输入登录邮箱" class="input_defalut"/>
										</span>
									</li>
									<li>
										<label>密 码：</label><span class="s1">
											<input name="password" type="password" id="password" value="" placeholder="请输入登录密码" class="input_defalut"/>
										</span>
									</li>
									<li>
										<label>验证码：</label><span class="s2">
											<input type="text" name="captcha" id="captcha"  placeholder="请输入验证码" class="input_defalut"/>
										</span><span class="yzm" id="captcha_span"><img  src="/captcha?${.now}" id="captcha_image" width="80" height="33"  style="cursor:pointer" title="看不清楚？换一个" onclick="Security.captcha();"/>
											<br />
											<a href="#" onclick="return Security.captcha();">看不清楚，换一张</a></span>
									</li>
								</ul>
								<div class="clear"></div>
							</div>
							<div id="inputtext_bottom"></div>
						</div>
						<!--inputbox end-->
						<div id="login_function">
							<label><input name="remeberme" type="checkbox"  class="floatLeft" id="remeberme" value="1"/>
							记住我</label><a href="#" class="forget_pw">忘记密码</a>
							<div>
								<input name="提交" type="submit"  class="but_login" id="button" onclick="Security.login()" value="登录"/>
							</div>
							<div class="clear"></div>
						</div>
						<!--login_function end-->
						<div id="login_footer">
							版权所有 &copy; Copyright 2011 上课
						</div>
						<div class="clear"></div>
					</div>
				</form>
				<!--loginbox end-->
			</div>
		</div>
	</body>
</html>
<script src="${STATIC_DOMAIN}/theme/base/js/jquery.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<script src="${STATIC_DOMAIN}/theme/base/js/sec.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<script src="${STATIC_DOMAIN}/theme/kiss/kiss-min.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<script src="${STATIC_DOMAIN}/theme/base/js/base.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
