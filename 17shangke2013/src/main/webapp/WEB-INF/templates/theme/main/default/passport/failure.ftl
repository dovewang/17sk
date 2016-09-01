<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head><#assign SECURE=true/>
		<#include "../meta.ftl" />
	</head>
	<#assign t=RequestParameters["t"]! />
	<body>
		<#include "nologin.header.ftl" />
		<div class="page-container">
			<div id="course_top_ad">
				<!--广告位-->
			</div>
			<div class="page-body">
				<div class="page-left">
					<div class="box">
						<#if t="active">
						很遗憾，您的链接可能已经失效，请重新验证
						<#elseif  t="recovery">
						很抱歉您用户名和邮箱可能匹配
						<#elseif  t="password.recovery">
						很遗憾，您的链接可能已经失效，请<a href="${SECURE_DOMAIN}/recovery.html" class="button">重新验证</a>
						<#elseif  t="password.reset" >
						网络繁忙，请稍候再试……
						</#if>
					</div>
				</div>
				<div class="page-right"></div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>
<!--
<script type="text/javascript" src="广告代码"></script>
-->