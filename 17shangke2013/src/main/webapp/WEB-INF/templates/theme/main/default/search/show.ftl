<#assign CATEGORY="I讲台-"/>
<#assign description="I讲台是为了展示您自己，为自己获得人气，会有更多的学员来关注参加课程，以获得更好的收益"/>
<#assign keywords="分享学习心得，专题讲座，优秀的教学视频，专业的教学辅导突破，自我展示"/>
<!DOCTYPE html>
<html>
	<head>
		<#include "../meta.ftl" />
		<#assign keyword=RequestParameters["q"]! />
		<#assign tab=RequestParameters["tab"]!"all" />
		<#assign c=RequestParameters["c"]! />
		<#--秀秀检索范围-->
		<#assign TYPE=TYPE!0 />
	</head>
	<body>
		<#include "../header.ftl"/>
		<div class="clearfix" style="background: #2e4442;">
			<div style="width:985px;height:auto;margin:0 auto;">
				<div node-type="slider" activeIndex="0" class="slider" style="width:985px;height:132px">
					<ul class="slider-child">
						<li>
							<a href="/search/question.html"><img src="/theme/main/default/ad/a1.jpg"  ></a>
						</li>
						<li>
							<a href="/search/course.html"><img src="/theme/main/default/ad/a2.jpg"  ></a>
						</li>
						<li>
							<a href="/tutor/post.html"><img src="/theme/main/default/ad/a3.jpg"></a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="page-container">
			<form action="" id="search-box">
				<dl>
					<dt>
						<ul>
							<li>
								<a href="/index.html">一起上课</a>
							</li>
							<li>
								<a href="/search/course.html?q=${keyword}" >找课程</a>
							</li>
							<li>
								<a href="/search/question.html?q=${keyword}">问作业</a>
							</li>
							<li class="active">
								<a href="/search/show.html?q=${keyword}">I讲台</a>
							</li>
							<li>
								<a href="/search/teacher.html?q=${keyword}">找老师</a>
							</li>
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
				<div class="page-left">
					<div class="box" style="position:relative;" id="show-guide-box">
						<img src="/theme/main/default/images/course_g2.jpg" width="698" height="150" />
						<a class="button primary large"  href="/show/form.html?t=4" style="position:absolute;right:280px; top:100px;">转发视频</a>
						<#if flint.isLogin()>
						<div id="show_upload"  style="position:absolute;right:150px;top:100px;">
							<form id="showUploadForm"  action="/upload/video.html" method="post" enctype="multipart/form-data" >
								<input name="progress" value="true" type="hidden"/>
								<input name="flag" value="show" type="hidden"/>
								<a class="button primary large" node-type="upload-button"  name="file" onchange="Show.upload(this)">上传我的视频</a>
							</form>
						</div>
						<div id="uploadProgress" class="progress progress-info progress-striped active hide" >
							<div class="bar" id="uploadProgress_bar"></div>
						</div>
						<#else>
						<a class="button primary large" security="true" style="position:absolute;right:150px; top:100px;">上传我的视频</a>
						</#if>
						<a class="button primary large"  href="/abc/form.html?t=3" style="position:absolute;right:50px; top:100px;" node-type="pop" setting="{id:'create-room-pop',effect:'mac',css:{width:500}}">在线秀秀</a>
						<div class="text-left">
							1、I讲台是为了展示您自己，为自己获得人气，会有更多的学员来关注参加课程，以获得更好的收益。
							<br/>
							2、您也可以直接参加有主题的活动，会获得一定的收益，优秀的作品将会列入本站精品课程。<a href="#">点击这里查看详情</a>
							<br/>
							3、 关于I讲台发布协议，<a href="javascript:void(0)" onclick="$('#show_agreement').toggle()">点击查看</a>。
							<#include "/agreement/show.ftl"/>
						</div>
					</div>
					<#include "../show/show.filter.ftl" />
					<div class="tab-header" node-type="tabnavigator">
						<ul>
							<li <#if tab=="all">class="active"</#if> >
								<a  href="?q=${keyword}&p=1&c=${c}&tab=all">全部</a>
							</li>
							<li <#if tab=="online">class="active"</#if>>
								<a href="?q=${keyword}&p=1&c=${c}&tab=online">在线秀秀</a>
							</li>
							<li <#if tab=="video">class="active"</#if>>
								<a href="?q=${keyword}&p=1&c=${c}&tab=video">视频</a>
							</li>
						</ul>
					</div>
					<div id="course_tab" >
						<div class="order-box">
							<ul>
								<li>
									<@flint.condition condition,"?q="+keyword+"&p=1&c=@&tab="+tab,4,"0","默认排序<i class=\"sort-desc\"></i>"/>
								</li>
								<li>
									<@flint.condition condition,"?q="+keyword+"&p=1&c=@&tab="+tab,4,"1","时间排序<i class=\"sort-desc\"></i>"/>
								</li>
								<li>
									<@flint.condition condition,"?q="+keyword+"&p=1&c=@&tab="+tab,4,"2:","人气<i class=\"sort-desc\"></i>"/>
								</li>
								<li>
									<@flint.condition condition,"?q="+keyword+"&p=1&c=@&tab="+tab,4,"3:","播放次数<i class=\"sort-desc\"></i>"/>
								</li>
								<li>
									<@flint.condition condition,"?q="+keyword+"&p=1&c=@&tab="+tab,4,"4","最多收藏<i class=\"sort-desc\"></i>"/>
								</li>
							</ul>
							<span class="float-right search"> <label>关键字:</label>
								<input type="text" name="q" id="q"  value="${keyword}" maxlength="50"  style="width:120px" placeholder="请输入关键字" onkeydown="if(event.keyCode == 13){Searcher.searchShow(this.value);}"/>
								<button  type="button" class="button" onclick="Searcher.searchShow($('#q').val())"></button> </span>
						</div>
						<div id="show_list">
							<#include "../show/list.ftl"/>
						</div>
					</div>
				</div>
				<div class="page-right">
					<div   node-type="show-hot" class="box show-hot"></div>
					<div class="box  online-user-box" node-type="online-teacher"></div>
					<div class="box" node-type="guess-member" data-size="3"></div>
					<div class="box" node-type="guess-course" data-size="3"></div>
				</div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>
<script type="text/javascript">
	$(function() {
		$.guider.add({
			group : "show",
			steps : [{
				hightlight : "#show-guide-box",
				content : "您可以再这里分享您的作品，可以上传视频，也可以从优酷等视频网站转发喜欢的课程和大家分享，同时也可以利用我们的平台录制自己的课程",
				footer : "<button guide-type='next' class='button primary'>下一步</button><button guide-type='end' class='button'>退出向导</button>",
				attach : "#show-guide-box"
			}, {
				hightlight : "#show-filter-box",
				content : "发布后的视频，您可以在这里检索",
				footer : "<button guide-type='prev' class='button'>上一步</button><button guide-type='next' class='button primary'>下一步</button><button guide-type='end' class='button'>退出向导</button>",
				attach : "#show-filter-box"
			}],
			end : function() {

			}
		});
		//$.guider.start("show");
	}); 
</script>
