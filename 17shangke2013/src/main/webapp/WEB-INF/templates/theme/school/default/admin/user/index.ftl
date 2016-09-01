<!DOCTYPE html>
<html>
<head>
<#include "../../head.ftl" />
<link href="/theme/kiss/kiss.min.css" rel="stylesheet" type="text/css">
<link href="/theme/school/default/css/skin.css" rel="stylesheet" type="text/css">
<link  href="/theme/kiss/kiss-responsive.min.css" rel="stylesheet" type="text/css">
</head><body>
<#include "../top.ftl"/>
<div class="page-header" style="margin-top:45px;"></div>
<div class="page-body">
<div class="page-left"></div>
<div class="page-content">
  <div class="container-fluid" id="main">
  <div class="box">
         <table width="90%">
  <tr>
    <td width="80">入学年度：</td>
    <td width="50%"><span node-type="combobox"  data-name="year" class="combobox-box">
        <#list years as item>
          <span class="item" data-value="${item[0]}">${item[0]}<sup class="label label-info">${item[1]}</sup></span>
        </#list>  
    </span></td>
    <td width="80">班级：</td>
    <td><button class="btn" type="button">选择</button></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>名字：</td>
    <td><label for="textfield"></label>
      <input type="text" name="textfield" id="textfield"></td>
    <td>职务：</td>
    <td><span node-type="combobox"  data-name="role" class="combobox-box" max="1">
          <span class="item" data-value="1">全部</span>
          <span class="item" data-value="1">老师</span>
          <span class="item" data-value="1">学生</span> 
    </span></td>
    <td><button class="btn btn-primary" type="button">查询</button></td>
  </tr>
  </table>
</div>

          <table node-type="ajax-grid"
            checkable="true"  
            multi-sort="true"
            scrollable="true" 
            pageable="true"
            method="GET"
            action="/admin/user/list.html?type=0"
            id="user" >
            <thead>
              <tr>
                <th resizeable="true" name="name" dataType="String" sortable="true">姓名</th>
                <th resizeable="true" name="email" dataType="String" >邮箱</th>
                <th resizeable="true" name="tel" dataType="String" >电话</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr class="template">
                <td>[name]</td>
                <td>[email]</td>
                 <td>[tel]</td>
                <td><a href="javascript:;">编辑</a>|<a href="javascript:;">毕业</a>|<a href="javascript:;">解散</a></td>
              </tr>
            </tbody>
          </table> 
  </div>
</div>
<div class="page-footer"></div>
</body>
</html>
