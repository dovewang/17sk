<!DOCTYPE html>
<html>
<head>
<#include "../../meta.ftl" />
<script src="${IMAGE_DOMAIN}/theme/main/js/bis.course.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<script src="${IMAGE_DOMAIN}/theme/base/js/bis.doc.js?" type="text/javascript" charset="${ENCODING}"></script>
</head>
<body>
<#include "../../header.ftl" />
<div class="page-container">
  <div class="page-body course-manager-layout"> 
   <#include "edit.header.ftl" />
    <div class="page-main">
      <div class="page-left"> <#include "manager.left.ftl"/> </div>
      <div class="page-right box" id="main-box">
 对不起，该功能暂不开放 
<!--
					<div class="tab-header" node-type="tabnavigator>
						<ul>
							<li>
								<a href="#!/exercise/subjectExercise.html?sid=${courseId}&st=1" id="subjectExercise1" remote="true" once="true" autoload="true" view="classView">课堂练习</a>
							</li>
							<li>
								<a href="#!/exercise/subjectExercise.html?sid=${courseId}&st=2" id="subjectExercise2" remote="true" once="true" view="afterView">课后练习</a>
							</li>
						</ul>
					</div>
					<div class="tab-body" id="classView"></div>
					<div class="tab-body" id="afterView" style="display:none"></div>
					--> 
      </div>
    <div class="clear"></div>
  </div>
</div>
</div>
<#include "../../footer.ftl"/>
</body>
</html>