<div class="user-head-box">
  <h2 class="name">
    ${__USER.name}
  </h2>
  <div class="expert"> 擅长科目：
    ${user.expert!}
    <a href="/user/profile.html"><i class="icon icon-edit"></i></a> </div>
  <div class="trade-notify">
    <dl>
      <dt>
        <h3>交易提醒：</h3>
      </dt>
      <dd> <a href="#">待确认(<span  class="count">
        ${tradeStat.confirms}
        </span>)</a> </dd>
      <dd> <a href="#">待付款(<span class="count">
        ${tradeStat.pays}
        </span>)</a> </dd>
      <dd> <a href="#">待评价(<span  class="count">
        ${tradeStat.comments}
        </span>)</a> </dd>
    </dl>
  </div>
  <div class="action-group">
    <dl>
      <dd> <a href="/course/manager/guide.html">分享I讲台</a> </dd>
      <dd> | </dd>
      <#if  USER_TYPE==2 ||   USER_TYPE==4>
      <dd> <a href="/course/manager/guide.html">发布课程</a> </dd>
      <dd>|</dd>
      <dd><a href="${SITE_DOMAIN}/tutor/post.html">发布辅导服务</a></dd>
      <#else> <!-- 学生 --><!-- 家长 -->
      <dd><a href="${SITE_DOMAIN}/tutor/post.html">发布辅导需求</a></dd>
      </#if>
    </dl>
  </div>
  <div class="user-stat">
    <dl>
      <dd> <span>
        ${user.answers}
        </span>解答 </dd>
      <dd> <span>
        ${user.courses}
        </span>课程 </dd>
      <dd> <span>
        ${user.students}
        </span>学生 </dd>
      <dd> <span>
        ${user.favor}
        %</span>好评 </dd>
    </dl>
  </div>
</div>
