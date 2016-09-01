<!DOCTYPE html>
<html>
<head>
<#include "../../head.ftl" />
<link href="/theme/school/default/css/skin.css?${.now}" rel="stylesheet" type="text/css">
</head><body>
<#include "../../top.ftl"/>
<div class="container">
  <div class="row-fluid">
    <div class="span3"><#include "../left.ftl"/></div>
    <div class="span9">
      <div>
        <ul class="group-years clearfix">
          <#list years as item>
          <li><a href="?year=${item[0]}" <#if year=item[0]>class="active"</#if>>
            ${item[0]}
            <sup>
            ${item[1]}
            </sup> </a></li>
          </#list>
        </ul>
      </div>
      <div class="row-fluid">
        <div class="span4" id="group-list">
          <div>
            <button rel="/admin/class/form.html" pop-id="pop-classform" class="btn btn-primary" node-type="pop">新增班级</button>
          </div>
          <dl class="group-item active"  onclick="School.loadGroupUsers(this)" href="/admin/user/list.html" data-target="#member-group-list">
            <dt><img name=""  width="64" height="64" src="http://purecss.io/img/common/tilo-avatar.png"></dt>
            <dd> <span class="group-memebers  hidden-phone">54人</span>
              <h5>未分组</h5>
            </dd>
          </dl>
          <#list groups.result as group>
          <dl class="group-item" id="classItem${group.groupId}" groupid="${group.groupId}" onclick="School.loadGroupUsers(this)" href="/admin/user/list.html?classid=${group.groupId}" data-target="#member-group-list">
            <dt><img   width="64" height="64" src="http://purecss.io/img/common/tilo-avatar.png"></dt>
            <dd> <span class="group-memebers">
              ${group.members}
              人</span>
              <h5>
                ${group.name}
              </h5>
              <div class="control-bar"><a href="/admin/class/form.html?id=${group.groupId}" pop-id="pop-classform" node-type="pop">编辑</a> | <a href="javascript:;" node-type="pop-confirm" data-message="您确定要删除该信息？" ok="School.deleteClass(${group.groupId},this)">删除</a><a href="">毕业</a></div>
            </dd>
          </dl>
          </#list> </div>
        <div class="span8" id="member-list">
          <div>名字：
            <input type="text" name="name" id="name">
            <span node-type="combobox"  data-name="role" class="combobox-box" max="1"> <span class="item" data-value="1">全部</span> <span class="item" data-value="1">老师</span> <span class="item" data-value="1">学生</span> </span></div>
          <div id="member-group-list"></div>
        </div>
      </div>
    </div>
  </div>
</div>
<footer>
  <div id="page-footer"></div>
</footer>
<#include "../../../global.footer.ftl"/>
</body>
</html>