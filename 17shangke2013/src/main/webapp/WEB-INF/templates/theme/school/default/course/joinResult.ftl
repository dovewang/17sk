<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<style type="text/css">
#rightbox .message {
	text-align:center;
	width:300px;
	padding:50px;
}
		</style>
		<#include "../head.ftl" />
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
								<div class="enrolmentbox">
									<div class="enrolmen_pic"><img src="${FILE_DOMAIN}/${result.cover}" width="326" height="190" alt=""/>
									</div>
									<div class="enrolmen_con">
										<h1>${result.name}</h1>
										<div class="course_conTeacher">
											<span>老师： <a href="#" usercard="${result.teacher}">吴海雅</a></span>
											<div class="clear"></div>
										</div>
										<div class="enrolmen_text">
											适合学生：${result.scope}
											<br />
											开课时间：${flint.timeToDate(result.predictStartTime,"yyyy-MM-dd HH:mm")}至${flint.timeToDate(result.predictEndTime,"yyyy-MM-dd HH:mm")}
										</div>
									</div>
									<!--enrolmen_con end-->
									<div class="clear"></div>
								</div>
								<div align="center" style="border-top:1px dashed #CBCBCB;">
									<div class="message">
										<em  class="icon success"></em>
										<strong>报名成功！</strong>
										<br/>
										<br/>
										您可以在<a href="${SITE_DOMAIN}/index.html#!/course-mys.html">我的主页</a>找到您报名的课程
									</div>
								</div>
							</div>
							<!--rightbox end-->
							<#include "../foot.ftl" />
	</body>
</html>
