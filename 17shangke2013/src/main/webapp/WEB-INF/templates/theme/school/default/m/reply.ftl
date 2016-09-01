<#include "/conf/config.ftl" />
<#assign id=RequestParameters["id"]! />
<div class="text-arrow"><em>◆</em><span>◆</span></div>
<div class="replybox" style="padding:7px;">
	<div>
		<input name="newcom_${id}" type="text" id="newcom_${id}" value=""  class="input" style="width:98%"/>
	</div>
	<div id="feedback_function" style="width:650px;padding:5px 0;background:none;">
		<a href="javascript:void(0)" class="icon icon-face" onclick="loadFace(this,'newcom_${id}')">表情</a>
		<button class="float-right button green small" onclick="Mblog.postComments(${id});return false;" style="margin-right:20px">评论</button>
	</div>
	<div class="clear"></div>
</div>
<#list result.result as m>
<dl class="box-list small" id="comment${m.id}">
	<dt class="face">
		<a href="/u/${m.userId}.html" target="_blank"><img usercard="${m.userId}" src="/theme/school/default/images/noface_s.jpg" width="40" height="40" alt="学生名"/></a>
	</dt>
	<dd class="content">
		<p>
			<a href="/u/${m.userId}.html" target="_blank" usercard="${m.userId}">${m.userId}</a>：
			${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()}
		</p>
		<p class="data-info">
			<span> <a href="javascript:;" onclick="Mblog.replyComment(${m.id},${id},${m.userId});return false;">回复</a> </span>${helper.passTime(m.createTime)}
		</p>
	</dd>
</dl>
</#list>