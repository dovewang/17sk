<div  style="margin-top:20px;">
	<div align="center">
		<textarea name="intro" node-type="editor" style="height:120px;width:100%;" id="aintro_${answer.answerId}"  placeholder="输入详细内容" node-type="editor" >${answer.content!}</textarea>
	</div>
	<div class="text-right">
    	<button onclick="$('#aide_${answer.answerId}').slideUp();return false;" class="btn">取消</button>
	    <button  onclick="Answer.update(${answer.answerId});return false;"class="btn btn-primary">更新</button>
	</div>
</div>
