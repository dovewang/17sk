<!DOCTYPE html>
<html>
	<head>
		<#include "../head.ftl" />
		<link href="/theme/school/default/css/class.css" rel="stylesheet" type="text/css">
		<link  href="/theme/doc/doc.css" rel="stylesheet" type="text/css">
		<link href="/theme/kiss/timetable.css" rel="stylesheet" type="text/css">
	</head>
	<#assign  role_domain="group"+group.groupId+":"/>
	<#assign  isManager=__USER.one(role_domain+"teacher,"+role_domain+"admin,"+role_domain+"master")/>
	<body>
		<#include "../top.ftl"/>
		<div class="container">
			<div class="row">
				<div class="span9">
					<div id="timetable"  class="timetable"></div>
					<div id="post">
						<#include "/common/mblog/poster.ftl" />
					</div>
					<div   id="mblog_list"></div>
				</div>
				<!--右边栏 -->
				<div class="span3  hidden-phone">
					<div class="group-widget group-widget-notice">
						<h3 class="group-widget-title">班级公告 <a href="javascript:;" onclick="$('#group-notice').focus();"><i class="icon icon-edit"></i></a></h3>
						<div class="notice-content">
							<textarea id="group-notice" placeholder="暂无公告" onchange="Group.announce(this)">${group.announce!}
</textarea>
						</div>
					</div>
					<div class="group-widget">
						<h3 class="group-widget-title">班级信息<a href=""><i class="icon icon-setting"></i></a></h3>
						<dl>
							<dd>
								班&nbsp;主&nbsp;任：<#if  position.p1?exists>
								<@flint.userLinks position.p1/>
								<#else>暂无</#if>
							</dd>
							<!--<dd>辅&nbsp;导&nbsp;员：暂无</dd>-->
							<dd>
								班&nbsp;&nbsp;&nbsp;&nbsp;长：<#if  position.p2?exists>
								<@flint.userLinks position.p2/>
								<#else>暂无</#if>
							</dd>
							<dd>
								班级成员：
								${group.members}
								人
							</dd>
							<dd>
								班级简介：
								${group.intro!"无"}
							</dd>
						</dl>
					</div>
					<div class="group-widget group-widget-members">
						<h3 class="group-widget-title">班级成员</h3>
						<div class="members-content" node-type="group-members"></div>
					</div>
				</div>
			</div>
		</div>
		<div id="page-footer"></div>
		<#include "../foot.ftl"/>
		<script src="/theme/kiss/timetable.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<script  src="/theme/doc/courseware.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<script src="${IMAGE_DOMAIN}/theme/base/js/bis.group.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<script src="${IMAGE_DOMAIN}/theme/base/js/bis.mblog.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<script type="text/javascript">
			Env.GROUP_ID = "${group.groupId}";
			Group.id = Env.GROUP_ID;
			Group.type = "{group.type}";
			$("#timetable").timetable({
				editable : ${isManager?string("true","false")},
				td : function(td, me) {
					td.click(function() {
						if (me.editable && (td.hasClass("blank")/*||td.attr("teacher")==Env.USER_ID*/)) {
							var sindex = td.attr("schedule-index");
							Pop.show({
								template : false,
								destroy : true,
								url : "/courseware/form.html?g=" + Env.GROUP_ID + "&date=" + td.attr("date"),
								afterShow : function($pop) {
									var schedule = $('<div data-name="schedule" data-id="scheduleIndex" data-value="' + sindex + '" min="1" class="combobox-box" style="width:415px;"></div>');

									/*新增只允许选择未发布状态的，可能会存在用户选错的情况？这个如何处理*/
									me.body.find("td.blank[date=" + td.attr("date") + "]").each(function(index, element) {
										schedule.append('<span  class="item" data-value="' + $(this).attr("schedule-index") + '">' + $(this).parent().attr("label") + '</span>');
									});
									$pop.find("dd span.schedule").append(schedule);
									schedule.combobox();
								}
							});
						} else if (td.attr("published") == "true") {
							Pop.show({
								template : false,
								destroy : true,
								url : "/courseware/chapter/" + td.attr("cid") + ".html?g=" + Env.GROUP_ID,
								afterShow : function($pop) {
									$pop.find(".pop-header h3").html(td.attr("lesson"));
									var schedule = $('<div data-name="schedule" data-id="scheduleIndex" data-value="' + td.attr("schedule-index") + '" min="1" class="combobox-box" style="width:415px;" readonly="readonly"></div>');
									me.body.find("td[date=label]").each(function(index, element) {
										schedule.append('<span  class="item" data-value="' + $(this).attr("schedule-index") + '">' + $(this).parent().attr("label") + '</span>');
									});
									$pop.find("dd span.schedule").append(schedule);
									schedule.combobox();
								}
							});
						}
					})
				}
			});
			Mblog.groupid = Env.GROUP_ID;
			Mblog.loadAll();
		</script>
	</body>
</html>
