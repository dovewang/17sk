<img id="left_member_header" src="${(__USER.getAttribute("face")!("/theme/main/default/images/noface_l.jpg"))}" width="150" height="150" />
<ul class="zone-nav" id="user-nav">
	<li class="zicon1">
		<a href="${SITE_DOMAIN}/user/index.html">我的首页</a>
	</li>
	<li class="zicon2">
		<a href="${SITE_DOMAIN}/user/profile.html">基本资料</a>
	</li>
	<li class="zicon3">
		<a href="${SITE_DOMAIN}/passport/account/index.html">账户管理</a>
	</li>
	<li class="zicon4">
		<a href="${SITE_DOMAIN}/trade/index.html">交易管理</a>
	</li>
	<li class="zicon5">
		<a href="${SITE_DOMAIN}/follow/index.html">我的好友</a>
	</li>
	<li class="zicon6">
		<a href="${SITE_DOMAIN}/user/fav.html">我的收藏</a>
	</li>
	<li class="zicon7">
		<a href="${SITE_DOMAIN}/user/message.html">我的消息</a>
	</li>
	<#if USER_TYPE==1||USER_TYPE==2||USER_TYPE==3||USER_TYPE==4>
		<li class="zicon8"><a href="${SITE_DOMAIN}/tutor/post.html">辅导管理</a></li><!-- 学生，家长 --><!-- 老师，学校 -->
	</#if>
</ul>
<script type="text/javascript">
	$(function() {
		var path = URL(document.URL).path;
		$("#user-nav  li").each(function() {
			var href = $(this).children().attr("href");
			if(href == path || document.URL.split("?")[0] == href){
				$(this).addClass("active");
			}
		});
	});
</script>