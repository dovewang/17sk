<!DOCTYPE html>
<html>
<head>
<#include "../head.ftl" />
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
        <object width="100%" height="450">
          <param name="movie" value="/theme/base/player/jwplayer/player.swf" />
          <param name="flashvars" value="file=${course.dir!}" />
          <param name="allowFullScreen" value="true" />
          <param name="allowscriptaccess" value="never" />
          <param name="wmode" value="transparent" />
          <embed src="/theme/base/player/jwplayer/player.swf"  type="application/x-shockwave-flash" allowscriptaccess="never"
										allowfullscreen="true" width="700" height="450"
										flashvars="file=${course.dir!}" wmode="transparent"></embed>
        </object>
        </div>
      </div>
      <div class="tab-header" node-type="tabnavigator" mode="hash">
        <ul>
          <li> <a href="#!/course-intro.html" remote="false" view="#introView">课程简介</a> </li>
          <li> <a href="#!/exercise/get.html?exerciseId=1" remote="true" view="#exerciseView" >相关资料</a> </li>
          <li> <a href="#!/course/comments-${course.courseId},-1.html" remote="false" view="#commentView" once="true" group="true"  mode="hash">评论<#if (course.comments>0) >(<span id="cnums${course.courseId}">
            ${course.comments}
            </span>)</#if></a> </li>
        </ul>
      </div>
      <!--tab_title end-->
      <div class="tab-view fluid" id="introView">${course.intro}</div>
      <div class="tab-view fluid" id="exerciseView">没有相关资料</div>
      <!--tab_con end-->
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
<!-- 参加或者观看该课程的用户     <div class="side-section"> 
        <div class="side-section-inner">
          
        </div>
      </div>-->
      <!--主将简介-->
      <div class="side-section">
        <div class="side-section-inner"> <a  href="/u/${user.userId}.html"> <img src="${user.face}"  height="30"  width="30"> </a>${user.name}
          <div class="gray-light">${user.experience}</div>
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
<#include "../foot.ftl"/>
</body>
</html>
