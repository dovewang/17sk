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
    <dd> <#if   condition[0]!="0">
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
  <dl>
    <dt>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",2,"-1","不限状态"/>
    </dt>
    <dd>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",2,"1","报名中"/>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",2,"9","已结课"/>
    </dd>
  </dl>
  <dl>
    <dt>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",6,"0","不限时间"/>
    </dt>
    <dd>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",6,"1","最近三天"/>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",6,"2","最近一周"/>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",6,"3","最近两周"/>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",6,"4","最近一月"/>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",6,"5","最近两月"/>
      <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",6,"6","最近三月"/>
    </dd>
  </dl>
</div>
<div class="clear"></div>
<!--course_selectionbox end-->
<div class="rightbox_funciton">
  <div class="filterbox">
    <ul>
      <li>
        <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",7,"0","默认排序"/>
      </li>
      <li> | </li>
      <li>
        <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",7,"2:","满意度"/>
      </li>
      <li> | </li>
      <#if condition[2]=="2">
      <li>
        <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",7,"4","最多播放"/>
      </li>
      <li> | </li>
      </#if>
      <li>
        <@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@",7,"5","最多收藏"/>
      </li>
    </ul>
  </div>
  <!--filterbox end-->
  <div class="filter_search">
    <label>请您输入检索关键字：</label>
    <input type="text" name="q" id="q" class="filter_input" value="${keyword}" placeholder="请输入关键字" onkeydown="if(event.keyCode == 13){Searcher.searchCourse(this.value,true);}"/>
    <input type="button"  name="button" id="button" value="提交"  class="but_filter_search" onclick="Searcher.searchCourse($('#q').val(),true)"/>
  </div>
  <!--filter_search end--> 
</div>
<!--rightbox_funciton end-->
<div class="tab_conshow"> <#include "../course/list.ftl"/>
  <div class="clear"></div>
</div>
