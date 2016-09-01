<#--问题过滤筛选-->
<#include "/conf/config.ftl" />
<#assign keyword=RequestParameters["q"]! />
<#--问题检索范围，如果不存在，代表只能在所有问题中查找-->
<#assign TYPE=TYPE!0 />
<#assign tab=RequestParameters["tab"]!"1,1" />
<div class="filter-box box">
<dl>
    <#assign grade=kindHelper.getGrades()/>
    <dt><@flint.condition condition,"?tab="+tab+"&is="+TYPE+"&q="+keyword+"&p=1&c=@",0,"0","所有年级"/></dt>
    <dd><#list grade?keys as key><@flint.condition condition,"?tab="+tab+"&is="+TYPE+"&q="+keyword+"&p=1&c=@",0,key,grade[key]/> </#list></dd>
</dl>
<dl>
    <dt><@flint.condition condition,"?tab="+tab+"&is="+TYPE+"&q="+keyword+"&p=1&c=@",1,"0","全部学科"/> </dt>
    <dd>
    <#if condition[0]!="0">
        <#assign kind=kindHelper.getKind(condition[0])/>      
        <#list kind as k>
        <@flint.condition condition,"?tab="+tab+"&is="+TYPE+"&q="+keyword+"&p=1&c=@",1,""+k.kind,k.name/>
        </#list>
        <#else>
        <#assign kinds=kindHelper.getKinds()/>
        <#list kinds?keys as key>
        <@flint.condition condition,"?tab="+tab+"&is="+TYPE+"&q="+keyword+"&p=1&c=@",1,key,kinds[key]/>
        </#list>
        </#if>
    </dd>
</dl>
<#--知识点-->     
<#assign  knowledgePath=condition[3] />
<#assign knowledge=knowledgeHelper. getKnowledge(condition[0]?number,condition[1]?number,0) />                              
<dl>
   <dt><@flint.condition condition,"?tab="+tab+"&is="+TYPE+"&q="+keyword+"&p=1&c=@",3,"0","所有知识点"/></dt>
   <dd>
        <#list knowledge as k>
        <@flint.condition condition,"?tab="+tab+"&is="+TYPE+"&q="+keyword+"&p=1&c=@",3,k.knowledgeId?string,k.knowledge/>
        </#list>
    </dd>
</dl>
<dl>
     <dt><@flint.condition condition,"?tab="+tab+"&is="+TYPE+"&q="+keyword+"&p=1&c=@",2,"9","所有问题"/></dt>
     <dd>
     <@flint.condition condition,"?tab="+tab+"&is="+TYPE+"&q="+keyword+"&p=1&c=@",2,"2","已解决"/>
     <@flint.condition condition,"?tab="+tab+"&is="+TYPE+"&q="+keyword+"&p=1&c=@",2,"1","待解决"/>
     <@flint.condition condition,"?tab="+tab+"&is="+TYPE+"&q="+keyword+"&p=1&c=@",2,"8","零解答"/>
      <i class="float-right search">
      <label>关键字:</label>
      <input type="text" name="q" id="q" value="${keyword}" maxlength="50"  style="width:120px" placeholder="请输入关键" onkeydown="if(event.keyCode == 13){Searcher.searchQuestion(this.value,${QUESTION_AJAX!"false"});}"/><button  class="button" type="button"  onclick="Searcher.searchQuestion($('#q').val(),${QUESTION_AJAX!"false"})"></button>
     </i>
     </dd>
</dl>
</div>
