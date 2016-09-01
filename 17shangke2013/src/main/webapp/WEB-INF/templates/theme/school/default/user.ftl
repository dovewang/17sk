<!DOCTYPE html>
<html>
<head>
<#include "head.ftl" />
</head>
<body class="user-zone">
<#include "top.ftl" />
<div class="container">
  <div class="row-fluid">
    <div class="span9">
      <div class="user-info">
      <h3>${user.realName!user.name}，老师</h3>
      <dl class="clearfix">
        <dt>
         <img name="${user.realName!user.name}" src="${user.face}" width="180" height="180" alt="" /> 
        </dt>
        <dd>
          <ul class="items">
             <li>擅长：${user.expert!}</li>
             <li>${user.intro!"这家伙很懒，什么也没留下"}</li>
          </ul>
        </dd>
      </dl>
      <div class="user-control">获得 8520赞同 2197感谢   关注|私信<a  href="javascript:void(0)" class="left-arrow" onclick="MC.showForm(${user.userId},'${user.realName!user.name}');return false">发送消息</a> <a href="javascript:void(0)"  class="left-arrow" onclick="MC.askQuestion(${user.userId},'${user.realName!user.name}');return false">问作业</a> </div>
      </div>
      <div class="tab-header" node-type="tabnavigator" mode="hash">
        <ul>
          <li> <a href="#!/m/list-${user.userId}.html"   view="#mblog_list"  autoload="true" >TA的动态</a> </li>
          <li> <a href="#!/question-mya.html?u=${user.userId}"  view="#question_list" >TA解答的问题</a> </li>
          <li> <a href="#!/question-myq.html?u=${user.userId}"  view="#question_list" >TA提出的问题</a> </li>
          <li> <a href="#!/course-mys.html?u=${user.userId}"  view="#course_list">TA参加的课程</a> </li>
          <li> <a href="#!/course-myc.html?u=${user.userId}"  view="#course_list">TA发布的课程</a> </li>
        </ul>
      </div>
      <div class="tab-view" id="mblog_list"></div>
      <div class="tab-view" id="question_list" style="display:none"></div>
      <div class="tab-view" id="course_list" style="display:none"></div>
      <div class="tab-view" id="info_list" style="display:none"></div>
    </div>
    <div class="span3">...</div>
  </div>
</div>
<footer>
  <div id="page-footer"></div>
</footer>
<#include "foot.ftl"/>
</body>
</html>