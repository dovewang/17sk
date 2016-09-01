<#assign callback=RequestParameters["cb"]!"" />
<div style="width:450px;">
	<a class="close" href="javascript:;" node-type="pop-close">×</a>
	<form  id="documentForm"  method="post" action="/doc/upload.html"  enctype="multipart/form-data"  onsubmit="return false;">
		<input id="courseId"  name="attr1"  type="hidden"  value="${RequestParameters["attr1"]!}"      />
		<input id="attr2"     name="attr2"  type="hidden"  value="${RequestParameters["attr2"]!}"      />
		<input id="attr3"     name="attr3"  type="hidden"  value="${RequestParameters["attr3"]!}"      />
		<dl class="form label80">
			<dd>
				<label> 上传文件： </label>
				<span> <a  class="button green"  node-type="upload-button" file-id="doc_file" file-name="filedata" file-change="$('#name').val(this.value.substring(this.value.lastIndexOf('\\')+1,this.value.lastIndexOf('.'))).change()"> 选择文件 </a></span>
			</dd>
			<dd>
				<label>&nbsp;</label>
				<span  style="color:#F60;">目前只支持ppt，pptx，gif，png，jpeg，bmp等图片格式文件</span>
			</dd>
			<dd>
				<label> 课件名称： </label>
				<span>
					<input type="text" id="name" name="name"  class="input" style="width:300px" maxlength="20"/>
				</span>
			</dd>
			<dd>
				<label> 课件描述： </label>
				<span> 					<textarea id="intro"  class="input" name="intro"  style="width:300px" rows="4"></textarea> </span>
			</dd>
			<dd class="text-right">
				<button type="button" onclick="DM.upload('${callback}',this);return false;"  node-type="pop-close-trigger" class="button ok" >
					确定
				</button>
				<button  type="button" node-type="pop-close" class="button cancle">
					取消
				</button>
			</dd>
		</dl>
	</form>
</div>
