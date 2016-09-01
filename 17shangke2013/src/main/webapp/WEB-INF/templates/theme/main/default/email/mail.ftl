<!DOCTYPE html>
<html>
	<head>
		<#include "/conf/site.ftl" />
		<#import "/lib/flint.ftl" as flint >
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${SUBJECT!"通知"}-${SITE_NAME}</title>
		<style type="text/css">
			a, a:link, a:visited {
				color: #fff;
				text-decoration: none;
			}
			a:hover {
				color: #f60;
			}
			#blackboard a {
				color: #2087c8;
			}
			#sign {
				margin-top: 15px;
				padding: 5px;
				color: #ccc;
				border-top: 1px solid #ccc;
				text-align: center;
			}

		</style>
	</head>
	<body>
		<div id="container" style="background:#2E4442;">
			<div id="wraper" style="width:720px;margin:0 auto;padding:15px 8px; text-align:center">
				<div id="top" style="text-align:center"><img src="${SITE_DOMAIN}/theme/main/default/images/newsletter-top.png" />
				</div>
				<div id="content" style="background:#fff ;width:688px;">
					<div id="blackboard-top" style="background:url(${SITE_DOMAIN}/theme/main/default/images/newsletter-bg.png) no-repeat;*background-image:url(${SITE_DOMAIN}/theme/main/default/images/newsletter-bg.gif); height:30px"></div>
					<div id="blackboard" style="background:#fff url(${SITE_DOMAIN}/theme/main/default/images/newsletter-bg1.png) repeat-y;color:#444; text-align:left; padding:20px 25px 50px 25px">
						<#include "/email/${template}.ftl"/>
						<p>
							此致
						</p>
						<p align="right" style="padding-right:50px; padding-top:30px;">
							一起上课团队
							<br/>
							${.now?date}
						</p>
					</div>
					<div id="blackboard-bottom" style="background:url(${SITE_DOMAIN}/theme/main/default/images/newsletter-bg.png) 0 bottom no-repeat;*background-image:url(${SITE_DOMAIN}/theme/main/default/images/newsletter-bg.gif);height:30px"></div>
				</div>
				<div id="bottom" style="position:relative;text-align:center">
					<img src="${SITE_DOMAIN}/theme/main/default/images/newsletter-bottom.png" />
					<div style="position:absolute;top:5px;left:150px;">
						Copyright Reserved 2011-2022© <a href="http://www.17shangke.com">一起上课www.17shangke.com</a>
					</div>
				</div>
			</div>
		</div>
		</div>
	</body>
</html>