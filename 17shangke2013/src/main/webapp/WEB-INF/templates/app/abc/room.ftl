<#assign TITLE=room.subject!"临时课题"/>
<!DOCTYPE html>
<html>
	<head>
		<#include "head.ftl" />
	</head>
	<body>
		<div id="altContent" style="z-index: -1;">
			<object id="classroom" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
			codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,0,0"
			width="1000" height="700" align="middle">
				<param name="menu" value="false">
				<param name="scale" value="noScale">
				<param name="bgcolor" value="#FFFFFF">
				<param name="flashvars" value="configurl=${SITE_DOMAIN}/abc/config${room.roomId}.html">
				<param name="movie" value="/classroom.swf" />
				<param name="quality" value="high" />
				<param name="wmode" value="opaque" />
				<embed src="/classroom.swf" id="classroom_fx" quality="high" bgcolor="#ffffff" width="1000"
				height="700" name="classroom" align="middle"
				allowFullscreen="true"  menu="false"   scale="noScale"
				flashvars="configurl=${SITE_DOMAIN}/abc/config${room.roomId}.html" wmode="opaque"
				type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/go/getflashplayer" />
			</object>
		</div>
		<script src="${IMAGE_DOMAIN}/theme/room/js/room.js?${VERSION}" type="text/javascript"></script>
		<script type="text/javascript">
			window.NO_COMETD = true;
			window.roomid = "${room.roomId}";
			window.subjectId = "${room.subjectId}";
			window.subject = document.title;
			/*包含特殊字符时直接获取*/
			window.subjectType = "${room.subjectType}";
		</script>
	</body>
</html>

