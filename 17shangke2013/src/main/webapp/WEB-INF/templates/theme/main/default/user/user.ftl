<!DOCTYPE html>
<html>
	<head>
		<#include "../meta.ftl" />
		<link href="${IMAGE_DOMAIN}/theme/base/css/jquery.Jcrop.css" type="text/css" rel="stylesheet" media="screen">
        <link href="${IMAGE_DOMAIN}/theme/main/skins/default/css/zone.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/> 
	</head>
	<body class="zone-page">
		<#include "header.ftl" />
		<div class="page-container">
			<div class="page-body">
				<div class="page-left">
					<#include "left.ftl" />
				</div>
				<div class="page-main">
					<!--
                <div class="box m15 text-left">
                    <dl>
                      <dd>您的登录邮箱：</dd>
                      <dd>手机：</dd>
                      <dd>上次登录：******(不是您登录的？请点这里)</dd>
                      <dd>您的认证：实名认证，手机认证，邮箱认证，学位认证，教师资格认证</dd>
                      <dd>信誉担保：实名认证，手机认证，邮箱认证，学位认证，教师资格认证</dd>
                    </dl>
                </div>
               -->
				<div class="m10 box">
					<div class="tab-header" node-type="tabnavigator">
						<ul>
							<li>
								<a href="#!/user/base.html" remote="false" autoload="true" view="#baseView">基本信息</a>
							</li>
							<li>
								<a href="#!/user/avatar.html" remote="false"  view="#avatarView">我的头像</a>
							</li>
							<li>
								<a href="#!/user/detail.html" remote="false"  view="#detailView">详细信息</a>
							</li>
							<li>
								<a href="#!/user/password.html" remote="false"  view="#passwordView">修改密码</a>
							</li>
						</ul>
					</div>
					<!--tab_title end-->
					<div class="tab-body" id="baseView" >
						<#include "user.base.ftl"/>
					</div>
					<div class="tab-body" id="avatarView" style="height:380px;">
						<#include "user.avatar.ftl"/>
					</div>
					<div class="tab-body" id="detailView">
						<#include "user.detail.ftl"/>
					</div>
					<div class="tab-body" id="passwordView">
						<#include "user.password.ftl"/>
					</div>
				</div>
				</div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>
<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/base/js/city-min.js?${VERSION}"></script>
<script src="${IMAGE_DOMAIN}/theme/base/js/bis.user.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/base/js/jquery.Jcrop.js?${VERSION}"></script>
<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/base/js/flint.avatar.js?${VERSION}"></script>