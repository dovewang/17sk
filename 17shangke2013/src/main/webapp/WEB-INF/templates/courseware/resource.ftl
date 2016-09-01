<!--文本-->
<div id="chapterResource-container">
<#assign edit=edit!true/>
<#list resources.list0 as r>
<div class="doc-part clearfix">
  <h6>
    ${r.title}
  </h6>
  <div>
    ${r.text}
  </div>
</div>
</#list> 
<#if edit>
<div class="doc-part clearfix">
  <h6>文本资料</h6>
<ul>
<li class="add" data-type="text" onclick="Courseware.add(this)">+</li>
</div>
</#if> 
<div id="chapterResource_text"></div>
<!--图片--> 
<#if edit||(resources.list1?size>0)>
<div class="doc-part clearfix" >
  <h6>图片资料</h6>
  <ul  node-type="gallery">
    <#list resources.list1 as r>
    <li><i class="cover image"></i><span>
      ${r.title}
      <br/>
      <em class="gray">
      ${helper.passTime(r.dateline)}
      </em>
      <div class="doc-link"> <#if r.docId=0> <a href="${r.url}"  class="gallery-item" data-ajax-image="true">预览</a> <#else> <a href="/doc/preview/${r.docId}.html"  class="gallery-item" data-ajax-image="true">预览</a> </#if> </div>
      </span> </li>
    </#list>
    <#if edit>
    <li class="add" data-type="image" onclick="Courseware.add(this)">+</li>
    </#if>
  </ul>
</div>
<div id="chapterResource_image"></div>
</#if>
      <#if edit||(resources.list2?size>0)> 
      <!--文档-->
      <div class="doc-part clearfix">
  <h6>文档资料</h6>
  <ul>
          <#list resources.list2 as r>
          <li> <i class="cover document"></i><span>
            ${r.title}
            <br/>
            <em class="gray">
          ${helper.passTime(r.dateline)}
          </em>
            <div class="doc-link"> <#if r.docId=0> <a href="${r.url}"   target="_blank">查看</a> <#else> <a href="/doc/preview/${r.docId}.html"  target="_blank">预览</a> | <a href="/doc/download/${r.docId}.html" target="_blank">下载</a> </#if> </div>
            </span> </li>
          </#list>
          <#if edit>
          <li class="add" data-type="document" onclick="Courseware.add(this)">+</li>
          </#if>
        </ul>
</div>
</#if>
<div id="chapterResource_document"></div>
<#if edit||(resources.list3?size>0)> 
<!--视频-->
<div class="doc-part clearfix">
  <h6>视频资料</h6>
  <ul>
    <#list resources.list3 as r>
    <li> <i class="cover video"></i><span>
      ${r.title}
      <br/>
      <em class="gray">
      ${helper.passTime(r.dateline)}
      </em>
      <div class="doc-link"> <#if r.docId=0> <a href="${r.url}"   target="_blank">查看</a> <#else> <a href="/doc/preview/${r.docId}.html"  target="_blank">预览</a> | <a href="/doc/download/${r.docId}.html" target="_blank">下载</a> </#if> </div>
      </span> </li>
    </#list>
    <#if edit>
    <li class="add" data-type="video" onclick="Courseware.add(this)">+</li>
    </#if>
  </ul>
</div>
</#if>
<div id="chapterResource_video"></div>
<#if edit||(resources.list4?size>0)> 
<!--音频-->
<div class="doc-part clearfix">
  <h6>音频资料</h6>
  <ul>
    <#list resources.list4 as r>
    <li> <i class="cover audio"></i><span>
      ${r.title}
      <br/>
      <em class="gray">
      ${helper.passTime(r.dateline)}
      </em>
      <div class="doc-link"> <#if r.docId=0> <a href="${r.url}"   target="_blank">查看</a> <#else> <a href="/doc/preview/${r.docId}.html"  target="_blank">预览</a>|<a href="/doc/download/${r.docId}.html" target="_blank">下载</a> </#if> </div>
      </span> </li>
    </#list>
    <#if edit>
    <li class="add" data-type="audio" onclick="Courseware.add(this)">+</li>
    </#if>
  </ul>
