<#include "/conf/config.ftl" />
<#if result.totalCount==0>
<div style="text-align:center;height:80px;line-height:80px;vertical-align:middle">
	没有任何消息
</div>
<#else>
<#list result.result as m>
<div class="admin_conlist" id="announceItem${m.messageId}">
	<div class="admin_conlist_pic">
		<a href="#"><img src="/theme/school/default/images/message_school.jpg" schoolcard="${m.sender}" width="40" height="40" alt=""/></a>
	</div>
	<div class="admin_conlist_con">
		<div class="publishEvent">
			学校公告
		</div>
		<div class="publishSay">
			${m.subject} <#if m.message!=""> <a href="javascript:void(0)" onclick="$('#m${m.messageId}').slideToggle()">详细&gt;</a> </#if>
		</div>
        <div class="announce_content" style="display:none" id="m${m.messageId}">
            ${m.message}
        </div>
		<div class="timeAndOperation">
			<div class="publishTime">
				${helper.passTime(m.sendTime)}
			</div>
			<div class="publishOperation">
				<ul>
					<li>
						<a href="javascript:void(0)" onclick="MC.deleteAnnounce(${m.messageId});return false;">删除</a>
					</li>
				</ul>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<!--tab_conlist_con end-->
</div>
</#list>
<@flint.pagination result,"","","" />
</#if> 