<!DOCTYPE html>
<html>
<head>
<#include "head.ftl" />
</head>
<body>
<div id="wrap">
<div id="wrapbottom">
<div id="wrapcon">
<div id="conframe">
<#include "top.ftl" /> <!--header end-->
<div id="content">
<div class="error_message">
  ${message!"网络繁忙，请稍后再试"}
   <div><a href="javascript:void(0)" onclick="history.back()">>>返回</a></div>
</div>
<!--error_message end--> 
<!--rightbox end--> 
<#include "foot.ftl" />
</body>
</html>
