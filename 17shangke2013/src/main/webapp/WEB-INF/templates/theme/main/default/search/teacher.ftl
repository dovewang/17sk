<#assign CATEGORY="老师-"/>
<#assign keywords="教师、优秀教师、在线答疑、辅导服务"/>
<#assign description="优秀的教室7*24为您在线答疑，提供辅导服务；"/>
<!DOCTYPE html>
<html>
<head>
<#include "../meta.ftl" />
<link href="${IMAGE_DOMAIN}/theme/main/default/css/search.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
<#assign keyword=RequestParameters["q"]! />
		<#assign c=RequestParameters["c"]!  />
		</head>
		<body>
<#include "../header.ftl"/>
<div class="clearfix" style="background: #2e4442;">
          <div style="width:985px;height:auto;margin:0 auto;">
    <div node-type="slider" activeIndex="0" class="slider" style="width:985px;height:132px">
              <ul class="slider-child">
        <li><a href="/search/question.html"><img src="/theme/main/default/ad/a1.jpg"  ></a> </li>
        <li><a href="/search/course.html"><img src="/theme/main/default/ad/a2.jpg"  ></a> </li>
        <li><a href="/tutor/post.html"><img src="/theme/main/default/ad/a3.jpg"></a> </li>
      </ul>
            </div>
  </div>
        </div>
<div class="page-container">
          <form action="" id="search-box">
    <dl>
              <dt>
        <ul>
                  <li> <a href="/index.html">一起上课</a> </li>
                  <li> <a href="/search/course.html?q=${keyword}">找课程</a> </li>
                  <li> <a href="/search/question.html?q=${keyword}">问作业</a> </li>
                  <li> <a href="/search/show.html?q=${keyword}">I讲台</a> </li>
                  <li class="active"> <a href="/search/teacher.html?q=${keyword}">找老师</a> </li>
                </ul>
        <div class="clear"></div>
      </dt>
              <dd>
        <input type="text" name="q" xe-webkit-spech value="${keyword}" maxlength="50" style="width:873px;height:37px; line-height:37px; vertical-align:middle;"/>
        <input type="image" src="/theme/main/default/images/search.png"  name="search"  style="position:absolute; right:-2px;top:-2px"/>
      </dd>
            </dl>
  </form>
          <div class="page-body" id="page-main">
    <div class="filter-box box" style="margin-bottom: 0"> 
              <!-- 
					<dl>
						<#assign grade=kindHelper.getGrades()/>
						<dt>
							<@flint.condition condition,"?q="+keyword+"&p=1&c=@",0,"0","所有年级"/>
						</dt>
						<dd>
							<#list grade?keys as key>
							<@flint.condition condition,"?q="+keyword+"&p=1&c=@",0,key,grade[key]/>
							</#list>
						</dd>
					</dl>
					-->
              <dl>
        <dt>
                  <@flint.condition condition,"?q="+keyword+"&p=1&c=@",1,"0","全部学科"/>
                </dt>
        <dd> <#if   condition[0]!="0">
                  <#assign kind=kindHelper.getKind(condition[0])/>
                  <#list kind as k>
                  <@flint.condition condition,"?q="+keyword+"&p=1&c=@",1,""+k.kind,k.name/>
                  </#list>
                  <#else>
                  <#assign kinds=kindHelper.getKinds()/>
                  <#list kinds?keys as key>
                  <@flint.condition condition,"?q="+keyword+"&p=1&c=@",1,key,kinds[key]/>
                  </#list>
                  </#if> </dd>
      </dl>
            </div>
    <div class="order-box">
              <ul>
        <li>
                  <@flint.condition condition,"?q="+keyword+"&p=1&c=@",3,"0:","学生数量<i class=\"sort-desc\">
                  </i>"/> </li>
        <li> | </li>
        <li>
                  <@flint.condition condition,"?q="+keyword+"&p=1&c=@",4,"t","教师在线"/>
                </li>
      </ul>
            </div>
    <div id="user-list" node-type="waterfall" style="position:relative;"> <#--0代表没关注，1代表已关注，2代表被关注，3代表互相关注 --> 
              <#list  result.result as  user >
              <div class="box user-wf-box">
        <div  class="user-face"> <img source="${user.face}" height="180" width="180" src="/files/avatar/default.jpg">
                  <div class="user-button-group"> <#assign focus=(user.memo=="1"||user.memo=="3")/> <a href="javascript:;" node-type="follow" uid="${user.userId}" state="${user.memo}" focus="${focus?string("true","false")}"> <#if !focus>
                    +关注
                    <#else> <i class="icon icon-follow${user.memo}"></i>取消关注</#if> </a> <a href="javascript:;" onclick="MC.showForm(${user.userId},'${user.name}');return false;">私信</a><a href="/u/${user.userId}.html" >详细</a></div>
                  <div class="clear"></div>
                </div>
        <h3 class="user status{user.online}">
                  ${user.name}
                </h3>
        <p> 擅长：
                  ${kindHelper.getKindLabels((user.expert!)?j_string)!"未设置"}
                </p>
        <p> 简介：
                  ${user.experience!"无"}
                </p>
        <div class="user-mark">
                  <ul>
            <li> <a  href="#">
              ${user.answers}
              </a><b>解答</b> </li>
            <li> <a href="#">
              ${user.courses}
              </a><b>课程</b> </li>
            <li> <a href="#">
              ${user.students}
              </a><b>学生</b> </li>
            <li> <a href="#">
              ${user.favor}
              % </a><b>好评</b> </li>
          </ul>
                  <div class="clear"></div>
                </div>
        <div class="totur"> <#if user.cookie??>
                  <p>TA提供的辅导服务：</p>
                  <#assign json=user.cookie?eval />
                  <#list json as s>
                  <p>$
            ${s.price}
            ${MONEY_UNIT}
            /小时 
            <#if s.kind1!=0>
            ${kindHelper.getKindLabel(s.kind1?string)}
            </#if>
            <#if s.kind2!=0>、
            ${kindHelper.getKindLabel(s.kind2?string)}
            </#if>
            <#if s.kind3!=0>、
            ${kindHelper.getKindLabel(s.kind3?string)}
            </#if> </p>
                  </#list>
                  </#if> </div>
      </div>
              </#list> </div>
  </div>
          <#if (result.totalPage>1)> <a href="/search/teacher.html?q=${keyword}&p=2&c=${c}"  url="/search/teacher.html?q=${keyword}&c=${c}" max="${result.totalPage}" class="load-more-button" onclick="return loadMore(this)">加载更多</a> </#if> </div>
<#include "../footer.ftl"/>
</body>
</html>
<script type="text/javascript">
	var handler = $('#user-list  div.user-wf-box');
	var options = {
		container : $("#user-list"),
		offset : 15
	};
	handler.waterfall(options);
	function loadMore(obj) {
		var $this=$(obj)
		var p=($this.attr("p")||1)*1+1,max=$this.attr("max")*1;
		$this.attr("p",p);
		var url=$this.attr("url")+"&p="+p;
		$.get(url, function(html) {
			var users = $(html).find("#user-list").children();
			if (users.length > 0){
				$("#user-list").append(users);
				users.reload();
				handler = $('#user-list  div.user-wf-box');
				handler.waterfall(options);
				$this.attr("href",url);
			}else {
				$this.hide();
			}
			if(p==max){
				$this.hide();
			}
		});
		return false;
	}
</script>