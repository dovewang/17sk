<div class="navbar  navbar-fixed-top">
  <div class="navbar-inner" id="navbar">
    <div class="container">
      <button type="button" class="btn btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      <div class="nav-collapse">
<!--        <ul class="nav">
          <li><a href="/admin/index.html">我的工作台</a></li>
          <li><a href="/admin/class/index.html">班级管理</a></li>
          <li><a href="/admin/user/index.html">人员管理</a> </li>
          <li><a href="/admin/school/timetable.html">作息时间表</a></li>
          <li><a href="/admin/school/index.html">学校系统设置</a></li>
        </ul>-->
        <ul class="nav pull-right">
          <li> <a href="${SITE_DOMAIN}/index.html">返回网站首页</a> </li>
          <li class="divider-vertical"></li>
          <li class="dropdown"> <a href="javascript:;" class="dropdown-toggle" node-type="dropdown">
            ${USER_NAME}
            <b class="caret"></b></a>
            <ul class="dropdown-menu">
              <li> <a href="#"><i class="icon-user"></i>个人资料</a> </li>
              <li> <a href="#"><i class="icon-star"></i>修改密码</a> </li>
              <li class="divider"></li>
              <li> <a href="${SECURE_DOMAIN}/logout.html"><i class="icon-off"></i>退出</a> </li>
            </ul>
          </li>
        </ul>
      </div>
      <!-- /.nav-collapse --> 
    </div>
  </div>
  <!-- /navbar-inner --> 
</div>
