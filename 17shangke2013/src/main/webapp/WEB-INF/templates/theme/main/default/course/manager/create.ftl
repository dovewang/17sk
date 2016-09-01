<!DOCTYPE html>
<html>
	<head>
		<#include "../../meta.ftl" />
		<script src="${IMAGE_DOMAIN}/theme/base/js/city-min.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<script src="${IMAGE_DOMAIN}/theme/main/js/bis.course.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/base/js/jquery.Jcrop.js?${VERSION}"></script>
		<link href="${IMAGE_DOMAIN}/theme/base/css/jquery.Jcrop.css" type="text/css" rel="stylesheet" media="screen">
	</head>
	<#assign school_id=site.id/>
	<#if USER_TYPE==4>
	<#assign school_id=__USER.getAttribute("school")/>
	</#if>
	<body>
		<#include "../../header.ftl" />
		<div class="page-container">
			<div class="page-body course-manager-layout box">
				<h1 class="title clearfix">
				<div class="step float-right">
					<#if  type==1>
					<ul>
						<li class="first active">
							<span><font> 1. </font>填写基本信息</span><i></i>
						</li>
						<li>
							<span><font> 2. </font>上传课件资料</span><i></i>
						</li>
						<li>
							<span><font> 3. </font>上传 / 编写练习</span><i></i>
						</li>
						<li>
							<span><font> 4. </font>预览确认</span><i></i>
						</li>
						<li class="last">
							<span><font> 5. </font>发布课程</span><i></i>
						</li>
					</ul>
					<#else>
					<ul>
						<li class="first active">
							<span><font> 1. </font>填写基本信息</span><i></i>
						</li>
						<li>
							<span><font> 2. </font>预览确认</span><i></i>
						</li>
						<li class="last">
							<span><font> 3. </font>发布课程</span><i></i>
						</li>
					</ul>
					</#if>
				</div> 填写基本信息</h1>
				<div class="clear"></div>
				<div class="page-main">
					<div class="page-right">
						<#include "item${type}.ftl"/>
					</div>
					<div class="clear"></div>
				</div>
			</div>
		</div>
		<#include "../../footer.ftl"/>
	</body>
</html>