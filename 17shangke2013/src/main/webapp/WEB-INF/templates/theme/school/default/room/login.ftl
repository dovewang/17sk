<#include "/conf/config.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${SITE_NAME}</title>
		<link href="${IMAGE_DOMAIN+"/"+THEME_PATH}/css/global.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}" />
		<link href="${IMAGE_DOMAIN+"/"+THEME_PATH}/css/login.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
		<script  type="text/javascript">
window.Env= {
VERSION:"${VERSION}",TIME:"2010-11-24",SITE_DOMAIN:"${SITE_DOMAIN}",SITE_NAME:"${SITE_NAME}",IMAGE_DOMAIN:"${IMAGE_DOMAIN}",FILE_DOMAIN:"${FILE_DOMAIN}",SECURE_DOMAIN:"${SECURE_DOMAIN}",THEME_PATH:"${THEME_PATH}"
}
		</script>
		<script src="${IMAGE_DOMAIN}/theme/base/js/jquery.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<script src="${IMAGE_DOMAIN}/theme/base/js/flint.core.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<script src="${IMAGE_DOMAIN}/theme/base/js/flint.pngfix.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<script src="${IMAGE_DOMAIN}/theme/base/js/sha256.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<!--[if lt IE 9]>
		<script src="${IMAGE_DOMAIN}/theme/base/js/html5.js"></script>
		<![endif]-->
	</head>
	<body style="overflow:hidden">
		<div id="login_wrap">
			<div id="login_con">
				<form name="loginForm" method="post" action="" >
					<div id="logobox">
						<div id="loginlogo"></div>
						<div id="loginlogo_ex"></div>
						<div class="clear"></div>
					</div>
					<div id="loginbox">
						<div id="inputtext_box">
							<div class="warn_text" id="login_message">${result}</div>
							<div id="inputlist_box">
								<ul>
									<li>
										  <label style="width:100px">教室密码：</label><span class="s1">
											<input name="v" type="password" id="password" value="" placeholder="请输入登录密码" class="input_defalut"/>
										</span>
									</li>
								</ul>
								<div class="clear"></div>
							</div>
							<div id="inputtext_bottom"></div>
						</div>
							<div id="login_function">
							<div>
								<input name="提交" type="submit"  class="but_login" id="button" onclick="login()" value="登录"/>
							</div>
							<div class="clear"></div>
						</div>
					</div>
				</form>
				<!--loginbox end-->
			</div>
		</div>
	</body>
</html>
