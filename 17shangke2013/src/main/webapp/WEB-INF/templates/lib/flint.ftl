<#--时间转化-->
<#function timeToDate value,format><#return (value*1000)?number_to_datetime?string(format) ></#function>
<#--
枚举调用
name:字段名称
type:字段类型(string,time,number,money,enum)
format:格式化数据(时间格式式"T"代表过去的时间,枚举时代表枚举名称)
align:对其方式，默认左对齐
defaultLabel:默认显示值
-->
<#macro field data,type,name,format,extend...>
<#if format=="boolean">
<#assign  value=data[name]?string("1","0") />
<#else>
<#assign  value=data[name]?default("") />
</#if>
<div class="${type?default("string")}"  field="${name?default("")}" value="${value}" align="${align?default("left")}">
	<#if type=="enum">
	<#if format=="boolean">
	${enumHelper.getLabel("boolean"?j_string,value?j_string)}
	<#else>
	${enumHelper.getLabel(format?j_string,value?j_string)}
	</#if>
	<#elseif type="time">
	${value}
	<#elseif type="money">
	${value}
	<#elseif type="number">
	${value}
	<#else>
	${value}
	</#if>
</div>
</#macro>

<#--
condition:条件数组
url:链接路径
index:替换值的序号
value:替换值
text:显示值
-->
<#macro condition condition,url,index,value,text>
<#local link=""/>
<#local select=""/>
<#list condition as c>
<#if c_index==index>
<#local link=link+value />
<#if (c==value ||c==(value+"a")||c==(value+"d"))>
<#local select="true"/>
<#if c==(value+"a")>
<#local link=link+"d" />
<#local text="<span class='active asc'>"+text+"</span>"/>
<#local select="false"/>
<#elseif (c==(value+"d")||value?ends_with(":"))>
<#local link=link+"a" />
<#local text="<span class='active desc'>"+text+"</span>"/>
<#local select="false"/>
<#elseif (c=="t"||value?ends_with("t"))>
<#local select="false"/>
<#local link=link+"f" />
<#local text="<span class='active'>"+text+"</span>"/>
</#if>
</#if>
<#else>
<#local link=link+c />
</#if>
<#if c_has_next>
<#local link=link+","/>
</#if>
</#list>
<#if select=="true">
<span class="active">${text}</span>
<#else>
<a href="${url?replace("@",link)}" param="${url?replace("@",link)}">${text}</a>
</#if>
</#macro>
<#--
分业导航
page:页面对象
link:链接,其中'@'代表页号替换位置
param:参数，例如可以使用ajax方式加载
ext:扩展属性
-->
<#macro pagination page,link,function,ext>
<#if page.totalPage gt 1>
<#if link=""><#local link = page.url+"?"+page.param /></#if>
<#if function=""><#local function = " param=\"remote-href\"" /></#if>
<#local pageLen = $PAGEBAR_SIZE?default(4) />
<div class="clear"></div>
<div class="pagebar" ${ext?default("")}>
	<#if page.curPage!=1 >
	<a href="${link?replace("@","1")}"  ${function?replace("@",1)} >第一页</a>
	<#else>
	<span class="disabled">第一页</span>
	</#if>
	<#if page.hasPrev()>
	<a href="${link?replace("@",page.curPage-1)}" ${function?replace("@",page.curPage-1)} class="prev">上一页</a>
	<#else>
	<span class="prev disabled">上一页 </span>
	</#if>
	<#if (page.curPage>pageLen) >
	<#assign pageStart = page.curPage-pageLen/>
	<#else>
	<#assign pageStart = 1 />
	</#if>
	<#if (page.curPage<(page.totalPage-pageLen)) >
	<#assign pageEnd = page.curPage+pageLen/>
	<#else>
	<#assign pageEnd = page.totalPage />
	</#if>
	<#if (page.curPage>1) >
	<#list  pageStart..(page.curPage-1) as i>
	<a href="${link?replace("@",i)}" ${function?replace("@",i)} >${i}</a>
	</#list>
	</#if>
	<span class="current">${page.curPage}</span>
	<#if (page.curPage
	<page.totalPage) >
		<#list (page.curPage+1)..pageEnd as i>
		<a href="${link?replace("@",i)}"  ${function?replace("@",i)}>${i}</a>
		</#list>
		</#if>
		<#if page.hasNext()>
		<a href="${link?replace("@",page.curPage+1)}"  ${function?replace("@",page.curPage+1)} class="next"> 下一页</a>
		<#else>
		<span class="next disabled">下一页</span>
		</#if>
		<#if page.curPage!=page.totalPage>
		<a href="${link?replace("@",page.totalPage)}"  ${function?replace("@",page.totalPage)}>最后页</a>
		<#else>
		<span class="disabled">最后页 </span>
		</#if>
		<#--<span>共${page.totalCount}条记录，第
			<input type="text" name="page" node-type="page-input"  value="${page.curPage}"/>
			页，共${page.totalPage}页
			<button class="button primary" node-type="page-button">
				确定
			</button></span>--> <span>共${page.totalCount}条记录，共${page.totalPage}页</span>
