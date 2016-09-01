<!DOCTYPE html>
<html>
<head>
<#include "../head.ftl" />>
</head><body>
<#include "../top.ftl"/>
<div class="container">
  <div class="row-fluid">
    <div class="span9"> 
      <!-- -->
      <div class="course-section"> 
        <a href="#" class="tag">${kindHelper.getKindLabel(course.category?j_string)}</a>
        <h1>${course.name}</h1>
        <div class="course-content">
          <dl>
            <dt><img src="${imageHelper.resolve(course.cover!,"s300,225")}" width="300" height="225" /> </dt>
            <dd>
              <ul>
              <li> 主讲老师： <a href="/u/${course.teacher}.html" usercard="${course.teacher}"></a> </li>
              <li> 适用范围：${kindHelper.getGradeLabel(course.scope)}</li>
              <li> 开课时间：<strong>${flint.timeToDate(course.startTime,"yyyy-MM-dd HH:mm")}</strong> </li>
              <li> 课程时长：${course.time/60}分钟 </li>
              <li>
                  <#if course.status==9> <a href="${SITE_DOMAIN}/v/id_${course.roomId!"0"}.html"  title="观看" class="btn">观看</a>
                  <div> <strong>${course.completes}</strong>人参加了该课程 </div>
                  <#else>
                  <#--用户在线课程，允许用户未报名直接进入，如果在线名额已满则不允许未报名的部分用户进入-->
                  <#if  ((course.startTime*1000)<(.now?long)&&(course.status=1||course.status=2||course.status=3))> 
                     <a href="javascript:;"  data-param="{subjectId:${course.courseId},subject:'${course.name}',type:2}" class="btn btn-danger" node-type="goto-room-fast">进入课室</a> 
                  <#--课程只有在报名阶段才显示能够报名 -->
                  <#elseif course.status=1 &&USER_ID!=course.userId?string> <a href="javascript:;" onclick="Trade.order(${course.courseId},2)" class="button  button-join"><span class="join">马上报名</span><span  class="count" node-type="countdown" date="${course.startTime}" when="location.reload()"></span></a> <#--显示课程状态-->
                  <#else> 
                  <span  style="color:red">
                  ${enumHelper.getLabel("course_status",course.status?j_string)}
                  </span> </#if>
                  <#-- 已报名的人员-->
                  <#if (joins?size>0) >
                  <div> <strong>
                    ${joins?size}
                    </strong>人报名参加
                    <ul class="user-list small">
                      <#list joins as m>
                      <li> <a href="/u/${m}.html" target="_blank"><img   usercard="${m}" src="/theme/school/default/images/noface_s.jpg" height="30" width="30"/></a> </li>
                      </#list>
                    </ul>
                  </div>
                  </#if>
                  </#if> 
              </li>
              </dd>
          </dl>
          <div class="clear"></div>
          <div id="ckepop" class="share"> <span class="jiathis_txt">分享到：</span><a class="jiathis_button_tsina"></a><a class="jiathis_button_renren"></a><a class="jiathis_button_kaixin001"></a><a class="jiathis_button_taobao"></a><a class="jiathis_button_tqq"></a><a class="jiathis_button_qzone"></a><a class="jiathis_button_googleplus"></a><a class="jiathis_button_ujian"></a><a class="jiathis_button_ishare"></a><a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank">更多</a><a class="jiathis_counter_style"></a> </div>
        </div>
      </div>
      <div class="tab-header" node-type="tabnavigator" mode="hash">
        <ul>
          <li> <a href="#!/course-intro.html" remote="false" view="#introView" autoload="true">课程简介</a> </li>
          <#if course.status==9>
          <li> <a href="#!/exercise/get.html?exerciseId=1" remote="true" view="#exerciseView" >课后练习</a> </li>
          </#if>
          <li> <a href="#!/course/comments-${course.courseId},-1.html" remote="false" view="#commentView" once="true" group="true" >评价<#if (course.comments>0) >(<span id="cnums${course.courseId}">
            ${course.comments}
            </span>)</#if></a> </li>
        </ul>
      </div>
      <!--tab_title end-->
      <div class="tab-view fluid" id="introView">
        ${course.intro}
      </div>
      <div class="tab-view fluid" id="exerciseView"> 没有课后练习 </div>
      <!--tab_con end-->
      <div class="tab-view" id="commentView">
        <div  class="tab-subheader">
          <ul>
            <li> <a href="#!/course/comments-${course.courseId},-1.html"  view="#commentsListView"  autoload="true" page="true" id="commentAll">全部</a> </li>
            <li> <a href="#!/course/comments-${course.courseId},5.html"   view="#commentsListView" page="true"> 非常有用</a> </li>
            <li> <a href="#!/course/comments-${course.courseId},4.html"   view="#commentsListView" page="true"> 有用</a> </li>
            <li> <a href="#!/course/comments-${course.courseId},3.html"   view="#commentsListView" page="true"> 一般</a> </li>
            <li> <a href="#!/course/comments-${course.courseId},1.html"  view="#commentsListView" page="true"> 作用不大</a> </li>
          </ul>
        </div>
        <div class="tab-view fluid" style="display:block">
           <#include "/comment/post.ftl"/>
           <div   id="commentsListView"></div>
        </div>
      </div>
      
      <!-- --> 
      
    </div>
    <div class="span3">
      <div class="side-section"> 
        <!--参加或者观看该课程的用户-->
        <div class="side-section-inner">
          <button class="btn btn-success"><i class="icon-book icon-white"></i> &nbsp;报名&nbsp;&nbsp;&nbsp; </button>
          <button class="btn"> <i class="icon-facetime-video"></i>&nbsp;观看录像&nbsp;&nbsp;&nbsp; </button>
          <div> <a href="#">${joins?size}</a>人参加该课程 </div>
          <!--参加的用户列表-->
          <#list joins as m>
          <div class="list"> <a  href="#"> <img src="http://p1.zhimg.com/f5/c4/f5c4d8c99_s.jpg" > </a> </div>
          </#list>
        </div>
      </div>
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
