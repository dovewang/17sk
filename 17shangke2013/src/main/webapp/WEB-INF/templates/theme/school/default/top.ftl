<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container"><a class="brand" href="/index.html">
      ${SITE_NAME}
      </a>
      <div class="nav-collapse collapse">
         <input type="text"  style="width:250px;" class="search-query">
         <button class="btn btn-primary" rel="/question/ask.html" node-type="pop">提问</button>
        <ul class="nav">
          <li> <a href="${SITE_DOMAIN}/index.html">学校广场</a></li>
          <li><a href="${SITE_DOMAIN}/search/question.html">问题答疑</a></li>
          <li> <a href="${SITE_DOMAIN}/search/course.html">课程</a></li>
          <li> <a href="${SITE_DOMAIN}/search/teacher.html">老师</a></li>
          <li> <a href="${SITE_DOMAIN}/user/message.html" id="rt_message">消息</a></li>
        </ul>
        <ul class="nav pull-right">
          <li><a href="/class/index.html">我的班级</a></li>
          <li class="drop-box right"> <a href="javascript:;" class="dropdown-toggle drop-title" node-type="dropdown">${USER_NAME}<b class="caret"></b></a>
            <ul class="dropdown-menu">
               <li> <a href="/user/profile.html"><i class="icon-user"></i>我的主页</a> </li>
              <li> <a href="/user/profile.html#!/user/password.html"><i class="icon-star"></i>修改密码</a> </li>
              <li class="divider"></li>
              <#if __USER.hasRole("ADMIN")>
              <li><a href="/admin/index.html">后台管理</a></li>
              </#if>
              <li> <a href="${SECURE_DOMAIN}/logout.html"><i class="icon-off"></i>退出</a> </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </div>
</div>
<header>
  <div style="margin-top:45px;"></div>
</header>