</div>
<#else>
<div style="height:60px;"></div>
</#if>
</#macro>

<#macro simplepage page,function>
<#local link =page.url+"?"+page.param />
<#if function=""><#local function = " param=\"remote-href\"" /></#if>
<div class="page pagebar" >
	<#if page.curPage!=1 >
	<a href="${link?replace("@","1")}"    ${function?replace("@","1")} >第一页</a>
	<#else>
	<span class="disabled">第一页</span>
	</#if>
	<#if page.hasPrev()>
	<a href="${link?replace("@",page.curPage-1)}"  class="prev" ${function?replace("@",page.curPage-1)} >上一页</a>
	<#else>
	<span class="prev disabled">上一页 </span>
	</#if>
	<#if page.hasNext()>
	<a href="${link?replace("@",page.curPage+1)}"   ${function?replace("@",page.curPage+1)} class="next"> 下一页 </a>
	<#else>
	<span class="next disabled">下一页</span>
	</#if>
	<#if page.curPage!=page.totalPage>
	<a href="${link?replace("@",page.totalPage)}"    ${function?replace("@",page.totalPage)} >最后页</a>
	<#else>
	<span class="disabled">最后页 </span>
	</#if>
	<span>共${page.totalCount}条记录，第
		<input type="text" name="page" id="page"  value="${page.curPage}"/>
		页，共${page.totalPage}页
		<button class="button primary">
			确定
		</button></span>
</div>
</div>
</#macro>

<#--用户session相关-->
<#function session><#return Session["FLINT.USER_SESSION"] ></#function>
<#macro userName>
<#if session()?exists>${session().name}<#else>游客</#if>
</#macro>
<#--取得登录用户ID-->
<#function userId>
<#assign s=session()!"nologin">
<#if session()?exists>
<#return s.id >
<#else>
<#return "guest">
</#if>
</#function>
<#macro userLink userid>
<span class="user" itemValue="${userid}"><a href="">&nbsp;&nbsp;</a><a href="/${userid}/teacher.html" target="_blank"></a></span>
</#macro>
<#macro userLinks userids><#local us=userids?split(",")/><#list us as u><a href="/u/${u}.html" target="_blank" usercard="${u}"></a>  </#list></#macro>
<#-- 验证用户的基本认证情况-->
<#--验证邮箱-->
<#function vEmail active><#return helper.and(active,1)!=0 ></#function>
<#-- 验证手机-->
<#function vTel active><#return helper.and(active,2)!=0 ></#function>
<#-- 验证实名-->
<#function vRealName active><#return helper.and(active,4)!=0 ></#function>
<#-- 验证学位-->
<#function vDegree active><#return helper.and(active,8)!=0 ></#function>
<#--验证信誉保障-->
<#function vEnsure active><#return helper.and(active,16)!=0 ></#function>
<#--认证信息-->
<#macro cert active>
<#if flint.vEnsure(active)><a href="#cc"><u class="credit"></u></a></#if>
<#if flint.vDegree(active)><a href="#cc"><u class="degree"></u></a></#if>
<#if flint.vRealName(active)><a href="#cc"><u class="realName"></u></a></#if>
</#macro>
<#--判断用户是否登录-->
<#function isLogin>
<#return Session["FLINT.USER_SESSION"].isAuthenticated() >
</#function>
<#macro login content><#if isLogin()>${content}<#else>javascript:flint.showLogin();</#if></#macro>
<#--文件大小-->
<#macro filesize s><#if s>1024>${s/1024}KB</#if></#macro>
<#--判断用户报名参加了该课程-->
<#function hasCourse courseId>
<#return (((Session["FLINT.USER_SESSION"].getAttribute("course")!"")?index_of(","+courseId+","))!=-1) >
</#function>

