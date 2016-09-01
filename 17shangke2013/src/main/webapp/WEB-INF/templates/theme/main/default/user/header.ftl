<div class="page-header"  id="global-header">
	<div class="global-nav" id="global-nav">
		<div class="global-nav-container" id="global-nav-bar" style="position:relative">
			<span id="logo"><a href="/index.html"><img src="/theme/main/skins/default/images/logo.png"  alt="一起上课"></a><span style="color:#999; font-size:9px;position:absolute;top:0;left:70px;">Beta</span></span>
			<ul class="menu-header">
				<!--<li> <a  href="/user/invite.html">邀请好友</a> </li>-->
				<li>
					<a  href="javascript:;" title="加入收藏" onclick="addFavorite('一起上课','http://17shangke.com')">加入收藏</a>
				</li>
				<li>
					<a  href="javascript:;" title="设为主页" onclick="setHome('http://17shangke.com')">设为主页</a>
				</li>
				<li>
					<a href="http://weibo.com/17shangke" target="_blank" title="关注我们">关注我们</a>
				</li>
			</ul>
			<ul class="list">
				<li>
					<a href="/index.html" title="一起上课">一起上课首页</a>
				</li>
				<#if isLogin>
				<li class="username">
					<a href="/user/index.html" class="name"  title="个人中心"> ${USER_NAME} (<span id="spanRole">${ROLE_NAME}</span>)</a>
				</li>
				<#if IS_ADMIN>
				<li>
					<a href="/manager/index.html"  title="后台管理" >后台管理</a>
				</li>
				</#if>
				<li>
					<a href="/abc/form.html" node-type="pop" setting="{id:'create-room-pop',effect:'mac',css:{width:500}}" title="创建课室" id="createRoom-pop">创建课室</a>
				</li>
				<li id="rt_message">
					<a href="/user/message.html" title="消息">消息</a>
				</li>
				<li>
					<a class="logout" href="${SECURE_DOMAIN}/logout.html?url=${SECURE_DOMAIN}/login.html" title="退出">退出</a>
				</li>
				<#if MUSER?size!=0>
				<li id="user_muser_li">
					<a node-type="top-muserdrop" href="javascript:;" class="link">切换账号</a>
					<#list MUSER as m>
					<i muser = "{'userid':'${m.userId}','name':'${m.name}','status':'${m.status}','userType':'${m.userType}'}"></i>
					</#list>
				</li>
				</#if>
				<#else>
				<li class="username">
					<a href="/user/index.html" class="name">未登录用户(<span id="spanRole"> ${ROLE_NAME} </span>)</a><a node-type="top-username" href="javascript:;" class="link">切换身份</a>
				</li>
				<li>
					<a href="/help/index.html" title="帮助">帮助</a>
				</li>
				<li>
					<a href="${SECURE_DOMAIN}/login.html?url=${CURRENT_URL}" title="登录">登录</a>
				</li>
				<li id="signup">
					<a href="${SECURE_DOMAIN}/register.html" title="注册">注册</a>
				</li>
				</#if>
			</ul>
		</div>
	</div>
</div>