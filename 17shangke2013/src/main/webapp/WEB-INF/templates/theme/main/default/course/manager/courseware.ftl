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
  <div class="page-body course-manager-layout"> <#include "edit.header.ftl" />
    <div class="page-main">
      <div class="text-left"> <a href="/doc/form.html?attr1=${course.courseId}}&cb=Course.loadDocument" node-type="pop" class="button"  id="uploadoCourseware" >上传课件</a> </div>
      <div  id="documentList"   courseId="${course.courseId}"> </div>
    </div>
  </div>
</div>
<#include "../../footer.ftl"/>
</body>
</html>
<script type="text/javascript">Course.loadDocument("${course.courseId}");</script>