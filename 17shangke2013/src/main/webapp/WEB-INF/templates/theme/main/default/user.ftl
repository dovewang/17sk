<!DOCTYPE html>
<html>
	<head>
		<#include "meta.ftl" />
		<link href="${IMAGE_DOMAIN}/theme/main/skins/default/css/zone.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
		<link href="${IMAGE_DOMAIN}/theme/main/default/css/search.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
	</head>
	<body class="zone-page">
		<#include "user/header.ftl" />
		<div class="page-container">
			<div class="page-body">
				<#include "left.ftl"/>
				<div class="page-main">
					<div class="user-head-box">
						<h2 class="name"> ${user.name} </h2>
						<div class="expert">
							擅长科目：
							${user.expert!}
						</div>
						<div class="expert">
							个人简介：
							${user.experience!"暂无信息"}
						</div>
						<div class="user-button-group">
							<#assign focus=(user.memo=="1"||user.memo=="3")/>
							<a href="javascript:;"  node-type="follow" uid="${user.userId}" state="${user.memo}" focus="${focus?string("true","false")}"> <#if !focus>
							+关注
							<#else> <i class="icon icon-follow${user.memo}"></i>取消关注</#if> </a>
							<a href="javascript:;" onclick="MC.showForm(${user.userId},'${user.name}');return false;">私信</a>
						</div>
						<div class="user-stat">
							<dl>
								<dd>
									<span> ${user.answers} </span>解答
								</dd>
								<dd>
									<span> ${user.courses} </span>课程
								</dd>
								<dd>
									<span> ${user.students} </span>学生
								</dd>
								<dd>
									<span> ${user.favor}
										%</span>好评
								</dd>
							</dl>
						</div>
					</div>
					<div class="page-center">
						<#if user.userType==1||user.userType==3>
							<#if result.totalCount!=0>
							<div id="my-account">
							<table class="table">
								<thead>
									<tr>
										<th>辅导科目1</th>
										<th>辅导科目2</th>
										<th>辅导科目3</th>
										<th>辅导价格(${MONEY_UNIT}/小时)</th>
										<th>发布时间</th>
									</tr>
								</thead>
								<tbody id = "tutor-tbody">
									<#list result.result as m>
									<tr node-type="tutor-item" id="tutor-item-${m.demandId}"  intro="${m.intro!""}">
										<td id="kind1" key="${m.kind1}" align="right">${kindHelper.getKindLabel(m.kind1?j_string)}</td>
										<td id="kind2" key="${m.kind2}" align="right">${kindHelper.getKindLabel(m.kind2?j_string)}</td>
										<td id="kind3" key="${m.kind3}" align="right">${kindHelper.getKindLabel(m.kind3?j_string)}</td>
										<td id="price" key="${m.price}">
										<#if m.price==0>
											面议
										<#elseif m.price==1>
											30以下
										<#elseif m.price==2>
											30-60
										<#elseif m.price==3>
											60-100
										<#elseif m.price==4>
											100以上
										</#if>
										</td>
										<td>${flint.timeToDate(m.dateline,"yyyy-MM-dd HH:mm:ss")}</td>
									</tr>
									</#list>
								</tbody>
							</table>
							</div>
							</#if>
						<#elseif user.userType==2||user.userType==4>
							<#if result.totalCount!=0>
							<div id="my-account">
							<table class="table">
								<thead>
									<tr>
										<th>辅导科目1</th>
										<th>辅导科目2</th>
										<th>辅导科目3</th>
										<th>辅导价格(${MONEY_UNIT}/小时)</th>
										<th>发布时间</th>
									</tr>
								</thead>
								<tbody id = "tutor-tbody">
									<#list result.result as m>
									<tr node-type="tutor-item" id="tutor-item-${m.tutorId}" tutorid = "${m.tutorId}" intro="${m.intro!""}">
										<td id="kind1" key="${m.kind1}" align="right">${kindHelper.getKindLabel(m.kind1?j_string)}</td>
										<td id="kind2" key="${m.kind2}" align="right">${kindHelper.getKindLabel(m.kind2?j_string)}</td>
										<td id="kind3" key="${m.kind3}" align="right">${kindHelper.getKindLabel(m.kind3?j_string)}</td>
										<td id="price" key="${m.price}">
										<#if m.price==0>
											面议
										<#else>
											${m.price}
										</#if>
										</td>
										<td>${flint.timeToDate(m.dateline,"yyyy-MM-dd HH:mm:ss")}</td>
									</tr>
									</#list>
								</tbody>
							</table>
							</div>
							</#if>
						</#if>
						<div class="tab-header  mt15" node-type="tabnavigator" mode="hash">
							<ul>
								<li>
									<a href="#!/m/list-${user.userId}.html"  view="#mblog_list"   group="true">动态</a>
								</li>
								<li>
									<a href="#!/search/question.html" remote="false" view="#question_list"   group="true">问题</a>
								</li>
								<li>
									<a href="#!/course.html" remote="false" view="#course_list"  group="true"> 课程</a>
								</li>
							</ul>
						</div>
						<div class="tab-body p10" id="mblog_list"></div>
						<div id="question_list" style="display:none">
							<div class="tab-subheader">
								<ul>
									<li>
										<a href="#!/question-myq.html?u=${user.userId}" remote="true" view="#questionView" page="true">TA的提问 </a>
									</li>
									<li>
										<a href="#!/question-mya.html?u=${user.userId}" remote="true" view="#questionView" page="true">TA的解答 </a>
									</li>
								</ul>
								<span class="float-right"><a href="/search/question.html" target="_blank" class="button primary">我要提问</a></span>
							</div>
							<div class="tab-body p10" id="questionView"></div>
						</div>
						<div  id="course_list"  style="display:none">
							<div class="tab-subheader">
								<ul>
									<li>
										<a href="#!/course-mys.html?u=${user.userId}" remote="true" view="#courseView" page="true">TA参与的课程</a>
									</li>
									<li>
										<a href="#!/course-myc.html?u=${user.userId}" remote="true" view="#courseView" page="true">TA创建的课程</a>
									</li>
									<li>
										<a href="#!/course/fav.html" remote="true" view="#courseView" page="true"> TA收藏的课程</a>
									</li>
								</ul>
								<span class="float-right"><a href="/course/manager/guide.html" target="_blank" class="button primary">创建新课程</a></span>
							</div>
							<div class="tab-body" id="courseView"></div>
						</div>
					</div>
					<!--center end -->
					<div class="page-right">
						<!--我关注的人-->
						<div  class="user-list-box clearfix">
							<h4>TA关注的人(
							${follows.totalCount}
							)</h4>
							<#if follows.totalCount==0>
							TA还没有关注任何人
							<#else>
							<ul>
								<#list follows.result as f>
								<li>
									<a href="/u/${f.userId}.html" target="_blank"><img src="${f.face}"/ alt="${f.name}" width="40" height="40" usercard="${f.userId}"></a><span><a href="/u/${f.userId}.html" target="_blank" usercard="${f.userId}"> ${f.name} </a></span>
								</li>
								</#list>
							</ul>
							</#if>
						</div>
						<!--我的粉丝-->
						<div  class="user-list-box clearfix">
							<h4>TA的粉丝(
							${fans.totalCount}
							)</h4>
							<#if fans.totalCount==0>
							TA还没有粉丝
							<#else>
							<ul>
								<#list fans.result as f>
								<li>
									<a href="/u/${f.userId}.html" target="_blank"><img src="${f.face}"/ alt="${f.name}" width="40" height="40" usercard="${f.userId}"></a><span><a href="/u/${f.userId}.html" target="_blank" usercard="${f.userId}"> ${f.name} </a></span>
								</li>
								</#list>
							</ul>
							</#if>
						</div>
					</div>
					<!--right end -->
				</div>
			</div>
		</div>
		</div>
		<#include "footer.ftl"/>
	</body>
</html>