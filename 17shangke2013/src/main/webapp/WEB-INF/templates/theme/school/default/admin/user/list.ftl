<#include "/conf/config.ftl" />
<#assign classid=RequestParameters["classid"]!"0" >
<div class="text-right"><button  type="button" rel="/admin/user/form.html?cid=${classid}&is=add" class="btn btn-primary" node-type="pop" setting="{id:'UserFormView',cache:false,css:{width:480}}">新增用户</button></div>
<#if result.totalCount==0>
<div class="placeholder-box"> 该班级没有任何成员 </div>
<#else>
<#list  result.result as user >
<#if classid != "0">
<dl class="user-item">
  <dt><img  width="32" height="32" usercard="${user.user_id}"></dt>
  <dd> <span>
    ${user.name}(<#if user.role==0>普通成员<#elseif user.role==1>班主任<#elseif user.role==2>班长<#elseif user.role==3>副班长</#if>)
    </span>
    <div class="control-bar"><a href="/admin/user/form.html?uid=${user.user_id}&cid=${classid}" class="popwin"  node-type="pop" setting="{id:'UserFormView',cache:false,css:{width:480}}"><i class="icon-edit"></i>编辑</a>
    				<a href="javascript:void(0)" onclick="School.deleteUser(this,${user.user_id},${classid})">删除</a> |
						<#if  user.status=1> <a href="javascript:void(0)" onclick="School.lockUser(this,${user.user_id})">锁定账号</a> 
						<#else> <a href="javascript:void(0)" onclick="School.unlockUser(this,${user.user_id})" style="color:red">解锁账号</a> 
						</#if> | <a href="javascript:void(0)" onclick="MC.showForm(${user.user_id},'${user.name}')">发送消息</a></div>
  </dd>
</dl>
<#else>
<dl class="user-item">
  <dt><img  width="32" height="32" usercard="${user.user_id}"></dt>
  <dd> <span>
    ${user.name}(<#if user.user_type==1>学生<#elseif user.user_type==2>老师<#elseif user.user_type==127>管理员</#if>)
    </span>
    <div class="control-bar"><a href="/admin/user/form.html?uid=${user.user_id}&cid=${classid}" class="popwin"  node-type="pop" setting="{id:'UserFormView',cache:false,css:{width:480}}"><i class="icon-edit"></i>编辑</a>				<a href="javascript:void(0)" onclick="School.deleteUser(this,${user.user_id},${classid})">删除</a> |
						<#if  user.status=1> <a href="javascript:void(0)" onclick="School.lockUser(this,${user.user_id})">锁定账号</a> 
						<#else> <a href="javascript:void(0)" onclick="School.unlockUser(this,${user.user_id})" style="color:red">解锁账号</a> 
						</#if> | <a href="javascript:void(0)" onclick="MC.showForm(${user.user_id},'${user.name}')">发送消息</a></div>
  </dd>
</dl>
</#if>
</#list>
<@flint.pagination result,"","","" />
</#if>