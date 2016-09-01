<!DOCTYPE html >
<html>
	<head>
		<#include "../head.ftl" />
		<script type="text/javascript" src="${IMAGE_DOMAIN+"/"+THEME_PATH}/js/question.js?${VERSION}"></script>
		<script type="text/javascript" src="${IMAGE_DOMAIN+"/"+THEME_PATH}/js/course.js?${VERSION}" charset="${ENCODING}"></script>
		<#assign keyword=RequestParameters["q"]! />
		<#assign searchType=searchType!"error" />
	</head>
	<body>
		<div id="wrap">
			<div id="wrapbottom">
				<div id="wrapcon">
					<div id="conframe">
						<#include "../top.ftl" /> <!--header end-->
						<div id="content">
							<div class="advanced_search">
								<div class="search_inputbox">
									<form action="" method="get" id="searchForm">
										<div class="search_inputcon">
											<input type="text" name="q" id="q" value="${keyword}" />
										</div>
										<div class="search_inputright"></div>
										<div class="but_advanced_search">
											<a href="javascript:void(0)" onclick="$('#searchForm').submit()"></a>
										</div>
									</form>
								</div>
								<!--search_inputbox end-->
								<div class="clear"></div>
							</div>
							<div id="leftbox">
								<div class="search_nav">
									<ul>
										
										<li class="search_navbg1">
											<a href="/search/course.html?q=${keyword}">课 程</a>
										</li>
										<li class="search_navbg2">
											<a href="/search/question.html?q=${keyword}">问 题</a>
										</li>
										<li class="search_navbg3">
											<a href="/search/teacher.html?q=${keyword}">老 师</a>
										</li>
										<li class="active_search_navbg4">
											学 生
										</li>
									</ul>
									<div class="clear"></div>
								</div>
								<!--search_nav end
								<div class="search_history">
								<h2>搜索历史</h2>
								<div class="history_list">
								<ul>
								<li>
								<a href="#">吴海雅</a>
								</li>
								<li>
								<a href="#">朱小玲</a>
								</li>
								<li>
								<a href="#">高峰</a>
								</li>
								<li>
								<a href="#">全部清除</a>
								</li>
								</ul>
								</div>
								</div>
								search_history end-->
							</div>
							<div id="rightbox">
									<div class="filter-box box">
										<dl>
											<#assign grade=kindHelper.getGrades()/>
											<dt>
												<@flint.condition condition,"?q="+keyword+"&p=1&c=@",0,"0","所有年级"/>
											</dt>
											<dd>
												<#list grade?keys as key>
												<@flint.condition condition,"?q="+keyword+"&p=1&c=@",0,key,grade[key]/>
												</#list>
											</dd>
										</dl>
									</div>
									<div class="clear"></div>
								<!--rightbox_funciton end-->
								<div class="tab_conshow">
									<#include "../user/list.student.ftl"/>
								</div>
							</div>
							<!--rightbox end-->
							<#include "../foot.ftl" />
	</body>
</html>
