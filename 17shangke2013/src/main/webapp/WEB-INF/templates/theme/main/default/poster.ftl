<div class="poster" id="poster-wrap">
	<div class="poster-header">
		<a href="javascript:;" onclick="$('#faces_list').remove();" action-type="poster" poster="question-poster">问作业</a> <#if !(QUESTION_ONLY?exists)> <a href="javascript:;" class="active" action-type="poster" poster="mblog-poster">随便说说</a> </#if> <span  id="poster-count">您还可以输入<strong>100</strong>字</span>
	</div>
	<div id="mblog-poster">
		<input type="hidden" value="" id="mblog_media"/>
		<div class="poster-body">
			<textarea id="mblog_editor"  placeholder="随便说说"></textarea>
		</div>
		<div class="poster-footer">
			<span style="display:inline-block;zoom: 1;*display: inline;overflow: hidden">
			<a href="javascript:;" onclick="loadFace(this,'mblog_editor')"><i class="icon icon-smile"></i>表情</a>
			</span>
			<span style="display:inline-block;zoom: 1;*display: inline;overflow: hidden">
			<form enctype="multipart/form-data" id="imageForm" action="/upload/image.html" method="post" >
				<a href="javascript:;"  node-type="upload-button"  id="file"  name="filedata"  onchange="Mblog.uploadImage()"><i class="icon icon-image"></i>插入图片 </a>
			</form>
			</span>
			<div id="mblog_media_div" class="popover top" style="top:35px;left:100px;width:120px"></div>
			<button class="poster-button-post disabled"  disabled="disabled"  onclick="Mblog.post()" id="mblog-poster-button"></button>
		</div>
	</div>
	<div id="question-poster" style="display:none">
		<#include "question/question.poster.ftl"/>
	</div>
</div>
