<#include "/conf/config.ftl" />
<dl class="list-item mblog${m.type}" id="feed_${m.id}" node-type="feed-item" feedid="${m.id}">
	<dt class="face">
		<a href="/u/${m.userId}.html" target="_blank"><img usercard="${m.userId}" src="/theme/school/default/images/noface_s.jpg" /></a>
	</dt>
	<dd class="content">
		<p>
			<a href="/u/${m.userId}.html" target="_blank" usercard="${m.userId}">${m.userId}</a>：
			<#if m.type==1>
			${m.content?replace("<a>","<a href=\"/qitem-${m.media}.html\" target=\"_blank\">")}
			<#elseif m.type==2>
			   ${m.content?replace("<a>","<a href=\"/course/${m.media}-${VERSION}.html\" target=\"_blank\">")}				
			<#elseif m.type==3>
			${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()?replace("<a>","<a href=\"/show/item-${m.media}.html\" target=\"_blank\">")}
           <#elseif m.type==4>
               ${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()?replace("<a>","<a href=\"/u/${m.userId}.html#dm${m.media}\" target=\"_blank\">")}
          <#elseif m.type==5>
               ${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()?replace("<a>","<a href=\"/u/${m.userId}.html#sv${m.media}\" target=\"_blank\">")}
			<#else>
			${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()}
			<#if  ((m.media?exists)&&(m.media!=""))>
			<div  onclick="Mblog.showImage(${m.id},true)" id="media_s_${m.id}"><img src="${imageHelper.resolve(m.media,"s120,0")}" style="max-width:120px;"  class="zoom-in"/>
			</div>
			<div  style="display:none"  id="media_${m.id}">
			<dl class="box">
			<dt>
			<a href="javascript:void(0)" onclick="Mblog.showImage(${m.id},false)"  class="btn btn-link"><i class="kicon-view-packup"></i>收起</a><a href="${m.media}" target="_blank"  class="btn btn-link"><i class="kicon-view-source"></i>查看大图</a>
<a href="javascript:void(0)" onclick="Mblog.rotateLeft(${m.id})"  class="btn btn-link"><i class="kicon-turn-left"></i>向左转</a><a href="javascript:void(0)"  onclick="Mblog.rotateRight(${m.id})"  class="btn btn-link"><i class="kicon-turn-right"></i>向右转</a>
			</dt>
			<dd onclick="Mblog.showImage(${m.id},false);"><img src="${imageHelper.resolve(m.media,"s500,0")}" id="img_container${m.id}"  class="zoom-out"  style="max-width:600px" />
			</dd>
			</dl>
			</div>
			</#if>
			</#if>
			</p><p class="data-info">
			<span class="link-group float-right"> 
			<#if m.userId?string==USER_ID||USER_TYPE==127> 
			<a href="javascript:void(0)"  action-type="feed-delete"><i class="kicon-trash"></i>删除</a>| 
			</#if> 
			<a href="javascript:void(0)" action-type="feed-favor" favor="0"><i class="kicon-like"></i>收藏</a> |<a href="javascript:void(0)" id="comments_link${m.id}" action-type="feed-show-comment"><i class="kicon-comment"></i>评论<#if (m.comments>0) >(<i id="cnums${m.id}">${m.comments}</i>)</#if></a></span>${helper.passTime(m.dateline)}
		</p>
		<div class="comments box hide" id="feedc_${m.id}"  ></div>
	</dd>
</dl>