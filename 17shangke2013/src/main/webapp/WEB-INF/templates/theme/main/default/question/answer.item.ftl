<html>
	<head>
		<#include "../meta.ftl" />
		<script type="text/javascript">
			window.NO_COMETD = true;
		</script>
	</head>
	<body>
		<div id="now-view-answer">
			<#if content?exists>
			${content}
			<#else>
			<p class="viewed">
				<#if answer.type==1> <a href="/v/id_${answer.roomId}.html" target="_blank"><img src="/theme/school/default/images/videopic.jpg" width="120" height="90" alt="视频名称"/></a>
				<#else>
				${answer.content}
				</#if>
				<button class="button primary" onclick="Question.selectAnswer(this,${answer.questionId},${answer.answerId},1)">
					采纳为答案
				</button>
			</p>
			</#if>
		</div>
	</body>
</html>
<script type="text/javascript">
	parent.Question.loadAnswer("${answer.answerId}", $("#now-view-answer").html());
</script>