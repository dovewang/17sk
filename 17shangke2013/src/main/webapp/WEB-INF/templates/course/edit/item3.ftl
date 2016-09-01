<#if edit?exists>
<form id="courseForm" method="post" action="${SITE_DOMAIN}/course/manager/update.html"  onsubmit="return false;"  data-validator-url="/v/course.json">
  <input id="courseId" type="hidden" name="courseId" value="${course.courseId}" />
  <input  type="hidden" name="type" value="3" />
  <input  type="hidden" name="schoolId" value="${course.schoolId}" />
  <dl class="form label80 input-xxlarge">
    <dd>
      <label><em class="required">*</em>课程分类：</label>
      <span>
      ${kindHelper.getKindLabels(course.category?j_string)}
      </span> </dd>
    <dd>
      <label><em class="required">*</em>适用范围：</label>
      <span>
      ${kindHelper.getGradeLabel(course.scope)}
      </span> </dd>
    <dd>
      <label><em class="required">*</em>课程名称：</label>
      <span>
      <input type="text" name="name" id="name"  maxlength="100" value="${course.name}"/>
      </span> </dd>
      		<dd>
			<label><em class="required">*</em>课程目的：</label>
			<span> 				<textarea  name="aim" id="aim" >${course.aim!}</textarea>
				<br/>
				<span class="gray1">简单的描述您的授课对用户的帮助，限100字以内</span> </span>
		</dd>
    <#if school_id!=0>
    <dd>
      <label>公开：</label>
      <span>
      <label> <input type="radio" name="shareModel" value="1" <#if course.shareModel==1>checked="checked"</#if>/>
        学校内部</label>
      <label> <input type="radio"  name="shareModel" value="0" <#if course.shareModel==0>checked="checked"</#if>/>
        完全公开</label>
      </span> </dd>
    </#if>
    <dd>
      <label>标签：</label>
      <span>
      <input type="tag" name="tag" id="tag" value="${course.tag}"/>
      </span> </dd>
    <dd>
      <label> <em class="required">*</em>报名时段： </label>
      <span>
      <input id="applyTime"   name="applyTime"    type="text"   value="${course.applyTime!}" maxlength="50"/>
      <br/>
      <span class="gray1">请根据您的实际情况填写，例如周一到周五，工作日等</span> </span> </dd>
    <dd>
      <label> <em class="required">*</em>开课时段： </label>
      <span>
      <input id="openTime"   name="openTime"    type="text" maxlength="50"  value="${course.openTime!}"/>
      <br/>
      <span class="gray1">请根据您的实际情况填写，例如周一到周五，工作日等</span> </span> </dd>
    <dd>
      <label><em class="required">*</em>上课地址： </label>
      <span>
      ${course.memo!}
      </span> </dd>
    <dd>
      <label><em class="required">*</em>课程价格： </label>
      <span>
      <input type="text" id="price" name="price" value="${course.price}" maxlength="4"/>
      ${MONEY_UNIT}
      </span> </dd>
    <dd>
      <label> <em class="required">*</em>课程简介： </label>
    </dd>
    <dd> <span>
      <textarea id="intro" name="intro" node-type="editor"  height="300" width="720">${course.intro}
</textarea>
      </span> </dd>
    <dd class="text-right">
      <button onclick="Course.saveLocal();" class="button primary large save"> 保存 </button>
    </dd>
  </dl>
