<#assign TITLE=result.subject!"临时课题"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<#include "../head.ftl" /> <script src="${IMAGE_DOMAIN}/theme/base/js/swfobject.js" type="text/javascript"></script>
		<script src="${IMAGE_DOMAIN}/theme/base/js/bis.room.js?${VERSION}" type="text/javascript"></script>
		<style type="text/css">
<!--
body {
margin: 0px auto;
text-align:center;
background-image: url(/wall.png);
background-repeat: repeat;
}
#documentManager,#exerciseManager,#exerciseResult {
width:800px;
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
		<div id="altContent">
			<object id="classroom" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
			codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,0,0"
			width="1000" height="700" align="middle">
				<param name="menu" value="false">
				<param name="scale" value="noScale">
				<param name="bgcolor" value="#FFFFFF">
				<param name="flashvars" value="configurl=/room/config${result.roomId}=${verify}.html">
				<param name="movie" value="/classroom.swf" />
				<param name="quality" value="high" />
				<embed src="/classroom.swf" id="classroom_fx" quality="high" bgcolor="#ffffff" width="1000"
				height="700" name="classroom" align="middle"
				allowFullscreen="true"  menu="false"   scale="noScale"
				flashvars="configurl=/room/config${result.roomId}=${verify}.html"
				type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/go/getflashplayer" />
			</object>
		</div>
	</body>
</html>
<script type="text/javascript">
/**
 * 由于在Flash中使用，必须使用iframe才能显示在flash上层，flash不能使用透明方式载入，否则将导致中文输入法无法正常使用
 */
PopUp.flashModel = true;
window.roomid="${result.roomId}";
window.subjectId="${result.subjectId}";
window.subjectType="${result.subjectType}";
self.moveTo(0,0)
self.resizeTo(screen.availWidth,screen.availHeight);
swfobject.registerObject("classroom","10.0.0","/expressInstall.swf");

</script>
