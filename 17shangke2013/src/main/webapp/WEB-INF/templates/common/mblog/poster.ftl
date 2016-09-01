<div class="poster" id="poster-wrap">
  <div class="poster-header"><span  id="poster-count">您还可以输入<strong>140</strong>字</span> </div>
  <div id="mblog-poster">
    <input type="hidden" value="" id="mblog_media"/>
    <div class="poster-body">
      <textarea id="mblog_editor"  placeholder="随便说说"></textarea>
    </div>
    <div class="poster-footer"> <a href="javascript:;" onclick="loadFace(this,'mblog_editor')"><i class="kicon-smile"></i>表情</a>
      <form enctype="multipart/form-data" id="imageForm" action="/upload/image.html" method="post" style="display:inline" >
        <a href="javascript:;"  node-type="upload-button" id="file" name="filedata" onchange="Mblog.uploadImage(this)"><i class="kicon-image"></i>插入图片 </a>
      </form>
      <button class="btn btn-primary  disabled"  disabled="disabled"  onclick="Mblog.post()" id="mblog-poster-button">发布</button>
    </div>
  </div>
</div>
