<!DOCTYPE html>
<html>
	<head>
		<#include "../abc/head.ftl" />
		<link href="${IMAGE_DOMAIN}/theme/base/css/doc.css?{VERSION}" rel="stylesheet" type="text/css" />
		<script src="${IMAGE_DOMAIN}/theme/base/js/bis.exercise.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<title>练习题</title>
	</head>
	<body>
		<div id="pop_box">
			<div class="pop_top">
				<h2 class="lianxi"></h2>
			</div>
			<div  class="ez-wr pop_conbox" style="height: 400px;">
				<table class="table gray">
					<thead>
						<tr>
							<th>用户</th>
							<th>提交时间</th>
							<th>得分</th>
							<th>正确率</th>
							<th>错题编号</th>
							<th>知识点弱项</th>
						</tr>
					</thead>
					<tbody id="exercise_status">
						<#list result as e>
						<tr id="u${e.userId}">
							<td><a href="/u/${e.userId}.html" usercard="${e.userId}" target="_blank">${e.userId}</a><span>[ <a href="javascript:void(0)" onclick="Exercise.mark(${e.id},${e.exerciseId},true)">自动阅卷</a> | <a href="javadcript:void(0)" onclick="markExercise(${e.id},${e.exerciseId},false)">手工阅卷</a> ] </span></td>
							<td>${flint.timeToDate(e.exerciseTime,"yyyy-MM-dd HH:mm:ss")}</td>
							<td>${e.score}</td>
							<td>${e.corrects}/${e.wrongs+e.corrects}</td>
							<td>${e.wrongQuestions!}</td>
							<td>${e.weak!}</td>
						</tr>
						</#list>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>

