<#assign keyword=RequestParameters["q"]! />
<#assign tab=RequestParameters["tab"]!"all" />
<#assign c=RequestParameters["c"]! />
<div class="filter-box box">
		<dl>
			<#assign grade=kindHelper.getGrades()/>
			<dt>
				<@flint.condition condition,"?q=${keyword}&p=1&c=@",0,"0","所有年级"/>
			</dt>
			<dd>
				<#list grade?keys as key>
				<@flint.condition condition,"?q=${keyword}&p=1&c=@",0,key,grade[key]/>
				</#list>
			</dd>
		</dl>
		<dl>
			<dt>
				<@flint.condition condition,"?q=${keyword}&p=1&c=@",1,"0","全部学科"/>
			</dt>
			<dd>
				<#if   condition[0]!="0">
				<#assign kind=kindHelper.getKind(condition[0])/>
				<#list kind as k>
				<@flint.condition condition,"?q=${keyword}&p=1&c=@",1,""+k.kind,k.name/>
				</#list>
				<#else>
				<#assign kinds=kindHelper.getKinds()/>
				<#list kinds?keys as key>
				<@flint.condition condition,"?q=${keyword}&p=1&c=@",1,key,kinds[key]/>
				</#list>
				</#if>
			</dd>
		</dl>
        <#assign tutor_type=enumHelper.getEnum("tutor_type") />
        <dl>
            <dt> <@flint.condition condition,"?q=${keyword}&p=1&c=@",2,"0","不限辅导类型"/> </dt>            	
            <dd>
              <#list tutor_type?keys as key>
                 <@flint.condition condition,"?q=${keyword}&p=1&c=@",2,key,tutor_type[key]/>
              </#list>   
            </dd>
		</dl>
        <dl>
            <dt> <@flint.condition condition,"?q=${keyword}&p=1&c=@",3,"0","不限辅导时段"/> </dt>
            <dd>
                <@flint.condition condition,"?q=${keyword}&p=1&c=@",3,"1","8:00-10:00"/>
                <@flint.condition condition,"?q=${keyword}&p=1&c=@",3,"2","10:00-12:00"/>
                <@flint.condition condition,"?q=${keyword}&p=1&c=@",3,"3","14:00-16:00"/>
                <@flint.condition condition,"?q=${keyword}&p=1&c=@",3,"4","16:00-18:00"/>
                <@flint.condition condition,"?q=${keyword}&p=1&c=@",3,"5","19:00-20:00"/>
            </dd>
		</dl>
        <dl>
            <dt> <@flint.condition condition,"?q=${keyword}&p=1&c=@",4,"0","不限辅导价格"/> </dt>
            <dd>
                <@flint.condition condition,"?q=${keyword}&p=1&c=@",4,"1","30以下"/>
                <@flint.condition condition,"?q=${keyword}&p=1&c=@",4,"2","30-60"/>
                <@flint.condition condition,"?q=${keyword}&p=1&c=@",4,"3","60-100"/>
                <@flint.condition condition,"?q=${keyword}&p=1&c=@",4,"4","100以上"/>
            </dd>
		</dl>
		<dl>
			<dt>
				<@flint.condition condition,"?q=${keyword}&p=1&c=@",5,"0","不限发布时间"/>
			</dt>
			<dd>
				<@flint.condition condition,"?q=${keyword}&p=1&c=@",5,"1","最近三天"/>
				<@flint.condition condition,"?q=${keyword}&p=1&c=@",5,"2","最近一周"/>
				<@flint.condition condition,"?q=${keyword}&p=1&c=@",5,"3","最近两周"/>
				<@flint.condition condition,"?q=${keyword}&p=1&c=@",5,"4","最近一月"/>
				<@flint.condition condition,"?q=${keyword}&p=1&c=@",5,"5","最近两月"/>
			</dd>
		</dl>
</div>
<div class="tab-header" node-type="tabnavigator">
    <ul>
        <li <#if tab=="all">class="active"</#if> >
            <a  href="?q=${keyword}&p=1&c=${c}&tab=all">全部</a>
        </li>
        <li <#if tab=="school">class="active"</#if>>
            <a href="?q=${keyword}&p=1&c=${c}&tab=school">辅导机构</a>
        </li>
        <li <#if tab=="teacher">class="active"</#if>>
            <a href="?q=${keyword}&p=1&c=${c}&tab=teacher">辅导老师</a>
        </li>
    </ul>
    <a class="button float-right mt5" id="tutor_post_service" href="/tutor/service.form.html"  action-type="popup">免费发布辅导服务信息</a>
</div>
    <div id="course_tab" >
         <div class="order-box">
                    <ul>
                        <li>
                            <@flint.condition condition,"?q="+keyword+"&p=1&c=@&tab="+tab,6,"0","默认排序<i class=\"sort-desc\"></i>"/>
                        </li>
                        <li>
                           <@flint.condition condition,"?q="+keyword+"&p=1&c=@&tab="+tab,7,"2:","人气<i class=\"sort-desc\"></i>"/>
                        </li>
                        <li>
                           <@flint.condition condition,"?q="+keyword+"&p=1&c=@&tab="+tab,8,"3:","教师等级<i class=\"sort-desc\"></i>"/>
                        </li>
                        <li>
                           <@flint.condition condition,"?q="+keyword+"&p=1&c=@&tab="+tab,9,"5","家教保障<i class=\"sort-desc\"></i>"/>
                        </li>
                    </ul>
                    <span class="float-right search">
                        <input type="text" readonly="readonly" style="width:50px;" value="所在地">
						<label>关键字:</label>
						<input type="text" name="q" id="q"  value="${keyword}" placeholder="请输入关键字" onkeydown="if(event.keyCode == 13){Searcher.searchCourse(this.value,true);}"/><button  type="button" class="button" onclick="Searcher.searchCourse($('#q').val(),true)"></button>
					</span>
                </div>
        <div id="show_list">
                 <#include "../tutor/list.ftl"/>
         </div>
</div>