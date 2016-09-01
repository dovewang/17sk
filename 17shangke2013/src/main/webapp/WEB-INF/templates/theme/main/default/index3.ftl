<!DOCTYPE html>
<html>
	<head>
		<#include "meta.ftl" />
		<style type="text/css">
			/*列表导航样式*/
			.nav-list {
				clear: both;
				margin: 10px;
				padding: 2px;
				background-color: #fff7eb;
				border: 1px solid #ffd698;
			}
			.nav-list.hot {
				background: url(/theme/main/default/images/hot.png) no-repeat right top;
			}
			.nav-list dl {
				float: left;
				display: block;
				margin: 8px 0;
				text-indent: 20px;
				font-size: 13px;
				width: 33%;
			}
			.nav-list dt {
				color: #f60;
				text-align: left;
				font-size: 14px;
			}
			.nav-list dd {
				float: left;
				display: block;
				padding-left: 5px;
				margin: 0;
				text-align: left;
				text-indent: 15px;
				width: 95px;
				overflow: hidden;
				height: 24px;
				line-height: 24px;
				text-overflow: ellipsis;
			}
			.nav-list dd a {
				color: #444;
			}
			.nav-list dd a:hover {
				color: #444;
			}
			.nav-list .center {
				border-right: 1px solid #ffd698;
				border-left: 1px solid #ffd698;
			}
			/*图片列表*/
			.big-image {
				float: left;
				margin: 10px;
				padding: 5px;
				width: 300px;
				text-align: left;
			}
			.small-image ul {
				display: none
			}
			.small-image li {
				float: left;
				padding: 5px 10px 0;
			}
			.small-image li a, .small-image li span {
				display: block;
			}
			.small-image li span {
				width:54px;
				overflow: hidden;
				text-overflow: ellipsis;
			}
			.small-image li a img {
				border: 2px solid #FFFFFF;
			}
			.small-image li a img:hover, .small-image .auto img {
				border: 2px solid #ff9900;
			}
			.small-image li a:hover, .small-image .auto a {
				color: #000000;
			}
			.small-image .current-list {
				display: block;
			}

			.user-t1 dt, .user-t1 dd {
				float: left;
			}
			.user-t1 dt {
				width: 125px;
			}
			.user-t1 dd {
				width: 160px;
				padding: 5px;
			}
			.user-t1 span{ color:gray;}
			.user-t2 {
				background: #fff7eb;
				padding: 5px;
			}
			.user-t2 span {
				display: inline-block;
				width: 22%;
				text-align: center
			}
			.user-t2 span a, .user-t2 span b {
				display: block;
			}
			.user-t2 span a {
				color: #f90;
				font-weight: bold;
			}
			div.course-nav-title {
				text-align: left;
				background: url(/theme/main/default/images/c-title.png) no-repeat left bottom;
				position: relative;
				height: 50px;
				border-bottom: 2px solid #f90;
			}
			div.course-nav-title div.text-line {
				position: absolute;
				left: 25px;
				bottom: 5px;
			}
			div.course-nav-title h2 {
				font-size: 16px;
				color: #000;
				display: inline;
				padding-right: 35px;
			}
			div.course-nav-title a {
				padding: 0 10px;
			}
			.course-list {
				margin: 5px 0;
			}
			.course-list .course-item-thumb {
				margin: 3px;
			}
			.course-list .course-cover {
				width: 155px;
			}
			.course-list .thumbnail {
				width: 148px;
				padding: 2px;
			}
			.course-list .course-base h2 {
				width: 148px;
				font-size: 13px;
				font-weight: normal;
			}
			div.local-title {
				text-align: left;
				border-bottom: 2px solid #f90;
				padding: 8px;
			}
			div.local-title h2 {
				font-size: 16px;
				color: #000;
				display: inline;
			}
		</style>
	</head>
	<body>
		<#include "header.ftl" />
		<div class="clearfix" style="background: #2e4442;">
			<div style="width:985px;height:auto;margin:0 auto;">
				<div node-type="slider" activeIndex="0" class="slider" style="width:985px;height:132px">
					<ul class="slider-child">
							<li><a href="/search/question.html"><img src="/theme/main/default/ad/a1.jpg"  ></a>
						</li>
						<li><a href="/search/course.html"><img src="/theme/main/default/ad/a2.jpg"  ></a>
						</li>
						<li><a href="/tutor/post.html"><img src="/theme/main/default/ad/a3.jpg"></a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="page-container">
			<form   id="search-box" action="/search/course.html">
				<dl>
					<dt>
						<ul>
							<li class="active">
								<a href="/index.html">一起上课</a>
							</li>
							<li>
								<a href="/search/course.html">找课程</a>
							</li>
							<li>
								<a href="/search/question.html">问作业</a>
							</li>
							<li>
								<a href="/search/show.html">I讲台</a>
							</li>
							<li>
								<a href="/search/teacher.html">找老师</a>
							</li>
						</ul>
						<div class="clear"></div>
					</dt>
					<dd>
						<input type="text" name="q"  xe-webkit-spech value=""  style="width:873px;height:37px; line-height:37px; vertical-align:middle;"/>
						<input type="image" src="/theme/main/default/images/search.png"  name="search"  style="position:absolute; right:-2px;top:-2px"/>
					</dd>
				</dl>
			</form>
			<div class="page-body">
				<div class="page-left" style="width:650px;">
					<!--名气即推荐教师-->
					<div style="text-align: left;font-size:16px;">
						<h2>人气名师</h2>
					</div>
					<div id="teacher">
						<div  class="big-image" id="teacher-big">
							<#list hotteacher.result as t>
							<#if t_index=0>
							<div class="detail" >
								<#else>
								<div class="detail" style="display: none">
									</#if>
									<dl class="user-t1">
										<dt><img src="${t.face}" width="125" height="125" border="2">
										</dt>
										<dd>
											<div>
												${t.name}
											</div>
											<div>
												<span>擅长：</span>${kindHelper.getKindLabels((t.expert!)?j_string)!"未设置"}
											</div>
											<div style="height:60px; overflow:hidden;">
												<span>简介：</span>${t.experience!"无"}
											</div>
											<div id="user-t3">
												<#assign focus=(t.memo=="1"||t.memo=="3")/>
												<button class="button follow-btn" node-type="follow" uid="${t.userId}" focus="${focus?string("true","false")}">
													<#if !focus>
													+关注
													<#else>
													<span class="follow status${t.memo}"><i></i>取消关注</span>
													</#if>
												</button>
											</div>
										</dd>
									</dl>
									<div class="clear"></div>
									<div class="user-t2">
										<span><a href="#">${t.answers} </a><b>解答</b></span>
										<span><a href="#">${t.courses} </a><b>课程</b></span>
										<span><a href="#">${t.students} </a><b>学生</b></span>
										<span><a href="#">${t.favor}</a><b>好评</b></span>
									</div>
								</div>
								</#list>
							</div>
							<div  class="small-image">
								<ul class="current-list">
									<#list hotteacher.result as t>
									<li>
										<a href="/u/${t.userId}.html" target="_blank"><img width="50" src="${t.face}" usercard="${t.userId}" height="50"> <span class="user">${t.name}</span></a>
									</li>
									<#if (t_index!=0)&&(((t_index+1) % 8)==0)&&((t_index+1)!=(hotteacher.result?size))>
								    </ul>
								    <ul>
									</#if>
									</#list>
								</ul>
							</div>
						</div>
						<div class="clear"></div>
						<!-- 名师导航-->
						<div id="teacher-nav" class="nav-list clearfix hot">
							<dl>
								<dt>
									<h2>语文名师</h2>
								</dt>
								<dd>
									<a href="/search/teacher.html?q=古文&c=0,10,0,0,0,0">古文名师</a>
								</dd>
								<dd>
									<a href="/search/teacher.html?c=0,12,0,0,0,0">写作名师</a>
								</dd>
								<dd>
									<a href="/search/teacher.html?q=阅读">阅读名师</a>
								</dd>
								<dd>
									<ahref="/search/teacher.html?q=文学常识">文学常识老师</a>
								</dd>
								<dd>
									<a href="/search/teacher.html?q=高考作文">高考作文专家</a>
								</dd>
							</dl>
							<dl class="center">
								<dt>
									<h2>数学名师</h2>
								</dt>
								<dd>
									<a href="/search/teacher.html?q=奥数">奥数名师</a>
								</dd>
								<dd>
									<a href="/search/teacher.html?q=应用题">应用题名师</a>
								</dd>
								<dd>
									<a href="/search/teacher.html?q=数列">数列导师</a>
								</dd>
								<dd>
									<a href="/search/teacher.html?q=几何">几何名师</a>
								</dd>
								<dd>
									<a href="/search/teacher.html?q=三角函数">三角函数</a>
								</dd>
							</dl>

							<dl>
								<dt>
									<h2>英语名师</h2>
								</dt>
								<dd>
									<a href="/search/teacher.html?q=听力">听力专项名师</a>
								</dd>
								<dd>
									<a href="/search/teacher.html?q=写作">英语写作</a>
								</dd>
								<dd>
									<a href="/search/teacher.html?q=口语">口语名师</a>
								</dd>
								<dd>
									<a href="/search/teacher.html?q=托福">托福专家</a>
								</dd>
								<dd>
									<a href="/search/teacher.html?q=CRE">GRE突破</a>
								</dd>
								<dd>
									<a href="/search/teacher.html?q=GMAT">GMAT名师</a>
								</dd>
							</dl>
						</div>
						<!--小学课程-->
						<div class="clear"></div>
						<div class="course-nav-title">
							<div class="text-line">
								<h2>小学课程</h2>
								<a href="/search/course.html?q=小学&tab=online">线上课程</a>|<a href="/search/course.html?q=小学&tab=local" >线下课程</a>|<a href="/search/course.html?q=小学&tab=video" >视频课程</a> |<a href="/search/course.html?c=6,9990,-1,0,0,0,0,0,0" ><strong>小升初</strong></a>
							</div>
						</div>
						<div class="course-list">
							<#list coursemap["course1"] as c>
							<dl class="course-item-thumb" node-type="course-item" id="ce${c.courseId}" cid="${c.courseId}" ctype="4">
								<dt class="course-cover">
									<span class="course-type t${c.type}">${enumHelper.getLabel("course_type",c.type?j_string)}</span><a href="${SITE_DOMAIN}/course/${c.courseId}-${VERSION}.html" target="_blank" class="thumbnail"><img source="${imageHelper.resolve(c.cover!,"s120,90")}" src="/theme/main/default/images/course_cover.jpg" alt="${c.name}" width="148" height="111" style="display: block; "></a><span class="course-price free">免费</span>
								</dt>
								<dd class="course-base">
									<h2><a href="${SITE_DOMAIN}/course/${c.courseId}-${VERSION}.html" target="_blank">${c.name} </a></h2>
									发布人： <a href="/u/${c.userId}.html" target="_blank" usercard="${c.userId}" class="user status0">${c.userId}</a>
									<br>
									<span class="gray">${flint.timeToDate(c.publishTime,"yyyy-MM-dd HH:mm")}</span>
								</dd>
							</dl>
							</#list>
						</div>
						<!--初中课程-->
						<div class="clear"></div>
						<div class="course-nav-title">
							<div class="text-line">
								<h2>初中课程</h2>
								<a href="/search/course.html?tab=online&q=初中">线上课程</a>|<a href="/search/course.html?q=初中&tab=local" >线下课程</a>|<a href="/search/course.html?q=初中&tab=video" >视频课程</a>|<a href="/search/course.html?c=9,9990,-1,0,0,0,0,0,0" ><strong>中考</strong></a>
							</div>
						</div>
						<div class="course-list">
							<#list coursemap["course2"] as c>
							<dl class="course-item-thumb" node-type="course-item" id="ce${c.courseId}" cid="${c.courseId}" ctype="4">
								<dt class="course-cover">
									<span class="course-type t${c.type}">${enumHelper.getLabel("course_type",c.type?j_string)}</span><a href="${SITE_DOMAIN}/course/${c.courseId}-${VERSION}.html" target="_blank" class="thumbnail"><img source="${imageHelper.resolve(c.cover!,"s120,90")}" src="/theme/main/default/images/course_cover.jpg" alt="${c.name}" width="148" height="111" style="display: block; "></a><span class="course-price free">免费</span>
								</dt>
								<dd class="course-base">
									<h2><a href="${SITE_DOMAIN}/course/${c.courseId}-${VERSION}.html" target="_blank">${c.name} </a></h2>
									发布人： <a href="/u/${c.userId}.html" target="_blank" usercard="${c.userId}" class="user status0">${c.userId}</a>
									<br>
									<span class="gray">${flint.timeToDate(c.publishTime,"yyyy-MM-dd HH:mm")}</span>
								</dd>
							</dl>
							</#list>
						</div>
						<!--高中课程-->
						<div class="clear"></div>
						<div class="course-nav-title">
							<div class="text-line">
								<h2>高中课程</h2>
								<a href="/search/course.html?tab=online&q=高中">线上课程</a>|<a href="/search/course.html?q=高中&tab=local" >线下课程</a>|<a href="/search/course.html?q=高中&tab=video" >视频课程</a>|<a href="/search/course.html?c=12,9990,-1,0,0,0,0,0,0" ><strong>高考</strong></a>
							</div>
						</div>
						<div class="course-list">
							<#list coursemap["course3"] as c>
							<dl class="course-item-thumb" node-type="course-item" id="ce${c.courseId}" cid="${c.courseId}" ctype="4">
								<dt class="course-cover">
									<span class="course-type t${c.type}">${enumHelper.getLabel("course_type",c.type?j_string)}</span><a href="${SITE_DOMAIN}/course/${c.courseId}-${VERSION}.html" target="_blank" class="thumbnail"><img source="${imageHelper.resolve(c.cover!,"s120,90")}" src="/theme/main/default/images/course_cover.jpg" alt="${c.name}" width="148" height="111" style="display: block; "></a><span class="course-price free">免费</span>
								</dt>
								<dd class="course-base">
									<h2><a href="${SITE_DOMAIN}/course/${c.courseId}-${VERSION}.html" target="_blank">${c.name} </a></h2>
									发布人： <a href="/u/${c.userId}.html" target="_blank" usercard="${c.userId}" class="user status0">${c.userId}</a>
									<br>
									<span class="gray">${flint.timeToDate(c.publishTime,"yyyy-MM-dd HH:mm")}</span>
								</dd>
							</dl>
							</#list>
						</div>
						<div class="clear"></div>
						<!--
						<div id="teacher-nav" class="nav-list clearfix hot">
							<dl>
								<dt>
									<h2>语文名师</h2>
								</dt>
								<dd>
									<a href="#">古文名师</a>
								</dd>
								<dd>
									<a href="#">写作名师</a>
								</dd>
								<dd>
									<a href="#">阅读名师</a>
								</dd>
								<dd>
									<a href="#">文学常识老师</a>
								</dd>
								<dd>
									<a href="#">高考写作专家</a>
								</dd>
							</dl>
							<dl class="center">
								<dt>
									<h2>数学名师</h2>
								</dt>
								<dd>
									<a href="#">奥数名师</a>
								</dd>
								<dd>
									<a href="#">应用题名师</a>
								</dd>
								<dd>
									<a href="#">微积分名师</a>
								</dd>
								<dd>
									<a href="#">几何名师</a>
								</dd>
								<dd>
									<a href="#">三角函数</a>
								</dd>
							</dl>

							<dl>
								<dt>
									<h2>英语名师</h2>
								</dt>
								<dd>
									<a href="#">听力专项名师</a>
								</dd>
								<dd>
									<a href="#">英语写作</a>
								</dd>
								<dd>
									<a href="#">口语名师</a>
								</dd>
								<dd>
									<a href="#">托福专家</a>
								</dd>
								<dd>
									<a href="#">GRE突破</a>
								</dd>
								<dd>
									<a href="#">GMAT名师</a>
								</dd>
							</dl>
						</div>
						-->
						<!--广州课程-->
						<div class="clear"></div>
						<div>
							<div class="local-title">
								<h2>线下课程&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;<span>广州</span></h2>
							</div>
							<div>
								<#assign citys=regionHelper.getCitys(440100)/><#--目前只取广州-->
								<#assign citys_size=citys?size/>
								<#list citys as a>
								<a href="/search/course.html?c=0,0,-1,0,0,0,0,0,${a.code}&tab=local&v=thumb">${a.name}</a>
								<#if a_index!=(citys_size-1)>|</#if>
								</#list>
							</div>
							<div class="course-list">
								<#list coursemap["course4"] as c>
								<dl class="course-item-thumb" node-type="course-item" id="ce${c.courseId}" cid="${c.courseId}" ctype="4">
									<dt class="course-cover">
										<span class="course-type t${c.type}">${enumHelper.getLabel("course_type",c.type?j_string)}</span><a href="${SITE_DOMAIN}/course/${c.courseId}-${VERSION}.html" target="_blank" class="thumbnail"><img source="${imageHelper.resolve(c.cover!,"s120,90")}" src="/theme/main/default/images/course_cover.jpg" alt="${c.name}" width="148" height="111" style="display: block; "></a><span class="course-price free">免费</span>
									</dt>
									<dd class="course-base">
										<h2><a href="${SITE_DOMAIN}/course/${c.courseId}-${VERSION}.html" target="_blank">${c.name} </a></h2>
										发布人： <a href="/u/${c.userId}.html" target="_blank" usercard="${c.userId}" class="user status0">${c.userId}</a>
										<br>
										<span class="gray">${flint.timeToDate(c.publishTime,"yyyy-MM-dd HH:mm")}</span>
									</dd>
								</dl>
								</#list>
							</div>

						</div>
					</div>
					<div class="page-right" style="width:300px;">
						<div class="box" node-type="mblog">
							<h2 class="box-title"><a href="/user/index.html"  class="float-right">更多&gt;</a>随便说说</h2>
							<div  style="height:250px;overflow:hidden;z-index: 2;position:relative;">
								<div id="Mblog-box">
									<#list mbloglist as m>
									 <dl class="list-item">
									 	<dt class="face"><a href="/u/${m.userId}.html" target="_blank" class="face-link"><img usercard="${m.userId}" src="" width="40" height="40" alt=""/></a></dt>
									 	<dd class="content">
									 		<a href="/u/${m.userId}.html" target="_blank" usercard="${m.userId}"></a>
									 		<#if m.type==1>
												${m.content?replace("<a>","<a href=\"/qitem-${m.media}.html\" target=\"_blank\">")}
											<#elseif m.type==2>
											   ${m.content?replace("<a>","<a href=\"/course/${m.media}.html\" target=\"_blank\">")}				
											<#elseif m.type==3>
												${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()?replace("<a>","<a href=\"/show/item-${m.media}.html\" target=\"_blank\">")}
								           	<#elseif m.type==4>
								               ${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()?replace("<a>","<a href=\"/u/${m.userId}.html#dm${m.media}\" target=\"_blank\">")}
								          	<#elseif m.type==5>
								               ${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()?replace("<a>","<a href=\"/u/${m.userId}.html#sv${m.media}\" target=\"_blank\">")}
											<#else>
												${bbcodeHelper.doFilter(m.content,"1").getReplaceContent()}
											</#if>
									 	</dd>
									 </dl>
									 </#list>
								</div>
							</div>
						</div>
						<div><a href="/tutor/post.html"><img src="/theme/main/default/ad/ad-c.jpg" width="300" height="95"></a>
						</div>
						<div class="box  online-user-box" node-type="online-teacher"></div>
						<div class="box" node-type="guess-member" data-size="3"></div>
						<div class="box" node-type="guess-course" data-size="3"></div>
					</div>
				</div>
			</div>
			<#include "footer.ftl"/>
	</body>
