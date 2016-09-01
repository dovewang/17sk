<!DOCTYPE html>
<html>
<head>
<#include "../../head.ftl" />
<link href="${IMAGE_DOMAIN}/theme/base/css/jquery.Jcrop.css" type="text/css" rel="stylesheet" media="screen">
</head><#assign school_id=site.id/>
<body>
<#include "../../top.ftl"/>
<div class="container">
  <div class="span3">...</div>
  <div class="row-fluid">
    <div class="span9"><#include "/course/edit/item${type}.ftl"/> </div>
  </div>
</div>
<footer>
  <div id="page-footer"></div>
</footer>
<#include "../../../global.footer.ftl"/>
</body>
</html>
