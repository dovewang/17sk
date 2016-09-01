<#include "/conf/config.ftl" />
<#if result.totalCount==0>
<div  class="noresult">
	<h2>很遗憾，没能找到您需要的问题~</h2>
	<h3>再找找吧</h3>
	<p>
		请尽量输入常用词， 请尽量使用简洁的关键词，可用空格将多个关键词分开
	</p>
	<h3>您还可以：</h3>
	<p>
		直接发布问题，我们有专业的老师可以实时为您解答问题。
	</p>
</div>
<#else>
<div id="question_page_list">
	<!--刷新页面选择器-->
	﻿<#include "list.ftl" />
	<@flint.pagination result,"","","" />
</div>
</#if> 