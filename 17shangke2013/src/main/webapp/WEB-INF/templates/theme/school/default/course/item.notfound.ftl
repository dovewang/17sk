<!DOCTYPE html>
<html>
<head>
<#include "../head.ftl" />
</head>
<body>
<#include "../top.ftl"/>
<div class="container">
  <div class="row-fluid"> 
  <div class="alert alert-block alert-error">
            <h4 class="alert-heading">您访问的页面我没找到噢~</h4>
            <p> 很抱歉，您访问的课程不存在，可能已经关闭或已删除！ </p>
            <p> <a href="javascript:void(0)" class="btn btn-danger"  onclick="history.back()">返回</a></p>
          </div>
  </div>
</div>
<#include "../foot.ftl"/>
</body>
</html>