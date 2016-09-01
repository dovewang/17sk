<!DOCTYPE html>
<html>
<head>
<#include "../header.ftl"/>
<script src="${IMAGE_DOMAIN}/theme/main/js/bis.course.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
</head>
<body>
<#include "../top.ftl"/>
<div id="page-main">
<div>
  <form action="/manager/business/course.html"  method="post">
    <table width="98%" border="1">
      <tr>
        <td>课程编号</td>
        <td><input name="courseId" type="text" id="courseId" value="${RequestParameters["courseId"]!}"/></td>
        <td>课程类型</td>
        <td>${enumHelper.getSelect("course_type","courseType",RequestParameters["courseType"]!,"全部")}</td>
        <td>课程状态</td>
        <td>${enumHelper.getSelect("course_status","status",RequestParameters["status"]!,"全部")}</td>
      </tr>
      <tr>
        <td>课程发布时间</td>
        <td><input name="start" type="date" id="start" value="${RequestParameters["start"]!}"/>
          至
          <input name="end" type="date" id="end" value="${RequestParameters["end"]!}"/></td>
        <td>关键字</td>
        <td><input name="keyword" type="text" id="keyword" value="${RequestParameters["keyword"]!}"/></td>
        <td></td>
        <td><button class="btn btn-primary"  type="submit"  >查询</button></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
  </form>
</div>
<div class="btn-group">
  <button class="btn" type="button" onclick="reIndex()">课程索引重建</button>
</div>
<table width="98%" class="table table-striped table-bordered table-condensed">
  <thead>
    <tr>
      <th>#</th>
      <th>课程名称</th>
      <th>类型</th>
      <th>状态</th>
      <th>发布时间<b class="sort desc"></b></th>
      <th>发布人</th>
      <th>价格</th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody>
  <#list result.result as course>
  <tr>
    <td>${course.courseId}</td>
    <td style="width:200px;"><a href="/course/${course.courseId}-${VERSION}.html" target="_blank">
      ${course.name}
      </a></td>
    <td>${enumHelper.getLabel("course_type",course.type?j_string)}</td>
    <td>${enumHelper.getLabel("course_status",course.status?j_string)}</td>
    <td>${flint.timeToDate(course.publishTime,"yyyy-MM-dd HH:mm")}
      <#if course.updateTime!=0>(更新于
      ${flint.timeToDate(course.updateTime,"yyyy-MM-dd HH:mm")}
      )</#if></td>
    <td><a href="/u/${course.userId}.html" target="_blank" usercard="${course.userId}">
      ${course.userId}
      </a></td>
    <td><div align="right"><span class="label label-important">
        ${course.price}
        ${MONEY_UNIT}
        </span> (原价: ${course.oldPrice})</div></td>
    <td><a href="/course/manager/${course.courseId}/edit.html" target="_blank"><i class="icon-edit"></i>审核</a><a href="#course-close" node-type="pop" setting="{id:'course-close-pop',effect:'mouse',modal:false}" data-param="{courseId:${course.courseId}"><i class="icon-off"></i>关闭</a><a href="#course-delete" node-type="pop" data-param="{courseId:${course.courseId}" setting="{id:'course-delete-pop',effect:'mouse',modal:false}"><i class="icon-trash"></i>删除</a></td>
  </tr>
  </#list>
    </tbody>
  
  <tfoot>
    <tr>
      <td colspan="8"><@flint.pagination result,"","","" /></td>
    </tr>
  </tfoot>
</table>
<div id="course-close" style="display:none">
  <div class="pop-header"> <a href="#" class="close" node-type="pop-close">×</a>
    <h3>关闭课程</h3>
  </div>
  <div class="pop-body">
    <p> 请录入关闭原因:<!--原因选择功能--> 
    </p>
    <textarea >您的课程违反规定被关闭</textarea>
  </div>
  <div class="pop-footer">
    <button  class="btn btn-primary" onclick="Course.close()"> 确定 </button>
    <button class="btn" node-type="pop-close"> 取消 </button>
  </div>
</div>
<div id="course-delete" style="display:none">
  <div class="pop-header"> <a href="#" class="close" node-type="pop-close">×</a>
    <h3>课程删除</h3>
  </div>
  <div class="pop-body">
    <p> 请录入删除原因:<!--原因选择功能--> 
    </p>
    <textarea>您的课程违反规定被删除</textarea>
  </div>
  <div class="pop-footer">
    <button  class="btn btn-primary" onclick="Course.delete()"> 确定 </button>
    <button class="btn" node-type="pop-close"> 取消 </button>
  </div>
</div>
</body>
</html>
<script type="text/javascript">
	function reIndex(){
		$.post("/manager/business/course.reindex.html",function(data){
			$.alert.show(data);
		})
	}
	
</script>