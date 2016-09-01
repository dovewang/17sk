<div node-type="combobox" min="1" data-name="scope"  class="combobox-box" data-value="${RequestParameters["g"]!}" data-id="lesson_scope">
	<#list result.result as g> <span class="item" data-value="${g.groupId}" data-text="${g.name}"> ${g.name} </span> </#list>
</div>
