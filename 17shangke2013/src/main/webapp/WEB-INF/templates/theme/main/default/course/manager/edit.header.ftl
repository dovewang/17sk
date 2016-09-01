<div class="box">
  <dl class="title" node-type="course-item" cid="${course.courseId}" ctype="${course.type}">
    <dt><img src="${course.cover}" height="90" width="120"></dt>
    <dd>
      <h1><a href="/course/${course.courseId}-${VERSION}.html" target="_blank" class="ellipsis" title="${course.name}">
        ${course.name}
        </a></h1>
      <div><span><a href="/u/${course.userId}.html" target="_blank" usercard="${course.userId}">小王</a></span>
       <#if course.updateTime!=0>
       创建于<span class="gray">
        ${flint.timeToDate(course.updateTime,"yyyy-MM-dd HH:mm:ss")}
        </span></#if>(
        ${enumHelper.getLabel("course_status",course.status?j_string)}
        )
        ${enumHelper.getLabel("course_type",course.type?j_string)}
      </div>
      <div class="link-group mt15"> 
      <a href="/course/${course.courseId}-${VERSION}.html" target="_blank" class="strong main">预览</a>            
      <a href="javascript:;" node-type="course-publish" class="strong main">发布</a> 
      <#if course.type==1&&course.status==1>
       <a  href="javascript:;"  node-type="course-cancle" class="strong">取消</a>
      </#if> 
      <#if course.status!=10 >
       <a  href="javascript:;" node-type="course-close" class="strong">关闭</a> </div>
      </#if>
    </dd>
  </dl>
</div>
