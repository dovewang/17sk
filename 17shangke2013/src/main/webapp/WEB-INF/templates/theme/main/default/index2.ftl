<!DOCTYPE html>
<html>
	<head>
		<#include "meta.ftl" />
		<style type="text/css">
			.filter-box dd {
				width: 540px;
			}
		</style>
	</head>
	<body>
		<#include "header.ftl" />
		<div class="clearfix" style="background: #2e4442;">
			<div style="width:985px;height:auto;margin:0 auto;">
				<div node-type="slider" activeIndex="0" class="slider" style="width:985px;height:132px">
					<ul class="slider-child">
						<li>
							<a href="/search/question.html"><img src="/theme/main/default/ad/a1.jpg"  ></a>
						</li>
						<li>
							<a href="/search/course.html"><img src="/theme/main/default/ad/a2.jpg"  ></a>
						</li>
						<li>
							<a href="/tutor/post.html"><img src="/theme/main/default/ad/a3.jpg"></a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="page-container">
			<form   id="search-box" action="/search/course.html">
				<dl>
					<dt>
						<ul>
							<li class="active">
								<a href="/index.html">一起上课</a>
							</li>
							<li>
								<a href="/search/course.html">找课程</a>
							</li>
							<li>
								<a href="/search/question.html">问作业</a>
							</li>
							<li>
								<a href="/search/show.html">I讲台</a>
							</li>
							<li>
								<a href="/search/teacher.html">找老师</a>
							</li>
						</ul>
						<div class="clear"></div>
					</dt>
					<dd>
						<input type="text" name="q" value="" xe-webkit-spech style="width:873px;height:37px; line-height:37px; vertical-align:middle;"/>
						<input type="image" src="/theme/main/default/images/search.png"  name="search"  style="position:absolute; right:-2px;top:-2px"/>
					</dd>
				</dl>
			</form>
			<div class="page-body">
				<div class="page-left" style="width:660px;">
					<div id="guess" node-type="user-need-tour"></div>
					<div class="box" id="guess-user-box">
						<div class="tutorDemand-user-box" data-size="6"  node-type="totur-demand"  id="tutorDemand-user-box"></div>
						<div class="tutorDemand-related" id="demand-guess-box"></div>
					</div>
					<div class="mblog" style="margin-top: 10px">
						<#assign tab=RequestParameters["tab"]!"1,1" />
						<div class="tab-header" >
							<ul>
								<#if tab?split(",")[0]="1">
								<li class="active">
									<#else>
								<li>
									</#if><a href="?tab=1,1">大家说点什么</a>
								</li>
								<#if tab?split(",")[0]="2">
								<li class="active">
									<#else>
								<li>
									</#if><a href="?tab=2,1">问作业</a>
								</li>
							</ul>
						</div>
						<#if tab=="1,1"||tab=="1,2">
						<div id="mblog_filter">
							<div class="tab-subheader">
								<ul>
									<#if tab?split(",")[1]="1">
									<li class="active">
										<#else>
									<li>
										</#if><a href="?tab=1,1">全部</a>
									</li>
									<#if tab?split(",")[1]="2">
									<li class="active">
										<#else>
									<li>
										</#if><a href="?tab=1,2">我关注的</a>
									</li>
								</ul>
							</div>
							<!--微博列表-->
							<div id="mblog_list">
								<#include "/common/mblog/list.ftl" />
							</div>
						</div>
						<#else>
						<div id="question_filter">
							<div class="tab-subheader">
								<ul>
									<#if tab?split(",")[1]="1">
									<li class="active">
										<#else>
									<li>
										</#if><a href="?tab=2,1">全部</a>
									</li>
									<#if tab?split(",")[1]="2">
									<li class="active">
										<#else>
									<li>
										</#if><a href="?tab=2,2">擅长领域<i  class="s-arrow primary bottom"></i></a>
									</li>
								</ul>
							</div>
							<!--问题列表-->
							<div id="questionView">
								<#if tab=="2,1">
								<#include "question/question.filter.ftl" />
								<#include "question/result.list.ftl" />
								<#elseif tab=="2,2">
								<#include "question/listgoodat.ftl"/>
								</#if>
							</div>
						</div>
						</#if>
					</div>
				</div>
				<div class="page-right" style="width:290px;">
					<div class="box" node-type="mblog">
						<h2 class="box-title"><a href="/user/index.html"  class="float-right">更多&gt;</a>随便说说</h2>
						<div  style="height:250px;overflow:hidden;z-index: 2;position:relative;">
							<div id="Mblog-box">
								<#list mbloglist as m>
								<dl class="list-item">
									<dt class="face">
										<a href="/u/${m.userId}.html" target="_blank" class="face-link"><img usercard="${m.userId}" src="" width="40" height="40" alt=""/></a>
									</dt>
									<dd class="content">
										<a href="/u/${m.userId}.html" target="_blank" usercard="${m.userId}"></a>
										<#if m.type==1>
										${m.content?replace("<a>","<a href=\"/qitem-${m.media}.html\" target=\"_blank\">")}
										<#elseif m.type==2>
										${m.content?replace("<a>","<a href=\"/course/${m.media}-${VERSION}.html\" target=\"_blank\">")}
										<#elseif m.type==3>
										${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()?replace("<a>","<a href=\"/show/item-${m.media}.html\" target=\"_blank\">")}
										<#elseif m.type==4>
										${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()?replace("<a>","<a href=\"/u/${m.userId}.html#dm${m.media}\" target=\"_blank\">")}
										<#elseif m.type==5>
										${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()?replace("<a>","<a href=\"/u/${m.userId}.html#sv${m.media}\" target=\"_blank\">")}
										<#else>
										${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()}
										</#if>
									</dd>
								</dl>
								</#list>
							</div>
						</div>
					</div>
					<div class="box  online-user-box" node-type="online-student"></div>
					<div class="box" node-type="guess-member" data-size="3"></div>
					<div class="box" node-type="guess-course" data-size="3"></div>
				</div>
			</div>
		</div>
		<#include "footer.ftl"/>
	</body>
</html>
<script>
	var scrtime;
	$("#Mblog-box").hover(function() {
		clearInterval(scrtime);
	}, function() {
		scrtime = setInterval(function() {
			var $dlv = $("#Mblog-box");
			var liHeight = $dlv.find("dl:last").outerHeight();
			$dlv.find("dl:last").prependTo($dlv);
			$dlv.css("marginTop", -liHeight).animate({
				marginTop : 0
			}, 1000);
		}, 3000);
	}).mouseleave(); 
</script>