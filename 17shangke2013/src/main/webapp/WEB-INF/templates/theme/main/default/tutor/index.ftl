<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<#include "../meta.ftl" /> <script src="${IMAGE_DOMAIN}/theme/base/js/city-min.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/main/js/bis.tutor.js?${VERSION}"></script>
	</head>
	<body>
		<#include "../header.ftl" />
		<div class="page-container">
			<div  class="mt10 mb10" style="position:relative"><img src="/theme/main/default/ad/t1.jpg" />
			<a class="button primary large" id="tutor_post_service" href="/tutor/service.form.html"  action-type="popup" style="position:absolute;right:220px;bottom:10px;">免费发布辅导服务信息</a>
			</div>
			<div  class="mt10 mb10" style="position:relative"><img src="/theme/main/default/ad/t2.jpg" />
			<a class="button primary large" id="tutor_post_demand" href="/tutor/demand.form.html"  action-type="popup"  style="position:absolute;right:50px;bottom:10px;">免费发布辅导需求信息</a>
			</div>
			<div class="page-body">
				<h1 class="title">导师检索</h1>
				<div class="page-left">
					<#include "../search/tutor.ftl"/>
				</div>
				<div class="page-right">
                  <div class="box">
						   <h2 class="box-title">在线教师<a href="/search/teacher.html?q=&p=1&c=0,0,0,0,t,0"  class="float-right">更多&gt;</a></h2>
	                       <div node-type="online-teacher" class="online-user-box"></div>
					</div>
                </div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>