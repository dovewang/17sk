<#assign result=result! />
<div class="pop-header">
	<h3>班级信息</h3>
    <a href="javascript:void(0)" class="close" node-type="pop-close">×</a>
</div>
<div style="width:550px" class="pop-body">
	<form id="classForm" action="/admin/class/save.html?is=true" data-validator-url="/v/school.class.json">
		<input type="hidden" name="groupId"  value="${result.groupId!0}"/>
		<dl class="form label80 input-260">
			<dd>
				<label><em class="required">*</em>班级名称：</label>
				<span>
					<input type="text" name="name" id="name"  value="${result.name!}" maxlength="50"/>
				</span>
			</dd>
            <dd>
				<label><em class="required">*</em>入学年份：</label>
				<span>
					<input type="date"  format="yyyy" name="year" id="year" readonly  value="${result.year!}" maxlength="50" start="y" max="y"/>
				</span>
			</dd>
            <dd>
				<label><em class="required">*</em>班级类型：</label>
				<span>
					 理科班|文科班|重点班|实验班<i class="icon icon-setting"></i>
				</span>
			</dd>
			<dd>
				<label>班级简介：</label>
				<span> <textarea name="intro" rows="6"  id="intro">${result.intro!"无"}</textarea> </span>
			</dd>
			<dd>
				<label>备注：</label>
				<span>
					<input type="text" name="memo" id="memo"   value="${result.memo!""}" maxlength="100"/>
				</span>
			</dd>
		</dl>
	</form>
</div>
<div class="pop-footer">
	<button type="button" node-type="pop-close" class="btn">
		取消
	</button>
    	<button type="button" onclick="School.saveClass(this);" node-type="pop-close-trigger" class="btn btn-primary">
		确定
	</button>
</div>