</div>
</#if>
<div id="chapterResource_audio"></div>
</div>
<!--资料添加的模版-->
<div id="template_chapter_resource" style="display:none">
  <div id="template_chapter_resource_text">
    <div class="box" style="border:1px  dashed #bbb">
      <dl class="form" tid="chapter_resource_text">
        <dd>
          <label>标题：</label>
          <span>
          <input type="text" name="title">
          <div class="gray">您可以在这里发布，例如练习，课堂练习等信息，如果您需要编辑</div>
          </span></dd>
        <dd>
        <dd>
          <label>内容：</label>
          <textarea  name="text"  rows="3"></textarea>
        </dd>
        <dd>
            <label>&nbsp;</label>
            <span>
            <button type="button" class="btn btn-primary" onclick="Courseware.text()">保存</button>
            </span></dd>
      </dl>
    </div>
  </div>
  <!--文档-->
  <div id="template_chapter_resource_document">
    <div class="box" style="border:1px  dashed #bbb">
      <div class="tab-header simple">
        <ul>
          <li><a href="javascript:;" view="#resource_tab">本站上传</a></li>
          <li><a href="javascript:;"  view="#resource_tab_out">外部引用</a></li>
        </ul>
      </div>
      <div tid="resource_tab">
        <form action="/courseware/resource/upload.html" method="post" tid="chapterResouceForm" enctype="multipart/form-data" onsubmit="return false">
          <input type="hidden" tid="chapterResouceForm_lessonId" name="lessonId" value="">
          <input type="hidden" tid="chapterResouceForm_chapterId" name="chapterId" value="">
          <dl class="form">
            <dd>
              <label>文档名称：</label>
              <span>
              <input type="text" name="name">
              </span></dd>
            <dd>
              <label>文件：</label>
              <span>
              <input type="file" name="filedata">
              </span></dd>
            <dd>
              <label>&nbsp;</label>
              <span>
              <button type="button" class="btn btn-primary" onclick="Courseware.upload()">上传</button>
              </span></dd>
          </dl>
        </form>
      </div>
      <div tid="resource_tab_out" class="hide">
        <dl class="form">
          <dd>
            <label>文档名称：</label>
            <span>
            <input type="text" name="name">
            </span></dd>
          <dd>
            <label>文档URL地址：</label>
            <span>
            <input type="text" name="url">
            </span></dd>
          <dd>
            <label>&nbsp;</label>
            <span>
            <button type="button" class="btn btn-primary" onclick="Courseware.external(2)">确定</button>
            </span></dd>
        </dl>
      </div>
    </div>
  </div>
  <div id="template_chapter_resource_image">
    <div class="box" style="border:1px  dashed #bbb">
      <div class="tab-header simple">
        <ul>
          <li><a href="javascript:;" view="#resource_tab">本站上传</a></li>
          <li><a href="javascript:;"  view="#resource_tab_out">外部引用</a></li>
        </ul>
      </div>
      <div id="resource_tab">
        <form action="/courseware/resource/upload.html" method="post" tid="chapterResouceForm" enctype="multipart/form-data" onsubmit="return false">
          <input type="hidden" tid="chapterResouceForm_lessonId" name="lessonId" value="">
          <input type="hidden" tid="chapterResouceForm_chapterId" name="chapterId" value="">
          <dl class="form">
            <dd>
              <label>图片名称：</label>
              <span>
              <input type="text" name="name">
              </span></dd>
            <dd>
              <label>图片文件：</label>
              <span>
              <input type="file" name="filedata">
              </span></dd>
            <dd>
              <label>&nbsp;</label>
              <span>
              <button type="button" class="btn btn-primary" onclick="Courseware.upload()">上传</button>
              </span></dd>
          </dl>
        </form>
      </div>
      <div tid="resource_tab_out" class="hide">
        <dl class="form">
          <dd>
            <label>图片名称：</label>
            <span>
            <input type="text" name="name">
            </span></dd>
          <dd>
            <label>图片URL地址：</label>
            <span>
            <input type="text" name="url">
            </span></dd>
          <dd>
            <label>&nbsp;</label>
            <span>
            <button type="button" class="btn btn-primary" onclick="Courseware.external(1)">确定</button>
            </span></dd>
        </dl>
      </div>
    </div>
  </div>
  <div id="template_chapter_resource_video">
    <div class="box" style="border:1px  dashed #bbb">
      <div class="tab-header simple">
        <ul>
          <li><a href="javascript:;" view="#resource_tab">本站上传</a></li>
          <li><a href="javascript:;"  view="#resource_tab_out">外部引用</a></li>
        </ul>
      </div>
      <div  tid="resource_tab">
        <form action="/courseware/resource/upload.html" method="post" tid="chapterResouceForm" enctype="multipart/form-data" onsubmit="return false">
          <input type="hidden" tid="chapterResouceForm_lessonId" name="lessonId" value="">
          <input type="hidden" tid="chapterResouceForm_chapterId" name="chapterId" value="">
          <dl class="form">
            <dd>
              <label>视频名称：</label>
              <span>
              <input type="text" name="name">
              </span></dd>
            <dd>
              <label>视频文件：</label>
              <span>
              <input type="file" name="filedata">
              </span></dd>
            <dd>
              <label>&nbsp;</label>
              <span>
              <button type="button" class="btn btn-primary" onclick="Courseware.upload()">上传</button>
              </span></dd>
          </dl>
        </form>
      </div>
      <div class="hide"  tid="resource_tab_out">
        <dl class="form">
          <dd>
            <label>视频名称：</label>
            <span>
            <input type="text" name="name">
            </span></dd>
          <dd>
            <label>视频URL地址：</label>
            <span>
            <input type="text" name="url">
            </span></dd>
          <dd>
            <label>&nbsp;</label>
            <span>
            <button type="button" class="btn btn-primary" onclick="Courseware.external(3)">确定</button>
            </span></dd>
        </dl>
      </div>
    </div>
  </div>
  <div id="template_chapter_resource_audio">
    <div class="box" style="border:1px  dashed #bbb">
      <div class="tab-header simple">
        <ul>
          <li><a href="javascript:;" view="#resource_tab">本站上传</a></li>
          <li><a href="javascript:;"  view="#resource_tab_out">外部引用</a></li>
        </ul>
      </div>
      <div tid="resource_tab">
        <form action="/courseware/resource/upload.html" method="post" tid="chapterResouceForm" enctype="multipart/form-data" onsubmit="return false">
          <input type="hidden" tid="chapterResouceForm_lessonId" name="lessonId" value="">
          <input type="hidden" tid="chapterResouceForm_chapterId" name="chapterId" value="">
          <dl class="form">
            <dd>
              <label>音频名称：</label>
              <span>
              <input type="text" name="name">
              </span></dd>
            <dd>
              <label>音频文件：</label>
              <span>
              <input type="file" name="filedata">
              </span></dd>
            <dd>
              <label>&nbsp;</label>
              <span>
              <button type="button" class="btn btn-primary" onclick="Courseware.upload()">上传</button>
              </span></dd>
          </dl>
        </form>
      </div>
      <div class="hide"  tid="resource_tab_out">
        <dl class="form">
          <dd class="form">
            <label>音频名称：</label>
            <span>
            <input type="text" name="name">
            </span></dd>
          <dd>
            <label>音频URL地址：</label>
            <span>
            <input type="text" name="url">
            </span></dd>
          <dd>
            <label>&nbsp;</label>
            <span>
            <button type="button" class="btn btn-primary" onclick="Courseware.external(4)">确定</button>
            </span></dd>
        </dl>
      </div>
    </div>
  </div>
</div>

