<#assign type=messageType!0 />
<!DOCTYPE html>
<html>
	<head>
		<#include "../meta.ftl" />
		<link href="${IMAGE_DOMAIN}/theme/main/skins/default/css/zone.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
		<link href="${IMAGE_DOMAIN}/theme/main/default/css/private_message.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
	</head>
	<body class="zone-page">
		<#include "header.ftl" />
		<div class="page-container">
			<div class="page-body">
				<div class="page-left">
					<#include "left.ftl" />
				</div>
				<div class="page-main">
					<div class="m10 box">
						<div class="tab-header">
							<ul id="message-tab">
								<li <#if type==0>class="active"</#if>>
									<a href="javascript:;" onclick="MC.getMessagePage(0);">私信</a>
								</li>
								<li <#if type==1>class="active"</#if>>
									<a href="javascript:;" onclick="MC.getMessagePage(1);">学校公告</a>
								</li>
								<li <#if type==2>class="active"</#if>>
									<a href="javascript:;" onclick="MC.getMessagePage(2);">系统消息</a>
								</li>
							</ul>
						</div>
						<div id="message-privateview">
							<#include "messageList.ftl"/>
						</div>
					</div>
				</div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>