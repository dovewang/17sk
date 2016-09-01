<footer>
  <div id="page-footer"></div>
</footer>
<div id="message-wall"> <a href="javascript:void(0)"  id="message-expand-wall"><span ><em class="arrow"></em></span></a>
  <div id="message-content">
    <ul id="im-user-list">
    </ul>
    <div id="im-box" class="box popover right" cim="" style="display:none"> <i class="arrow"><em>◆</em><span>◆</span></i><a href="javascript:;" class="close"  onclick="IM.hide()">×</a>
      <div id="im-header"> 您正在和<span>****</span>聊天 </div>
      <div id="im-user"></div>
      <div id="im-message"></div>
      <div id="im-toolbar">
        <input type="hidden" value="" id="im_media"/>
        <a href="javascript:;" class="icon-face" onclick="loadFace(this,'im-poster-area')">表情</a> <a href="javascript:;" class="icon-image" style="position: relative;overflow: hidden;">插入图片
        <form enctype="multipart/form-data" id="imForm" action="/upload/image.html" method="post">
          <input id="file" type="file" name="filedata" class="file" onchange="IM.upload()">
        </form>
        </a> <a class="enter-room" id="im-enter-room" node-type="pop" setting="{id:'create-im-room',effect:'mac',css:{width:500},onShow : function($pop, options){alert(1);return false;}}" href="/abc/form.html?callback=IM.inviteToRoom">进入教室</a>
        <div style="float:right" id="im_pvm_info"> 您还可以输入<strong style="color:#49C146">100</strong>字 </div>
      </div>
      <div id="im-poster">
        <textarea rows="4" class="input max" name="content" id="im-poster-area" onkeyup="MC.countWord(this,'im_pvm_info')" onkeydown="return MC.wordCheck(event,this)"></textarea>
      </div>
      <div id="im_media_div"  class="popover"></div>
      <div id="im-control">
        <div id="ImSender"> <a id="imSenderButton" href="javascript:;" onclick="IM.send()">发送</a>
          <div id="imSenderChoose"> <a href="javascript:;" id="aImSenderChoose"></a>
            <ul style="display: none" id="im-key">
              <li class="active"> <a href="javascript:;" im-key="1">按Enter键发送</a> </li>
              <li class="line"></li>
              <li> <a href="javascript:;" im-key="2">按Ctrl+Enter键发送</a> </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<#include "../global.footer.ftl"/>