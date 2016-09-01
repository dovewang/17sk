<ul class="user-nav box" id="user-nav">
    <li><a href="/school/admin/profile.html">学校信息</a></li>
    <li><a href="/account/index.html">账户管理</a></li>
    <li><a href="/trade/index.html">交易管理</a></li>
    <li><a href="/tutor/manager.html">辅导管理</a></li>
    <li><a href="/user/fav.html">我的收藏</a></li>
    <li><a href="/user/message.html">我的消息</a></li>
</ul>
 <script type="text/javascript">
	$(function() {
		var path = URL(document.URL).path;
		trace("" + path);
		$("#user-nav  li").each(function() {
			var href = $(this).children().attr("href");
			if(href == path || document.URL == href) {
				$(this).addClass("active");
			}
		});
	});
</script>
<div class="box">
   <h2 class="box-title">您可能感兴趣的人<a href="javascript:void(0)" onclick="$('#member-guess-box').trigger('guess')" class="float-right">换一换</a></h2>
	<div action-type="guess-box" data-size="3" data-type="follow" id="member-guess-box">
							
	</div>
 </div>
