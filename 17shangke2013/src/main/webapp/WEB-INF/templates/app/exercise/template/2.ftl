<#assign callback=RequestParameters["cb"]!"" />
<#assign eid=RequestParameters["eid"]!"0" />
<div style="width:700px;">
	<a href="javascript:void(0)" class="close" node-type="pop-close">×</a>
	<#if result?exists>
	<form id="exerciseForm" action="/q/update.html" onsubmit="return false;">
		<input type="hidden" id="exerciseId" name="exerciseId" value="${eid}"/>
		<input type="hidden" id="exercise_type" name="type" value="2"/>
		<input type="hidden" id="exercise_picks" name="picks" value="1"/>
		<input type="hidden" id="exercise_answer" name="answer" value="1"/>
		<dl class="form">
			<dd>
				请输入问题内容：
			</dd>
			<dd>
				<textarea rows="4" class="editor" name="content" style="width:100%;height:160px"  id="exercise_content">${result.content}</textarea>
			</dd>
			<dd>
				<span>难度系数：
					<input name="difficult" type="number" class="input number" value="80"  min="1" max="100"/>
				</span>&nbsp;&nbsp;&nbsp;&nbsp;<span><a href="javascript:void(0)" onclick="Knowledge.show();return false;">知识点设置</a><span id="setKnowledgeDiv" style="padding-left:20px;"></span></span>
			</dd>
			<dd id="exercise_answerArea">
				答案：<#assign pick=result.picks?split(",")/>
				<#list pick as p> <span>${p}.
					<input name="option" type="checkbox" value="${p}" <#if (result.answer?index_of(p)!=-1)>checked="checked"</#if> />
				</span>
				</#list> <span style="float:right; padding-right:45px"><a href="javascript:void(0)" onclick="Exercise.addOption()">再加一个选项</a></span>
			</dd>
			<dd>
				答案评析：
			</dd>
			<dd>
				<textarea rows="3"  style="width:100%;height:120px"  class="editor" name="analyse"  id="analyse">${result.analyse}</textarea>
			</dd>
			<dd class="text-right">
				<button onclick="Exercise.save(${callback});return false;" class="button ok">
					确定
				</button>
				<button action-type="popup-close" class="button cancle">
					取消
				</button>
			</dd>
		</dl>
	</form>
	<#else>
	<form id="exerciseForm" action="/q/save.html" onsubmit="return false;">
		<input type="hidden" id="exerciseId" name="exerciseId" value="${eid}"/>
		<input type="hidden" id="exercise_type" name="type" value="2"/>
		<input type="hidden" id="exercise_picks" name="picks" value="1"/>
		<input type="hidden" id="exercise_answer" name="answer" value="1"/>
		<dl class="form">
			<dd>
				请输入问题内容：
			</dd>
			<dd>
				<textarea rows="4" class="editor" name="content" style="width:100%;height:160px"  id="exercise_content"></textarea>
			</dd>
			<dd>
				<span>难度系数：
					<input name="difficult" type="number" class="input number" value="80"  min="1" max="100"/>
				</span>&nbsp;&nbsp;&nbsp;&nbsp;<span><a href="javascript:void(0)" onclick="Knowledge.show();return false;">知识点设置</a><span id="setKnowledgeDiv" style="padding-left:20px;"></span></span>
			</dd>
			<dd id="exercise_answerArea">
				答案：<span>A.
					<input name="option" type="checkbox" value="A" />
				</span>
				<span>B.
					<input name="option" type="checkbox" value="B" />
				</span>
				<span>C.
					<input name="option" type="checkbox" value="C" />
				</span>
				<span>D.
					<input name="option" type="checkbox" value="D" />
				</span>
				<span style="float:right; padding-right:45px"><a href="javascript:void(0)" onclick="Exercise.addOption()">再加一个选项</a></span>
			</dd>
			<dd>
				答案评析：
			</dd>
			<dd>
				<textarea rows="3"  style="width:100%;height:120px"  class="editor" name="analyse"  id="analyse"></textarea>
			</dd>
			<dd class="text-right">
				<button onclick="Exercise.save(${callback});return false;" class="button ok">
					确定
				</button>
				<button action-type="popup-close" class="button cancle">
					取消
				</button>
			</dd>
		</dl>
	</form>
	</#if>
</div>