<button class="button" rel="#resourceFormDiv" node-type="pop" setting="{css:{width:500}}"> 上传资料 </button>
<!--上传文件表单-->
<div id="resourceFormDiv"   class="hide">
  <div class="pop-header"> <a node-type="pop-close" class="close" href="javascript:;">×</a>
    <h3>上传资料</h3>
  </div>
  <div class="pop-body">
    <form id="resourceForm"  method="post" enctype="multipart/form-data">
      <input type="hidden" name="scope" value="group">
      <dl class="form input-180">
        <dd>
          <label>资料名称：</label>
          <span>
          <input type="text" name="name">
          </span> </dd>
        <dd>
          <label>选择文件：</label>
          <span>
          <input type="file" name="filedata">
          </span> </dd>
        <dd>
          <label>标签：</label>
          <span>
          <input type="tag" name="tags">
          </span> </dd>
        <dd>
          <label>说明：</label>
          <span>
          <textarea name="memo"></textarea>
          </span> </dd>
      </dl>
    </form>
  </div>
  <div class="pop-footer">
    <button class="button primary" onclick="Group.share()"> 上传 </button>
  </div>
</div>
