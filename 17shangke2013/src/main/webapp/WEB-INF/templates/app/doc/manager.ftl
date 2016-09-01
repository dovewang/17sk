<!DOCTYPE html >
<html>
	<head>
		<#include "/global.ftl" /> 
		<script src="${IMAGE_DOMAIN}/theme/base/js/bis.doc.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
        <link href="${IMAGE_DOMAIN}/theme/room/css/default.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
	</head>
	<body  class="notebook">
    <div class="page-container">
       <div class="page-header document-manager">                
          <span class="float-right search"><a href="/doc/form.html?cb=DM.refresh" id="docwin" class="button primary" node-type="pop" title="上传文件">上传文件</a><label>关键字:</label><input type="text" name="q" id="q"  value="" placeholder="请输入关键字" onkeydown="if(event.keyCode == 13){DM.search();}"/><button  type="button" class="button" onclick="DM.search()"></button></span>
       </div>
       <div class="page-body">
       <div class="page-left">
           <div class="tab-header" id="docTab" node-type="tabnavigator">
                        <ul>
                            <li>
                                <a  href="#!/doc/my.html?c=-1" remote="true" view="documentList"  id="documentAll" page="true" autoload="true">全部</a>
                            </li>
                            <li>
                                <a href="#!/doc/my.html?c=1" remote="true" view="documentList" page="true" >图片</a>
                            </li>
                            <li>
                                <a  href="#!/doc/my.html?c=2" remote="true" view="documentList" page="true">课件</a>
                            </li>
                            <li class="tag-add">
                                <a href="javascript:void(0)" onclick="DM.addTag()"><i></i></a>
                            </li>
                        </ul>
            </div>
         </div>
         <div class="page-right">
            <div     style="height:450px" id="documentList"></div>
         </div>
       </div>
       <div class="page-footer"></div>
    
		</div>
	</body>
</html>
