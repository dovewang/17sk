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
								<a href="#!/trade/list.html?t=0" remote="true" autoload="true" view="#allView">全部订单</a>
							</li>
							<li>
								<a href="#!/trade/list.html?c=true" remote="false"  view="#courseTab" group="true">课程订单</a>
							</li>
							<li>
								<a href="#!/trade/list.html?t=1" remote="true"  view="#questionView">问题订单</a>
							</li>
                             <li>
								<a href="#!/trade/list.html?t=4" remote="true"  view="#tempView">临时辅导订单</a>
							</li>
						</ul>
					</div>
					<div class="tab-body" id="allView" ></div>
					<div  id="courseTab" style="display:none">
							<div class="tab-subheader">
								<ul>
									<!--
                                    <li>
										<a href="#!/trade/list.html?t=3" remote="true" view="courseView" autoload="true">全部</a>
									</li>
									-->
									<li>
										<a href="#!/trade/list.html?t=3" remote="true" view="#courseView" autoload="true">本地课程</a>
									</li>
									<li>
										<a href="#!/trade/list.html?t=2" remote="true" view="#courseView"  >在线课程</a>
									</li>
								</ul>
							</div>
							<div class="tab-body p10" id="courseView"></div>
					</div>
                    <div class="tab-body" id="questionView" ></div>
                    <div class="tab-body" id="tempView" ></div>
				</div>
              </div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>