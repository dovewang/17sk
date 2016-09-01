<!DOCTYPE html>
<html>
	<head>
		<#include "head.ftl" />
		<script src="/theme/base/js/swfobject.js" type="text/javascript"></script>
		<script src="/theme/room/js/room.js?${VERSION}" type="text/javascript"></script>
		<style type="text/css">
			<!--
			body {
			margin: 0px auto;
			text-align:center;
			background-image: url(/wall.png);
			background-repeat: repeat;
			}
			.popup {
			position: absolute;
			z-index: 9999;
			}
			#documentManager,#exerciseManager,#exerciseResult {
			width:730px;
			height:450px;
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
		<div id="altContent"></div>
	</body>
</html>
<script type="text/javascript">
	window.replay = true;
	window.roomid = "${room.roomId}";
	window.subjectId = "${room.subjectId}";
	window.subjectType = "${room.subjectType}";
	self.moveTo(0, 0)
	self.resizeTo(screen.availWidth, screen.availHeight);
	var flashvars = {
		configurl : "/v/config${room.roomId}.html"
	};
	var params = {
		menu : "false",
		scale : "noScale",
		allowFullscreen : "true",
		allowScriptAccess : "always",
		wmode : "opaque",
		bgcolor : "#FFFFFF"
	};
	var attributes = {
		id : "classroom"
	};
	swfobject.embedSWF("/player/replay.swf", "altContent", "1000", "700", "10.0.0", "/expressInstall.swf", flashvars, params, attributes); 
</script>