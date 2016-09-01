<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<#include "../../meta.ftl" />
		<script type="text/javascript" src="/theme/main/js/bis.question.js?${VERSION}"></script>
        <script type="text/javascript" src="/theme/main/js/bis.course.js?${VERSION}"></script>
         <script src="${IMAGE_DOMAIN}/editor/xheditor.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
	</head>
	<body>
		<#include "header.ftl" />
		<div class="page-container">
            
			<div class="page-body  right-left-swap">
			  <div class="page-left">
              <#include "left.ftl" />
              </div>
			  <div class="page-right">
			   <div class="tab-header mt15" node-type="tabnavigator">
									<ul>
										<li>
											<a href="#!/question.html" remote="false" view="question_list"  autoload="true" group="true">问题</a>
										</li>
										<li>
											<a href="#!/course.html" remote="false" view="course_list"  group="true"> 课程</a>
										</li>
									    <li>
											<a href="#!/course.html" remote="false" view="course_list"  group="true"> 我的秀秀</a>
										</li>
                                        <li>
											<a href="#!/course.html" remote="false" view="course_list"  group="true"> 我的辅导</a>
										</li>
									</ul>
				</div>                        
								<div id="question_list">
									<div class="tab-subheader">
										<ul>
											<li>
												<a href="#!/question-all.html" remote="true" view="questionView" page="true">所有问题</a>
											</li>
											<li>
												<a href="#!/question-myq.html" remote="true" view="questionView" page="true">我的提问 </a>
											</li>
											<li>
												<a href="#!/question-mya.html" remote="true" view="questionView" page="true">我的解答 </a>
											</li>
											<li>
												<a href="#!/question/fav.html" remote="true" view="questionView" page="true"> 我收藏的问题</a>
											</li>
										</ul>
                                        <span class="float-right"><a href="/index.html" target="_blank" class="button primary">我要提问</a></span>
									</div>
									<div class="tab-body p10" id="questionView"></div>
								</div>
								<div  id="course_list"  style="display:none">
									<div class="tab-subheader">
										<ul>
											<li>
												<a href="#!/course-all.html" remote="true" view="courseView" page="true">所有课程</a>
											</li>
											<li>
												<a href="#!/course-mys.html" remote="true" view="courseView" page="true">我参与的课程</a>
											</li>
											<li>
												<a href="#!/course-myc.html" remote="true" view="courseView" page="true">我创建的课程</a>
											</li>
											<li>
												<a href="#!/course/fav.html" remote="true" view="courseView" page="true"> 我收藏的课程</a>
											</li>
										</ul>
                                         <span class="float-right"><a href="/course/manager/guide.html" target="_blank" class="button primary">创建新课程</a></span>
									</div>
									<div class="tab-body" id="courseView"></div>
								</div>
							</div>
              </div>
			</div>
		</div>
		<#include "../../footer.ftl"/>
	</body>
</html>
<!--
<script type="text/javascript" src="广告代码"></script>
-->