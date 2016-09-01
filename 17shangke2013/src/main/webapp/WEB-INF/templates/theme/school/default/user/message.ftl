<#assign type=messageType!0 />
<!DOCTYPE html>
<html>
<head>
<#include "../head.ftl" />
<link href="${IMAGE_DOMAIN}/theme/main/default/css/private_message.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
</head><body>
<#include "../top.ftl" />
<div class="container">
  <div class="row-fluid">
    <div class="span9">
      <div class="tab-header" >
        <ul id="message-tab">
          <li <#if type==0>class="active"</#if>> <a href="javascript:;" onclick="MC.getMessagePage(0);">私信</a>
          </li>
          <li <#if type==1>class="active"</#if>> <a href="javascript:;" onclick="MC.getMessagePage(1);">学校公告</a>
          </li>
          <li <#if type==2>class="active"</#if>> <a href="javascript:;" onclick="MC.getMessagePage(2);">系统消息</a>
          </li>
        </ul>
      </div>
      <div id="message-privateview" > <#include "messageList.ftl"/> </div>
    </div>
    <div class="span3">...</div>
  </div>
</div>
<footer>
  <div id="page-footer"></div>
</footer>
<#include "../foot.ftl"/>
</body>
</html>
