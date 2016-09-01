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
  <div class="enrolmentbox">
    <div class="enrolmen_pic"><img src="${course.cover}" /> </div>
    <div class="enrolmen_con">
      <p> 主讲老师： <a href="/u/${course.teacher}.html" usercard="${course.teacher}"></a> </p>
      <p> 适用范围：
        ${kindHelper.getGradeLabel(course.scope)}
      </p>
      <p> 开课时间：<strong>
        ${flint.timeToDate(course.startTime,"yyyy-MM-dd HH:mm")}
        </strong> </p>
      <p> 课程时长：
        ${course.time/60}
        分钟 </p>
      <div class="but_apply marginTop15"> 
      	暂不开放
      	<!--
      	     <a href="/course/join.html?i=${course.courseId}" title="马上报名" class="button xbtn course-enrollment" ></a> 
      	    -->
      	</div>
    </div>
    <!--enrolmen_con end-->
    <div class="clear"></div>
  </div>
  <div class="tab-header" node-type="tabnavigator">
    <ul>
      <li> <a href="#!/course-intro.html" remote="flase" view="introView" autoload="true">课程简介</a> </li>
      <#if course.status==9>
      <li> <a href="#!/exercise/get.html?exerciseId=1" remote="true" view="exerciseView" >课后练习</a> </li>
      </#if>
      <li> <a href="#!/course/comments-${course.courseId},-1.html" remote="false" view="commentView" once="true" group="true" >评论<#if (course.comments>0) >(<span id="cnums${course.courseId}">
        ${course.comments}
        </span>)</#if></a> </li>
    </ul>
  </div>
  <!--tab_title end-->
  <div class="tab-body" id="introView" style="padding:10px">
    ${course.intro}
  </div>
  <div class="tab-body" id="exerciseView"> 没有课后练习 </div>
  <!--tab_con end-->
  <div style="display:none" id="commentView">
    <div  class="tab-subheader">
      <ul>
        <li> <a href="#!/course/comments-${course.courseId},-1.html" remote="true" view="commentsListView"  autoload="true" page="true" id="commentAll">全部</a> </li>
        <li> <a href="#!/course/comments-${course.courseId},3.html" remote="true" view="commentsListView" page="true"> 非常有用</a> </li>
        <li> <a href="#!/course/comments-${course.courseId},2.html" remote="true" view="commentsListView" page="true"> 有用</a> </li>
        <li> <a href="#!/course/comments-${course.courseId},1.html" remote="true" view="commentsListView" page="true"> 一般</a> </li>
        <li> <a href="#!/course/comments-${course.courseId},0.html" remote="true" view="commentsListView" page="true"> 作用不大</a> </li>
      </ul>
    </div>
    <#include "../comments/post.ftl"/>
    <div class="tab-body"  id="commentsListView"></div>
  </div>
</div>
<!--rightbox end--> 
<#include "../foot.ftl" />
</body>
</html>
