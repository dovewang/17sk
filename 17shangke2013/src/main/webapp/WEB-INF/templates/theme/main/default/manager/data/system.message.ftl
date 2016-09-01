<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl"/>
<script src="${IMAGE_DOMAIN}/editor/xheditor.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<link href="/theme/school/default/css/management.css?${VERSION}" rel="stylesheet" type="text/css" />
</head>
<body>
<#include "../top.ftl"/>
<div id="page-main">
<div class="admin_funcitonbox">
	<div id="admin_feedback">
		<form id="anounceForm" action="/admin/announce.html" method="post" onsubmit="return false;">
			<div id="feedback-title3"></div>
			<div id="feedback-prompt">
				<!--您还可以输入<strong>140</strong>字-->
			</div>
			<div id="admin_feedbackTextAreabox">
				<textarea name="subject" id="subject" class="input admin_feedback_textarea" style="height:50px" placeholder="请输入简单公告的信息或者较长公告的的标题"></textarea>
				<div id="admin_feedback_function">
                 <div class="insertQuestion">
                    <a href="javascript:void(0)"  onclick="$('#content').slideToggle()">详细信息</a></div>
				</div>
               <div id="content" style="width:100%; padding:0; display:none;"> 
               	<textarea name="content" id="content_editor" node-type="editor" style="width:100%; height:200px"></textarea>
               </div>
			</div>
			<!--right_funcitonbox end-->
			<div  class="text-right mt10">
				<button class="btn btn-primary"  type="button" onclick="User.systemMessage()" >发布</button>
				<div class="clear"></div>
			</div>
		</form>
	</div>
</div>
<#if result.totalCount==0>
<div style="text-align:center;height:80px;line-height:80px;vertical-align:middle">
	没有任何消息
</div>
<#else>
<table width="98%" class="table table-striped table-bordered table-condensed">
  <thead>
    <tr>
      <th>消息主题</th>
      <th>消息内容</th>
      <th>状态</th>
      <th>发送时间</th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody>
  <#list result.result as m>
  <tr>
    <td width="150">
    	<#if (m.subject!"")?length gt 15>${(m.subject!"")?substring(0,15)}...<#else>${m.subject!"无"}</#if>
    	<div style="display:none;border:1px solid;width:150px;word-break:break-all;" id="s${m.messageId}">
            ${m.subject!"无"}
        </div>
    </td>
    <td width="550">
    	<#if (m.message!"")?length gt 35>${(m.message!"")?substring(0,35)}...<#else>${m.message!}</#if>
    	<div style="display:none;border:1px solid;width:550px;word-break:break-all;" id="m${m.messageId}">
            ${m.message!}
        </div>
    </td>
    <td>${m.sendStatus!}</td>
    <td>${helper.passTime(m.sendTime)}</td>
    <td><i class="icon-edit"></i><a href="javascript:void(0)" onclick="$('#s${m.messageId}').slideToggle();$('#m${m.messageId}').slideToggle();">详细&gt;</a></td>
  </tr>
  </#list>
  </tbody>
  <tfoot>
    <tr>
      <td colspan="8"><@flint.pagination result,"","","" /></td>
    </tr>
  </tfoot>
</table>
</#if>
</div>
</body>
</html>