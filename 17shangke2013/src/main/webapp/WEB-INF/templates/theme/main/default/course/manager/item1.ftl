<#if edit?exists>
<form id="courseForm" method="post" action="${SITE_DOMAIN}/course/manager/update.html"  onsubmit="return false;" data-validator-url="/v/course.json">
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
			<label><em class="required">*</em>课程名称：</label>
			<span>
				<input type="text" name="name" id="name" maxlength="100" value="${course.name}"/>
			</span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程目的： </label>
			<span> 				<textarea name="aim" rows="3" id="aim">${course.aim}</textarea> </span>
		</dd>
		<#if site.id!=0>
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
				<input type="tag" name="tag" id="tag"  value="${course.tag}"/>
			</span>
		</dd>
		<dd>
			<label> <em class="required">*</em>开课时间： </label>
			<span>${flint.timeToDate(course.startTime,"yyyy-MM-dd HH:mm")} &nbsp;&nbsp;&nbsp;&nbsp;时长：${course.time/60}分钟</span>
		</dd>
		<dd>
			<label> <em class="required">*</em>课程容量： </label>
			<span>
				<select name="minCapacity" id="minCapacity" value="${course.minCapacity}">
					<option value="0">不限制</option>
					<#list 1..10 as i> <option value="${i}">${i}人</option>
					</#list>
				</select> -
				<select name="maxCapacity" id="maxCapacity" value="${course.maxCapacity}">
					<option value="0">不限制</option>
					<#list 2..20 as i> <option value="${i}">${i}人</option>
					</#list>
				</select> </select> </span>
		</dd>
		<dd>
			<label> <em class="required">*</em>主讲老师：</label>
			<span> <@flint.userName/> </span>
		</dd>
		<#if FEE_MODLE>
		<dd>
			<label><em class="required">*</em>课程价格： </label>
			<span>
				<input type="text" id="price" name="price"  value="${course.price}"/>
				${MONEY_UNIT} </span>
		</dd>
		</#if>
		<dd>
			<label> <em class="required">*</em>课程简介： </label>
		</dd>
		<dd>
			<span> 				<textarea id="intro" name="intro" node-type="editor"  height="300" width="720">${course.intro}</textarea></span>
		</dd>
		<dd>
			<button onclick="Course.saveCourse();" class="button primary large save">
				保存
			</button>
		</dd>
	</dl>
</form>
<#else>
<form id="courseForm" method="post" action="${SITE_DOMAIN}/course/manager/save.html"  onsubmit="return false;" data-validator-url="/v/course.json">
	<input  type="hidden" name="schoolId" value="${school_id}" />
	<input id="courseId" type="hidden" name="courseId" value="0" />
	<input  type="hidden" name="type" value="1" />
	<dl class="form label80 input-xxlarge">
		<dd>
			<label><em class="required">*</em>课程分类：</label>
			<span>
				<input  type="hidden" readonly name="category" id="category" value=""/>
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
			<label><em class="required">*</em>课程名称：</label>
			<span>
				<input type="text" name="name" id="name" maxlength="100"/>
			</span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程目的： </label>
			<span> 				<textarea name="aim" rows="3" id="aim"></textarea> </span>
		</dd>
		<#if site.id!=0>
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
				<input type="tag" name="tag" id="tag"  value=""/>
			</span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程封面：</label>
			<span>
				<input type="hidden" name="cover" id="courseCoverInput" />
				<div id="courseCoverDiv"></div>
				<button  node-type="pop" rel="/cover/select.html" class="button primary large" setting="{id:'cover-upload-pop',cache:false,css:{width:800,top:35}}">
					上传封面
				</button> </span>
			</span>
		</dd>
		<dd>
			<label> <em class="required">*</em>开课时间： </label>
			<span>
				<input type="date" name="planStartDate" readonly  id="planStartDate"  style="width:120px" min="d,M+10" max="d+30"/>
			</span>
		</dd>
		<dd>
			<label> <em class="required">*</em>时长： </label>
			<span> <label>
					<input  type="radio"  name="time" value="1800"/>
					30分钟</label> <label>
					<input  type="radio" name="time"  value="2700" checked="checked"/>
					45分钟</label> <label>
					<input  type="radio"  name="time" value="3600"/>
					1小时</label> <label>
					<input  type="radio"  name="time" value="5400"/>
					1小时30分钟</label> <label>
					<input  type="radio" name="time"  value="7200"/>
					2小时</label> </span>
		</dd>
		<dd>
			<label><em class="required">*</em>课程容量： </label>
			<span>
				<select name="minCapacity" id="minCapacity" >
					<option value="0">不限制</option>
					<#list 1..10 as i> <option value="${i}">${i}人</option>
					</#list>
				</select> -
				<select name="maxCapacity" id="maxCapacity" >
					<option value="0">不限制</option>
					<#list 2..20 as i> <option value="${i}">${i}人</option>
					</#list>
				</select> </select> </span>
		</dd>
		<dd>
			<label><em class="required">*</em>主讲老师：</label>
			<span> <@flint.userName/> </span>
		</dd>
		<#if FEE_MODLE>
		<dd>
			<label><em class="required">*</em>课程价格： </label>
			<span>
				<input type="text" id="price" name="price" />
				${MONEY_UNIT} </span>
		</dd>
		</#if>
		<dd>
			<label> <em class="required">*</em>课程简介： </label>
		</dd>
		<dd>
			<span> 				<textarea id="intro" name="intro" node-type="editor"  height="300" width="720"></textarea></span>
		</dd>
		<dd class="text-right">
			<button onclick="Course.saveCourse(true);" class="button primary large course-next">
				下一步
			</button>
			<button onclick="history.back();" class="button large course-cancle">
				取消
			</button>
		</dd>
	</dl>
</form>
</#if>