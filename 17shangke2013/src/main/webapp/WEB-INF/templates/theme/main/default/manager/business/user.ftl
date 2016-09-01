<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl"/>
</head>
<body>
<#include "../top.ftl"/>
<div id="page-main">
<div>
  <form action="/manager/business/user.html"  method="post">
    <table width="98%" border="1">
      <tr>
        <td>用户编号</td>
        <td><input name="userid" type="text" id="userid" value="${RequestParameters["userid"]!}"/></td>
        <td>用户邮箱</td>
        <td><input name="email" type="text" id="email" value="${RequestParameters["email"]!}"/></td>
        <td>
        	<button class="btn btn-primary"  type="submit" />查询</button>
        	<button class="btn btn-primary"  type="button" onclick="User.CreateIndex()" >重建索引</button>
        </td>
      </tr>
    </table>
  </form>
</div>
<table width="98%" class="table table-striped table-bordered table-condensed">
  <thead>
    <tr>
      <th>编号</th>
      <th>昵称</th>
      <th>实名</th>
      <th>邮箱</th>
      <th>注册时间</th>
      <th>状态</th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody>
  <#list result.result as u>
  <tr>
    <td>${u.userId}</td>
    <td><a href="${SITE_DOMAIN}/u/${u.userId}.html">${u.name!}</a></td>
   <td>${u.realName!}</td>
    <td>${u.email!""}</td>
    <td>${flint.timeToDate(u.regiditTime,"yyyy-MM-dd HH:mm")}</td>
    <td>
    	<#if u.status==0>
    		未激活
    	<#elseif u.status==1>
    		正常
    	<#elseif u.status==2>
    		冻结
    	<#elseif u.status==3>
    		删除
    	<#elseif u.status==4>
    		证书过期
    	</#if>
    </td>
    <td>${u.memo!""}</td>
    <td>
    	<#if u.status==2>
    		<a href="javascript:;" id="freeze${u.userId}" node-type="pop-confirm" data-message="您确定要解锁吗？" OK="User.freeze(this,${u.userId})"  style="color:red"><i class="icon-edit"></i>解锁</a>
    	<#else>
    		<a href="javascript:;" id="freeze${u.userId}" node-type="pop-confirm" data-message="您确定要加锁吗？" OK="User.unfreeze(${u.userId})" ><i class="icon-edit"></i>锁定</a>
    	</#if>
    </td>
  </tr>
  </#list>
  </tbody>
  <tfoot>
    <tr>
      <td colspan="8"><@flint.pagination result,"","","" /></td>
    </tr>
  </tfoot>
</table>
</body>
</html>