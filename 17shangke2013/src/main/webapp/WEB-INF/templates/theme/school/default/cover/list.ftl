<#include "/conf/config.ftl" />
<#assign c=RequestParameters["c"]!"-1"/>
<div class="filter-section">
	<dl>
		<dt>
			<#if c=="-1" >
			<span class="active">全部</span>
			<#else>
			<a href="/cover/list.html?t=0&c=-1" param="?c=-1">全部</a>
			</#if>
		</dt>
		<dd>
			<#assign kinds=kindHelper.getKinds()/>
			<#list kinds?keys as key>
			<#if key==c>
			<span class="active">${kinds[key]}</span>
			<#else>
			<a href="/cover/list.html?t=0&c=${key}" param="/cover/list.html?t=0&c=${key}">${kinds[key]}</a>
			</#if>
			</#list>
			<#if c=="0">
			<span class="active">其他</span>
			<#else>
			<a href="/cover/list.html?t=0&c=0" param="/cover/list.html?t=0&c=0">其他</a>
			</#if>
		</dd>
	</dl>
</div>
<ul class="images">
	<#list result.result as cover>
	<li>
		<i></i>
		<a href="javascript:;"><img src="${cover.url}" width="200" height="150"></a>
	</li>
	</#list>
</ul>
<div class="clear"></div>
<div>
	<@flint.pagination result,"","","" />
</div>
<div class="clear"></div>
<div>
	<button class="button primary large"  onClick="Course.saveCover(true)">
		保存
	</button>
	<button class="button large"   onclick="Pop.hide($('#cover-upload-pop')); ">
		取消
	</button>
</div>
<script type="text/javascript">
	$(function() {
		$(".images a").click(function() {
			$(".images li.selected").removeClass('selected');
			$(this).parent().addClass("selected")
		});
	})
</script>