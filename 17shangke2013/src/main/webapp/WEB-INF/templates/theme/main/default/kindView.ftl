<div align="left">
<dl class="knowledge">
  <dt class="text-left" style="height:30px;line-height:30px; font-weight:bold;" ><span id="knowledge_text"></span><span id="knowledge_tag_span" style="display:none">>
    <input id="knowledge_tag" type="tag"  placeholder="自定义标签" maxlength="10" style="width:80px"/>
    </span> </dt>
  <dd> <#assign grade=kindHelper.getGrades()/>
    <select id="knowledge_grade" name="grade"  size="10"  style="width:120px" onchange="Knowledge.loadKind(this);">
      <#list grade?keys as key>
      <option value="${key}">
      ${grade[key]}
      </option>
      </#list>
    </select>
    <select id="knowledge_kind" name="kind"  size="10" style="width:120px" onchange="Knowledge.loadKnowledge(this,0,1)">
    </select>
  </dd>
</dl>
</div>
<div>如果您的问题无法归入任何子分类，您可以只选择一级分类</div>
<div>
	<button class="button primary large" type="button" node-type="knowledge-action-submit">确定</button>
</div>
