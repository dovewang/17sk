<#include "/conf/config.ftl" />
<#assign id=RequestParameters["id"]! />
<div class="comments-inner">
  <div>
    <input name="newcom_${id}" type="text" id="newcom_${id}" value=""  class="input" style="width:98%" maxlength="140"/>
  </div>
  <div class="comments-footer"> <a href="javascript:void(0)"  " onclick="loadFace(this,'newcom_${id}')"><i class="kicon-smile"></i>表情</a>
    <button class="btn  float-right" onclick="Mblog.postComments(${id});return false;" style="margin-right:20px">评论</button>
  </div>
</div>
<#list result.result as m>
<dl class="list-item small" id="comment${m.id}">
  <dt class="face"> <a href="/u/${m.userId}.html" target="_blank"><img usercard="${m.userId}" src="/theme/school/default/images/noface_s.jpg" width="30" height="30" alt=""/></a> </dt>
  <dd class="content">
    <p> <a href="/u/${m.userId}.html" target="_blank" usercard="${m.userId}">
      ${m.userId}
      </a>：
      ${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()}
    </p>
    <p class="data-info"> <span class="link-group float-right pr10"> <a href="javascript:;" onclick="Mblog.replyComment(${m.id},${id},${m.userId});return false;">回复</a> <#if m.userId?string==USER_ID> 
      | <a href="javascript:;" onclick="Mblog.delComment(this,${m.id},${id});return false;"><i class="kicon-trash"></i>删除</a> </#if> </span>
      ${helper.passTime(m.createTime)}
    </p>
  </dd>
</dl>
</#list>