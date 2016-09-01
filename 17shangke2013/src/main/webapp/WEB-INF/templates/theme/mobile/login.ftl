<!doctype html>
<html>
<head>
<#assign SECURE=true/>
		<#include "/conf/config.ftl" />
		<title>移动版-一起上课</title>
		</head>
		<body>
<div class="pop-header" style="position:relative;">
          <h3>用户登录</h3>
          <div style="position:absolute;right:15px; bottom:10px; width:150px;"> 还没有17账号？<a href="${SECURE_DOMAIN}/register.html" class="parent-link a1">立即注册</a> </div>
        </div>
<div class="pop-body">
          <form id="loginForm" name="loginForm" method="post" onsubmit="return false">
    <input id="forwardUrl" name="forwardUrl" type="hidden" value="${RequestParameters["url"]?default(HTTP_DOMAIN+"/index.html")}" />
    <div id="login_message" class="error"></div>
    <dl class="form label60 input-middle">
              <dd >
        <label>用户名：</label>
        <span>
                <input type="text" name="userName" id="userName"  placeholder="请输入登录邮箱" class="input"/>
                </span> </dd>
              <dd>
        <label>密 码：</label>
        <span>
                <input name="password" type="password" id="password" value="" placeholder="请输入登录密码" class="input"/>
                </span> </dd>
              <dd>
        <label>&nbsp;</label>
        <span>
                <label>
          <input name="remeberme" type="checkbox" id="remeberme"/>
          记住登录状态</label>
                &nbsp;&nbsp;&nbsp;&nbsp;<a class="parent-link" href="${SECURE_DOMAIN}/recovery.html">忘记密码</a> </span> </dd>
              <dd>
        <label>&nbsp;</label>
        <span>
                <button  type="submit" class="button primary large" id="button" onclick="Security.login(true)"> 登录 </button>
                </span> </dd>
            </dl>
  </form>
          <div id="login-social" style="margin-top:5px">
    <p> 您也可以使用以下账号登录 </p>
    <ul>
              <li class="sina"> <a class="parent-link" href="${SITE_DOMAIN}/social/sina/authz.html?url=${RequestParameters["url"]?default(HTTP_DOMAIN+"/index.html")}">新浪微博</a> </li>
              <li class="qq"> <a class="parent-link" href="${SITE_DOMAIN}/social/qq/authz.html?url=${RequestParameters["url"]?default(HTTP_DOMAIN+"/index.html")}">QQ</a> </li>
            </ul>
  </div>
        </div>
</body>
</html>
