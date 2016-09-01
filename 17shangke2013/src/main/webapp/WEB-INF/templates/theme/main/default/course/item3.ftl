<#assign TITLE=kindHelper.getKindLabels(course.category?j_string)+course.name/>
<#assign CATEGORY="-课程-"/>
<#assign description=TITLE/>
<!DOCTYPE html>
<html>
	<head>
		<#include "../meta.ftl" />
		<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/main/js/bis.trade.js"></script>
	</head>
	<body>
		<#include "item.header.ftl" />
		<div class="page-container">
			<div class="page-body">
				<div class="page-left">
					<div class="course-detail box">
						<h1><a href="#"> ${kindHelper.getKindLabels(course.category?j_string)} </a>:
						${course.name} </h1>
						<div    class="course-mark">
							<#assign sc=domainHelper.getDomainById(course.schoolId)/> <span class="float-right">来自<a href="http://${sc.domain}.17shangke.com/about.html" target="_blank"> ${sc.name} </a></span><span>发布时间：${helper.passTime(course.publishTime)}</span>
							<span><i class="icon icon-view"></i>浏览(${course.views})</span>
							<span><i class="icon icon-tag"></i>标签：${course.tag!"无"} </span>
						</div>
						<#if (course.price>0)>
						<#if course.oldPrice!=course.price> <span class="course-price" style="text-decoration:line-through;"> ${course.oldPrice}
							${moneyUnit} </span><span class="course-price new"> ${course.price}
							${moneyUnit} </span> <#else> <span class="course-price"> ${course.price}
							${moneyUnit} </span> </#if>
						<#else><span class="course-price free">免费</span></#if><span class="course-type t${course.type}"> ${enumHelper.getLabel("course_type",course.type?j_string)} </span>
						<div class="course-content">
							<dl>
								<dt><img src="${imageHelper.resolve(course.cover!,"s300,225")}" width="300" height="225"/>
								</dt>
								<dd>
									<div>
										上课校区：
										${course.memo!}
										<br />
										适用范围：
										${kindHelper.getGradeLabel(course.scope)}
										<br/>
										课程目的：
										${course.aim!}
										<br />
										开课时段：<strong> ${course.openTime} </strong>
										<br/>
										报名时段：<strong> ${course.applyTime} </strong>
									</div>
                                    <#if course.status==1 &&USER_ID!=course.userId?string>
									<a href="javascript:;" onclick="Trade.order(${course.courseId},3)" title="马上报名" class="button large">马上报名</a>                            <#else>
                                    <span  style="color:red"> ${enumHelper.getLabel("course_status",course.status?j_string)} </span>
                                    </#if>  
								</dd>
							</dl>
							<div class="clear"></div>
							<div id="ckepop" class="share">
								<span class="jiathis_txt">分享到：</span><a class="jiathis_button_tsina"></a><a class="jiathis_button_renren"></a><a class="jiathis_button_kaixin001"></a><a class="jiathis_button_taobao"></a><a class="jiathis_button_tqq"></a><a class="jiathis_button_qzone"></a><a class="jiathis_button_googleplus"></a><a class="jiathis_button_ujian"></a><a class="jiathis_button_ishare"></a><a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank">更多</a><a class="jiathis_counter_style"></a>
							</div>
						</div>
					</div>
					<div class="tab-header" node-type="tabnavigator">
						<ul>
							<li>
								<a href="#!/course-intro.html" remote="flase" view="#introView" autoload="true">课程简介</a>
							</li>
							<li>
								<a href="#!/course/comments-${course.courseId},-1.html" remote="false" view="#commentView" once="true" group="true" >评价<#if (course.comments>0) >(<span id="cnums${course.courseId}"> ${course.comments} </span>)</#if></a>
							</li>
						</ul>
					</div>
					<!--tab_title end-->
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
									<a href="#!/course/comments-${course.courseId},0.html" remote="true" view="#commentsListView" page="true"> 作用不大</a>
								</li>
							</ul>
						</div>
						<#include "../comments/course.post.ftl"/>
						<div class="tab-body"  id="commentsListView"></div>
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