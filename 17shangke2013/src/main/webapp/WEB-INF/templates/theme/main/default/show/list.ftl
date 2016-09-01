<#include "/conf/config.ftl" />
<#if result.totalCount==0>
<div  class="nodata">
	对不起，没有找到相关的秀秀
</div>
<#else>
<#list result.result as  show>
<dl class="course-item-thumb" action-type="show-item" id="show${show.showId}" sid="${show.showId}">
	<dt class="course-cover">
		<span class="course-type t${show.type}">${enumHelper.getLabel("course_type",show.type?j_string)}</span>
		<a href="${SITE_DOMAIN}/show/item-${show.showId}.html"  target="_blank"><img  source="${imageHelper.resolve(show.cover!,"s200,150")}"  src="/theme/main/default/images/show_cover.jpg" alt="${show.title}" width="200" height="150"/></a>
	</dt>
	<dd class="course-base">
		<a href="${SITE_DOMAIN}/show/item-${show.showId}.html"  target="_blank"><h2>${show.title}</h2></a>
		主讲老师： <a href="/u/${show.userId}.html" target="_blank" usercard="${show.userId}">${show.userId}</a>
		<br/>
		分享时间：<span class="gray">${flint.timeToDate(show.dateline,"yyyy-MM-dd HH:mm")} </span>
		<br/>
		<div>
			<span><i class="icon-views"></i>${show.views}</span>
			<span><i class="icon-comments"></i>${show.comments}</span>
			<span><i class="icon-ups"></i>${show.ups}</span>
			<#if show.userId?string==USER_ID>
			<span><a href="${SITE_DOMAIN}/show/view.html?sid=${show.showId}">编辑</a> |</span>
			<span><a href="javascript:;" node-type="show-delete" sid="${show.showId}">删除</a> |</span>
			</#if>
			<span><a href="javascript:;" favor="0" node-type="favor" objectId="${show.showId}" objectType="6">收藏♥</a></span>
			</span>
		</div>
	</dd>
</dl>
</#list>
<@flint.pagination result,"","","" /></#if>
<script type="text/javascript">
	var c = [];
	$("a[node-type='favor']").each(function() {
		c.push($(this).attr("objectId"));
	});
	if (c.length > 0) {
		$.get(Env.SITE_DOMAIN + "/getCollects.html", {
			objectids : $.unique(c).toString(),
			objectType : 6
		}, function(result) {
			var list = result.result;
			if (list) {
				for (var i = 0; i < list.length; i++) {
					$("a[node-type='favor'][objectId=" + list[i].objectId + "]").text("取消收藏").attr("favor", 1);
				}
			}
		});
	}
</script>