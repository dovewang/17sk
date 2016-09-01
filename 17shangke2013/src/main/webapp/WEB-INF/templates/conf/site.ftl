<#assign VERSION=.now?long />
<#assign ENCODING="UTF-8" />
<#assign SITE_NAME=site.name />
<#assign SITE_ID=site.id />
<#--暂时处理一下，主站使用https登录，分站使用http登录-->
<#assign SECURE_DOMAIN="http://${domain}/passport"/>
<#--only user login redirect-->
<#assign HTTP_DOMAIN="http://${domain}"/>
<#if SECURE?exists>
<#assign IMAGE_DOMAIN=HTTP_DOMAIN/>
<#else>
<#assign IMAGE_DOMAIN="http://${domain}"/>
</#if>
<#assign STATIC_DOMAIN=IMAGE_DOMAIN/>
<#assign THEME_PATH=site.themePath/>
<#assign SITE_LOGO=site.logo />
<#assign FILE_DOMAIN="http://${domain}"/>
<#assign MIN=""/> 