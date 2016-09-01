<#include "/conf/config.ftl" />
<#if result.totalCount==0>
<div  class="placeholder-box"> 对不起，没有找到您要的用户 </div>
<#else>
<#list  result.result as  user >
<div class="search_conlist">
  <div class="search_main_mess">
    <div class="search_main_pic"> <a href="/u/${user.userId}.html"><img src="${FILE_DOMAIN+"/"+user.face!(THEME_PATH+"/images/noface")}_m.jpg" width="75" height="75" usercard="${user.userId}" alt=""/></a> </div>
    <div class="search_right_mess">
      <h3><a href="/u/${user.userId}.html" usercard="${user.userId}">
        ${user.realName!user.name}
        </a></h3>
      <div class="search_right_function"> <a  href="javascript:void(0)" onclick="MC.showForm(${user.userId},'${user.realName!user.name}');return false">发送消息</a> <a href="javascript:void(0)" onclick="MC.askQuestion(${user.userId},'${user.realName!user.name}');return false">问作业</a>
        <div class="clear"></div>
      </div>
    </div>
  </div>
  <!--search_main_mess end-->
  <div class="search_right_text1"> 擅&nbsp;&nbsp;&nbsp;&nbsp;长：
    ${kindHelper.getKindLabels((user.expert!)?j_string)!"未设置"}
    <br />
    学&nbsp;&nbsp;&nbsp;&nbsp;生：
    ${user.students}
    人 <br />
    解决问题：
    ${user.correctAnswers}
    题 <br />
    开办课程：
    ${user.successCourse}
    门 </div>
  <!--search_right_text1 end-->
  <div class="search_right_text2">
    ${user.experience!""}
    <a href="/u/${user.userId}.html">详细</a> </div>
  <!--search_right_text2 end--> 
</div>
</#list>
<@flint.pagination result,"","","" />
</#if>