<div class="poster-body">
	<textarea id="question_editor"  placeholder="写出您的疑问"></textarea>
</div>
<div class="poster-footer">
	<a href="javascript:;" node-type="question-extend"><i class="icon icon-supplement"></i>问题补充</a>
	<a href="javascript:;" node-type="kownleadge-select" view="kownleadge-box"><i class="icon icon-setting"></i>设置分类</a>
	<a href="javascript:;" node-type="help-extend"><i class="icon icon-seek"></i>求助老师</a>
	<span class="kownleadge" id="kownleadge-box" style="width:240px;"></span>
	<#if FEE_MODLE>
	<span class="xuanshang">悬赏：<i  class="number-steper" node-type="number-steper">
		<button>
			-
		</button>
		<input type="number" min="0" max="99" id="question-price" class="price" name="price" value="1">
		<button>
			+
		</button> </i> ${MONEY_UNIT} </span>
	</#if>
	<!--<label>
	<input type="checkbox" id="question-anonymous">
	匿名</label>
	<label>
	<input type="checkbox" id="question-urgent">
	紧急</label>-->
	<button class="poster-button-post disabled"  disabled="disabled"  onclick="Question.post()" id="question-poster-button"></button>
</div>
<div id="question-extend" class="hide">
	<textarea id="question-content"  node-type="editor"  height="100px"></textarea>
</div>
<div id="knowledge-extend" class="hide"></div>
<div id="help-extend" class="hide">
	<div class="box expert-user-box">
		<span>请设置分类以便查找到更合适的老师!</span>
	</div>
</div>
<div id="searchQuestion"></div>
