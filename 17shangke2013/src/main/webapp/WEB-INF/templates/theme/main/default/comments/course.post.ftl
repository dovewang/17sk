<#--允许评论时才能评论-->
<#if allowComment>
<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/kiss/rate-min.js"></script>
<div class="comments-box" id="commentPostDiv">
	<form id="commentForm" action="/course/comment.html" >
		<input type="hidden" name="courseId" value="${course.courseId}"/>
		<input type="hidden" id="teacherScore" name="teacherScore" value="2"/>
		<dl>
			<dd>
				<strong>这堂课对您有帮助吗？</strong>
				<span>
					<input name="courseScore" id="courseScore3" type="radio" value="3"  />
					<label for="courseScore3" id="cs3">非常有用</label></span>
				<span>
					<input name="courseScore" id="courseScore2" type="radio" value="2" checked="checked"/>
					<label for="courseScore2"  id="cs2">有用</label></span>
				<span>
					<input name="courseScore" id="courseScore1" type="radio" value="1" />
					<label for="courseScore1"  id="cs1">一般</label></span>
				<span>
					<input name="courseScore" id="courseScore0" type="radio" value="0" />
					<label for="courseScore0"  id="cs0">作用不大</label></span>
			</dd>
			<dd>
				<strong>您喜欢这位老师的授课吗？</strong>
				<span  node-type="rate" rated="3" target="#teacherScore"></span>
			</dd>
			<dd>
				<strong>还想说点什么</strong>
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
			$("[node-type=rate]").rate(function(rate) {
				$("#teacherScore").val(rate);
			});
		})
	</script>
</div>
</#if>