<#include "../header.ftl"/>
<#assign gid=RequestParameters["gid"]/>
<#assign kid=RequestParameters["kid"]/>
<#assign parentid=RequestParameters["parentid"]!"0"/>
<form id="addknowledge" action="${SITE_DOMAIN}/manager/data/saveKnowledge.html" method="POST">
<div>
  <div class="pop-header"> <a href="javascript:void(0)" class="close" node-type="pop-close">×</a>
    <h3>新增知识点</h3>
  </div>
  <div class="pop-body">
  	<input type="hidden" id="grade" name="grade" value="${gid}"/>
  	<input type="hidden" id="kind" name="kind" value="${kid}"/>
  	<input type="hidden" id="parentKnowledgeId" name="parentKnowledgeId" value="${parentid}"/>
  	<p> 知识点名称:</p>
	<input type="text" id="knowledge" name="knowledge"/>
    <p> 知识点描述:</p>
    <textarea id="intro" name="intro"></textarea>
  </div>
  <div class='pop-footer'>
    <button class='button ok' type="button" node-type='submit-ok'>提交</button>
    <button class='button cancle' type="button"  node-type='pop-close'>取消</button>
  </div>
</div>
</form>
