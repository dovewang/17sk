<!DOCTYPE html>
<html>
<head>
<#include "../head.ftl" />
<link href="${IMAGE_DOMAIN}/theme/base/css/jquery.Jcrop.css" type="text/css" rel="stylesheet" media="screen">
</head><body>
<#include "../top.ftl" />
<div class="container">
  <div class="row-fluid">
      <div class="tab-header " node-type="tabnavigator" mode="hash">
        <ul>
          <li> <a href="#!/user/base.html" remote="false" autoload="true" view="#baseView">基本信息</a> </li>
          <li> <a href="#!/user/avatar.html" remote="false"  view="#avatarView">我的头像</a> </li>
          <li> <a href="#!/user/detail.html" remote="false"  view="#detailView">详细信息</a> </li>
          <li> <a href="#!/user/password.html" remote="false"  view="#passwordView">修改密码</a> </li>
        </ul>
      </div>
      <!--tab_title end-->
      <div class="tab-view" id="baseView" > <#include "user.base.ftl"/> </div>
      <div class="tab-view" id="avatarView" style="height:380px;"> <#include "../../../main/default/user/user.avatar.ftl"/> </div>
      <div class="tab-view" id="detailView"> <#include "../../../main/default/user/user.detail.ftl"/> </div>
      <div class="tab-view" id="passwordView"> <#include "../../../main/default/user/user.password.ftl"/> </div>
    </div>
</div>
<footer>
  <div id="page-footer"></div>
</footer>
<#include "../foot.ftl"/>
</body>
</html>
<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/base/js/jquery.Jcrop.js?${VERSION}"></script>
<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/base/js/flint.avatar.js?${VERSION}"></script>
<script src="${IMAGE_DOMAIN}/theme/base/js/bis.user.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
