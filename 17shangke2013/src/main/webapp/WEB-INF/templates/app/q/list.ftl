<#if result.totalCount==0>
<div  class="nodata">
	暂时还没有任何练习
</div>
<#else>
<div class="exercise">
	<#list result.result as question>
	<div class="question-box"  id="eq_${question.questionId}" qid="${question.questionId}" qtype="${question.type}">
		<span class="question-control"><a href="javascript:void(0)"  onclick="parent.Exercise.add(${eid},${question.questionId},${type})">加入到练习</a> | <a href="/exercise/template.html?id=${question.questionId}" class="popwin" id="exercise" callback="bindEditor">编辑</a> | <a href="javascript:void(0)" onclick="Exercise.delQ(${question.questionId})">删除</a> | <a href="javascript:void(0)" onclick="Exercise.analyseQ(${question.questionId})">评析</a></span>
		<h3>${question.questionId}<sup>#</sup></h3>
		<div class="question-content">
			${question.content!""}
		</div>
		<div class="question-answer-box">
			<#if (question.type=1) >
			<dl>
				<dt>
					答案：
				</dt>
				<#assign pick=question.picks?split(",")/>
				<#list pick as p>
				<dd>
					<label>${p}.</label><span>
						<input name="q${question.questionId}" type="radio" value="${p}" <#if question.answer==p>checked="checked"</#if>/>
					</span>
				</dd>
				</#list>
			</dl>
			<#elseif (question.type=2)>
			<dl>
				<dt>
					答案：
				</dt>
				<#assign pick=question.picks?split(",")/>
				<#list pick as p>
				<dd>
					<label>${p}.</label><span>
						<input name="q${question.questionId}" type="checkbox" value="${p}" <#if (question.answer?index_of(p)!=-1)>checked="checked"</#if>/>
					</span>
				</dd>
				</#list>
			</dl>
			<#elseif (question.type=3)>
			<dl>
				<dt>
					答案：
				</dt>
				<dd>
					<label>对.</label><span>
						<input name="q${question.questionId}" type="radio" value="1"  <#if question.answer=="1">checked="checked"</#if>/>
					</span>
				</dd>
				<dd>
					<label>错.</label><span>
						<input name="q${question.questionId}" type="radio" value="0"  <#if question.answer=="0">checked="checked"</#if>/>
					</span>
				</dd>
			</dl>
			<#elseif (question.type=4)>
			<#assign pick=question.answer?split("&#8718;")/>
			<dl>
				<#list pick as p>
				<dd>
					<label>${p_index+1}.</label><span>
						<input type="text" class="input fill"  name="q${question.questionId}" value="${p}"/>
					</span>
				</dd>
				</#list>
			</dl>
			<#elseif (question.type=5)> 
            <textarea name="q${question.questionId}" style="width:100%;height:120px"  class="editor">${question.answer}</textarea>
			</#if>
		</div>
		<div class="question-analyse">
			答案解析：${question.analyse}
		</div>
	</div>
	</#list>
</div>
</#if> 