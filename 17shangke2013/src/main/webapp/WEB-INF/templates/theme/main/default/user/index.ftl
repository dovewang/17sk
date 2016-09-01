<!DOCTYPE html>
<html>
	<head>
		<#include "../meta.ftl" />
		<link href="${IMAGE_DOMAIN}/theme/main/skins/default/css/zone.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
	</head>
	<body class="zone-page">
		<#include "header.ftl" />
		<div class="page-container">
			<div class="page-body">
				<div class="page-left">
					<#include "left.ftl" />
				</div>
				<div class="page-main">
					<#include "user.head.ftl"/>
					<div class="page-center" id="page-center">
						<#include "../poster.ftl"/>
						<div class="tab-header mt15" node-type="tabnavigator" mode="hash">
							<ul>
								<li>
									<a href="#!/m/index.html" remote="false" view="#mblog_filter"  autoload="true" group="true">动态</a>
								</li>
								<li>
									<a href="#!/search/question.html" remote="false" view="#question_list"  group="true">问题</a>
								</li>
								<li>
									<a href="#!/course.html" remote="false" view="#course_list"  group="true">课程</a>
								</li>
								<!--<li> <a href="#!/tutor/index.html" remote="false" view="#tutor_list"  group="true">教室</a> </li>-->
								<li>
									<a href="#!/show/index.html" remote="false" view="#show_list"  group="true">I讲台</a>
								</li>
							</ul>
						</div>
						<div id="mblog_filter" style="display:none">
							<div class="tab-subheader" >
								<ul>
									<li>
										<a href="#!/m/list-0.html?g=0"   view="#mblog_list" >全部</a>
									</li>
									<li>
										<a href="#!/m/list-${USER_ID}.html?g=1"  view="#mblog_list">我关注的</a>
									</li>
									<!--
									<li>
									<a href="#!/m/list.html?g=1" remote="true" view="#mblog_list" page="true">相互关注</a>
									</li>
									<li>
									<a href="#!/m/list.html?g=2" remote="true" view="#mblog_list" page="true">特别关注</a>
									</li>
									<li>
									<a href="#!/m/list.html?g=3" remote="true" view="#mblog_list" page="true">更多</a>
									</li>
									-->
								</ul>
							</div>
							<!--微博列表-->
							<div id="mblog_list"></div>
						</div>
						<div id="question_list" style="display:none">
							<div class="tab-subheader">
								<ul>
									<li>
										<a href="#!/question-all.html"  view="#questionView" page="true">所有问题</a>
									</li>
									<li>
										<a href="#!/question-myq.html"  view="#questionView" page="true">我的提问 </a>
									</li>
									<li>
										<a href="#!/question-mya.html"   view="#questionView" page="true">我的解答 </a>
									</li>
									<li>
										<a href="#!/question/fav.html"   view="#questionView" page="true"> 我收藏的问题</a>
									</li>
									<li>
										<a href="#!/question/at.html"   view="#questionView" page="true">向我求助的</a>
									</li>
								</ul>
								<span class="float-right"><a href="/search/question.html" target="_blank" class="button primary">我要提问</a></span>
							</div>
							<div class="tab-body p10" id="questionView"></div>
						</div>
						<div  id="course_list"  style="display:none">
							<div class="tab-subheader">
								<ul>
									<li>
										<a href="#!/course-all.html" remote="true" view="#courseView" page="true">所有课程</a>
									</li>
									<li>
										<a href="#!/course-mys.html" remote="true" view="#courseView" page="true">我参与的课程</a>
									</li>
									<li>
										<a href="#!/course-myc.html" remote="true" view="#courseView" page="true">我创建的课程</a>
									</li>
									<li>
										<a href="#!/course/fav.html" remote="true" view="#courseView" page="true"> 我收藏的课程</a>
									</li>
								</ul>
								<#--学生和家长不能创建课程-->
								<#if USER_TYPE!=1&&USER_TYPE!=3> <span class="float-right"><a href="/course/manager/guide.html" target="_blank" class="button primary">创建新课程</a></span> </#if>
							</div>
							<div class="tab-body" id="courseView"></div>
						</div>
						<div  id="tutor_list"  style="display:none">
							<div class="tab-subheader">
								<ul>
									<li>
										<a href="#!/course-all.html" remote="true" view="#tutorView" page="true">所有教室</a>
									</li>
									<li>
										<a href="#!/course-myc.html" remote="true" view="#tutorView" page="true">我创建的教室</a>
									</li>
									<li>
										<a href="#!/course-mys.html" remote="true" view="#tutorView" page="true">我参与的教室</a>
									</li>
								</ul>
							</div>
							<div class="tab-body" id="tutorView"></div>
						</div>
						<div  id="show_list"  style="display:none">
							<div class="tab-subheader">
								<ul>
									<li>
										<a href="#!/show/list.html" remote="true" view="#showView">全部</a>
									</li>
									<li>
										<a href="#!/show/u${USER_ID}-list.html" remote="true" view="#showView">我的作品</a>
									</li>
									<li>
										<a href="#!/show/commentlist.html" remote="true" view="#showView">评论过的</a>
									</li>
									<li>
										<a href="#!/show/fav.html" remote="true" view="#showView">收藏的</a>
									</li>
								</ul>
								<span class="float-right"><a href="/course/manager/guide.html#show-guide" target="_blank" class="button primary">发布作品</a></span>
							</div>
							<div class="tab-body" id="showView"></div>
						</div>
					</div>
					<!--center end -->
					<div class="page-right">
						<!--我关注的人-->
						<div  class="user-list-box clearfix">
							<h4>我关注的人(
							${follows.totalCount}
							)</h4>
							<#if follows.totalCount==0>
							您还没有关注任何人
							<#else>
							<ul>
								<#list follows.result as f>
								<li>
									<a href="/u/${f.userId}.html" target="_blank"><img src="${f.face}"/ alt="${f.name}" width="40" height="40" usercard="${f.userId}"></a><span><a href="/u/${f.userId}.html" target="_blank" usercard="${f.userId}"> ${f.name} </a></span>
								</li>
								</#list>
							</ul>
							</#if>
						</div>
						<!--我的粉丝-->
						<div  class="user-list-box clearfix">
							<h4>我的粉丝(
							${fans.totalCount}
							)</h4>
							<#if fans.totalCount==0>
							您还没有关粉丝
							<#else>
							<ul>
								<#list fans.result as f>
								<li>
									<a href="/u/${f.userId}.html" target="_blank"><img src="${f.face}"/ alt="${f.name}" width="40" height="40" usercard="${f.userId}"></a><span><a href="/u/${f.userId}.html" target="_blank" usercard="${f.userId}"> ${f.name} </a></span>
								</li>
								</#list>
							</ul>
							</#if>
						</div>
					</div>
					<!--right end -->
				</div>
			</div>
			<div class="shady" style="margin-bottom:27px"></div>
		</div>
		</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>
<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/main/js/bis.tutor.js?${VERSION}"></script>
<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/main/js/bis.course.js?${VERSION}"></script>