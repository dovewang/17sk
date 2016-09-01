<#include "/conf/config.ftl" />
<#assign keyword=RequestParameters["q"]! />
<#--问题检索范围，如果不存在，代表只能在所有问题中查找-->
<#assign TYPE=TYPE!0 />
<#if expert??>
<div class="filter-box box">
<dl>
    <dt>
    	<@flint.condition condition,"?tab="+tab+"&is=&q="+keyword+"&p=1&c=@",1,"0","所有擅长"/>
    </dt>
    <dd>
		<#list expert as key>
       	 	<@flint.condition condition,"?tab="+tab+"&is=&q=&p=1&c=@",1,key,kindHelper.getKindLabel(key?j_string)/>
        </#list>
    </dd>
</dl>
</div>
</#if>
<#include "result.list.ftl" />
