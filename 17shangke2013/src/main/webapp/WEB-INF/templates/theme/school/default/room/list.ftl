<#include "/conf/config.ftl" />
<table class="table gray">
	<thead>
		<tr>
			<th width="200">主题</th>
			<th>状态</th>
			<th>开始时间-结束时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<#list result.result as  room>
		<tr>
			<td><a href="${SITE_DOMAIN}/room${room.roomId}.html" target="_blank">${room.subject}</a></td>
			<td></td>
			<td nowrap><#if room.startTime==0>——<#else>${flint.timeToDate(room.startTime,"yyyy-MM-dd HH:mm:ss")}</#if>至
			<#if room.endTime==0>——<#else>${flint.timeToDate(room.endTime,"yyyy-MM-dd HH:mm:ss")}</#if></td>
			<td nowrap class="link-group"><a href="${SITE_DOMAIN}/room${room.roomId}.html" target="_blank">进入</a>|<a href="/user/" class="popwin">邀请</a><#if ((room.endTime -room.startTime)>0)  >| <a href="${SITE_DOMAIN}/v/id_${room.roomId}.html"  target="_blank">回放</a></#if></td>
		</tr>
		</#list>
	</tbody>
</table>
