<#assign TITLE=kindHelper.getKindLabel(course.category?j_string)+course.name/>
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
						<h1><a href="#"> ${kindHelper.getKindLabel(course.category?j_string)} </a>:
						${course.name} </h1>
						<div class="course-mark">
							<#assign sc=domainHelper.getDomainById(course.schoolId)/> <span class="float-right">来自<a href="http://${sc.domain}.17shangke.com/about.html" target="_blank"> ${sc.name} </a></span><span>发布时间：${helper.passTime(course.publishTime)}</span>
							<span>浏览(${course.views})</span>
							<span>标签：
								${course.tag!"无"} </span>
						</div>
						<#if (course.price>0)><span class="course-price"> ${course.price}
							${moneyUnit} </span><#else><span class="course-price free">免费</span></#if><span class="course-type t${course.type}"> ${enumHelper.getLabel("course_type",course.type?j_string)} </span>
						<div class="course-content">
							<dl>
								<dt><img src="${imageHelper.resolve(course.cover!,"s300,225")}" width="300" height="225" />
								</dt>
								<dd>
									<p>
										主讲老师： <a href="/u/${course.teacher}.html" usercard="${course.teacher}"></a>
									</p>
									<p>
										适用范围：
										${kindHelper.getGradeLabel(course.scope)}
									</p>
									<p>
										开课时间：<strong> ${flint.timeToDate(course.startTime,"yyyy-MM-dd HH:mm")} </strong>
									</p>
									<p>
										课程时长：
										${course.time/60}
										分钟
									</p>
									<#if course.status==9>
									<a href="${SITE_DOMAIN}/v/id_${course.roomId!"0"}.html"  title="观看" class="button large course-view">观看</a>
									<div>
										<strong> ${course.completes} </strong>人参加了该课程
									</div>
									<#else>
									<#--用户在线课程，允许用户未报名直接进入，如果在线名额已满则不允许未报名的部分用户进入-->
									<#if  ((course.startTime*1000)<(.now?long)&&(course.status=1||course.status=2||course.status=3))>
									<a href="javascript:;"  data-param="{subjectId:${course.courseId},subject:'${course.name}',type:2}" class="button large" node-type="goto-room-fast">进入课室</a>
									<#--课程只有在报名阶段才显示能够报名 -->
									<#elseif course.status=1 &&USER_ID!=course.userId?string>
									<a href="javascript:;" onclick="Trade.order(${course.courseId},2)" class="button  button-join"><span class="join">马上报名</span><span  class="count" node-type="countdown" date="${course.startTime}" when="location.reload()"></span></a> <#--显示课程状态-->
									<#else> <span  style="color:red"> ${enumHelper.getLabel("course_status",course.status?j_string)} </span> </#if>
									<#-- 已报名的人员-->
									<#if (joins?size>0) >
									<div>
										<strong> ${joins?size} </strong>人报名参加
										<ul class="user-list small">
											<#list joins as m>
											<li>
												<a href="/u/${m}.html" target="_blank"><img   usercard="${m}" src="/theme/school/default/images/noface_s.jpg" height="30" width="30"/></a>
											</li>
											</#list>
										</ul>
									</div>
									</#if>
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
							<#if course.status==9>
							<li>
								<a href="#!/exercise/get.html?exerciseId=1" remote="true" view="#exerciseView" >课后练习</a>
							</li>
							</#if>
							<li>
								<a href="#!/course/comments-${course.courseId},-1.html" remote="false" view="#commentView" once="true" group="true" >评价<#if (course.comments>0) >(<span id="cnums${course.courseId}"> ${course.comments} </span>)</#if></a>
							</li>
						</ul>
					</div>
					<!--tab_title end-->
					<div class="tab-body" id="introView" style="padding:10px">
						${course.intro}
					</div>
					<div class="tab-body" id="exerciseView">
						没有课后练习
					</div>
					<!--tab_con end-->
					<div style="display:none" id="commentView">
						<div  class="tab-subheader">
							<ul>
								<li>
									<a href="#!/course/comments-${course.courseId},-1.html" remote="true" view="commentsListView"  autoload="true" page="true" id="commentAll">全部</a>
								</li>
								<li>
									<a href="#!/course/comments-${course.courseId},3.html" remote="true" view="commentsListView" page="true"> 非常有用</a>
								</li>
								<li>
									<a href="#!/course/comments-${course.courseId},2.html" remote="true" view="commentsListView" page="true"> 有用</a>
								</li>
								<li>
									<a href="#!/course/comments-${course.courseId},1.html" remote="true" view="commentsListView" page="true"> 一般</a>
								</li>
								<li>
									<a href="#!/course/comments-${course.courseId},0.html" remote="true" view="commentsListView" page="true"> 作用不大</a>
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
<script type="text/javascript" src="http://v2.jiathis.com/code/jia.js" charset="utf-8"></script>
<script  charset="utf-8">
</script>