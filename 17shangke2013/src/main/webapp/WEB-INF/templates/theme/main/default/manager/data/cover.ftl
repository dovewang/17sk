<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl"/>
</head>
<body>
<#include "../top.ftl"/>
<div id="page-main">
<div>
  <form action="/manager/business/question.html"  method="post">
    <table width="98%" border="1">
      <tr>
	      <td><button class="btn btn-primary"  type="button" onclick="Cover.upload()" >封面上传</button></td>
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
<table width="98%" class="table table-striped table-bordered table-condensed">
  <thead>
    <tr>
      <th>所属科目</th>
      <th>图片</th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody>
  <#list result.result as c>
  <tr id="manager-cover-${c.id}">
    <td>${kindHelper.getKindLabel(c.category?string)}<#if c.category==0>其它</#if></td>
    <td><img id="imageCover" src="${c.url!}" width="60" height="35" alt="" /></td>
    <td><i class="icon-edit"></i>
    <a href="javascript:;" node-type="pop-confirm" data-message="您确定要删除该信息？"  OK="Cover.del('${c.id}')">删除</a>
    </td>
  </tr>
  </#list>
  </tbody>
  <tfoot>
    <tr>
      <td colspan="8"><@flint.pagination result,"","","" /></td>
    </tr>
  </tfoot>
</table>
</body>
</html>