<!DOCTYPE html>
<html>
	<head>
		<#include "head.ftl" />
		 <script src="${IMAGE_DOMAIN}/theme/room/js/abc.js?${VERSION}" type="text/javascript"></script>
		<script src="${IMAGE_DOMAIN}/theme/base/js/bis.doc.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<link href="${IMAGE_DOMAIN}/theme/room/css/default.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
		<script src="${IMAGE_DOMAIN}/theme/base/js/bis.exercise.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<style type="text/css">
			<!--
			body {
			margin: 0px auto;
			text-align:center;
			}
			#documentManager,#exerciseManager,#exerciseResult {
			width:800px;
			height:520px;
			}
			#paper{
			width:830px;
			height:550px;
			top:1px;
			}
			-->
		</style>
	</head>
	<body>
		<div>
			<iframe  name="abcbox" src="${SITE_DOMAIN}/abc/start.html?roomId=${RequestParameters["roomId"]}" width="100%" height="720" frameborder="0"></iframe>
		</div>
	</body>
</html>
<script type="text/javascript">
	self.moveTo(0, 0)
	self.resizeTo(screen.availWidth, screen.availHeight);

</script>
