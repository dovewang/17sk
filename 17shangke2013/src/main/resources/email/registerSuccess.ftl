<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>注册信息-一起上课</title>
<#include "style.ftl" />
</head>
<body>
<div id="wrap">
   <div class="logo"><a id="logo" href="${SITE_DOMAIN}/"><img src="${IMAGE_DOMAIN}/images/email-head-logo.png" border="0"></a></div>
   <div id="content">
        尊敬的用户：
          <p>非常感谢您注册<a href="#">一起上课</a>！无论您是学生、家长还是老师，在这里都能找到您的位置，解决您的尴尬问题。 </p>
          <p> 您的账号：${userName}</p>
     <p>请点击此连接激活您的邮箱：<a  href="${validateUrl}">${validateUrl}</a></p>
          <p align="right" style="padding-right:50px; padding-top:30px;">一起上课团队欢迎您的加入</p>
   </div>
</div>
</body>
</html>
