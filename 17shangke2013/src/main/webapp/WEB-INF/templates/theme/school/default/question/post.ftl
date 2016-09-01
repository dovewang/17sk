<div id="feedback">
	<div id="feedback-title2"></div>
	<div id="feedback-prompt">
		您还可以输入<strong>100</strong>字
	</div>
	<div id="feedbackTextAreabox">
		<textarea id="publish_editor"  placeholder="写出您的疑问" class="input feedback_textarea"></textarea>
		<div id="question_function">
			<a href="javascript:void(0)" onclick="$('#intro_div').slideToggle()" style="height:100%;">问题补充</a>
			<a href="javascript:void(0)" node-type="kownleadge-select">设置分类</a>
			<a href="javascript:;" node-type="help-extend">求助老师</a>
		</div>
		<div id="intro_div" style="display:none;">
			<textarea name="intro" rows="5" style="width:100%;height:120px;" id="intro"  placeholder="输入详细内容"  node-type="editor"></textarea>
		</div>
		<div id="knowledge-extend" class="hide"></div>
		<div id="help-extend" class="hide">
			<div class="box expert-user-box">
				<span>请设置分类以便查找到更合适的老师!</span>
			</div>
		</div>
	</div>
	<!--feedbackTextAreabox end-->
	<div id="feedback_publish">
		<span id="setKnowledgeDiv" style="padding-left:20px;"></span>
		<button  onclick="Question.post();" class="button xbtn post disabled float-right" disabled="disabled" id="question_post_btn"></button>
		<div class="clear"></div>
	</div>
</div>
<div class="right_funcitonbottom"></div>