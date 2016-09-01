<#if edit?exists>
<form id="videoForm" method="post" action="${SITE_DOMAIN}/course/manager/update.html"  onsubmit="return false;" data-validator-url="/v/course.json">
	<input id="courseId" type="hidden" name="courseId" value="${course.courseId}" />
	<dl class="form label80 input-xxlarge">
		<dd>
			<label><em class="required">*</em>课程分类：</label>
			<span>${kindHelper.getKindLabel(course.category?j_string)}</span>
		</dd>
		<dd>
			<label><em class="required">*</em>适用范围：</label>
			<span>${kindHelper.getGradeLabel(course.scope)}</span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程名称：</label><span>
				<input type="text" name="name" id="name"  maxlength="100" value="${course.name}"/>
			</span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程目的： </label>
			<span> 				<textarea name="aim" rows="3"  id="aim">${course.aim}</textarea> </span>
		</dd>
		<#if school_id!=0>
		<dd>
			<label>公开：</label><span> <label>
					<input type="radio" name="shareModel" value="1" <#if course.shareModel==1>checked="checked"</#if>/>
					学校内部</label> <label>
					<input type="radio"  name="shareModel" value="0" <#if course.shareModel==0>checked="checked"</#if>/>
					完全公开</label> </span>
		</dd>
		</#if>
		<dd>
			<label>标签：</label><span>
				<input type="tag" name="tag" id="tag" value="${course.tag}"/>
			</span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程封面：</label><span><img src="${course.cover}"></span>
		</dd>
		<#if FEE_MODLE>
		<dd>
			<label><em class="required">*</em>课程价格： </label>
			<span>
				<input type="text" id="price" name="price" value="${course.price}"/>
			</span>
		</dd>
		<dd>
			<label><em class="required">*</em>试看时长： </label>
			<span>
				<input type="text" id="tryTime" name="tryTime" />
			</span>
		</dd>
		</#if>
		<dd>
			<label> <em class="required">*</em>课程简介： </label>
		</dd>
		<dd>
			<span><textarea id="intro" name="intro" node-type="editor" height="300" width="720">${course.intro}</textarea></span>
		</dd>
		<dd>
			<button onclick="Course.saveVideo();"  class="button large primary save">
				保存
			</button>
		</dd>
	</dl>
</form>
<#else>
<#assign  data=RequestParameters["url"]!  />
<#assign  cover=RequestParameters["c"]!  />
<form id="videoForm" method="post" action="${SITE_DOMAIN}/course/manager/save.html"  onsubmit="return false;"  data-validator-url="/v/course.json">
	<input  type="hidden" name="schoolId" value="${school_id}" />
	<input id="courseId" type="hidden" name="courseId" value="0" />
	<input id="videoDir" type="hidden" name="dir" value="${data}" />
	<input id="videoCover" type="hidden" name="cover" value="${cover}" />
	<input  type="hidden" name="type" value="2" />
	<dl class="form label80 input-xxlarge">
		<dd>
			<label><em class="required">*</em>课程分类：</label>
			<span>
				<input  type="hidden"  name="category" id="category" value=""/>
				<span class="select-box box" style="width:500px" action-target="category" max="1"><#assign course_category=kindHelper.getKinds() />
					<#list  course_category?keys as key> <a href="javascript:void(0)" action-type="select-item"  action-data="${key}">${course_category[key]}</a> </#list></span> </span>
		</dd>
		<dd>
			<label><em class="required">*</em>适用范围：</label>
			<span>
				<input  type="hidden" readonly name="scope" id="scope" value=""/>
				<span> <#assign grade=kindHelper.getGrades()/><span class="select-box box" action-target="scope"  style="width:500px"> <#list  grade?keys as key> <a href="javascript:void(0)" action-type="select-item"  action-data="${key}">${grade[key]}</a> </#list></span></span> </span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程名称：</label><span>
				<input type="text" name="name" id="name"  maxlength="100"/>
			</span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程目的： </label>
			<span> 				<textarea name="aim" rows="3"  id="aim"></textarea></span>
		</dd>
		<#if school_id!=0>
		<dd>
			<label>公开：</label><span> <label>
					<input type="radio" name="shareModel" value="1" checked="checked"/>
					学校内部</label> <label>
					<input type="radio"  name="shareModel" value="0"/>
					完全公开</label> </span>
		</dd>
		</#if>
		<dd>
			<label>标签：</label><span>
				<input type="tag" name="tag" id="tag" />
			</span>
		</dd>

		<dd>
			<label><em class="required">*</em>课程封面：</label><span><img src="${cover}" /></span>
		</dd>
		<#if FEE_MODLE>
		<dd>
			<label><em class="required">*</em>课程价格： </label>
			<span>
				<input type="text" id="price" name="price" />
				${MONEY_UNIT} </span>
		</dd>
		<dd>
			<label><em class="required">*</em>试看时长： </label>
			<span>
				<input type="text" id="tryTime" name="tryTime"  />
			</span>
		</dd>
		</#if>
		<dd>
			<label> <em class="required">*</em>课程简介： </label>
		</dd>
		<dd>
			<span> 				<textarea id="intro" name="intro" node-type="editor" height="300" width="720"></textarea></span>
		</dd>
		<dd>
			<button  onclick="Course.saveVideo(true);"  class="button large primary course-next">
				保存
			</button>
		</dd>
	</dl>
</form>
</#if> 