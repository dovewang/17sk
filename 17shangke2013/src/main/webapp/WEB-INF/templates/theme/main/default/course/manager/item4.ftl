<#if edit?exists>
<form id="videoForm" method="post" action="${SITE_DOMAIN}/course/manager/update.html"  onsubmit="return false;" data-validator-url="/v/course.json">
	<input id="courseId" type="hidden" name="courseId" value="${course.courseId}" />
	<dl class="form label80 input-xxlarge">
		<dd>
			<label><em class="required">*</em>课程分类：</label>
			<span> ${kindHelper.getKindLabel(course.category?j_string)} </span>
		</dd>
		<dd>
			<label><em class="required">*</em>适用范围：</label>
			<span> ${kindHelper.getGradeLabel(course.scope)} </span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程名称：</label>
			<span>
				<input type="text" name="name" id="name" class="input " maxlength="100" value="${course.name}"/>
			</span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程目的： </label>
			<span> 				<textarea name="aim" rows="3" class="input " id="aim">${course.aim}</textarea> </span>
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
			<label><em class="required">*</em>课程封面：</label>
			<span><img src="${course.cover}"></span>
		</dd>
		<#if FEE_MODLE>
		<dd>
			<label><em class="required">*</em>课程价格： </label>
			<span>
				<input type="text" id="price" name="price" value="${course.price}"/>
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
<form id="videoForm" method="post" action="${SITE_DOMAIN}/course/manager/save.html"  onsubmit="return false;" data-validator-url="/v/course.json">
	<input  type="hidden" name="schoolId" value="${school_id}" />
	<input id="courseId" type="hidden" name="courseId" value="0" />
	<input  type="hidden" name="type" value="4" />
	<input id="videoDir" type="hidden" name="dir" value="" />
	<input id="videoCover" type="hidden" name="cover" value="" />
	<dl class="form label80 input-xxlarge">
		<dd>
			<label><em class="required">*</em>转发视频源：</label>
			<span>
				<input type="text" name="url" id="url" class="input " maxlength="100"/>
				<button class="button" type="button" onclick="Course.getVideo(this)">
					获取数据
				</button>
				<br/>
				<span class="gray1">只需要填写您视频播放的网页地址。例如：http://v.youku.com/v_show/id_XNDA0NDE0ODA4.html
					<br/>
					(目前仅支持优酷、土豆、新浪、酷六)</span> </span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程分类：</label>
			<span>
				<input  type="hidden" readonly name="category" id="category" value=""/>
				<span class="select-box box" style="width:500px" action-target="category" max="1"><#assign course_category=kindHelper.getKinds() />
					<#list  course_category?keys as key> <a href="javascript:void(0)" action-type="select-item"  action-data="${key}"> ${course_category[key]} </a> </#list></span> </span>
		</dd>
		<dd>
			<label><em class="required">*</em>适用范围：</label>
			<span>
				<input  type="hidden" readonly name="scope" id="scope" value=""/>
				<span> <#assign grade=kindHelper.getGrades()/><span class="select-box box" action-target="scope"  style="width:500px"> <#list  grade?keys as key> <a href="javascript:void(0)" action-type="select-item"  action-data="${key}"> ${grade[key]} </a> </#list></span></span> </span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程名称：</label>
			<span>
				<input type="text" name="name" id="videoName" class="input " maxlength="100"/>
			</span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程目的： </label>
			<span> 				<textarea name="aim" rows="3" class="input " id="aim" placehoder=""></textarea> </span>
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
				<input type="tag" name="tag" id="tag" value=""/>
			</span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程封面：</label>
			<span id="coverImage"></span>
		</dd>
		<#if FEE_MODLE>
		<dd>
			<label><em class="required">*</em>课程价格： </label>
			<span>
				<input type="text" id="price" name="price" value="0"/>
			</span>
		</dd>
		</#if>
		<dd>
			<label> <em class="required">*</em>课程简介： </label>
		</dd>
		<dd>
			<span><textarea id="intro" name="intro" node-type="editor" height="300" width="720"></textarea></span>
		</dd>
		<dd>
			<button  onclick="Course.saveVideo(true);"  class="button large primary course-next">
				 保存
			</button>
		</dd>
	</dl>
</form>
</#if> 