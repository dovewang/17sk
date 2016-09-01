<!DOCTYPE html>
<html>
<head>
<#include "../../meta.ftl" />
<script src="${IMAGE_DOMAIN}/theme/base/js/city-min.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<script src="${IMAGE_DOMAIN}/theme/main/js/bis.course.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<script src="${IMAGE_DOMAIN}/theme/base/js/bis.doc.js?" type="text/javascript" charset="${ENCODING}"></script>
<script type="text/javascript">Course.edit = true;</script>
</head>
<#assign edit=true />
   <#assign school_id=site.id/>
	<#if USER_TYPE==4>
	    <#assign school_id=__USER.getAttribute("school")/>
    </#if>
    <body>
<#include "../../header.ftl" />
<div class="page-container">
      <div class="page-body course-manager-layout"> <#include "edit.header.ftl" />
    <div class="page-main box"> <#include "item${course.type}.ftl"/> </div>
  </div>
    </div>
<#include "../../footer.ftl"/> 
<!--固定在底部对课程进修审核 --> 
<#if USER_TYPE==127>
<div  class="fixed-footer"> <a class="fixed-footer-button"  href="javascript:;" onclick="$('#course-check-box').slideToggle()"></a>
      <div id="course-check-box" class="page-container text-left" >
    <h2>课程审核</h2>
    <dl class="form" style="width: 400px;padding: 20px;">
          <#if course.type==3>
          <dd> 原始价格：
        ${course.oldPrice}
        ${MONEY_UNIT}
      </dd>
          <dd> 优惠价格：
        <input type="text" name="newPrice" id="newPrice" value="${course.price}"/>
        ${MONEY_UNIT}
      </dd>
          </#if>
          <dd>
        <button class="button primary" type="button" node-type="course-check" courseType="${course.type}" agree="true" courseId="${course.courseId}"> 审核通过 </button>
        <button class="button" type="button" onclick="$('#check_refuse').toggle()"> 驳回 </button>
      </dd>
          <dd style="text-align:left; display:none" id="check_refuse"> 驳回原因： <br/>
        <textarea style="width:98%" id="check_refuse_input">您的课程违反相关规定，请仔细阅读发布协议后再修改，谢谢。</textarea>
        <div class="text-right">
              <button class="button primary" type="button"  node-type="course-check" courseType="${course.type}" agree="false" courseId="${course.courseId}"> 确定驳回 </button>
            </div>
      </dd>
        </dl>
  </div>
    </div>
</#if>
</body>
</html>