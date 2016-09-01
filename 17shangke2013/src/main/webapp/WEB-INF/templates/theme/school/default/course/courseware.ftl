<!DOCTYPE html>
<html>
	<head>
		<#include "../head.ftl" /> <script src="${IMAGE_DOMAIN}/theme/base/js/bis.doc.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
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
											<div class="stepboxright2"></div>
											<div class="stepbox stepboxbg2">
												<strong>2</strong>
												<br />
												上传课件资料
											</div>
											<div class="stepboxright3"></div>
											<div class="stepbox stepboxbg3">
												<strong>3</strong>
												<br />
												上传 / 编写练习
											</div>
											<div class="stepboxright4"></div>
											<div class="stepbox stepboxbg3">
												<strong>4</strong>
												<br />
												预览课程
											</div>
											<div class="stepboxright4"></div>
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
									<div class="create_step2_up">
										<span>相关课件：</span>
										<a href="/doc/form.html?attr1=${courseId}&cb=Course.loadDocument"  node-type="pop" class="button   float-left" id="uploadoCourseware" ><i>上传课件</i></a>
										<div class="clear"></div>
									</div>
									<!--create_step2_up end-->
									<div class="coursewarebox" id="documentList" style="min-height:200px" courseId="${courseId}"></div>
									<!--coursewarebox end-->
									<div class="but_create_classbox">
											<a href="javascript:void(0)" onclick="Course.step('view',${courseId});return false;" class="button xbtn course-prev"></a>
											<a href="javascript:void(0)" onclick="Course.step('exercise',${courseId});return false;" class="button xbtn course-next"></a>
									</div>
								</div>
							</div>
							<!--rightbox end-->
							<#include "../foot.ftl" />
	</body>
</html>
<script>
Course.loadDocument("${courseId}");

</script>
