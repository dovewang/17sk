<!DOCTYPE html>
<html>
<head>
<#assign TITLE=""/>
		<#assign CATEGORY="学校广场-"/>
		<#include "head.ftl" />
		</head>
		<body>
<#include "top.ftl"/>
<div class="container">
          <div class="row-fluid">
    <div class="span9">
              <div id="post"> <#include "/common/mblog/poster.ftl" /> </div>
              <div   id="mblog_list"></div>
            </div>
    <div class="span3">...</div>
  </div>
        </div>
<#include "foot.ftl"/>
</body>
</html>
<script type="text/javascript">
  Mblog.loadAll();
</script>