<#assign demandId=RequestParameters["demandId"]!0 />
<#assign kind=RequestParameters["kind"]!0 />
<#assign price=RequestParameters["price"]!'0'/>
<#assign intro=RequestParameters["intro"]!'' />
<#include "/conf/config.ftl" />
<div class="popup-body" style="width:800px;">
	<form id="demandForm" action="/tutor/demand.post.html"  method="post" onsubmit="return false">
		<dl class="form">
			<input  type="hidden" id="demandId" name="demandId" value="${demandId}"/>
			<dd>
				<label>辅导科目：<span class="mark">(最多3项)</span></label>
				<#assign course_category=kindHelper.getKinds() />
				<input  type="hidden"  id="kind" name="kind"  value="${kind}"/>
				<span class="select-box box" action-target="kind" max="3" style="width:600px"> <#list  course_category?keys as key> <a href="javascript:void(0)" action-type="select-item"  action-data="${key}">${course_category[key]}</a> </#list></span>
			</dd>
			<dd>
				<label>辅导价格：</label><span>
					<select type="price" id="price" name="price">
					     <option value="0" <#if price=='0'>selected="selected"</#if>>面议</option>
					     <option value="1" <#if price=='1'>selected="selected"</#if>>30以下</option>
					     <option value="2" <#if price=='2'>selected="selected"</#if>>30-60</option>
					     <option value="3" <#if price=='3'>selected="selected"</#if>>60-100</option>
					     <option value="4" <#if price=='4'>selected="selected"</#if>>100以上</option>
					</select>${MONEY_UNIT}/小时
					</span>
			</dd>
			<dd>
				<label>描述：</label><span><textarea style="width:613px" id="intro" maxlength="50" rows="5" name="intro">${intro}</textarea></span>
			</dd>
		</dl>
	</form>
</div>
<div class="popup-footer" style="width:754px">
	<button class="button primary large"  action-type="popup-close"  onclick="Tutor.postDemand()">
		发布
	</button>
</div>