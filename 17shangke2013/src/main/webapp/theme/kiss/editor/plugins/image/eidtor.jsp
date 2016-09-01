<!DOCTYPE html>
<html>
<head>
<title>美图WEB开放平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="http://open.web.meitu.com/sources/xiuxiu.js" type="text/javascript"></script>
<script type="text/javascript">
var image="http://open.web.meitu.com/sources/images/1.jpg";
/*图片上传前调用的函数
*
*width	int	图片宽度
*height	int	图片高度
type	string	格式（目前只有jpg、png、gif）三种
size	int	图片字节数
name	string	图片名称
numOperate	int	打开编辑器之后编辑的第几张图片
isNet	booleam	是否是网络图片
url	string	如果是网络图片，则为图片url，否则为空
*/
var before=function(data, id){}
var after=function(data){}
<%
   String image=request.getParameter("image");
   String before=request.getParameter("before");
   String after=request.getParameter("after");
   if(image!=null&&!"".equals(image.trim())){
	   out.println("image=\""+image+"\";");
   }

   if(before!=null&&!"".equals(before.trim())){
	   out.println("before=window.parent."+before+";");
   }
   
   if(after!=null&&!"".equals(after.trim())){
	   out.println("after=window.parent."+after+";");
   }
%>
window.onload=function(){
	  /*第1个参数是加载编辑器div容器，第2个参数是编辑器类型，第3个参数是div容器宽，第4个参数是div容器高*/
	xiuxiu.embedSWF("altContent",3,"100%","100%");
	xiuxiu.setUploadType(2);
	xiuxiu.setUploadDataFieldName("filedata");
	xiuxiu.setUploadURL("http://<%=request.getServerName() %>/upload/image.html?JSESSIONID=<%=session.getId()%>");//修改为您自己的上传接收图片程序
	/*初始化加载的图片*/
	xiuxiu.onInit = function ()
	{
		xiuxiu.loadPhoto(image);
	}	
	
	xiuxiu.onBeforeUpload=before;
	xiuxiu.onUploadResponse =after;
}
</script>
<style type="text/css">
html, body {
	height: 100%;
	overflow: hidden;
}
body {
	margin: 0;
}
</style>
</head>
<body>
<div id="altContent">
  <h1>美图秀秀</h1>
</div>
</body>
</html>