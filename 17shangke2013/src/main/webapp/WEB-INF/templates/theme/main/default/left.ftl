<div class="page-left">
	<img id="left_member_header" src="${user.face}" width="150" height="150" />
	<ul class="zone-nav" id="user-nav">
		<li class="zicon1">
			<a href="/u/${user.userId}.html">TA的主页</a>
		</li>
	</ul>
	<script type="text/javascript">
		$(function() {
			var path = URL(document.URL).path;
			trace("" + path);
			$("#user-nav  li").each(function() {
				var href = $(this).children().attr("href");
				if (href == path || document.URL == href) {
					$(this).addClass("active");
				}
			});
		});

	</script>
</div>