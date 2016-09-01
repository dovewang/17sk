<#assign course_category=enumHelper.getEnum("course_category") />
<form id="basicInfoForm"   method="post" action="/user/updateUser.html"  data-validator-url="/v/user.basic.json">
	<dl class="form label100 input-xxlarge">
		<dd>
			<label><em class="required">*</em> 昵&nbsp;&nbsp;&nbsp;&nbsp;称： </label>
			<span> ${user.name!} </span>
		</dd>
		<dd>
			<label> 真实姓名： </label>
			<span>
				<input type="text" id="realName" name="realName"  value="${user.realName!}" />
			</span>
		</dd>
		<dd>
			<label> 所在城市： </label>
			<span node-type="city-selector" node-name="city" city="${user.city!}"> </span>
		</dd>
		<dd>
			<label> 身&nbsp;&nbsp;&nbsp;&nbsp;份： </label>
			<span class="text">${ROLE_NAME}</span>
		</dd>
		<dd>
			<label>学生年级：</label>
			<#assign grade=kindHelper.getGrades()/>
			<span class="select-box box" action-target="grade" max="1" style="width:500px"> 
				<input type="hidden"  id="grade" name="grade" value="${user.grade!"0"}"/>
				<#list  grade?keys as key> <a href="javascript:void(0)" action-type="select-item"  action-data="${key}">${grade[key]}</a> </#list>
			</span>
		</dd>
		<dd>
			<label>您关注的信息：<span class="mark">(最多10项)</span></label>
			<span class="select-box box" style="width:500px" action-target="focus" max="10"><#assign course_category=kindHelper.getKinds() />
				<input  type="hidden" readonly name="focus" id="focus" value="${user.focus!}"/>
				<#list  course_category?keys as key> <a href="javascript:void(0)" action-type="select-item"  action-data="${key}">${course_category[key]}</a> </#list></span>
		</dd>
		<dd>
			<label>您擅长的领域：<span class="mark">(最多3项)</span></label>
			<span class="select-box box" action-target="expert" max="3" style="width:500px">			<input  type="hidden"  id="expert" name="expert"  value="${user.expert!}"/> <#list  course_category?keys as key> <a href="javascript:void(0)" action-type="select-item"  action-data="${key}">${course_category[key]}</a> </#list></span>
		</dd>
		<dd>
			<label> <em class="required">*</em> 一句话介绍： </label>
			<span> 				<textarea name="experience" rows="4" id="experience"    placeholder="请简单的描述您的学历及教学经历">${user.experience!""}</textarea> </span>
		</dd>
		<dd>
			<label>&nbsp;</label>
			<span>
				<button type="button"  onclick="User.saveUser()"  class="button primary large" >
					保存
				</button></span>
		</dd>
	</dl>
</form>