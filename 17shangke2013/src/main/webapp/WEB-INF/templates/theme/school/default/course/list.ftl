<#include "/conf/config.ftl" />
<#if result.totalCount==0>
<div  class="placeholder-box">
	对不起，没有找到您要的课程
</div>
<#else>
${result.totalCount}
<#list result.result as  course>
<#if course.type!=1>
<dl class="course-item" action-type="course-item" id="ce${course.courseId}" cid="${course.courseId}">
	<dt class="course-cover">
		<a href="${SITE_DOMAIN}/course/${course.courseId}-${VERSION}.html"  target="_blank"><img src="${course.cover!}"  alt="${course.name}"/></a>
	</dt>
	<dd class="course-base">
		<a href="${SITE_DOMAIN}/course/${course.courseId}-${VERSION}.html"  target="_blank"><h2>${course.name}</h2></a>
		发布人： <a href="/u/${course.teacher}.html" target="_blank" usercard="${course.teacher}">${course.teacher}</a>
		<br/>
		发布时间：<span class="gray">${flint.timeToDate(course.publishTime,"yyyy-MM-dd HH:mm")} </span>
		<br/>
		<span class="link-group"> <#if (course.userId?string)==USER_ID> <a href="${SITE_DOMAIN}/course/manager/edit.html?i=${course.courseId}" target="_blank">编辑</a> | <a href="${SITE_DOMAIN}/course/manager/edit.html?i=${course.courseId}" target="_blank">删除</a>| </#if>
			<#if TYPE==3> <a href="javascript:void(0)" favor="1"   objectId="${course.courseId}" objectType="1"  node-type="favor" isFavor="ce${course.courseId}">取消收藏</a> <#else> <a href="javascript:void(0)" favor="0" objectId="${course.courseId}" objectType="1"  node-type="favor">收藏♥</a> </#if> </span>
	</dd>
	<dd class="course-info">
		适用范围： <span class="gray">${kindHelper.getGradeLabel(course.scope)}</span>
		<br />
		观看：<strong>${course.views}</strong>&nbsp;&nbsp;&nbsp;&nbsp;评论：<strong>${course.comments}</strong>
	</dd>
	<dd class="course-control">
		<a class="button xbtn course-view " href="${SITE_DOMAIN}/course/${course.courseId}-${VERSION}.html" target="_blank" title="观看"></a>
	</dd>
</dl>
<#else>
<dl class="course-item" action-type="course-item" id="ce${course.courseId}" cid="${course.courseId}">
	<dt class="course-cover">
		<a href="${SITE_DOMAIN}/course/${course.courseId}-${VERSION}.html"  target="_blank"><img src="${course.cover!}"  alt="${course.name}"/></a>
	</dt>
	<dd class="course-base">
		<a href="${SITE_DOMAIN}/course/${course.courseId}-${VERSION}.html"  target="_blank"><h2>${course.name}</h2></a>
		<span class="gray">${flint.timeToDate(course.startTime,"yyyy-MM-dd HH:mm")}</span>
		<br/>
		老师： <a href="/u/${course.teacher}.html" target="_blank" usercard="${course.teacher}">${course.teacher}</a>
		<br/>
		<span class="link-group"> <#if ((course.userId?string)==USER_ID&&course.status!=5&&course.status!=8&&course.status!=9)> <a href="${SITE_DOMAIN}/course/manager/edit.html?i=${course.courseId}" target="_blank">编辑</a> |
			<#if course.status==1 > <a href="javascript:void(0)" onclick="Course.cancle(${course.courseId})">取消</a> |
			</#if> 
			     <#if course.status!=10>
			       <a href="javascript:void(0)" onclick="Course.close(${course.courseId})">关闭</a> |
			     </#if>
			</#if>
			<#if TYPE==3> <a href="javascript:void(0)" favor="1" objectId="${course.courseId}" objectType="1"  node-type="favor" isFavor="ce${course.courseId}" >取消收藏</a> <#else> <a href="javascript:void(0)"   favor="0" objectId="${course.courseId}" objectType="1"  node-type="favor">收藏♥</a> </#if> </span>
	</dd>
	<dd class="course-info">
		适用范围： <span class="gray">${kindHelper.getGradeLabel(course.scope)}</span>
		<br />
		<#if course.status==9>
		观看：<strong>${course.views}</strong>&nbsp;&nbsp;&nbsp;&nbsp;评论：<strong>${course.comments}</strong>
		<#else>
		已报名<strong>${course.joins}<#if course.maxCapacity==0>(不限人数)<#else>/${course.maxCapacity}</#if></strong>
		</#if>
	</dd>
	<dd class="course-control">
		<#if course.status==9> <a class="button xbtn course-view " href="${SITE_DOMAIN}/course/${course.courseId}-${VERSION}.html" target="_blank" title="观看"></a>
		<#else>
		<#--进入课室的条件，要求必须有人报名，创建人或报名参加该课程的人-->
		<#if (course.joins>0)&&(flint.hasCourse(course.courseId) || (course.userId?string=USER_ID))>
		<a href="javascript:;"  data-param="{subjectId:${course.courseId},subject:'${course.name}',type:2}" class="button xbtn course-goto" node-type="goto-room-fast"></a>
		<span  class="countdown" date="${course.startTime}"></span>
		<br/>
		<#else>
		<#if (course.maxCapacity=course.joins&&course.maxCapacity!=0)>
		<div class="text-center">
			名额已满
		</div>
		<#else><a href="${SITE_DOMAIN}/course/${course.courseId}-${VERSION}.html"  class="button xbtn course-enrollment" title="马上报名" target="_blank"></a>
		</#if>
		</#if>
		</#if>
	</dd>
</dl>
</#if>
</#list>
<@flint.pagination result,"","","" /> </#if>