<!DOCTYPE html >
<html>
	<head>
		<#include "../head.ftl" />
	</head>
	<body>
		<div id="admin_wrap">
			<div id="admin_conframe">
				<#include "top.ftl"/>
				<!--admin_header end-->
				<div id="admin_content">
					<div id="admin_leftbox">
						<div class="side_nav" node-type="tabnavigator">
							<ul>
								<li>
									<a href="#!/admin/class/list.html?type=0" view="classView" id="classManager" remote="true" page="true" autoload="true">班级管理</a>
								</li>
							</ul>
							<div class="clear"></div>
						</div>
						<!--side_nav end-->
					</div>
					<!--admin_leftbox end-->
					<div id="admin_rightbox">
						<div class="personnelbox" style="min-height:300px" id="classView"></div>
					</div>
					<!--admin_rightbox end-->
					<div class="clear"></div>
				</div>
				<!--admin_content end-->
				<div id="admin_conframe_bot"></div>
			</div>
			<!--admin_conframe end-->
			<#include "footer.ftl" /> <!--footer end-->
		</div>
	</body>
</html>
