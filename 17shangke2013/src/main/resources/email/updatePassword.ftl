<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>密码找回-一起上</title>
<#include "style.ftl" />
</head>
<body>
<div id="wrap">
   <div class="logo"><a id="logo" href="${SITE_DOMAIN}/"><img src="${IMAGE_DOMAIN}/images/email-head-logo.png" border="0"></a>
   <div id="content">
        尊敬的用户：
          <p> 您好！一起上课已收到您的密码找回申请，您可以使用一下链接重置您的密码：</p>
          <p><a  href="${validateUrl}">${validateUrl}</a></p>
          <p align="right" style="padding-right:50px; padding-top:30px;">一起上课</p>
   </div>
</div>
</body>
</html>
