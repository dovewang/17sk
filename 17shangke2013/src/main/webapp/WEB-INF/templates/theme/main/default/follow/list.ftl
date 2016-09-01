<#include "/conf/config.ftl" />
<#if result.totalCount==0>
<div  class="nodata"> 你还没有任何关注对象，现在去 "我的粉丝" 关注一些；
  或去 "人气推荐" 关注一些吧～ </div>
<#else>
<#list  result.result as  user >
<dl class="user-item list-item">
  <dt class="face"> <a href="/u/${user.userId}.html"><img src="${user.face}" width="50" height="50" usercard="${user.userId}" alt=""/></a> </dt>
  <dd class="content">
    <p> <a href="/u/${user.userId}.html" usercard="${user.userId}">
      ${user.realName!user.name}
      </a> <br/>
      ${user.experience!""}</p>
    <span class="action-group"> <a  href="javascript:void(0)" onclick="MC.showForm(${user.userId},'${user.realName!user.name}');return false">发送消息</a> </span> </dd>
</dl>
</#list>
<@flint.pagination result,"","","" />
</#if> 