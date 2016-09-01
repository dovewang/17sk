<div id="feedback">
  <div id="feedback-title"></div>
  <div id="feedback-prompt" > 请文明发言，您还可以输入<strong>140</strong>字 </div>
  <div id="feedbackTextAreabox">
    <textarea  id="mblog_editor" class="input feedback_textarea" placeholder="说两句吧"></textarea>
    <input type="hidden" value="" id="mblog_media"/>
    <div id="feedback_function"> <a href="javascript:void(0)" class="icon icon-face" onclick="loadFace(this,'mblog_editor')">表情</a> <a href="javascript:void(0)"  class="icon icon-image" style="position: relative;overflow: hidden;" >插入图片
      <form enctype="multipart/form-data" id="imageForm" action="/common/fileupload.html" method="post">
        <input id="file"  type="file" name="filedata" class="file"   onchange="Mblog.uploadImage()"/>
      </form>
      </a>
      <div id="imageDiv" class="popover top" style="top:160px;left:120px;width:120px"> </div>
    </div>
  </div>
  <!--feedbackTextAreabox end-->
  <div id="feedback_publish">
    <button  onclick="Mblog.post();" class="button xbtn post disabled float-right" disabled="disabled" id="mblog-poster-button"></button>
    <div class="clear"></div>
  </div>
</div>
<!--feedback end-->
<div class="right_funcitonbottom"></div>
