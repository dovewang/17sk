<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl"/>
<style>
body, button, input, select, textarea {font:12px/1.5 tahoma, arial, \5b8b\4f53;}
ul, ol {margin:0;padding:0;list-style:none;}
.cc-tree-gname, .cc-hasChild-item { background:url(${SITE_DOMAIN}/theme/base/images/261-188.png) no-repeat }
#cate-cascading { padding:0 24px; border:1px solid #d5e4fa; background:#f5f9ff; height:298px; position:relative; z-index:11 }
.cc-listwrap { border-left:1px solid #d5e4fa; border-right:1px solid #d5e4fa; position:relative; overflow:hidden }
.cc-list { width:2000em; position:absolute; left:0; top:0 }
.cc-listwrap, .cc-list, .cc-list-item { height:100% }
.cc-list-item { overflow-y:auto; overflow-x:hidden; float:left; width:223px; border-right:2px solid #d5e4fa; background:#fff }
.cc-cbox-cont { padding:0 3px; margin-top:26px }
.cc-cbox-item i { background:none repeat scroll 0 0 #AAA; color:#FFF; display:inline-block; font-style:normal; height:13px; line-height:13px; text-align:center; text-transform:uppercase; width:13px; margin-right:17px; margin-left:-30px; _margin-top:3px }
.cc-tree-group { padding:3px; border-bottom:1px dashed #ccc;}
.cc-tree .cc-tree-last { border:0 }
.cc-tree-gname { color:#36c; background-position:right -107px; display:inline-block; *display:inline;*zoom:1;margin:2px 0; cursor:default }
.cc-tree-expanded .cc-tree-gname { background-position:right -123px }
.cc-cbox-item, .cc-tree-item { background-color:#FFF; border:1px solid #FFF; height:20px; line-height:20px; overflow:hidden; padding-left:41px; cursor:pointer }
.cc-tree-gname, .cc-tree-item { padding:0 16px 0 14px }
.cc-cbox-item { padding-right:16px }
.cc-tree-gcont { display:none }
.cc-tree-expanded .cc-tree-gcont { display:block }
li.cc-selected { border:1px solid #9cd7fc; background-color:#dff1fb }
li.cc-hasChild-item { background-position:right -137px }
.cc-cbox-filter { padding:2px 13px; *padding:1px 3px;background:#fff; position:absolute; top:0;}
.cc-cbox-filter a{text-decoration:none;}
</style>
</head>
<body>
<#include "../top.ftl"/>
<div id="page-main">
<div>
  <form action="/manager/business/question.html"  method="post">
    <table width="98%" border="1">
      <tr>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
      </tr>
    </table>
  </form>
</div>
<div class="cate-container">
    <div id="cate-cascading">
       <div class="cc-listwrap">
       <ol class="cc-list">
       <li id="cc-list-item" class="cc-list-item">
       		<ul node-type="tree" class="cc-tree">
       			<!--
       			<li class="cc-tree-group">
	       			<a href="#">+</a>
	       		</li>
	       		-->
       			<#list result?keys as key>
	       		<li id="cc-tree-group-${key}" action-type="tree-item" gid="${key}" class="cc-tree-group">
	       			<div id="${key}" class="cc-tree-gname">${result[key]}</div>
	       		</li>
	       		</#list>
       		</ul>
       	</li>
       	</ol>
       </div>
    </div>
</div>
</body>
</html>