<#include "/conf/config.ftl" />
<#if result.totalCount==0>
<div  class="nodata">
	对不起，没有找到您要的信息！
</div>
<#else>
<#list result.result as tutor>
<dl class="tutor-item">
	<dt>
		<a href="/u/${tutor.provider}.html" target="_blank"><img usercard="${tutor.provider}" src="/theme/main/default/images/noface_m.jpg" width="75" height="75"alt="学生名"/></a>
	</dt>
	<dd class="tutor-info">
		<h3>${tutor.title!}</h3>
		<span class="mark"><a href="/u/${tutor.provider}.html" target="_blank" usercard="${tutor.provider}">${tutor.provider}</a>发布于${helper.passTime(tutor.dateline)}</span>
	</dd>
	<dd class="tutor-price">
		<div>
			<span class="money">${tutor.price}</span>${MONEY_UNIT}/小时
		</div>
		<div>
			${tutor.openTime}-${tutor.closeTime}
		</div>
	</dd>
	<dd class="tutor-ensure">
		认证及保障
	</dd>
	<dd class="pt10">
		<button class="button primary large" action-type="candidate-add" candidate="${tutor.provider}">
			加入候选人
		</button>
	</dd>
</dl>
</#list>
</#if> 