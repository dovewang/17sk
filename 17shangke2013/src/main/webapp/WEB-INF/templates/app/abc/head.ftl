<#include "/conf/config.ftl" />
<title>${TITLE?default("")}</title>
<script  type="text/javascript">
window.Env= {VERSION:"${VERSION}",TIME:"2010-11-24",SITE_DOMAIN:"${SITE_DOMAIN}",SITE_NAME:"${SITE_NAME}",SITE_ID:"${SITE_ID}",
IMAGE_DOMAIN:"${IMAGE_DOMAIN}",FILE_DOMAIN:"${FILE_DOMAIN}",SECURE:${(SECURE!false)?string("true","false")},SECURE_DOMAIN:"${SECURE_DOMAIN}",THEME_PATH:"${THEME_PATH}",
COOKIE_DOMAIN:"${SITE_DOMAIN}",MBLOG_MAX_LENGTH:140,QUESTION_MAX_LENGTH:100,USER_ID:"${USER_ID}",USER_NAME:"${__USER.name}",face:"${(__USER.getAttribute("face")!("/theme/main/default/images/noface.jpg"))}",isLogin:${isLogin?string("true","false")},USER_TYPE:${__USER.userType}
,FEE_MODLE:${FEE_MODLE?string("true","false")}};
/*document.domain="17shangke.com";*/
</script>
<script src="${IMAGE_DOMAIN}/theme/base/js/json2.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<script src="${IMAGE_DOMAIN}/theme/base/js/jquery.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<script src="${IMAGE_DOMAIN}/theme/kiss/kiss-min.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<script src="${IMAGE_DOMAIN}/theme/base/js/sec.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<link href="${IMAGE_DOMAIN}/theme/base/css/flint-min.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
<link href="${IMAGE_DOMAIN}/theme/kiss/kiss-min.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
<script src="${IMAGE_DOMAIN}/theme/base/js/base.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<link href="${IMAGE_DOMAIN}/theme/main/default/css/default.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
<script src="${IMAGE_DOMAIN}/theme/main/js/bis.base.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>