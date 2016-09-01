<#include "../header.ftl"/>
<#assign gid=RequestParameters["gid"]/>
<form id="addKind" action="${SITE_DOMAIN}/manager/data/saveKind.html" method="POST">
<div>
  <div class="pop-header"> <a href="javascript:void(0)" class="close" node-type="pop-close">×</a>
    <h3>新增科目</h3>
  </div>
  <div class="pop-body">
  	<input type="hidden" id="grade" name="grade" value="${gid}"/>
  	<p> KIND:</p>
	<input type="text" id="kind" name="kind"/>
    <p> 名称:</p>
    <input type="text" id="name" name="name"/>
  </div>
  <div class='pop-footer'>
    <button class='button ok' type="button" node-type='submit-ok'>提交</button>
    <button class='button cancle' type="button"  node-type='pop-close'>取消</button>
  </div>
</div>
</form>
