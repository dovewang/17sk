<!DOCTYPE html>
<html>
	<head>
		<title>cramera</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<style type="text/css" media="screen">
			html, body {
				height: 100%;
			}
			body {
				margin: 0;
				padding: 0;
				overflow: hidden;
			}
			#flashContent {
				width: 100%;
				height: 100%;
			}
		</style>
		<script type="text/javascript">
			function bind(fn) {
				window.fn = fn;
			}

			/**
			 *拍照回调
			 */
			function takeback(message) {
				message = eval("(" + message + ")");
				if (!message.err || message.err == "") {
					window.fn(message.msg);
				}
			}
		</script>
	</head>
	<body>
		<div id="flashContent">
			<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" width="600" height="500" id="camera" align="middle">
				<param name="movie" value="camera.swf" />
				<param name="quality" value="high" />
				<param name="wmode" value="window" />
				<param name="scale" value="showall" />
				<param name="menu" value="false" />
				<param name="flashvars" value="url=http://<%=request.getServerName() %>/upload/image.html?JSESSIONID=<%=session.getId()%>">
				<param name="allowScriptAccess" value="sameDomain" />
				<!--[if !IE]>-->
				<object id="camera_fx" name="camera" type="application/x-shockwave-flash" data="camera.swf" width="600" height="500" pluginspage="http://www.adobe.com/go/getflashplayer">
					<param name="movie" value="camera.swf" />
					<param name="quality" value="high" />
					<param name="wmode" value="window" />
					<param name="scale" value="showall" />
					<param name="menu" value="false" />
					<param name="flashvars" value="url=http://<%=request.getServerName() %>/upload/image.html?JSESSIONID=<%=session.getId()%>">
					<param name="allowScriptAccess" value="sameDomain" />
				</object>
				<!--<![endif]-->
			</object>
		</div>
	</body>
</html>

