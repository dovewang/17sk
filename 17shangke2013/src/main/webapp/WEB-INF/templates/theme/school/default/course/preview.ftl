<!DOCTYPE html>
<html>
	<head>
		<#include "../head.ftl" />
		<script src="${IMAGE_DOMAIN+"/"+THEME_PATH}/js/course.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
	</head>
	<body>
		<div id="wrap">
			<div id="wrapbottom">
				<div id="wrapcon">
					<div id="conframe">
						<#include "../top.ftl" /> <!--header end-->
						<div id="content">
							<#include "../left.ftl" /> <!--leftbox end-->
							<div id="rightbox">
								<!--noticebox end-->
								<div class="right_funcitonbox">
									<div class="right_funcitoncon">
										<div class="create_classtitle"></div>
										<div class="create_class_step">
											<div class="stepbox stepboxbg1">
												<strong>1</strong>
												<br />
												填写基本信息
											</div>
											<div class="stepboxright6"></div>
											<div class="stepbox stepboxbg1">
												<strong>2</strong>
												<br />
												上传课件资料
											</div>
											<div class="stepboxright6"></div>
											<div class="stepbox stepboxbg1">
												<strong>3</strong>
												<br />
												上传 / 编写练习
											</div>
											<div class="stepboxright2"></div>
											<div class="stepbox stepboxbg2">
												<strong>4</strong>
												<br />
												预览课程
											</div>
											<div class="stepboxright3"></div>
											<div class="stepbox stepboxbg3">
												<strong>5</strong>
												<br />
												发布课程
											</div>
											<div class="stepboxright5"></div>
											<div class="clear"></div>
										</div>
										<!--create_class_step end-->
									</div>
									<div class="right_funcitonbottom"></div>
								</div>
								<!--right_funcitonbox end-->
								<div class="create_classbox">
									<div class="enrolmentbox">
										<div class="enrolmen_pic"><img src="${course.cover!}" width="300" height="255" alt=""/>
										</div>
										<div class="enrolmen_con">
											<h1>${course.name}</h1>
											<div class="course_conTeacher">
												<span>老师：${USER_NAME}</span>
												<div class="clear"></div>
											</div>
											<div class="enrolmen_text">
												适用范围：
												${kindHelper.getGradeLabel(course.scope)}
												<br/>
												课程目的：
												${course.aim}
												<#if course.type==1>
												<br />
												开课时间：${flint.timeToDate(course.startTime,"yyyy-MM-dd HH:mm")}
												<br/>
												时长: ${course.time/60}分钟
												</strong> <#elseif course.type==2>

												<#elseif course.type==3>
												<br />
												报名时段：
												${course.openTime}
												<br />
												开课时段：
												${course.applyTime}
												<br/>
												上课地址：
												${course.memo!}
												</#if>
											</div>
										</div>
										<!--enrolmen_con end-->
										<div class="clear"></div>
									</div>
									<!--enrolmentbox end-->
									<div class="create_step_title">
										<strong>课程简介：</strong>
										<br />
										${course.intro}
									</div>
									<div class="but_create_classbox">
										<a href="javascript:void(0)" onclick="Course.step('exercise',${course.courseId});return false;" class="button xbtn course-prev"></a>
										<a href="javascript:void(0)" onclick="Course.step('publish',${course.courseId});return false;" class="button xbtn course-publish"></a>
									</div>
									<!--but_create_classbox end-->
								</div>
							</div>
							<!--rightbox end-->
							<#include "../foot.ftl" />
	</body>
</html>
