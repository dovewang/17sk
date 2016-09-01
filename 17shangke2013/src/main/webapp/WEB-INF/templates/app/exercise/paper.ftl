<!DOCTYPE html>
<html>
	<head>
		 <#include "../abc/head.ftl" />
		<link href="${IMAGE_DOMAIN}/theme/base/css/doc.css?{VERSION}" rel="stylesheet" type="text/css" />
		<script src="${IMAGE_DOMAIN}/editor/xheditor.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<script src="${IMAGE_DOMAIN}/theme/base/js/bis.exercise.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<title>练习题</title>
	</head>
	<body>
		<div id="pop_box">
			<div class="pop_top">
				<h2 class="lianxi" title="文档管理器"></h2>
				<div class="lianxi_title">
					${RequestParameters["subject"]!"练习"}
				</div>
				<div class="lianxi_time">
					答题剩余时间：<strong>${RequestParameters["time"]!"10"}</strong>分钟
				</div>
			</div>
			<!--pop_top end-->
			<div class="pop_conbox2">
				<div class="questions_box" style="height:440px">
					<form id="exerciseForm" action="/exercise/submit.html">
						<input type="hidden" name="exerciseId" value="${RequestParameters["exerciseId"]}" id="exerciseId"/>
						<input type="hidden" name="roomid" value="${RequestParameters["roomid"]!"0"}"/>
						<#include "list.ftl" />
					</form>
				</div>
				<!--questions_box end-->
				<div class="lianxibut_box">
					<div class="but_finish_up">
						<a href="javascript:void(0)" onclick="Exercise.handOn(true);return false;">交卷</a>
					</div>
					<div class="but_checking">
						<a href="javascript:void(0)" onclick="Exercise.checkPaper('exerciseForm');return false;">检查</a>
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<!--pop_conbox end-->
		</div>
		<!--pop_box end-->
	</body>
</html>
