<#include "/conf/config.ftl" />
<div class="comments box" >
				<textarea rows="3" class="input" name="content" id="comment_content" style="width:98%"></textarea>
			<div class="comments-footer">
		<a href="javascript:void(0)" class="icon-face" onclick="loadFace(this,'comment_content')">表情</a>
		<button class="button large primary float-right" onclick="Show.comment();return false;" style="margin-right:20px">评论</button>
	</div>
</div>
<#if result.totalCount==0>
<div  class="nodata">
	暂时还没有评论
</div>
<#else>
<#list result.result as m>
<dl class="list-item" action-type="comment-item" commentid="${m.commentId}">
	<dt class="face">
		<a href="/u/${m.userId}.html" target="_blank"><img usercard="${m.userId}" src="/theme/school/default/images/noface_s.jpg" width="50" height="50" alt="学生名"/></a>
	</dt>
	<dd class="content">
		<p>
			<a href="/u/${m.userId}.html" target="_blank" usercard="${m.userId}">${m.userId}</a>：
			${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()}
		</p>
		<p class="data-info">
			<span class="link-group float-right"></span>${helper.passTime(m.dateline)}
		</p>
	</dd>
</dl>
</#list>
<@flint.pagination result,"/show/comments.${result.result[0].showId}.@.html","","" />
</#if> 