<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl"/>
</head>
<body>
<#include "../top.ftl"/>
<div id="page-main">
<div>
  <form action="/manager/business/show.html"  method="post">
    <table width="98%" border="1">
      <tr>
        <td>秀秀编号</td>
        <td><input name="showId" type="text" id="showId" value="${RequestParameters["showId"]!}"/></td>
        <td>秀秀标题</td>
        <td><input name="title" type="text" id="title" value="${RequestParameters["title"]!}"/></td>
      </tr>
      <tr>
        <td>发布时间</td>
        <td>
        	<input name="start" type="date" id="start" value="${RequestParameters["start"]!}"/>
     	     	至
            <input name="end" type="date" id="end" value="${RequestParameters["end"]!}"/></td>
        <td></td>
        <td>
        	<button class="btn btn-primary"  type="submit">查询</button>
        	<button class="btn btn-primary"  type="button" onclick="Show.CreateIndex()">重建索引</button>
        </td>
        <td></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
  </form>
</div>
<table width="98%" class="table table-striped table-bordered table-condensed">
  <thead>
    <tr>
      <th>编号</th>
      <th>标题</th>
      <th>类型</th>
      <th>发布时间</th>
      <th>发布人</th>
      <th>状态</th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody>
  <#list result.result as s>
  <tr>
    <td>${s.showId}</td>
    <td style="width:200px">
       <a href="/show/item-${s.showId}.html" target="_blank">${s.title!}</a>
    </td>
    <td>
    	<#if s.type==1>
        	在线秀秀
    	<#elseif s.type==4>
    		分享视频
    	</#if>
    </td>
    <td>${flint.timeToDate(s.dateline,"yyyy-MM-dd HH:mm")}</td>
    <td>
    	<a href="/u/${s.userId}.html" target="_blank" usercard="${s.userId}">
     	 ${s.userId}
      	</a>
    </td>
    <td>
        <#if s.status==9>
        	删除
    	<#else>
    		正常
    	</#if>
    </td>
    <td>
    	<#if s.status!=9>
    		<a href="#show-del${s.showId}" node-type="pop" setting="{id:'show-del-pop',effect:'mouse',modal:false}"><i class="icon-del"></i>删除</a>
    	</#if>
    	<div id="show-del${s.showId}" style="display:none">
		  <div class="pop-header"> <a href="#" class="close" node-type="pop-close">×</a>
		    <h3>删除秀秀</h3>
		  </div>
		  <div class="pop-body">
		    <p> 请输入原因:<!--原因选择功能--></p>
		    <textarea id="show-memo${s.showId}">您的秀秀违反规定被删除</textarea>
		  </div>
		  <div class="pop-footer">
		    <button  class="btn btn-primary" onclick="Show.Del('${s.showId}');"> 确定 </button>
		    <button class="btn" node-type="pop-close"> 取消 </button>
		  </div>
		</div>
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