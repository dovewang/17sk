<!DOCTYPE html>
<html>
<head>
<#include "../head.ftl" />
</head>
<body>
<#include "../top.ftl" />
<div class="container">
  <div class="row-fluid">
    <h2>我的收藏</h2>
    <div class="tab-header" node-type="tabnavigator" mode="hash">
      <ul>
        <li> <a href="#!/question/fav.html" view="#questionView"   once="true" autoload="true" page="true">我收藏的问题</a> </li>
        <li> <a href="#!/course/fav.html" view="#courseView"   once="true"  page="true">我收藏的课程</a> </li>
      </ul>
    </div>
    <div class="tab-view" id="questionView"></div>
    <div class="tab-view" id="courseView" ></div>
  </div>
</div>
<footer>
  <div id="page-footer"></div>
</footer>
<#include "../foot.ftl"/>
</body>
</html>
