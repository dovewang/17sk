<#include "/conf/config.ftl" />
<#if result.totalCount==0>
<div  class="nodata">
	暂时还没有评论
</div>
<#else>
<#list result.result as m>
<dl class="box-list" action-type="comment-item" commentid="${m. commentId}">
	<dt class="face">
		<a href="/u/${m.userId}.html" target="_blank"><img usercard="${m.userId}" src="/theme/school/default/images/noface_s.jpg" width="50" height="50" alt="学生名"/></a>
	</dt>
	<dd class="content">
		<p>
			<a href="/u/${m.userId}.html" target="_blank" usercard="${m.userId}">${m.userId}</a>：
			${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()}
		</p>
		<p class="data-info">
			<span class="link-group float-right"></span>${helper.passTime(m.createTime)}
		</p>
	</dd>
</dl>
</#list>
<@flint.pagination result,"","","" />
</#if>