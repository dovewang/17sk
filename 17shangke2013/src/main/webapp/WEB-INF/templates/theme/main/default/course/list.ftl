<#include "/conf/config.ftl" />
<#if result.totalCount==0>
<div  class="placeholder-box">
	对不起，没有找到您要的课程
</div>
<#else>
<#--大图方式显示课程信息,默认方式-->
<#assign view=view!"thumb" />
<#list result.result as  course>
<dl class="course-item-thumb" node-type="course-item" id="ce${course.courseId}" cid="${course.courseId}"  ctype="${course.type}">
	<dt class="course-cover">
		<span class="course-type t${course.type}"> ${enumHelper.getLabel("course_type",course.type?j_string)} </span><a href="${SITE_DOMAIN}/course/${course.courseId}-${VERSION}.html"  target="_blank" class="thumbnail"><img  source="${imageHelper.resolve(course.cover!,"s200,150")}" src="/theme/main/default/images/course_cover.jpg" alt="${course.name}" width="200" height="150"/></a> <#if (course.price>0)> <span class="course-price"> ${course.price}
			${moneyUnit} </span> <#else> <span class="course-price free">免费</span> </#if>
	</dt>
	<dd class="course-base">
		<h2><a href="${SITE_DOMAIN}/course/${course.courseId}-${VERSION}.html"  target="_blank"> ${course.name} </a></h2>
		发布人： <a href="/u/${course.userId}.html" target="_blank" usercard="${course.userId}"> ${course.userId} </a>
		<br/>
		发布时间：<span class="gray"> ${flint.timeToDate(course.publishTime,"yyyy-MM-dd HH:mm")} </span>
		<#assign sc=domainHelper.getDomainById(course.schoolId)/>
		 <div>来自<a href="http://${sc.domain}.17shangke.com/about.html" target="_blank">${sc.name}</a></div>
		<span class="link-group"> <#--用户可以进行的操作 -->
			<#if ((course.userId?string)==USER_ID)>
                ${enumHelper.getLabel("course_status",course.status?j_string)}
                <a href="${SITE_DOMAIN}/course/manager/${course.courseId}/edit.html" target="_blank"><i class="icon icon-setting"></i>管理</a> 
			</#if>
            <#--课程收藏-->
			<#if TYPE==3> <a href="javascript:;" node-type="favor"  favor="1"  objectId="${course.courseId}" objectType="1"><i class="icon icon-like-ative"></i>取消收藏</a> <#else> <a href="javascript:;" favor="0" node-type="favor" objectId="${course.courseId}" objectType="1"><i class="icon icon-like"></i>收藏</a> </#if> </span>
	</dd>
</dl>
</#list>
<@flint.pagination result,"","","" />
</#if>