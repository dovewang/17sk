<input type="hidden" name="title" id="qtitle_${question.questionId}" value="${question.title}"/>
<div style="padding:5px;">
	<strong>问题补充：</strong>
</div>
<div align="center">
	<textarea name="intro" style="width:100%;height:120px;" id="qintro_${question.questionId}"  placeholder="输入详细内容" node-type="editor" >${question.intro!}</textarea>
</div>
<div style=" height:30px; " class="text-right">
    <button  onclick="Question.update(${question.questionId});return false;"class="button ok">更新</button>
	<button onclick="$('#qide_${question.questionId}').slideUp();return false;" class="button cancle">取消</button>
</div>
