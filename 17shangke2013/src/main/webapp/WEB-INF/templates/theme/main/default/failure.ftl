<!DOCTYPE html>
<html>
<head>
<#include "meta.ftl" />
</head>
<body>
<#include "header.ftl" />
<div class="page-container">
  <div class="page-body">
    <div class="box" id="failure-box">
      ${message!"网络繁忙，请稍后再试"}
    </div>
  </div>
</div>
<#include "footer.ftl"/>
</body>
</html>