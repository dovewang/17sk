<div class="clear"></div>
<div class="page-footer" id="page-footer">
	<div class="footer-nav" style="position:relative;">
		<a href="http://weibo.com/17shangke" target="_blank">官方微博</a> | <a href="${SITE_DOMAIN}/about.html">关于我们</a> | <a href="${SITE_DOMAIN}/hr.html">诚征英才</a><!-- | <a href="${SITE_DOMAIN}/service.html">客服中心</a>
		<div style="position:absolute;top:5px;right:15px;"><iframe width="63" height="24" frameborder="0" allowtransparency="true" marginwidth="0" marginheight="0" scrolling="no" border="0" src="http://widget.weibo.com/relationship/followbutton.php?language=zh_cn&width=63&height=24&uid=2831603532&style=1&btn=red&dpc=1"></iframe></div>
		-->
	</div>
	<div class="hr"></div>
	<div class="copyright">
		Copyright Reserved 2011-2022© 一起上课www.17shangke.com | <a href="http://www.miitbeian.gov.cn">粤ICP备12072649号-1 </a>
		<br />
		广州摩赛网络科技有限公司
	</div>
</div>
<div>
	<div id="message-wall">
		<a href="javascript:void(0)"  id="message-expand-wall"><span ><em class="arrow"></em></span></a>
		<div id="message-content">
			<ul id="im-user-list"></ul>
			<div id="im-box" class="box popover right" cim="" style="display:none">
				<i class="arrow"><em>◆</em><span>◆</span></i><a href="javascript:;" class="close"  onclick="IM.hide()">×</a>
				<div id="im-header">
					您正在和<span>****</span>聊天
				</div>
				<div id="im-user"></div>
				<div id="im-message"></div>
				<div id="im-toolbar">
					<input type="hidden" value="" id="im_media"/>
					<a href="javascript:;" class="icon-face" onclick="loadFace(this,'im-poster-area')">表情</a><a href="javascript:;" class="icon-image" style="position: relative;overflow: hidden;">插入图片
					<form enctype="multipart/form-data" id="imForm" action="/upload/image.html" method="post">
						<input id="file" type="file" name="filedata" class="file" onchange="IM.upload()">
					</form> </a><a class="enter-room" id="im-enter-room" node-type="pop" setting="{id:'create-im-room',effect:'mac',css:{width:500},onShow : function($pop, options){alert(1);return false;}}" href="/abc/form.html?callback=IM.inviteToRoom">进入教室</a>
					<div style="float:right" id="im_pvm_info">
						您还可以输入<strong style="color:#49C146">100</strong>字
					</div>
				</div>
				<div id="im-poster">
					<textarea rows="4" class="input max" name="content" id="im-poster-area" onkeyup="MC.countWord(this,'im_pvm_info')" onkeydown="return MC.wordCheck(event,this)"></textarea>
				</div>
				<div id="im_media_div"  class="popover"></div>
				<div id="im-control">
					<div id="ImSender">
						<a id="imSenderButton" href="javascript:;" onclick="IM.send()">发送</a>
						<div id="imSenderChoose">
							<a href="javascript:;" id="aImSenderChoose"></a>
							<ul style="display: none" id="im-key">
								<li class="active">
									<a href="javascript:;" im-key="1">按Enter键发送</a>
								</li>
								<li class="line"></li>
								<li>
									<a href="javascript:;" im-key="2">按Ctrl+Enter键发送</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="suggest-bar">
		<a href="javascript:void(0)" onclick="Guide.showSuggest()">给点建议吧</a>
	</div>
	<script type="text/javascript">
		/*同步时间*/
		Date.now(true);
		var _gaq = _gaq || [];
		_gaq.push(['_setAccount', 'UA-34303692-1']);
		_gaq.push(['_setDomainName', '17shangke.com']);
		_gaq.push(['_trackPageview']);

		(function() {
			var ga = document.createElement('script');
			ga.type = 'text/javascript';
			ga.async = true;
			ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
			var s = document.getElementsByTagName('script')[0];
			s.parentNode.insertBefore(ga, s);
		})();
	</script>
