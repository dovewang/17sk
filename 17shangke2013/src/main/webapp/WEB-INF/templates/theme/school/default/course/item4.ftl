<!DOCTYPE html>
<html>
<head>
<#include "../head.ftl" />
</head>
<body>
<#include "../top.ftl"/>
<div class="container">
  <div class="row-fluid">
    <div class="span9"> 
      <!-- -->
      <div class="course-section">
        <a href="#" class="tag">${kindHelper.getKindLabel(course.category?j_string)}</a>
        <h1>${course.name}</h1>
        <div class="course-content">
        <embed src="${course.dir}" quality="high" width="100%" height="450" align="middle" allowScriptAccess="always" allowFullScreen="true" wmode="transparent" type="application/x-shockwave-flash"></embed>
        </div>
      </div>
      <div class="tab-header" node-type="tabnavigator" mode="hash">
        <ul>
          <li> <a href="#!/course-intro.html" remote="false" view="#introView" autoload="true">课程简介</a> </li>
          <li> <a href="#!/course/comments-${course.courseId},-1.html" remote="false" view="#commentView" once="true" group="true" >评论<#if (course.comments>0) >(<span id="cnums${course.courseId}">
            ${course.comments}
            </span>)</#if></a> </li>
        </ul>
      </div>
      <div class="tab-view fluid" id="introView">${course.intro}</div>
      <div class="tab-view" id="commentView">
        <div  class="tab-subheader">
          <ul>
            <li> <a href="#!/course/comments-${course.courseId},-1.html" remote="true" view="#commentsListView"  autoload="true" page="true" id="commentAll">全部</a> </li>
            <li> <a href="#!/course/comments-${course.courseId},5.html" remote="true" view="#commentsListView" page="true"> 非常有用</a> </li>
            <li> <a href="#!/course/comments-${course.courseId},4.html" remote="true" view="#commentsListView" page="true"> 有用</a> </li>
            <li> <a href="#!/course/comments-${course.courseId},3.html" remote="true" view="#commentsListView" page="true"> 一般</a> </li>
            <li> <a href="#!/course/comments-${course.courseId},1.html" remote="true" view="#commentsListView" page="true"> 作用不大</a> </li>
          </ul>
        </div>
          <div class="tab-view fluid" style="display:block">
           <#include "/comment/post.ftl"/>
          <div   id="commentsListView"></div>
        </div>
      </div>
    </div>
    <div class="span3">
        <!--主将简介-->
        <div class="side-section">
          <div class="side-section-inner"> <a  href="#"> <img src="http://p1.zhimg.com/f5/c4/f5c4d8c99_s.jpg" > </a>吴小康
            <div class="gray-light">曾经拍过多个电视广告，也是《美人志》杂志的专属模特儿曾经拍过多个电视广告，也是《美人志》杂志的专属模特儿曾经拍过多个电视广告，也是《美人志》杂志的专属模特儿曾经拍过多个电视广告，也是《美人志》杂志的专属模特儿曾经拍过多个电视广告，也是《美人志》杂志的专属模特儿曾经拍过多个电视广告，也是《美人志》杂志的专属模特儿</div>
          </div>
        </div>
        <div class="side-section">
          <div class="side-section-inner">
            <h3>相关课程</h3>
            <ul class="course-related">
              <li> </li>
            </ul>
          </div>
        </div>
        <div class="side-section">
          <div class="side-section-inner">
            <h3>分享课程</h3>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<footer>
  <div id="page-footer"></div>
</footer>
<#include "../../global.footer.ftl"/>
</body>
</html>
