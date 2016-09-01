<#--只包含mete定义，全局的css和基础javascript定义，其他javascript最好放置在页面的底部-->
<#include "/conf/config.ftl" />
<meta http-equiv="X-UA-Compatible"  content="IE=7"/>
<script  type="text/javascript">
window.Env= {VERSION:"${VERSION}",TIME:"2010-11-24",SITE_DOMAIN:"${SITE_DOMAIN}",SITE_NAME:"${SITE_NAME}",SITE_ID:"${SITE_ID}",GROUP_ID:0,
STATIC_DOMAIN:"${STATIC_DOMAIN}",FILE_DOMAIN:"${FILE_DOMAIN}",SECURE:${(SECURE!false)?string("true","false")},SECURE_DOMAIN:"${SECURE_DOMAIN}",THEME_PATH:"${THEME_PATH}",
COOKIE_DOMAIN:"${SITE_DOMAIN}",MBLOG_MAX_LENGTH:140,QUESTION_MAX_LENGTH:100,USER_ID:"${USER_ID}",USER_NAME:"${__USER.name}",face:"${(__USER.getAttribute("face")!("/theme/main/default/images/noface.jpg"))}",isLogin:${isLogin?string("true","false")},USER_TYPE:${__USER.userType}
,FEE_MODLE:${FEE_MODLE?string("true","false")}};
/*document.domain="17shangke.com";*/
</script>
<!--<link href="${STATIC_DOMAIN}/theme/base/css/flint-min.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>-->
<!--<link href="${STATIC_DOMAIN}/theme/kiss/kiss-min.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>-->
<link  href="/theme/kiss/kiss.min.css" rel="stylesheet" type="text/css">
<link  href="/theme/kiss/kiss-responsive.min.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
<script src="/theme/kiss/html5shiv.js"></script>
<![endif]-->