<#assign readonly="true"/>
<#if (result?size)==0>
<div  class="nodata">
	暂时还没有添加任何练习
</div>
<#else>
<div class="exercise-box" questions="${(result?size)}">
	<#list result as question>
	<div class="question-box" exerciseId="${question.exercise_id}"  id="eq_${question.question_id}" qid="${question.question_id}" qtype="${question.type}">
		<#if readonly!="true"><span class="question-control"> <a href="/exercise/template.html?id=${question.question_id}" class="popwin" id="exercise" callback="bindEditor">编辑</a> | <a href="javascript:void(0)" onclick="Exercise.del(${question.exercise_id},${question.question_id})">删除</a> | <a href="javascript:void(0)" onclick="Exercise.analyse(${question.question_id})">评析</a>| <a href="javascript:void(0)" onclick="Exercise.order(${question.exercise_id},${question.question_id},-1)">上移</a>| <a href="javascript:void(0)" onclick="Exercise.order(${question.exercise_id},${question.question_id},1)">下移</a></span>
		</#if> 
		<h3>${question_index+1}、（<strong>5</strong>分）</h3>
		<div class="question-content">
			${question.content!""}
		</div>
		<div class="question-answer-box">
			<#if (question.type=1) >
			<dl>
				<dt>
					选择答案：
				</dt>
				<#assign pick=question.picks?split(",")/>
				<#list pick as p>
				<dd>
					<label>${p}.</label><span>
						<input name="q${question.question_id}" type="radio" value="${p}" />
					</span>
				</dd>
				</#list>
				<dt>
					标记：
				</dt>
				<dd class="tag">
					<a href="javascript:void(0)" onclick="Exercise.setUncertain(this,${question.question_id})">答案不确定</a></dd>
			</dl>
			<#elseif (question.type=2)>
			<dl>
				<dt>
					选择答案：
				</dt>
				<#assign pick=question.picks?split(",")/>
				<#list pick as p>
				<dd>
					<label>${p}.</label><span>
						<input name="q${question.question_id}" type="checkbox" value="${p}" />
					</span>
				</dd>
				</#list>
				<dt>
					标记：
				</dt>
				<dd class="tag">
							<a href="javascript:void(0)" onclick="Exercise.setUncertain(this,${question.question_id})">答案不确定</a>
				</dd>
			</dl>
			<#elseif (question.type=3)>
			<dl>
				<dt>
					选择答案：
				</dt>
				<dd>
					<label>对.</label><span>
						<input name="q${question.question_id}" type="radio" value="1" />
					</span>
				</dd>
				<dd>
					<label>错.</label><span>
						<input name="q${question.question_id}" type="radio" value="0" />
					</span>
				</dd>
				<dt>
					标记：
				</dt>
				<dd class="tag">
					<a href="javascript:void(0)" onclick="Exercise.setUncertain(this,${question.question_id})">答案不确定</a>
				</dd>
			</dl>
			<#elseif (question.type=4)>
			<#assign pick=question.answer?split("&#8718;")/>
			<dl>
				<#list pick as p>
				<dd>
					<label>${p_index+1}.</label><span>
						<input type="text" class="input fill"  name="q${question.question_id}" />
					</span>
				</dd>
				</#list>
				<dt>
					标记：
				</dt>
				<dd class="tag">
						<a href="javascript:void(0)" onclick="Exercise.setUncertain(this,${question.question_id})">答案不确定</a>
				</dd>
			</dl>
			<#elseif (question.type=5)> 			
			<textarea name="q${question.question_id}" style="width:100%;height:120px"  class="editor"></textarea>
			</#if>
		</div>
	</div>
	</#list>
</div>
</#if> 