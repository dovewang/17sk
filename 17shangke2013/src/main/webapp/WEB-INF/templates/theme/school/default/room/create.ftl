<#assign TITLE=""/>
<#assign CATEGORY="临时教室-"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
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
								<form action="/room/goto.html" method="post" onsubmit="return false;" id="createForm">
									<input name="t" value="0"  type="hidden"/>
									<div class="creeate_classroom">
										<ul>
											<li>
												<span class="s1">主题：</span><span class="s2">
													<input type="text" name="s" id="subject" class="input_defalut"/>
												</span>
											</li>
											<li>
												<span class="s1">密码：</span><span class="s2">
													<input type="password" name="pw" id="password" class="input_defalut" />
												</span>
											</li>
											<li>
												<span class="s1">密码确定：</span><span class="s2">
													<input type="password" name="repassword" id="repassword"  class="input_defalut"/>
												</span>
											</li>
											<li>
												<span class="s3"><button onclick="create();return false;"class="button green small floatLeft">创建</button><a href="/room/goto.html?fast=true&t=0" class="button green floatLeft"><i>快速创建</i></a></span>
											</li>
										</ul>
										<div class="clear"></div>
									</div>
								</form>
								<div class="ez-box">
									<div class="tab-header tabnavigator">
										<ul>
											<li>
												<a href="#!/room/today.html" id="newroom" remote="false" autoload="true" once="true" view="newroomList" group="true">新教室</a>
											</li>
											<li>
												<a href="#!/room/today.html" id="historyroom" remote="false" once="true" view="historyroomList" group="true">历史教室</a>
											</li>
										</ul>
									</div>
									<!--tab_title end-->
									<div  id="newroomList">
										<div class="tab-subheader">
											<ul>
												<li>
													<a href="#!/room/today.html" remote="true" view="newroomView" page="true" id="mycreate">我创建的教室</a>
												</li>
												<li>
													<a href="#!/room/today.html" remote="true" view="newroomView" page="true">邀请我的教室</a>
												</li>
											</ul>
										</div>
										<div class="tab-body" id="newroomView"></div>
									</div>
									<!--tab_con end-->
									<div id="historyroomList" style="display:none">
										<div class="tab-subheader">
											<ul>
												<li>
													<a href="#!/room/today.html" remote="true" view="historyroomView" page="true">我创建的教室</a>
												</li>
												<li>
													<a href="#!/room/today.html" remote="true" view="historyroomView" page="true">邀请我的教室</a>
												</li>
											</ul>
										</div>
										<div class="tab-body" id="historyroomView"></div>
									</div>
								</div>
							</div>
							<!--rightbox end-->
							<#include "../foot.ftl" />
	</body>
</html>
<script>
function create() {
$("#createForm").postForm(function() {
$("#mycreate").trigger("refresh");
$("#createForm input").val("")
});
}
</script>