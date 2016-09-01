<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<#include "../meta.ftl" />
	</head>
	<body>
		<#include "../header.ftl" />
		<div class="page-container">
			<div class="page-body">
				<div class="box">
					<h1>评价</h1>
					<div class="comments_box">
						<form id="commentForm" action="/course/postComments.html">
							<input type="hidden" name="courseId" value=" "/>
							<input type="hidden" id="teacherScore" name="teacherScore" value="2"/>
							<dl>
								<dd>
									<strong>这堂课对您有帮助吗？</strong>
									<span>
										<input name="courseScore"  id="courseScore3" type="radio" value="3" />
										<label for="courseScore3" id="cs3">非常有用</label></span>
									<span>
										<input name="courseScore" id="courseScore2" type="radio" value="2" />
										<label for="courseScore2"  id="cs2">有用</label></span>
									<span>
										<input name="courseScore" id="courseScore1" type="radio" value="1" />
										<label for="courseScore1"  id="cs1">一般</label></span>
									<span>
										<input name="courseScore" id="courseScore0" type="radio" value="0" />
										<label for="courseScore0"  id="cs0">作用不大</label></span>
								</dd>
								<dd>
									<strong style="line-height:24px;">您喜欢这位老师的授课吗？</strong>
									<span id="rate"><a href="#" ></a><a href="#" ></a><a href="#" ></a><a href="#" ></a><a href="#" ></a></span>
								</dd>
								<dd>
									<strong>还想说一句</strong>
									<textarea row="1" class="input" name="content" id="comment_content" style="width:98%"></textarea>
								</dd>
								<dd>
									<button class="button comment" type="button"  onclick="Course.postComment()">
										评论
									</button>
								</dd>
							</dl>
						</form>
						<script type="text/javascript">
							$(function() {
								$("#rate").rate(function(rate) {
									$("#teacherScore").val(rate);
								});
							})
						</script>
					</div>
				</div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>