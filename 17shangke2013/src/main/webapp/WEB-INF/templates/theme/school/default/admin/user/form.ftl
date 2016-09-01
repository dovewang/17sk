<#assign is=RequestParameters["is"]!>
<#assign result=result! > 
<div class="pop-header">
<h3>成员信息</h3><a href="javascript:void(0)" class="close" node-type="pop-close">×</a></div>
  <form  id="userForm" action="/admin/user/save.html" method="POST" data-validator-url="/v/school.user.json">
    <input type="hidden" name="userid" id="userid" value="${result.user_id!0}"/>
    <input type="hidden" name="classid" id="classid" value="${result.group_id!0}"/>
    <dl class="form input-180">
      <#if is=="add"><!-- 新增 -->
     	  <dd>
	        <label><em class="required">*</em>邮箱：</label>
	        <span>
	        <input type="text" name="email" id="email"  value="${result.email!}" maxlength="50"/>
	        </span> </dd>
	      <dd>
	        <label>类型：</label>
	        <span>
	        <input type="radio" name="userType" value = "1" checked="true">
	      		 学生
	        <input type="radio" name="userType" value = "2">
	        	老师
	        <#if ((result.group_id!0)==0)><!--添加的是未分组用户-->
	        <input type="radio" name="userType" value = "127">
	        	管理员
	        </#if> 
	        </span> 
	      </dd>
      <#else>
	      <dd>
	          <label><em class="required">*</em>邮箱：</label>
	          <span>
	          <input type="text" name="email" id="email" value="${result.email!}" readonly/>
	          </span> 
	      </dd>
      </#if>
      <dd>
        <label><em class="required"></em>姓名：</label>
        <span>
        <input type="text" name="username" id="username"  value="${result.name!}" maxlength="30"/>
        </span> </dd>
      <dd>
        <label>学号/教工号：</label>
        <span>
        <input type="text" name="id" id="id"  value="${result.id!}" maxlength="50"/>
        </span> </dd>
      <dd>
        <label>电话：</label>
        <span>
        <input type="text" name="tel" id="tel"  value="${result.tel!}" maxlength="30"/>
        </span> </dd>
      <dd>
        <label>所在班级：</label>
        <span>
        <input type="text" name="className" id="className" value="${result.class_name!"未分组"}" readonly/>
        </span> </dd>
    </dl>
  </form>
</div>
<div class="pop-footer"><button type="button" node-type="pop-close" class="btn"> 取消 </button><button type="button" onclick="School.saveUser(this);" class="btn btn-primary"> 确定 </button>
        </div>