<div id="admin_header">
	<div id="admin_headercon">
		<div id="admin_logo" style="background-image:url(${site.logo})"></div>
		<div id="admin_logo_ex"></div>
		<div id="admin_sitename">
			学校管理系统
		</div>
		<div id="admin_loginbar">
			<span class="a_quit"><a href="${SECURE_DOMAIN}/logout.html?url=${SECURE_DOMAIN}/login.html">退出</a></span><span><a href="/index.html">返回首页</a></span><span>欢迎<@flint.userName />!</span>
		</div>
		<!--admin_loginbar end-->
		<div id="admin_nav">
			<div class="admin_menu">
				<ul>
					<li>
						<a href="${SITE_DOMAIN}/admin/index.html">系统概况</a>
					</li>
					<li>
						<a href="${SITE_DOMAIN}/admin/group.html">班级管理</a>
					</li>
					<li>
						<a href="${SITE_DOMAIN}/admin/user.html">人员管理</a>
					</li>
					<!--
					<li>
						<a href="${SITE_DOMAIN}/admin/course.html">课程管理</a>
					</li>
					-->
				</ul>
			</div>
		</div>
		<!--admin_nav end-->
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$("#admin_nav a").each(function() {
			var $link = $(this), $p = $link.parent();
			if (document.URL.toString().startsWith($link.attr("href"))) {
				$p.html($link.text()).addClass("active");
				return false;
			}
		})
	}); 
</script>