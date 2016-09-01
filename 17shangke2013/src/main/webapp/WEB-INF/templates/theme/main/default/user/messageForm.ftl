<#include "/conf/config.ftl" />
<form id="messageForm" onsubmit="return false;" action="/user/sendMessage.html">
  <input type="hidden" id="messageForm_receiver" name="receiver" >
  <dl class="form label50">
    <dt class="title1 send-message"></dt>
    <dd class="clearfix">
      <label>收信人：</label>
      <span>
      <input name="username" type="text" id="messageForm_username" class="input" style="width:120px" readonly/>
      </span>
      <div style="float:right" id="pvm_info"> 您还可以输入<strong style="color:#49C146">100</strong>字 </div>
    </dd>
    <dd>
      <label>内&nbsp;&nbsp;容：</label>
      <span>
      <textarea rows="4" class="input max" name="content" id="message_editor" style="width:450px" onkeyup="MC.countWord(this,'pvm_info')" onkeydown="return MC.wordCheck(event,this)"></textarea>
      </span> </dd>
    <dd>
      <label>&nbsp;</label>
      <span><a href="javascript:void(0)"  class="icon-face" onclick="loadFace(this,'message_editor')">表情</a> </span>
      <button  onclick="MC.send();"  class="float-right  button primary"> 确定 </button>
    </dd>
  </dl>
</form>
