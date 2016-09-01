<#assign tutorId=RequestParameters["tutorId"]!0 />
<#assign kind=RequestParameters["kind"]!0 />
<#assign price=RequestParameters["price"]!40 />
<#assign intro=RequestParameters["intro"]!'' />
<#include "/conf/config.ftl" />
<div class="popup-body">
	<form id="serviceForm" action="/tutor/service.post.html"  method="post" onsubmit="return false">
		<dl class="form">
			<input  type="hidden" id="tutorId" name="tutorId" value="${tutorId}"/>
			<dd>
				<label>辅导科目：<span class="mark">(最多3项)</span></label>
				<#assign course_category=kindHelper.getKinds() />
				<input  type="hidden"  id="kind" name="kind" value="${kind}"/>
				<span class="select-box box" action-target="kind" max="3" style="width:600px"> <#list  course_category?keys as key> <a href="javascript:void(0)" action-type="select-item"  action-data="${key}">${course_category[key]}</a> </#list></span>
			</dd>
			<dd>
				<label>辅导价格：</label><span>
					<input name="price" type="text" id="price" value="${price}" data-type="number">${MONEY_UNIT}/小时</span>
			</dd>
			<dd>
				<label>描述：</label><span><textarea id="intro" maxlength="50" style="width:613px" rows="5" name="intro">${intro}</textarea></span>
			</dd>
		</dl>
	</form>
</div>
<div class="popup-footer">
	<button class="button primary large"  action-type="popup-close" onclick="Tutor.postService()">
		发布
	</button>
</div>