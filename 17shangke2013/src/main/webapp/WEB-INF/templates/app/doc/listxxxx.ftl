<#include "/conf/config.ftl" />
<button class="button" rel="#resourceFormDiv" node-type="pop" setting="{css:{width:500},id:'resourceFormPop'}"> 上传资料 </button>
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
          <dd><label>标签：</label>
          <span>
          <input type="tag" name="tags">
          <div>常用标签：家庭作业  课程练习  课件资料</div>
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
<table class="table table-striped table-hover ">
  <thead>
    <tr>
      <th>文件名</th>
      <th>大小</th>
      <th>上传者</th>
      <th>上传时间</th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody>
  <#if (result.result?size)==0>
  <tr>
    <td  colspan="6"><div class="placeholder-box">暂时没有任何资料</div></td>
  </tr>
  <#else>
  <#list result.result as d>
  <tr id="doc${d.doc.docId}"  style="cursor:pointer">
    <td><img src="${IMAGE_DOMAIN}/theme/base/images/filetype/s/${d.extend!"unknown"}.png"  width="16" height="16" alt="${d.name}" /> <span id="dn${d.doc.docId}">
      ${d.name}
      </span><a href="#"><i  class="icon icon-tag"></i>${d.doc.tags!"未标记"}</a></td>
    <td>${helper.fileSize(d.size)}</td>
    <td><a href="/u/${d.doc.owner}.html" target="_blank" usercard="${d.doc.owner}"></a></td>
    <td>${flint.timeToDate(d.createTime,"yyyy-MM-dd HH:mm:ss")}</td>
    <td><a href="javascript:void(0)">下载</a> <#if USER_ID==d.doc.owner?string>|<a href="javascript:void(0)" onclick="DM.del(this,'${d.doc.docId}','doc${d.doc.docId}');">删除</a></#if></td>
  </tr>
  <tr class="active" id="rn${d.doc.docId}" style="display:none">
    <td><input type="text" name="name" id="dname${d.doc.docId}" class="input"value="${d.name}" style="width:78%" maxlength="20"/>
      <span class="con_mousehover">[ <a href="javascript:void(0)" onclick="DM.rename('${d.doc.docId}',true);return false;">确定</a> | <a href="javascript:void(0)" onclick="DM.showRename('${d.doc.docId}',false,true);return false;">取消</a> ] </span></td>
    <td>${helper.fileSize(d.size)}</td>
    <td>${flint.timeToDate(d.createTime,"yyyy-MM-dd HH:mm:ss")}</td>
  </tr>
  </#list>
    </tbody>
  
  <tfoot>
    <tr>
      <td colspan="3"><@flint.simplepage  result,"" /></td>
    </tr>
  </tfoot>
  </#if>
</table>
