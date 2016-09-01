<#assign callback=RequestParameters["cb"]!"" />
<#assign eid=RequestParameters["eid"]!"0" />
<div style="width:700px;">
	<a href="javascript:void(0)" class="close" node-type="pop-close">×</a>
	<#if result?exists>
	<form id="exerciseForm" action="/q/update.html" onsubmit="return false;">
		<input type="hidden" id="exerciseId" name="exerciseId" value="${eid}"/>
		<input type="hidden" id="exercise_type" name="type" value="4"/>
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
					<input name="difficult" type="number" class="input number" value="${result.difficult}" />
				</span>&nbsp;&nbsp;&nbsp;&nbsp;<span><a href="javascript:void(0)" onclick="Knowledge.show();return false;">知识点设置</a><span id="setKnowledgeDiv" style="padding-left:20px;"></span></span>
			</dd>
			<dd id="exercise_answerArea">
				<#assign pick=result.answer?split("&#8718;")/>
				答案：
				<#list pick as p> <span>${p_index+1}.
					<input type="text" class="input fill"  value="${p}"/>
				</span>
				</#list> <span  style="float:right; padding-right:45px"><a  href="javascript:void(0)" onclick="Exercise.addOption(true)">再加一个空</a></span>
			</dd>
			<dd>
				答案评析：
			</dd>
			<dd>
				<textarea style="width:100%;height:120px"  class="editor"  name="analyse"  id="analyse">${result.analyse}</textarea>
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
		<input type="hidden" id="exercise_type" name="type" value="4"/>
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
					<input name="difficult" type="number" class="input number" value="80" />
				</span>&nbsp;&nbsp;&nbsp;&nbsp;<span><a href="javascript:void(0)" onclick="Knowledge.show();return false;">知识点设置</a><span id="setKnowledgeDiv" style="padding-left:20px;"></span></span>
			</dd>
			<dd id="exercise_answerArea">
				答案：<span>1.
					<input name="option" type="text" value="" class="input fill"/>
				</span>
				<span  style="float:right; padding-right:45px"><a  href="javascript:void(0)" onclick="Exercise.addOption(true)">再加一个空</a></span>
			</dd>
			<dd>
				答案评析：
			</dd>
			<dd>
				<textarea style="width:100%;height:120px"  class="editor"  name="analyse"  id="analyse"></textarea>
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