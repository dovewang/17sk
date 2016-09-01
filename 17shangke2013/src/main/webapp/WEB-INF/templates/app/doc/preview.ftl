<!DOCTYPE html PUBLIC >
<html>
<head>
<script type="text/javascript" src="/theme/base/js/jquery.js"></script>
<script type="text/javascript"  src="/theme/kiss/docviewer/js/jquery.extensions.min.js"></script>
<script type="text/javascript" src="/theme/kiss/docviewer/js/flexpaper.js"></script>
<script type="text/javascript" src="/theme/kiss/docviewer/js/flexpaper_handlers.js"></script>
<style>
   html,body{height:100%; margin:0; padding:0}
</style>
</head>
<body>
<#assign metadata=Q.toJSON(doc.metadata)/>
<div id="documentViewer" class="flexpaper_viewer" style="height:100%;width:100%"></div>
<script type="text/javascript">
			$('#documentViewer').FlexPaperViewer({
				 config : {
					 DOC : escape('{/doc/view.html?id=${doc.id}&format={format}&page=[*,0],${metadata.pages}}'),
					 Scale : 0.6, 
					 ZoomTransition : 'easeOut',
					 ZoomTime : 0.5, 
					 ZoomInterval : 0.1,
					 FitPageOnLoad : true,
					 FitWidthOnLoad : true, 
					 FullScreenAsMaxWindow : false,
					 ProgressiveLoading : true,
					 MinZoomSize : 0.2,
					 MaxZoomSize : 5,
					 SearchMatchAll : false,
					 InitViewMode : 'Portrait',
					 ViewModeToolsVisible : true,
					 ZoomToolsVisible : true,
					 NavToolsVisible : true,
					 CursorToolsVisible : true,
					 SearchToolsVisible : true,
					 RenderingOrder:"flash",
					 jsDirectory : '/theme/kiss/docviewer/js/',
					 localeDirectory : '/theme/kiss/docviewer/locale/',
					 JSONDataType : 'jsonp',
					 WMode : 'window',
					 localeChain: 'zh_CN'
					 }
			});
		</script>
</body>
</html>
