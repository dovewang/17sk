<div  class="doc-widget" id="chapter${chapter.chapterId}">
  <div class="doc-widget-body clearfix">
    <div class="doc-widget-left">22222</div>
    <div class="doc-widget-content "> 
      <div>${chapter.content!"暂时没有课程内容"}</div>
       <#assign edit=false>
      <#include "resource.ftl"/>
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
  </div>
  <div class="doc-widget-footer"> </div>
</div>
