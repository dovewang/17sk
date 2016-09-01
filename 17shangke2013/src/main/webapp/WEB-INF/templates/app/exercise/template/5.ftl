<#assign callback=RequestParameters["cb"]!"" />
<#assign eid=RequestParameters["eid"]!"0" />
<div style="width:700px;">
	<a href="javascript:void(0)" class="close" node-type="pop-close">×</a>
	<#if result?exists>
	<form id="exerciseForm" action="/q/update.html" onsubmit="return false;">
		<input type="hidden" id="exerciseId" name="exerciseId" value="${eid}"/>
		<input type="hidden" id="exercise_type" name="type" value="5"/>
		<dl class="form">
			<dd>
				<label>请输入问题内容：</label>
			</dd>
			<dd>
				<textarea rows="4" class="editor" name="content" style="width:100%;height:120px"  id="exercise_content">${result.content}</textarea>
			</dd>
			<dd>
				<span>难度系数：
					<input name="difficult" type="number" class="input number" value="${result.difficult}" />
				</span>&nbsp;&nbsp;&nbsp;&nbsp;<span><a href="javascript:void(0)" onclick="Knowledge.show();return false;">知识点设置</a><span id="setKnowledgeDiv" style="padding-left:20px;"></span></span>
			</dd>
			<dd>
				答案：
			</dd>
			<dd>
				<textarea id="exercise_answer" style="width:100%;height:100px" class="editor"  name="answer">${result.answer}</textarea>
			</dd>
			<dd>
				答案评析：
			</dd>
			<dd>
				<textarea   style="width:100%;height:100px"  class="editor"   name="analyse"  id="analyse">${result.analyse}</textarea>
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
		<input type="hidden" id="exercise_type" name="type" value="5"/>
		<dl class="form">
			<dd>
				<label>请输入问题内容：</label>
			</dd>
			<dd>
				<span>					<textarea rows="4" class="editor" name="content" style="width:600px;height:100px"  id="exercise_content"></textarea></span>
			</dd>
			<dd>
				<label>难度系数：</label>
				<span>
					<input name="difficult" type="number" class="input number" value="80" />
					&nbsp;&nbsp;&nbsp;&nbsp;<span><a href="javascript:void(0)"  node-type="kownleadge-select">知识点设置</a><span id="setKnowledgeDiv" style="padding-left:20px;"></span></span> </span>
			</dd>
			<dd>
				答案：<span> 					<textarea id="exercise_answer" style="width:100%;height:100px" class="editor"  name="answer"></textarea></span>
			</dd>
			<dd>
				答案评析：
			</dd>
			<dd>
				<textarea   style="width:100%;height:100px"  class="editor"   name="analyse"  id="analyse"></textarea>
			</dd>
			<dd class="text-right">
				<button onclick="Exercise.save(${callback});return false;" class="button ok">
					确定
				</button>
				<button node-type="pop-close" class="button cancle">
					取消
				</button>
			</dd>
		</dl>
	</form>
	</#if>
</div>