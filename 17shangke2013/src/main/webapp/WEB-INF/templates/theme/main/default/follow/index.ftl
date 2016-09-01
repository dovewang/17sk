<!DOCTYPE html>
<html>
	<head>
		<#include "../meta.ftl" />
		<link href="${IMAGE_DOMAIN}/theme/main/skins/default/css/zone.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
	</head>
	<body class="zone-page">
		<#include "../user/header.ftl" />
		<div class="page-container">
			<div class="page-body">
				<div class="page-left">
					<#include "../user/left.ftl" />
				</div>
				<div class="page-main">
					<div class="m10 box">
						<div class="tab-header" node-type="tabnavigator" mode="hash">
							<ul>
								<li>
									<a href="#!/follow/followers.html?id=${__USER.id}" remote="true" autoload="true" view="#followView">我关注的</a>
								</li>
								<li>
									<a href="#!/follow/fans.html?id=${__USER.id}" remote="true"  view="#fansView">关注我的</a>
								</li>
								<li>
									<a href="#!/follow/fans.html?id=${__USER.id}&a=1" remote="true"  view="#fansView">绑定账号</a>
								</li>
								<!--
								<li>
								<a href="#!/follow/recommend.html" remote="false"  view="recommendView">人气推荐</a>
								</li>

								<li>
								<a href="#!/follow/guess.html?id=${__USER.id}" remote="true"  view="guessView">猜您感兴趣的</a>
								</li>
								-->
							</ul>
						</div>
						<div class="tab-body" id="followView" ></div>
						<div class="tab-body" id="fansView" ></div>
						<div class="tab-body" id="recommendView" ></div>
						<div class="tab-body" id="guessView"></div>
					</div>
				</div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>