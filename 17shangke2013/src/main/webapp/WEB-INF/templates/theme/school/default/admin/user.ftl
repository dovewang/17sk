<#assign classid=RequestParameters["classid"]!"0" >
<!DOCTYPE html>
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
								<#if (result?size>0)>
									<#list result as c>
									<li>
										<a href="#!/admin/user/list.html?classid=${c.groupId}" <#if (classid=="0"&&c_index==0) || ((classid?number)==c.groupId)>autoload="true"</#if> id="useList2" view="admin_rightbox" remote="true" page="true">${c.name}</a>
									</li>
									</#list>
									<li>
										<a href="#!/admin/user/list.html"  id="useList2" view="admin_rightbox" remote="true" page="true">未分组</a>
									</li>
								<#else>
									<li>
										<a href="#!/admin/user/list.html"  id="useList2" view="admin_rightbox" autoload="true" remote="true" page="true">未分组</a>
									</li>
								</#if>
							</ul>
							<div class="clear"></div>
						</div>
						<!--side_nav end-->
					</div>
					<!--admin_leftbox end-->
                   <div id="admin_rightbox">

                    </div>
                  <!--admin_rightbox end-->
                   <div class="clear"></div>
                 </div>
				<div id="admin_conframe_bot"></div>
			</div>
			<!--admin_conframe end-->
			<#include "footer.ftl" /> <!--footer end-->
		</div>
	</body>
</html>
