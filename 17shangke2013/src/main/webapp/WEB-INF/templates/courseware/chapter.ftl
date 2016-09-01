<#include "/conf/config.ftl" />
<#assign  groupId=RequestParameters["g"]!/>
<#assign  view=RequestParameters["view"]!/>
<#assign  role_domain="group"+groupId+":"/>
<#assign  isManager=__USER.one("school"+site.id+":admin,"+role_domain+"teacher,"+role_domain+"admin,"+role_domain+"master")/>
<div class="pop-header"><a node-type="pop-close" class="close" href="javascript:;">×</a>
  <h3></h3>
  <span class="title pull-left" window="true" id="chapterWindow">
  ${chapter.title}
  </span>
  <#if isManager>
  <span class="title pull-right ">
     <div class="dropdown">
  <a href="javascript:;" class="dropdown-toggle" node-type="dropdown" id="dLabel">
    预览模式
    <b class="caret"></b>
  </a>
  <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel" >
     <li role="creator"  onclick="Courseware.view(this,${chapter.chapterId})"><a  href="javascript:;">创建者（老师）</a></li>
     <li class="divider"></li>
     <li role="student" onclick="Courseware.view(this,${chapter.chapterId})"><a  href="javascript:;">阅读者（学生）</a></li>
     <li role="admin" onclick="Courseware.view(this,${chapter.chapterId})"><a    href="javascript:;">管理员</a></li>
  </ul>
</div>
  </span>
  </#if>
  <span class="title pull-right">
  <label>
    <input type="checkbox">
    显示大纲</label>
  </span><span class="title pull-right">
  ${flint.timeToDate(chapter.classtime,"yyyy-MM-dd")}
  </span> </div>
<div class="pop-body" style="width:800px"> 
  <#if isManager&&(view!="student")>
     <#include "chapter.edit.ftl"/>
  <#else>
       <#include "chapter.view.ftl"/>
  </#if> </div>
<div class="pop-footer"> <#if isManager>
  <button type="button" class="btn" node-type="pop-close">取消</button>
  <button type="button" class="btn btn-primary"  node-type="pop-close-trigger" onclick="Courseware.publish(this)">发布</button>
  <#else>
  <button type="button" class="btn" node-type="pop-close">关闭</button>
  </#if> </div>
