<#include "/conf/config.ftl" />
<#if result.totalCount==0>
<div  class="placeholder-box">
	没有发布任何信息
</div>
<#else>
<#list result.result as m>
<dl class="box-list" id="feed_${m.id}">
	<dt class="face">
		<a href="/u/${m.userId}.html" target="_blank"><img usercard="${m.userId}" src="/theme/school/default/images/noface_s.jpg" width="50" height="50" alt="学生名"/></a>
	</dt>
	<dd class="content">
		<p>
			<a href="/u/${m.userId}.html" target="_blank" usercard="${m.userId}">${m.userId}</a>：
			<#if m.type==1>
			${m.content?replace("<a>","<a href=\"/qitem-${m.media}.html\" target=\"_blank\">")}
			<#elseif m.type==2>
			   ${m.content?replace("<a>","<a href=\"/course/${m.media}-${VERSION}.html\" target=\"_blank\">")}				
			<#elseif m.type==3>
               ${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()?replace("<a>","<a href=\"/u/${m.userId}.html#dm${m.media}\" target=\"_blank\">")}
          	<#elseif m.type==5>
               ${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()?replace("<a>","<a href=\"/u/${m.userId}.html#sv${m.media}\" target=\"_blank\">")}
			<#else>
			${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()}
			<#if  ((m.media?exists)&&(m.media!=""))>
			<div  style="cursor:pointer" onclick="Mblog.showImage(${m.id},true)" id="media_s_${m.id}"><img src="${m.media}" style="max-width:120px;"/>
			</div>
			<div  style="cursor:pointer;display:none"  id="media_${m.id}">
				<dl class="box blue imagebox">
					<dt>
						<a href="javascript:void(0)" class="packUp" onclick="Mblog.showImage(${m.id},false)" >收起</a>|<a href="${m.media}" target="_blank" class="viewSource">查看大图</a>
						|<a href="javascript:void(0)"  class="rotateLeft" onclick="Mblog.rotateLeft(${m.id})">向左转</a>| <a href="javascript:void(0)"  class="rotateRight" onclick="Mblog.rotateRight(${m.id})">向右转</a>
					</dt>
					<dd onclick="Mblog.showImage(${m.id},false);"><img src="${m.media}" id="img_container${m.id}"   style="max-width:500px" />
					</dd>
				</dl>
			</div>
			</#if>
			</#if>
		</p>
		<p class="data-info">
			<span class="link-group float-right"> <#if m.userId?string==USER_ID> <a href="javascript:void(0)" mid="${m.id}"  node-type="feed-delete">删除</a>| </#if> <a href="javascript:void(0)" mid="${m.id}" id="comments_link${m.id}" node-type="feed-show-comment">评论<#if (m.comments>0) >(<i id="cnums${m.id}">${m.comments}</i>)</#if></a></span>${helper.passTime(m.createTime)}
		</p>
		<div class="comments box" id="feedc_${m.id}" style="display:none;"></div>
	</dd>
</dl>
</#list> <!--tab_conlist end-->
<@flint.pagination result,"","","" />
</#if>