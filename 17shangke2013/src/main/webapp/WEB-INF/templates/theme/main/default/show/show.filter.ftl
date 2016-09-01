<div class="filter-box box"id="show-filter-box">
	<dl>
		<dt>
			<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,0,"0","热门标签"/>
		</dt>
		<dd>
			<#assign tags = tagSearch!/>
			<#list tags as key>
			<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,0,key.name,key.name+"("+key.counts+")"/>
			</#list>
		</dd>
	</dl>
	<dl>
		<dt>
			<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,1,"-1","全部类别"/>
		</dt>
		<dd>
			<#assign show_category=enumHelper.getEnum("show_category") />
			<#list show_category?keys as key>
			<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,1,key,show_category[key]/>
			</#list>
		</dd>
	</dl>
	<!--
	<dl>
	<dt>
	<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,2,"0","不限分数"/>
	</dt>
	<dd>
	<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,2,"1","大于6"/>
	<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,2,"2","大于7"/>
	<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,2,"3","大于8"/>
	<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,2,"4","大于9"/>
	</dd>
	</dl>
	-->
	<dl>
		<dt>
			<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,3,"0","不限发布时间"/>
		</dt>
		<dd>
			<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,3,"1","最近三天"/>
			<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,3,"2","最近一周"/>
			<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,3,"3","最近两周"/>
			<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,3,"4","最近一月"/>
			<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,3,"5","最近两月"/>
			<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab,3,"6","最近三月"/>
		</dd>
	</dl>
</div>
