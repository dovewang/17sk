<#include "/conf/config.ftl" />
<#assign  groupId=RequestParameters["g"]/>
<#assign  role_domain="group"+groupId+":"/>
<#assign  isManager=__USER.hasRole(role_domain+"teacher,"+role_domain+"admin,"+role_domain+"master")/>
<div class="pop-header"><a node-type="pop-close" class="close" href="javascript:;">×</a>
  <h3></h3>
  <span class="title pull-left">
  ${chapter.title}
  </span><span class="title pull-right">
  <label>
    <input type="checkbox">
    显示大纲</label>
  </span><span class="title pull-right">
  ${flint.timeToDate(chapter.classtime,"yyyy-MM-dd")}
  </span> </div>
<div class="pop-body">
  <div  class="doc-widget" id="chapter${chapter.chapterId}">
    <div class="doc-widget-body clearfix">
      <div class="doc-widget-left">22222</div>
      <div class="doc-widget-content "> 
        <!--文本--> 
        <#list resources.list0 as r>
        <div class="doc-part">
          <div>
            ${r.text}
          </div>
        </div>
        </#list> 
        <!--图片-->
        <div class="doc-part">
          <h6>图片资料</h6>
          <ul  node-type="gallery">
              <#list resources.list1 as r> 
              <li>
             <#if r.docId=0>
               <a href="${r.url}"  class="gallery-item" data-ajax-image="true">预览</a>
             <#else>
             <a href="/doc/preview/${r.docId}.html"  class="gallery-item" data-ajax-image="true">预览</a>
             </#if>
             </li>
          </#list> 
           <li class="add">+</li>
         </ul>
        </div>
        <!--文档-->
        <div class="doc-part">
          <h6>文档资料</h6>
          <ul> <#list resources.list2 as r>
          <li>
            ${r.title!}
            <#if r.docId=0>
               <a href="${r.url}" target="_blank">查看</a> 
            <#else>
            <a href="/doc/preview/${r.docId}.html" target="_blank">下载</a><a href="/doc/preview/${r.docId}.html" target="_blank">预览</a> 
             </#if>
             </li>
            </#list> 
            <li class="add">+</li>
            </ul>
        </div>
        <!--视频-->
        <div class="doc-part">
          <h6>视频资料</h6>
          <ul> 
             <#list resources.list3 as r>
             <li>
              <#if r.docId=0>
                  <a href="${r.url}" target="_blank">查看</a> 
              <#else>
                  <a href="/doc/preview/${r.docId}.html" target="_blank">预览</a> 
             </#if>
             </li>
            </#list> 
            <li class="add">+</li>
          </ul>
        </div>
        <!--音频-->
        <div class="doc-part">
          <h6>音频资料</h6>
          <ul>
             <#list resources.list4 as r>
             <li>
            <#if r.docId=0>
                  <a href="${r.url}" target="_blank">查看</a> 
              <#else>
                  <a href="/doc/preview/${r.docId}.html" target="_blank">预览</a> 
             </#if>
             </li>
            </#list> 
             <li class="add">+</li>
            </div>
        </ul>
       </div>
      <div class="doc-widget-right">
        <ul class="tab">
          <li>提问</li>
          <li>我的笔记</li>
        </ul>
        <!--评论区域-->
        <div class="comment">
          <div class="comment-header">
            <div class="comment-poster">
              <input name="" type="text">
            </div>
          </div>
          <div class="comment-body">
            <div class="comment-list"></div>
          </div>
        </div>
      </div>
    </div>
    <div class="doc-widget-footer"> </div>
  </div>
</div>
<div class="pop-footer"> </div>
