<dl class="form">
  <dd>
    <label>本班科目：</label>
    <span>
    <div data-name="lesson" data-id="lessonId" max="1" min="1" class="combobox-box" node-type="combobox" data-source="/courseware/lessons.html?my=true&g=${groupId}" data-result="lessons" 
    data-value="${chapter.lessonId!}"
    data-item-text="lesson" 
    data-item-value="lessonId" style="width:500px"  item-click="Courseware.newLesson"> <span  class="item add">+</span> </div>
    </span></dd>
  <dd id="newLesson"  style="display:none">
    <dl class="form" style="border:1px  dashed #bbb">
      <dd>
        <label>科目名称：</label>
        <span>
        <input type="text" name="lesson_name" maxlength="20" id="lesson_name"/>
        </span> </dd>
      <dd>
        <label>授课老师：</label>
        <span>
        <input type="text" name="lesson_teacher" id="lesson_teacher" value="${USER_NAME}" readonly uid="${USER_ID}"/>
        </span> </dd>
    </dl>
  </dd>
  <dd>
    <label>上课日期：</label>
    <span>
    <input type="date" name="classtime" id="classtime" format="yyyy-mm-dd" value="${(charpter.classtime!RequestParameters["date"])!Q.now("yyyy-MM-dd")}"/>
    </span></dd>
  <dd>
  <dd>
    <label>上课时间：</label>
    <span class="schedule">
    </span></dd>
  <dd>
    <label>课程标题：</label>
    <span>
    <input name="chapter" type="text" id="chapter_name" value="${chapter.title!}">
    <input name="chapterId" type="hidden" id="chapterId" value="${chapter.chapterId!}">
    </span></dd>
  <dd>
    <textarea node-type="editor"  id="chapter_content"  height="200" placeholder="请在这里输入您的课程内容" name="content">${chapter.content!}</textarea>
  </dd>
  <!--内容编辑区域-->
  <dd>
      <#include "resource.ftl"/>
  </dd>
</dl>
</div>

