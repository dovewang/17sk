<#assign keyword=RequestParameters["q"]! />
<#assign tab=RequestParameters["tab"]!"all" />
<#assign c=RequestParameters["c"]! />
<#assign view=RequestParameters["v"]!"thumb" />
<#assign grade=kindHelper.getGrades()/>
<#assign CATEGORY="课程-"/>
<#assign description="打造完善的课程发布平台；提供优秀的在线教育资源，在线教课程；各种教育视频分享，更有地区线下课程，完善的价格的比较，方便您的学习，省钱省时省力学习您关注的课程;同时可以提高自己的教学能力"/>
<#assign keywords="课程、教学、线下课程，实时教学，分享课程，发布课程，公开课，提升教育"/>
<!DOCTYPE html>
<html>
	<head>
		<#include "../meta.ftl" />
       <script src="${IMAGE_DOMAIN}/theme/main/js/bis.course.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
	</head>
	<body>
		<#include "../header.ftl"/>
		<div class="clearfix" style="background: #2e4442;">
			<div style="width:985px;height:auto;margin:0 auto;">
				<div node-type="slider" activeIndex="0" class="slider" style="width:985px;height:132px">
					<ul class="slider-child">
							<li><a href="/search/question.html"><img src="/theme/main/default/ad/a1.jpg"  ></a>
						</li>
						<li><a href="/search/course.html"><img src="/theme/main/default/ad/a2.jpg"  ></a>
						</li>
						<li><a href="/tutor/post.html"><img src="/theme/main/default/ad/a3.jpg"></a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="page-container">
			<form action="" id="search-box">
				<dl>
					<dt>
						<ul>
							<li>
								<a href="/index.html">一起上课</a>
							</li>
							<li  class="active">
								<a href="/search/course.html?q=${keyword}">找课程</a>
							</li>
							<li>
								<a href="/search/question.html?q=${keyword}">问作业</a>
							</li>
							<li>
								<a href="/search/show.html?q=${keyword}">I讲台</a>
							</li>
							<li>
								<a href="/search/teacher.html?q=${keyword}">找老师</a>
							</li>
						</ul>
						<div class="clear"></div>
					</dt>
					<dd>
						<input type="text" name="q" xe-webkit-spech value="${keyword}"   maxlength="50" style="width:873px;height:37px; line-height:37px; vertical-align:middle;"/>
						<input type="image" src="/theme/main/default/images/search.png"  name="search"  style="position:absolute; right:-2px;top:-2px"/>
					</dd>
				</dl>
			</form>
			<div class="page-body" id="page-main">
				<div class="page-left">
					<div class="filter-box box">
						<dl>
							<dt>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,0,"0","所有年级"/>
							</dt>
							<dd>
								<#list grade?keys as key>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,0,key,grade[key]/>
								</#list>
							</dd>
						</dl>
						<dl>
							<dt>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,1,"0","全部学科"/>
							</dt>
							<dd>
								<#if   condition[0]!="0">
								<#assign kind=kindHelper.getKind(condition[0])/>
								<#list kind as k>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,1,""+k.kind,k.name/>
								</#list>
								<#else>
								<#assign kinds=kindHelper.getKinds()/>
								<#list kinds?keys as key>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,1,key,kinds[key]/>
								</#list>
								</#if>
							</dd>
						</dl>
						<dl>
							<dt>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,2,"-1","不限状态"/>
							</dt>
							<dd>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,2,"1","报名中"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,2,"9","已结课"/>
							</dd>
						</dl>
						<dl style="display:<#if condition[2]=="9">block<#else>none</#if>">
							<dt>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,3,"0","不限分数"/>
							</dt>
							<dd>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,3,"1","大于6"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,3,"2","大于7"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,3,"3","大于8"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,3,"4","大于9"/>
							</dd>
						</dl>
						<dl style="display:<#if condition[2]=="1">block<#else>none</#if>">
							<dt>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,4,"0","不限开课时间"/>
							</dt>
							<dd>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,4,"1","三天内"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,4,"2","本周内"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,4,"3","本月内"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,4,"4","两个月内"/>
							</dd>
						</dl>
						<dl>
							<dt>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,5,"0","不限价格"/>
							</dt>
							<dd>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,5,"1","免费"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,5,"2","50以下"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,5,"3","50-100"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,5,"4","100-150"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,5,"5","150以上"/>
							</dd>
						</dl>
						<dl>
							<dt>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,6,"0","不限发布时间"/>
							</dt>
							<dd>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,6,"1","最近三天"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,6,"2","最近一周"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,6,"3","最近两周"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,6,"4","最近一月"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,6,"5","最近两月"/>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,6,"6","最近三月"/>
							</dd>
						</dl>
						<#if tab=="local">
						<dl>
							<dt>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,8,"440100","广州地区"/>
							</dt>
							<dd>
								<#list regionHelper.getCitys(440100)  as a>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,8,a.code?string,a.name/>
								</#list>
							</dd>
						</dl>
						</#if>
					</div>
					<div class="tab-header">
						<ul>
							<li <#if tab=="all">class="active"</#if> >
								<a  href="?is=${TYPE}&q=${keyword}&p=1&c=${c}&tab=all&v=${view}">全部</a>
							</li>
							<li <#if tab=="online">class="active"</#if>>
								<a href="?is=${TYPE}&q=${keyword}&p=1&c=${c}&tab=online&v=${view}">在线课程</a>
							</li>
							<li <#if tab=="local">class="active"</#if>>
								<a href="?is=${TYPE}&q=${keyword}&p=1&c=${c}&tab=local&v=${view}">线下课程</a>
							</li>
							<li <#if tab=="video">class="active"</#if>>
								<a href="?is=${TYPE}&q=${keyword}&p=1&c=${c}&tab=video&v=${view}">视频课程</a>
							</li>
						</ul>
					</div>
					<div id="course_tab" >
						<div class="order-box">
							<ul>
								<li>
									<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,7,"0","<i class=\"sort-desc\"></i>默认排序"/>
								</li>
								<li>
									<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,7,"1:","<i class=\"sort\"></i>价格"/>
								</li>
								<li>
									<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,7,"2:","<i class=\"sort-desc\"></i>满意度"/>
								</li>
								<!--
								<li>
								<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,7,"3:","信誉度<i class=\"sort-desc\"></i>"/>
								</li>
								-->
								<#if condition[2]=="9">
								<li>
									<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,7,"4","<i class=\"sort-desc\"></i>最多播放"/>
								</li>
								</#if>
								<li>
									<@flint.condition condition,"?is="+TYPE+"&q="+keyword+"&p=1&c=@&tab="+tab+"&v="+view,7,"5","<i class=\"sort-desc\"></i>最多收藏"/>
								</li>
							</ul>
							<!--<span class="float-right" > <#if view=="thumb"> <a class="button show-list" href="?is=${TYPE}&q=${keyword}&p=1&c=${c}&tab=${tab}&v=list"><i></i>列表显示</a> <#else> <a class="button show-thumb" href="?is=${TYPE}&q=${keyword}&p=1&c=${c}&tab=${tab}&v=thumb"><i></i>大图显示</a> </#if> </span>-->
							<span class="float-right search"> <label>关键字:</label>
								<input type="text" name="q" maxlength="50"  style="width:120px" id="q"  value="${keyword}" placeholder="请输入关键字" onkeydown="if(event.keyCode == 13){Searcher.searchCourse(this.value);}"/>
								<button  type="button" class="button" onclick="Searcher.searchCourse($('#q').val())"></button> </span>
						</div>
						<div id="course_list">
							<#include "../course/list.ftl"/>
						</div>
					</div>
				</div>
				<div class="page-right">
					<div node-type="show-hot" class="show-hot box"></div>
					<div class="box  online-user-box" node-type="online-teacher"></div>
					<div class="box" node-type="guess-member" data-size="3"></div>
					<div class="box" node-type="guess-course" data-size="3"></div>
				</div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>