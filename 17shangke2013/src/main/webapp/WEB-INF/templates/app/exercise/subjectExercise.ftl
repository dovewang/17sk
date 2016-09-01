<#assign type=RequestParameters["st"]!"1" />
<#assign sid=RequestParameters["sid"]!"0" />
<#assign eid=0 />
<div>
	<div>
		<#if result?exists>
		<#assign eid=result.exerciseId />
		<form id="classForm${type}" action="/exercise/save.html" exerciseId="${result.exerciseId}" >
			<input type="hidden" name="exerciseId"   value="${result.exerciseId}" />
			<input type="hidden" name="subjectId"   value="${result.subjectId}"/>
			<input type="hidden" name="subjectType" value="${result.subjectType}"/>
			<table width="85%" border="0" align="center" cellpadding="2" cellspacing="5" class="table form">
				<tr>
					<td width="80" class="a">标题：</td>
					<td width="120" class="b">
					<input type="text" class="input" value="${result.subject}" name="subject"  />
					</td>
					<td width="160" class="a">时间限制：</td>
					<td class="b">
					<input type="number"  class="input number" value="${result.timeLimit}" min="0" name="timeLimit"/>
					分钟 </td>
				</tr>
				<tr>
					<td class="a">标签：</td>
					<td class="b">
					<input type="text"  class="input"   name="tag" value="${result.tag}" />
					</td>
					<td class="a">是否自动发送到邮箱：</td>
					<td class="b">
					<input name="sendResult" type="radio" value="1" <#if (result.sendResult==1)>checked="checked"</#if> />
					是
					<input name="sendResult" type="radio" value="0" <#if (result.sendResult==0)>checked="checked"</#if> />
					否 </td>
				</tr>
			</table>
		</form>
		<#else>
		<form id="classForm${type}" action="/exercise/save.html" exerciseId="0">
			<input type="hidden" name="exerciseId"   value="0" />
			<input type="hidden" name="subjectId"   value="${sid}"/>
			<input type="hidden" name="subjectType" value="${type}"/>
			<table width="85%" border="0" align="center" cellpadding="2" cellspacing="5" class="table form">
				<tr>
					<td width="80" class="a">标题：</td>
					<td width="120" class="b">
					<input type="text" class="input" value="练习" name="subject"  />
					</td>
					<td width="160" class="a">时间限制：</td>
					<td class="b">
					<input type="number"  class="input number" value="10" min="0" name="timeLimit"/>
					分钟 </td>
				</tr>
				<tr>
					<td class="a">标签：</td>
					<td class="b">
					<input type="text"  class="input"   name="tag" value="" />
					</td>
					<td class="a">是否自动发送到邮箱：</td>
					<td class="b">
					<input name="sendResult" type="radio" value="1" checked="checked" />
					是
					<input name="sendResult" type="radio" value="0" />
					否 </td>
				</tr>
			</table>
		</form>
		</#if>
		<div class="econtrol">
			<a href="/q/bank.html?eid=${eid}&st=${type}" class="popwin" iframe="true" popH="550px" popW="960px" id="exercise" >选题</a>
		</div>
	</div>
	<div id="classExcercise${type}" class="clearfix"></div>
</div>
<script type="text/javascript">
   function loadE${type}(){
	   $("#classExcercise${type}").load("/exercise/get.html?exerciseId=${eid}");
   }
   loadE${type}();
</script>
