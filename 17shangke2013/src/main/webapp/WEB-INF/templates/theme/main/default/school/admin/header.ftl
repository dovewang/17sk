<div class="page-header"  id="global-header">
	<div class="global-nav">
		<div class="global-nav-container">
			<ul class="list">
				<li>
					<a href="/user/index.html" class="name"><@flint.userName/></a>
				</li>
				<li>
					<a href="/user/message.html">消息</a>
				</li>
				<li action-type="myroom">
					<a href="javascript:void(0)"  class="link">我的课室</a>
				</li>
                <li action-type="myschool">
					<a href="/school/admin/index.html"  class="link">我的学校</a>
				</li>
				<li>
					<a href="/user/profile.html">账号</a>
				</li>
				<li>
					<a href="#">帮助</a>
				</li>
				<li>
					<a class="logout" href="${SITE_DOMAIN}/logout.html?url=${SITE_DOMAIN}/login.html">退出</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="header-container">
		<a href="${SITE_DOMAIN}/index.html"><img src="/theme/main/default/images/logo.png" alt="一起上课" width="205" height="69" class="logo"/></a>

		</div>
	</div>
</div>
</div>
<div class="page-nav" id="page-nav">
	<ul>
		<li>
			<a href="${SITE_DOMAIN}/school/admin/index.html">基本信息</a>
		</li>
		<li class="separator"></li>
		<li>
			<a href="${SITE_DOMAIN}/question.html">问作业</a>
		</li>
		<li class="separator"></li>
		<li>
			<a href="${SITE_DOMAIN}/course.html">课程中心</a>
		</li>
		<li class="separator"></li>
		<li>
			<a href="${SITE_DOMAIN}/tutor/index.html">辅导中心</a>
		</li>
	</ul>
</div>
<script type="text/javascript">
	$(function() {
		var path = URL(document.URL).path;
		trace("" + path);
		$("#page-nav  li:not('.separator')").each(function() {
			var href = $(this).children().attr("href");
			if(href == path || document.URL == href) {
				$(this).addClass("active");
			}
		});
	});

</script>