</form>
<#else>
<#assign sc=domainHelper.getDomainById(school_id)/>
<form id="courseForm" method="post" action="${SITE_DOMAIN}/course/manager/save.html"  onsubmit="return false;" data-validator-url="/v/course.json">
  <input  type="hidden" name="schoolId" value="${school_id}" id="schoolId"/>
  <input id="courseId" type="hidden" name="courseId" value="0" />
  <input  type="hidden" name="type" value="3" />
  <dl class="form label80 input-xxlarge">
    <dd>
      <label><em class="required">*</em>学校选择：</label>
      <span>
      <input type="text" value="${sc.name}" readonly node-type="pop" rel="/manager/school/select.html" id="schoolName">
      </span> </dd>
    <dd>
      <label><em class="required">*</em>课程分类：</label>
      <span> <span class="select-box box" style="width:500px" action-target="category">
      <input  type="hidden" readonly name="category" id="category" value=""/>
      <#assign course_category=kindHelper.getKinds() />
      <#list  course_category?keys as key> <a href="javascript:void(0)" action-type="select-item"  action-data="${key}">
      ${course_category[key]}
      </a> </#list></span> </span> </dd>
    <dd>
      <label><em class="required">*</em>适用范围：</label>
      <span> <span>
      <input  type="hidden" readonly name="scope" id="scope" value=""/>
      <#assign grade=kindHelper.getGrades()/><span class="select-box box" action-target="scope"  style="width:500px"> <#list  grade?keys as key> <a href="javascript:void(0)" action-type="select-item"  action-data="${key}">
      ${grade[key]}
      </a> </#list></span></span> </span> </dd>
    <dd>
      <label><em class="required">*</em>课程名称：</label>
      <span>
      <input type="text" name="name" id="name"  maxlength="30"/>
      <br/>
      <span class="gray">请填写您的课程名称，最多20个字符</span> </span> </span> </dd>
      		<dd>
			<label><em class="required">*</em>课程目的：</label>
			<span> 				<textarea  name="aim" id="aim" ></textarea>
				<br/>
				<span class="gray1">简单的描述您的授课对用户的帮助</span> </span>
		</dd>
    <#--非管理员登录的学校账户可以使用-->
    <#if school_id!=0>
    <dd>
      <label>公开：</label>
      <span>
      <label>
        <input type="radio" name="shareModel" value="1" checked="checked"/>
        学校内部</label>
      <label>
        <input type="radio"  name="shareModel" value="0"/>
        完全公开</label>
      </span> </dd>
    </#if>
    <dd>
      <label>标签：</label>
      <span>
      <input type="tag" name="tag" id="tag" />
      </span> </dd>
    <dd>
      <label><em class="required">*</em>课程封面：</label>
      <span>
      <input type="hidden" name="cover" id="courseCoverInput" />
      <div id="courseCoverDiv"></div>
      <button  node-type="pop" rel="/cover/select.html" class="button primary large" setting="{id:'cover-upload-pop',cache:false,css:{width:800,top:35}}"> 上传封面 </button>
      </span> </dd>
    <dd>
      <label> <em class="required">*</em>报名时段： </label>
      <span>
      <input id="applyTime"   name="applyTime"    type="text"   value=""/>
      <br/>
      <span class="gray1">请根据您的实际情况填写，例如周一到周五，工作日等</span></span> </dd>
    <dd>
      <label> <em class="required">*</em>开课时段： </label>
      <span>
      <input id="openTime"   name="openTime"    type="text"   value=""/>
      <br/>
      <span class="gray1">请根据您的实际情况填写，例如周一到周五，工作日等</span> </span> </dd>
    <dd>
      <label><em class="required">*</em>上课地址： </label>
      <span>
      <input id="addressId"  name="addressId"    type="hidden"   value="" maxlength="50"/>
      <input id="address_city"  name="city"    type="hidden"   value="" maxlength="50"/>
      <input type="text" id="address_text" value="请您选择上课地址" onclick="School.viewAddress()">
      </span> </dd>
    <dd>
      <label><em class="required">*</em>课程价格： </label>
      <span>
      <input type="text" id="price" name="price" value="0" maxlength="4"/>
      ${MONEY_UNIT}
      </span> </dd>
    <dd>
      <label> <em class="required">*</em>课程简介： </label>
      <span><a href="#intro_sample" node-type="pop">看看别人怎么写</a></span> </dd>
    <dd> <span>
      <textarea id="intro" name="intro" node-type="editor"  height="300" width="720"></textarea>
      </span> </dd>
    <dd class="text-right">
      <button onclick="Course.saveLocal(true);" class="button primary large course-next"> 下一步 </button>
      <button onclick="history.back();" class="button large course-cancle"> 取消 </button>
    </dd>
  </dl>
</form>
</#if> 
<!--
<div id="intro_sample">
退款规定：
1、开课前因不可抗力的原因需要申请退费的，退回全部学费；
</div>
-->