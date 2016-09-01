<#include "/conf/config.ftl" />
<#assign  groupId=RequestParameters["g"]/>
<#assign  role_domain="group"+groupId+":"/>
<#assign  isManager=__USER.hasRole(role_domain+"teacher,"+role_domain+"admin,"+role_domain+"master")/>
<#if isManager>
<button node-type="refresh" class="hide" id="refresh_button_${chapter.chapterId}">刷新</button>
<div id="resourceFormDiv-${chapter.chapterId}"   class="hide">
  <div class="pop-header"> <a node-type="pop-close" class="close" href="javascript:;">×</a>
    <h3>上传资料</h3>
  </div>
  <div class="pop-body">
    <form id="resourceForm-${chapter.chapterId}" action="/courseware/upload.html" method="post" enctype="multipart/form-data" onsubmit="return false">
      <input type="hidden" name="lessonId" value="${chapter.lessonId}">
      <input type="hidden" name="sectionId" value="${chapter.sectionId}">
      <input type="hidden" name="chapterId" value="${chapter.chapterId}">
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
    <button class="button primary" onclick="Courseware.upload(${chapter.chapterId})"> 上传 </button>
  </div>
</div>
</#if>
<div  class="doc-widget chapter" id="chapter${chapter.chapterId}">
  <h3>${chapter.title}</h3>
  <div class="doc-widget-content ">
  <div class="doc-part">
    <h3 class="yellow">课堂资料<#if isManager><span><a rel="#resourceFormDiv-${chapter.chapterId}"  node-type="pop"  setting="{css:{width:500},id:'resourceFormPop-${chapter.chapterId}'}">上传课件</a></span></#if></h3>
    <div class="doc-part-content" >
      <ul node-type="gallery">
      <#if (docs?size>0)>
       <#list docs as doc>
        <#if helper.isImage(doc.http)>
        <li>${doc.name}<span><i class="icon icon-tag"></i>${doc.doc.tags!"未标记"}</span>[<a href="${doc.http}"  class="gallery-item">预览</a>|<a href="#">下载</a><!--|<a href="#">重命名</a>|<a href="#">删除</a>|<a href="#">备注</a>-->]</li>
        <#elseif helper.isDocument(doc.name)>
        <li>${doc.name}<span><i class="icon icon-tag"></i>${doc.doc.tags!"未标记"}</span>[<a href="/doc/preview/${doc.doc.docId}.html" target="_blank">预览</a>|<a href="#">下载</a><!--|<a href="#">重命名</a>|<a href="#">删除</a>|<a href="#">备注</a>-->]</li>
        <#else>
          <li>${doc.name}<span><i class="icon icon-tag"></i>${doc.doc.tags!"未标记"}</span>[<a href="#">下载</a><!--|<a href="#">重命名</a>|<a href="#">删除</a>|<a href="#">备注</a>-->]</li>
       </#if> 
       </#list>
       <#else>
         <div class="placeholder-box">老师暂时没有上传任何资料</div>
      </#if>
      </ul>
    </div>
  </div>
  <div class="doc-part">
    <h3 class="green">练习/作业<#if isManager><span><a href="javascript:;" onclick="Courseware.Chapter.addExercise(${chapter.lessonId},${chapter.sectionId},${chapter.chapterId})">新增</a></span></#if></h3>
    <div class="doc-part-content">
      <ul id="chapter-exercise-${chapter.chapterId}">
        <#if (exercise?size>0)>
       <#list exercise as ex>
        <li>[<a href="#">${flint.timeToDate(ex.dateline,"yyyy-MM-dd")}</a>]${ex.content}</li>
       </#list> 
       <#else>
         <div class="placeholder-box">老师暂时没有布置作业</div>
      </#if>
      </ul>
    </div>
  </div>
  <!--
  <div class="doc-part">
    <h3 class="blue">课后作业及资料<span><a href="javascript:;">新增</a></span></h3>
    <div class="doc-part-content">
      <ul>
        <li>2月15号作业  P25页：1,3,5,6</li>
        <li>2月15号作业  P25页：1,3,5,6</li>
        <li>2月15号作业  P25页：1,3,5,6</li>
      </ul>
    </div>
  </div>
  -->
  <div class="doc-part">
    <h3>考点<#if isManager><span><a href="javascript:;" onclick="Courseware.Chapter.addFocus(${chapter.lessonId},${chapter.sectionId},${chapter.chapterId})">新增</a></span></#if></h3>
    <div class="doc-part-content">
      <ul id="chapter-focus-${chapter.chapterId}">
      <#if (focus?size>0)>
       <#list focus as f>
        <li style="color:red">${f_index+1}.${f.content}</li>
       </#list> 
       <#else>
         <div class="placeholder-box">老师暂时没有添加考点</div>
      </#if>
      </ul>
    </div>
  </div>
  <div class="doc-part">
    <h3 class="black">知识拓展<#if isManager><span><a href="javascript:;" onclick="Courseware.Chapter.editExpand(${chapter.chapterId})">编辑</a></span></#if></h3>
    <div class="doc-part-content" id="chapter_expand${chapter.chapterId}">
         <div class="chapter-expand-edit hide">
            <textarea class="chapter-expand" id="chapter-expand-value${chapter.chapterId}">${chapter.EXPAND!}</textarea>
            <div class="text-right"><button class="button primary" onClick="Courseware.Chapter.saveExpand(${chapter.chapterId},${chapter.lessonId})">保存</button></div>
         </div>
         <div class="chapter-expand-content">
         <#if chapter.expand?exists>
          ${chapter.expand}
          <#else>
            <div class="placeholder-box">暂时没有发布任何内容</div>
         </#if> 
         </div>
    </div>
  </div>
  <!--评论区域-->
  <div class="comment"></div>
  </div>
</div>

