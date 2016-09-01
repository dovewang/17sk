<#assign CATEGORY="问作业-"/>
<#assign description="闻道有先后，术业有专攻。您有学习的问题需要解决吗？小学、初中、高中，优秀教师24小时在线，实时为您解答问题 。您可以为他人解答问题吗？请来一起上课。"/>
<#assign keywords="答疑、解惑、解答、提问、问题、24小时、优秀教师"/>
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
			<form action="" id="search-box">
				<dl>
					<dt>
						<ul>
														<li>
								<a href="/index.html">一起上课</a>
							</li>
							<li>
								<a href="/search/course.html?q=${keyword}">找课程</a>
							</li>
							<li class="active">
								<a href="/search/question.html?q=${keyword}">问作业</a>
							</li>
							<li>
								<a href="/search/show.html?q=${keyword}">I讲台</a>
							</li>
							<li>
								<a href="/search/teacher.html?q=${keyword}">找老师</a>
							</li>
						</ul>
						<div class="clear"></div>
					</dt>
					<dd>
						<input type="text" name="q" xe-webkit-spech maxlength="50" value="${keyword}"  style="width:873px;height:37px; line-height:37px; vertical-align:middle;"/>
						<input type="image" src="/theme/main/default/images/search.png"  name="search"  style="position:absolute; right:-2px;top:-2px"/>
					</dd>
				</dl>
			</form>
			<div class="page-body" id="page-main">
				<div class="page-left">
					<div class="poster" id="poster-wrap">
						<div class="poster-header">
							<a href="javascript:;" action-type="poster" poster="question-poster">问作业</a>
							<span  id="poster-count">您还可以输入<strong>100</strong>字</span>
						</div>
						<div id="question-poster" >
							<#include "../question/question.poster.ftl"/>
						</div>
					</div>
					<#include "../question/question.filter.ftl" />
					<div id="question_list">
						<#include "../question/result.list.ftl" />
					</div>
				</div>
				<div class="page-right">
					<div node-type="show-hot" class="show-hot box"></div>
					<div class="box  online-user-box" node-type="online-teacher"></div>
					<div class="box" node-type="guess-member" data-size="3"></div>
					<div class="box" node-type="guess-course" data-size="3"></div>
				</div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>