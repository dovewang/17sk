<#assign ask=RequestParameters["ask"]!"false" />
<#if ask=="true">
<form id="messageForm" onsubmit="return false;" action="/user/sendMessage.html">
	<input type="hidden" id="messageForm_receiver" name="receiver" >
	<input type="hidden" name="ask" value="true">
	<dl class="form">
		<dt class="title1 ask-question"></dt>
		<dd>
			<label style="width:50px;">询问人：</label><span>
				<input name="username" type="text" id="messageForm_username" class="input" style="width:120px" readonly/>
			</span>
			<div style="float:right" id="pvm_info">
				您还可以输入<strong style="color:#49C146">100</strong>字
			</div>
		</dd>
		<dd>
			<label style="width:50px;">内&nbsp;&nbsp;容：</label><span> 				<textarea rows="4" class="input max" name="content" id="message_editor" style="width:450px" onkeyup="MC.countWord(this,'pvm_info')" onkeydown="return MC.wordCheck(event,this)">老师，您忙吗？我有个问题需要请教一下：#输入问题#</textarea></span>
		</dd>
		<dd>
			<div id="feedback_function" style="background:none; width:500px; margin-left:40px;">
				<a href="javascript:void(0)"  class="icon icon-face" onclick="loadFace(this,'message_editor')">表情</a>
				<button  onclick="MC.send();return false;"class="float-right button green small"  style="margin-right:20px">确定</button>
			</div>
		</dd>
	</dl>
</form>
<#else>
<form id="messageForm" onsubmit="return false;" action="/user/sendMessage.html">
	<input type="hidden" id="messageForm_receiver" name="receiver" >
	<dl class="form">
		<dt class="title1 send-message"></dt>
		<dd>
			<label style="width:60px;">收信人：</label><span>
				<input name="username" type="text" id="messageForm_username"   style="width:120px" readonly/>
			</span>
			<div style="float:right" id="pvm_info">
				您还可以输入<strong style="color:#49C146">100</strong>字
			</div>
		</dd>
		<dd>
			<label style="width:60px;">内&nbsp;&nbsp;容：</label><span> <textarea rows="4"  name="content" id="message_editor" style="width:420px" onkeyup="MC.countWord(this,'pvm_info')" onkeydown="return MC.wordCheck(event,this)"></textarea></span>
		</dd>
		<dd>
			<div id="feedback_function" style="background:none; width:500px; margin-left:40px;">
				<a href="javascript:void(0)"  onclick="loadFace(this,'message_editor')"><i class="kicon-smile"></i>表情</a>
                <button  onclick="MC.send();return false;"class="float-right  btn btn-primary"  style="margin-right:20px">确定</button>
			</div>
		</dd>
	</dl>
</form>
</#if> 