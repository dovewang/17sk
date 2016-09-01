<!DOCTYPE html>
<html>
<head>
<#assign TITLE=""/>
		<#assign CATEGORY="关于我们-"/>
		<#include "head.ftl" />
		</head>
		<body>
<div id="wrap">
<div id="wrapbottom">
<div id="wrapcon">
<div id="conframe">
<div id="header">
          <div id="headercon">
    <div id="logo" style="background-image:url(${school.logo})"></div>
    <div id="logo_ex"></div>
  </div>
        </div>
<div id="content">
          <div id="rightbox" style="min-height:500px; font-size:14px; line-height:22px; text-indent:30px;">
    ${school.intro}
  </div>
        </div>
<#include "foot.ftl" />
</body>
</html>
