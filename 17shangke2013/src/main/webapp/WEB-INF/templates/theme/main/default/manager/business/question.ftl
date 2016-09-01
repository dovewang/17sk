<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl"/>
</head>
<body>
<#include "../top.ftl"/>
<div id="page-main">
<div>
  <form action="/manager/business/question.html"  method="post">
    <table width="98%" border="1">
      <tr>
        <td>问题编号</td>
        <td><input name="questionId" type="text" id="questionId" value="${RequestParameters["questionId"]!}"/></td>
        <td>问题状态</td>
        <td>${enumHelper.getSelect("question_status","status",RequestParameters["status"]!,"全部")}</td>
      </tr>
      <tr>
        <td>问题发布时间</td>
        <td>
        	<input name="start" type="date" id="start" value="${RequestParameters["start"]!}"/>
     	     	至
            <input name="end" type="date" id="end" value="${RequestParameters["end"]!}"/></td>
        <td></td>
        <td>
        	<button class="btn btn-primary"  type="submit" >查询</button>
        	<button class="btn btn-primary"  type="button" onclick="Question.CreateIndex()" >重建索引</button>
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
      <th>年级</th>
      <th>学科</th>
      <th>价格</th>
      <th>最后更新时间</th>
      <th>提问人</th>
      <th>状态</th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody>
  <#list result.result as q>
  <tr>
  	<td>${q.questionId}</td>
    <td style="width:200px;">
	   	 <a href="/qitem-${q.questionId}.html" target="_blank">${q.title!""}</a>
    </td>
    <td>${kindHelper.getGradeLabel(q.grade?j_string)}</td>
    <td><#if q.kind!=0>${kindHelper.getKindLabels(q.kind?j_string)}</#if></td>
    <td>
    	<div align="right"><span class="label label-important">
        ${q.price}
        ${MONEY_UNIT}
        </span> </div>
    </td>
    <td>
      <#if q.lastUpdateTime!=0>(更新于
      ${flint.timeToDate(q.lastUpdateTime,"yyyy-MM-dd HH:mm")})
      </#if>
    </td>
    <td>
    	<a href="/u/${q.asker}.html" target="_blank" usercard="${q.asker}">
      	${q.asker}
      	</a>
    </td>
    <td id="td_${q.questionId}">${enumHelper.getLabel("question_status",q.status?j_string)!}</td>
    <td>
    	<a href="#question-verify${q.questionId}" node-type="pop" setting="{id:'question-verify-pop',effect:'mouse',modal:false}"><i class="icon-edit"></i>审核</a>
   		<#if q.status != 9>
    	<a href="javascript:void(0)"  onclick="Question.del(this,${q.questionId})">删除</a>
    	</#if>
   		<div id="question-verify${q.questionId}" style="display:none">
		  <div class="pop-header"> <a href="#" class="close" node-type="pop-close">×</a>
		    <h3>审核问题</h3>
		  </div>
		  <div class="pop-body">
		  	<p> 审核状态:</p>
		  	<input type="radio" name="verify${q.questionId}" value="-1">不通过&nbsp;
		    <input type="radio" name="verify${q.questionId}" value="1" checked="true">通过&nbsp;
		    <p> 不通过请输入原因:<!--原因选择功能--></p>
		    <textarea id="question-memo${q.questionId}">您的问题违反规定被删除</textarea>
		  </div>
		  <div class="pop-footer">
		    <button  class="btn btn-primary" onclick="Question.verify('${q.questionId}');"> 确定 </button>
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