</html>
<script>
	$(function() {
		var teachertimeId = window.setInterval(autoScanTeacher, 2000);
		$("#teacher .small-image li").mouseover(function() {
			var index = $('#teacher').attr("index");
			if (index) {
				$('#teacher .current-list li').eq(index * 1).removeClass("auto")
			}
			$("#teacher div.detail").hide().eq($(this).index("#teacher .small-image li")).show();
			clearTimeout(teachertimeId);
		}).mouseout(function() {
			teachertimeId = window.setInterval(autoScanTeacher, 2000);
		});

		$("#teacher-big").mouseenter(function() {
			window.clearInterval(teachertimeId);
		}).mouseleave(function() {
			teachertimeId = window.setInterval(autoScanTeacher, 2000);
		});
	});

	function autoScanTeacher() {
		var index = $('#teacher').attr("index") ? $('#teacher').attr("index") * 1 : -1;
		var group = $('#teacher').attr("group") ? $('#teacher').attr("group") * 1 : 0;
		if (index > -1) {
			$('#teacher .current-list li').eq(index).removeClass("auto");
		}
		if ($('#teacher .current-list li').size() == index + 1) {
			/*更换显示组*/
			if (group >=$('#teacher .small-image ul').size() - 1) {
				group = -1;
			}
			$('#teacher .current-list').removeClass("current-list");
			$('#teacher .small-image ul').eq(group + 1).addClass("current-list");
			group = group + 1;
			$('#teacher').attr("group", group);
			index = -1;
		}
		$("#teacher div.detail").hide().eq(index + 1 + (group * 8)).show();
		$('#teacher .current-list li').eq(index + 1).addClass("auto");
		$('#teacher').attr("index", index + 1);
	}
</script>
<!-- 隨便說說滾動效果 -->
<script>
	var scrtime;
	$("#Mblog-box").hover(function() {
		clearInterval(scrtime);
	}, function() {
		scrtime = setInterval(function() {
			var $dlv = $("#Mblog-box");
			var liHeight = $dlv.find("dl:last").outerHeight();
			$dlv.find("dl:last").prependTo($dlv);
			$dlv.css("marginTop",-liHeight).animate({
				marginTop :0
			}, 1000);
		}, 3000);
	}).mouseleave();
</script>