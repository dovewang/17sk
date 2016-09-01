<#include "/conf/config.ftl" />
<div id="mblog_new_count" class="alert text-center" style="display:none"></div>
<#if result.totalCount==0>
<div  class="placeholder-box" id="mblog_page_list"> 没有发布任何信息 </div>
<#else>
<div id="mblog_page_list"> <#list result.result as m>
  <#include "item.ftl" />
  </#list> 
 </div>
<#assign g=group!0 />
<@flint.pagination result,"javascript:void(0)","onclick=\"Mblog.loadAll(@,"+g+");return false;\"","" />
</#if>