<#include "/conf/config.ftl" />
<#assign  groupId=RequestParameters["g"]/>
<#assign  role_domain="group"+groupId+":"/>
<link href="/theme/doc/doc.css?${VERSION}" rel="stylesheet" type="text/css">
<div  class="lesson-list" id="doc-list">
  <table class="table table-striped ">
    <thead>
      <tr>
        <th>科目</th>
        <th>授课教师</th>
        <th>最后更新时间</th>
      </tr>
    </thead>
    <tbody>
    <#list cws as lesson>
    <tr>
      <td><h2> <a href="/courseware/item/${lesson.lessonId}.html?g=${groupId}" node-type="link">
          ${lesson.lesson}
          </a> </h2></td>
      <td>${lesson.teacher!}</td>
      <td><em>
        ${helper.passTime(lesson.updateTime)}
        </em></td>
    </tr>
    </#list>
      </tbody>
    
  </table>
</div>
<#--只允许老师，管理员和班长创建课件资料-->
<#if __USER.hasRole(role_domain+"teacher,"+role_domain+"admin,"+role_domain+"master") >
<div class="box box-white" id="newLesson">
  <div class="box-header">新增课程</div>
  <form action="/courseware/lesson.add.html" method="post" id="lessonForm">
    <dl class="form">
      <dd>
        <label>科目名称：</label>
        <span>
        <input type="text" name="lesson" class="w1" maxlength="30">
        </span></dd>
      <dd>
        <label>科目描述：</label>
        <span>
        <textarea name="intro" class="w1"></textarea>
        </span></dd>
      <dd>
        <label>任课老师：</label>
        <span>
        <input type="text" name="teacher" class="w1">
        </span></dd>
      <dd>
        <label>可访问的班级：</label>
        <span>
        <a node-type="link" data-target="#showgroups" href="/group/select.html" href="javascript:;">选择</a>
        <div id="showgroups">
        
        </div>
        </span></dd>
        
      <dd>
        <label>&nbsp;</label>
        <span>
        <button  type="button" class="button primary" onclick="Courseware.addLesson()"> 新增课程 </button>
        </span>
    </dl>
  </form>
</div>
</#if>
