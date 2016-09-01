<#--有人报名时，用户的通知模板-->
尊敬的用户：
<br />
<#if course.type==1>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 恭喜您报名<a href="${SITE_DOMAIN}/course/${course.courseId}-${VERSION}.html">${course.name}</a>课程成功，课程将于<strong>${flint.timeToDate(course.startTime,"yyyy-MM-dd HH:mm")}</strong>开始，<strong>${flint.timeToDate(course.predictEndTime,"yyyy-MM-dd HH:mm")}</strong>结束，请安排好您的时间，以免错过课程！
<#else>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 恭喜您报名<a href="${SITE_DOMAIN}/course/${course.courseId}-${VERSION}.html">${course.name}</a>成功，请您联系&nbsp; &nbsp;${course.memo}  &nbsp; &nbsp;报到，我们已通知校方及时与您联系。
</#if>