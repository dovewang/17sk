<#include "../header.ftl"/>
<#assign c=RequestParameters["c"]!"-1"/>
<form id="cover-upload-form" action="${SITE_DOMAIN}/manager/cover/upload.html" method="post" enctype="multipart/form-data">
<div>
  <div class="pop-header"> <a href="javascript:void(0)" class="close" node-type="pop-close">×</a>
    <h3>封面上传</h3>
  </div>
  <div class="filter-box box">
	<dl>
		<dt>
		</dt>
		<dd>
			<#assign kinds=kindHelper.getKinds()/>
			<#list kinds?keys as key>
			<input type="radio" name="kind" value="${key}"/>${kinds[key]}
			</#list>
			<input type="radio" name="kind" value="0"/>其它
		</dd>
	</dl>
  </div>
  <div class="popup-body" id="file-text"> 
   	<input type="file" name="file"/>
  </div>
  <div class='pop-footer'>
  	<button class='button ok' type="button" node-type='add-cover'>增加文件</button>
    <button class='button ok' type="button" node-type='ok-cover'>提交</button>
    <button class='button cancle' type="button"  node-type='pop-close'>取消</button>
  </div>
</div>
</form>
