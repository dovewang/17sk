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
											<div class="stepboxright6"></div>
											<div class="stepbox stepboxbg1">
												<strong>4</strong>
												<br />
												预览课程
											</div>
											<div class="stepboxright2"></div>
											<div class="stepbox stepboxbg2">
												<strong>5</strong>
												<br />
												发布课程
											</div>
											<div class="stepboxright7"></div>
											<div class="clear"></div>
										</div>
										<!--create_class_step end-->
									</div>
									<div class="right_funcitonbottom"></div>
								</div>
								<!--right_funcitonbox end-->
								<form action="" method="get">
									<div class="create_classbox5">
										<strong>成功发布课程！</strong>
										<br />
										你可以在<a href="/index.html#!/course-myc.html">我创建的课程</a>中找到你所创建的课程进行重新编辑
										<br />
											<a href="/course/manager/guide.html" class="button xbtn course-create"></a>
									</div>
								</form>
							</div>
							<!--rightbox end-->
							<#include "../foot.ftl" />
	</body>
</html>
