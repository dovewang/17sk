<#assign TITLE=kindHelper.getKindLabel(course.category?j_string)+course.name/>
<#assign CATEGORY="-课程-"/>
<#assign description=TITLE/>
<!DOCTYPE html>
<html>
	<head>
		<#include "../meta.ftl" />
	</head>
	<body>
		<#include "item.header.ftl" />
		<div class="page-container">
			<div class="page-body">
				<div class="page-left">
					<div class="course-detail box">
						<h1><a href="#">${kindHelper.getKindLabel(course.category?j_string)}</a>:${course.name}</h1>
						<div class="course-mark">
							<#assign sc=domainHelper.getDomainById(course.schoolId)/>
							<span class="float-right">来自<a target="_blank" href="http://${sc.domain}.17shangke.com/about.html">${sc.name}</a></span><span>发布时间：${helper.passTime(course.publishTime)}</span>
							<span>浏览(${course.views})</span>
							<span>标签：
								${course.tag!"无"} </span>
						</div>
						<div class="course-content">
							<embed src="${course.dir}" quality="high" width="700" height="450" align="middle" allowScriptAccess="always" allowFullScreen="true" wmode="transparent" type="application/x-shockwave-flash"></embed>
							<div id="ckepop" class="share">
								<span class="jiathis_txt">分享到：</span>
								<a class="jiathis_button_tsina"></a>
								<a class="jiathis_button_renren"></a>
								<a class="jiathis_button_kaixin001"></a>
								<a class="jiathis_button_taobao"></a>
								<a class="jiathis_button_tqq"></a>
								<a class="jiathis_button_qzone"></a>
								<a class="jiathis_button_googleplus"></a>
								<a class="jiathis_button_ujian"></a>
								<a class="jiathis_button_ishare"></a>
								<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank">更多</a>
								<a class="jiathis_counter_style"></a>
							</div>
						</div>
					</div>
					<div class="tab-header" node-type="tabnavigator">
						<ul>
							<li>
								<a href="#!/course-intro.html" remote="flase" view="#introView" autoload="true">课程简介</a>
							</li>
							<li>
								<a href="#!/course/comments-${course.courseId},-1.html" remote="false" view="#commentView" once="true" group="true" >评价<#if (course.comments>0) >(<span id="cnums${course.courseId}">${course.comments}</span>)</#if></a>
							</li>
						</ul>
					</div>
					<div class="tab-body" id="introView" style="padding:10px">
						${course.intro}
					</div>
					<div style="display:none" id="commentView">
						<div  class="tab-subheader">
							<ul>
								<li>
									<a href="#!/course/comments-${course.courseId},-1.html" remote="true" view="#commentsListView"  autoload="true" page="true" id="commentAll">全部</a>
								</li>
								<li>
									<a href="#!/course/comments-${course.courseId},3.html" remote="true" view="#commentsListView" page="true"> 非常有用</a>
								</li>
								<li>
									<a href="#!/course/comments-${course.courseId},2.html" remote="true" view="#commentsListView" page="true"> 有用</a>
								</li>
								<li>
									<a href="#!/course/comments-${course.courseId},1.html" remote="true" view="#commentsListView" page="true"> 一般</a>
								</li>
								<li>
									<a href="#!/course/comments-${course.courseId},0.html" remote="true" view="#commentsListView" page="true">作用不大</a>
								</li>
							</ul>
						</div>
						<#include "../comments/course.post.ftl"/>
						<div class="tab-body"  id="commentsListView">

						</div>
					</div>
				</div>
				<div class="page-right">
					<#include "item.right.ftl"/>
				</div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>
<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/main/js/bis.course.js?${VERSION}"></script>
<!-- JiaThis Button BEGIN -->
<script type="text/javascript" src="http://v2.jiathis.com/code/jia.js" charset="utf-8"></script>
<!-- JiaThis Button END -->