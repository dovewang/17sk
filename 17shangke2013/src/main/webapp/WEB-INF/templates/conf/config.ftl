<#--大写为全局变量，以"__"开头的为session中存储的数据-->
<#include "site.ftl" />
<#import "/lib/flint.ftl" as flint >
<#assign USER_STUDENT=1>
<#assign USER_TEACHER=2>
<#assign USER_ADMIN=127>
<#assign FEE_MODLE=false>
<#assign MONEY_UNIT=moneyUnit>
<#assign __SESSION=Session["FLINT.USER_SESSION"]>
<#assign __USER=__SESSION.get()>
<#assign MUSER=__USER.getAttribute("musers.id")!>
<#assign USER_ID=__USER.id>
<#assign USER_NAME=__USER.name>
<#assign USER_TYPE=__USER.userType>
<#assign isLogin=__SESSION.isAuthenticated()>
<#assign IS_ADMIN=false>  
<#if USER_TYPE==0 || USER_TYPE==1>
    <#assign ROLE_NAME="学生" />
<#elseif USER_TYPE==2>
    <#assign ROLE_NAME="老师" />
<#elseif USER_TYPE==3>
    <#assign ROLE_NAME="家长" />   
<#elseif USER_TYPE==4>
    <#assign ROLE_NAME="学校" /> 
<#elseif USER_TYPE==127>
    <#assign ROLE_NAME="管理员" /> 
    <#assign IS_ADMIN=true>              
</#if>
<#include "authz.ftl" />
