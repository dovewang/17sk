<#assign TITLE=""/>
<#assign CATEGORY="大家说什么-"/>
<!DOCTYPE html>
<html>
<head>
<#include "../head.ftl" />
</head>
<body>
<div id="wrap">
<div id="wrapbottom">
<div id="wrapcon">
<div id="conframe">
<#include "../top.ftl" /> <!--header end-->
<div id="content">
<#include "../left.ftl" /> <!--leftbox end-->
<div id="rightbox">
  <div class="right_funcitonbox"> <#include "post.ftl" /> </div>
  <!--right_funcitonbox end-->
  <div class="tab_questionbox">
      <div   id="mblog_list"></div>
    <div class="clear"></div>
  </div>
  <!--tab_questionbox end--> 
</div>
<!--rightbox end--> 
<#include "../foot.ftl" />
</body>
</html>
<script src="${IMAGE_DOMAIN}/theme/base/js/bis.mblog.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<script type="text/javascript">
  Mblog.loadAll();
</script>