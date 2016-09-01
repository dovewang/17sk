<#if allowComment> <script type="text/javascript" src="${IMAGE_DOMAIN}/theme/kiss/rate-min.js"></script>
<div class="comments_box" id="commentPostDiv">
	<form id="commentForm" action="/course/comment.html">
		<input type="hidden" name="courseId" value="${course.courseId}"/>
		<input type="hidden" id="teacherScore" name="teacherScore" value="3"/>
		<dl>
			<dd>
				<strong>您觉得这堂课怎么样?</strong><span>
					<input name="courseScore"  id="courseScore3" type="radio" value="3" />
					<label for="courseScore3" id="cs3">非常有用</label> </span><span>
					<input name="courseScore" id="courseScore2" type="radio" value="2" checked="checked"/>
					<label for="courseScore2"  id="cs2">有用</label> </span><span>
					<input name="courseScore" id="courseScore1" type="radio" value="1" />
					<label for="courseScore1"  id="cs1">一般</label> </span><span>
					<input name="courseScore" id="courseScore0" type="radio" value="0" />
					<label for="courseScore0"  id="cs0">作用不大</label> </span>
			</dd>
			<dd>
				<strong style="line-height:24px;">您喜欢这位老师的授课吗？</strong><span id="rate"><a href="#" ></a><a href="#" ></a><a href="#" ></a><a href="#" ></a><a href="#" ></a></span>
			</dd>
			<dd>
				<strong>还想说点什么吗？</strong>
				<textarea rows="5" class="input" name="content" id="comment_content" style="width:98%"></textarea>
			</dd>
			<dd>
				<button class="button xbtn comment" type="button"  onclick="Course.postComment()"></button>
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
</#if>