<#include "/conf/config.ftl" />
<table class="table gray">
	<thead>
		<tr>
			<th>练习名</th><th>科目</th><th>时长</th>
		</tr>
	</thead>
	<tbody>
		<#list result.result as e>
		<tr>
			<td>${e.subject} <span class="con_mousehover">[ <a href="javascript:void(0)" onclick="parent.abcbox.distrubuteExercise(${e.exerciseId},'${e.subject}','${e.timeLimit}')">分发</a> | <a href="javascript:void(0)" onclick="parent.abcbox.openExcercise('${e.exerciseId},${e.subject},${e.timeLimit!}')">预览</a>| <a href="javascript:void(0)" onclick="parent.abcbox.showExerciseResult(${e.exerciseId})">查看结果</a> ] </span></td>
			<td>${e.kind}</td><td>${e.timeLimit}分钟</td>
		</tr>
		</#list>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="3"> <@flint.simplepage  result,"" /> </td>
		</tr>
	</tfoot>
</table>