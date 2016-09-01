<div class="box" style="margin-top:20px;">
	<div style="padding:5px;">
		<strong>问题补充：</strong>
	</div>
	<div align="center">
		<textarea name="intro" node-type="editor" style="height:120px;width:100%;" id="aintro_${answer.answerId}"  placeholder="输入详细内容" node-type="editor" >${answer.content!}</textarea>
	</div>
	<div style=" height:30px; width:100%;" class="text-right">
	    <button  onclick="Answer.update(${answer.answerId});return false;"class="button ok">更新</button>
		<button onclick="$('#aide_${answer.answerId}').slideUp();return false;" class="button cancle">取消</button>
	</div>
</div>
