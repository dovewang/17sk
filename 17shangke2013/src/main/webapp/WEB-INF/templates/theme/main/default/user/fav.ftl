<!DOCTYPE html>
<html>
<head>
<#include "../meta.ftl" />
<link href="${IMAGE_DOMAIN}/theme/main/skins/default/css/zone.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
</head><body class="zone-page">
<#include "header.ftl" />
<div class="page-container">
  <div class="page-body">
    <div class="page-left"><#include "left.ftl" /></div>
    <div class="page-main">
      <div class="m10 box">
        <div class="tab-header" node-type="tabnavigator" mode="hash">
          <ul>
            <li> <a href="#!/question/fav.html" view="#questionView">收藏的问题</a> </li>
            <li> <a href="#!/course/fav.html" view="#courseView"  >收藏的课程</a> </li>
            <li> <a href="#!/show/fav.html" view="#showView">收藏的秀秀</a> </li>
            <!--
                <li>
					<a href="#!/m/fav.html" view="mblogView" remote="true" once="true"  page="true">收藏的微博</a>
				</li>
			-->
          </ul>
        </div>
        <div class="tab-body" id="questionView"></div>
        <div class="tab-body" id="courseView" ></div>
        <div class="tab-body" id="mblogView" ></div>
        <div class="tab-body" id="showView" ></div>
      </div>
    </div>
  </div>
</div>
<#include "../footer.ftl"/>
</body>
</html>
