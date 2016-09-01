<div class="filter-section">
  <dl>
    <#assign grade=kindHelper.getGrades()/>
    <dt>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",0,"0","所有年级"/>
    </dt>
    <dd> <#list grade?keys as key>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",0,key,grade[key]/>
      </#list> </dd>
  </dl>
  <dl>
    <dt>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",1,"0","全部学科"/>
    </dt>
    <dd> <#if condition[0]!="0">
      <#assign kind=kindHelper.getKind(condition[0])/>
      <#list kind as k>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",1,""+k.kind,k.name/>
      </#list>
      <#else>
      <#assign kinds=kindHelper.getKinds()/>
      <#list kinds?keys as key>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",1,key,kinds[key]/>
      </#list>
      </#if> </dd>
  </dl>
  <#--知识点-->
  <#assign  knowledgePath=condition[3] />
  <#assign knowledge=knowledgeHelper. getKnowledge(condition[0]?number,condition[1]?number,0) />
  <#if (knowledge?size>0)>
  <dl>
    <dd style="width:100%"> <#list knowledge as k>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",3,k.knowledgeId?string,k.knowledge/>
      </#list> </dd>
  </dl>
  </#if>
  <dl>
      <dt><@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",2,"9","所有问题"/></dt>
      <dd><@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",2,"2","已解决"/>
          <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",2,"1","待解决"/>
          <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",2,"8","零解答"/>
          <span class="pull-right">
          <div class="input-append">
    <input type="text" name="q" id="q"  value="${keyword}" placeholder="请输入关键" onkeydown="if(event.keyCode == 13){Searcher.searchQuestion(this.value);}"/>
    <button type="button"  name="button" id="button"  class="btn" onclick="Searcher.searchQuestion($('#q').val())"> <i class="icon-search"></i></button>
      </div>
      </span>
      </dd>
  </dl>
</div>
<div> 
   <#if result.totalCount==0>
      <div class="placeholder-box">很抱歉，没有找到您要的问题</div>
   <#else>
   <#include "../question/list.ftl" /> 
   </#if>
</div>
