<div class="user-info1 box">
  <dl class="mt5">
    <dt><img src="" usercard="${user.userId}" height="50" width="50"/> </dt>
    <dd>
      <div> <a href="/u/${user.userId}.html" usercard="${user.userId}">
        ${user.name}
        </a> </div>
      <div> <#assign focus=(user.memo=="1"||user.memo=="3")/>
        <button class="button" node-type="follow" uid="${user.userId}" state="${user.memo}" focus="${focus?string("true","false")}"> <#if !focus>
        +关注
        <#else> <i class="icon icon-follow${user.memo}"></i>取消关注 </#if> </button>
      </div>
    </dd>
  </dl>
  <div class="user-d1"> <span><a  href="#">
    ${user.answers}
    </a><b>解答</b></span> <span><a href="#">
    ${user.courses}
    </a><b>课程</b></span> <span><a href="#">
    ${user.students}
    </a><b>学生</b></span> <span><a href="#">
    ${user.favor}
    </a><b>好评</b></span> </div>
  <div  class="user-list-box clearfix">
    <h3>TA的粉丝(
      ${fans.totalCount}
      )</h3>
    <#if fans.totalCount==0>
    TA还没有粉丝
    <#else>
    <ul>
      <#list fans.result as f>
      <li> <a href="/u/${f.userId}.html" target="_blank"><img src="${f.face}"/ alt="${f.name}" width="40" height="40" usercard="${f.userId}"></a><span><a href="/u/${f.userId}.html" target="_blank" usercard="${f.userId}">
        ${f.name}
        </a></span> </li>
      </#list>
    </ul>
    </#if> </div>
</div>
<div class="box">
  <h3>TA的课程</h3>
  <#list courses as  c>
  <dl class="course-item-thumb" node-type="course-item" id="ce${c.courseId}" cid="${c.courseId}"  ctype="${c.type}" >
    <dt class="course-cover"> 
       <span class="course-type t${c.type}">
      ${enumHelper.getLabel("course_type",c.type?j_string)}
      </span><a href="${SITE_DOMAIN}/course/${c.courseId}-${VERSION}.html"  target="_blank" class="thumbnail" style="width:160px"><img  source="${imageHelper.resolve(c.cover!,"s160,120")}" src="/theme/main/default/images/course_cover.jpg" alt="${c.name}" width="160" height="120"/></a> <#if (c.price>0)> <span class="course-price" style="left:140px">
      ${c.price}
      ${moneyUnit}
      </span> <#else> <span class="course-price free" style="left:140px">免费</span> </#if> </dt>
    <dd class="course-base">
      <h2><a href="${SITE_DOMAIN}/course/${c.courseId}-${VERSION}.html"  target="_blank">
        ${c.name}
        </a></h2>
      发布时间：<span class="gray">
      ${flint.timeToDate(c.publishTime,"yyyy-MM-dd HH:mm")}
      </span> </dd>
  </dl>
  </#list> </div>
