<!DOCTYPE html>
<html>
	<head>
		<#include "/conf/site.ftl" />
		<#import "/lib/flint.ftl" as flint >
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title> ${SUBJECT!"通知"}
			-
			${SITE_NAME} </title>
		<style type="text/css">
			#container {
				border: 1px solid #c3eafb;
			}
			#top {
				background-image: url('${IMAGE_DOMAIN+"/"+THEME_PATH}/images/mail/bg_top.jpg');
				background-repeat: repeat-x;
				height: 80px;
			}
			#wraper {
				background-image: url('${IMAGE_DOMAIN+"/"+THEME_PATH}/images/mail/bg_content.jpg');
				background-repeat: repeat-x;
				background-position: center bottom;
				padding: 15px 8px;
			}
			#content {
				min-height: 200px;
				border: 1px solid #c3eafb;
				color: #666666;
				font-size: 14px;
				padding: 5px;
			}
			#sign {
				margin-top: 15px;
				padding: 5px;
				color: #ccc;
				border-top: 1px solid #ccc;
				text-align: center;
			}
			#content a {
				color: #2087c8;
			}
			#bottom {
				background-image: url('${IMAGE_DOMAIN+"/"+THEME_PATH}/images/mail/bg_bottom.jpg');
				background-repeat: repeat-x;
				height: 118px;
			}
		</style>
	</head>
	<body>
		<div id="container">
			<div id="top">
				<div id="logo" style="background-image:url(${SITE_LOGO})"></div>
			</div>
			<div id="wraper">
				<div id="content">
					<br />
					<#include "/email/${template}.ftl"/>
					<div id="sign" style="text-align:left; padding:10px;">
						${SITE_NAME}
					</div>
				</div>
			</div>
			<div id="bottom"></div>
		</div>
	</body>
</html>