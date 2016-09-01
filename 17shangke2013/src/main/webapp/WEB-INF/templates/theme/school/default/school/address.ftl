<div class="pop-header">
	<a node-type='pop-close' class='close' href='javascript:;'>×</a>
	<h3>学校上课地址管理</h3>
</div>
<div class="pop-body">
	<form action="/school/address.add.html" method="post" id="addressForm"  data-validator-url="/v/school.address.json">
		<#assign school_id=RequestParameters["id"]?number?long/>
		<#assign sc=domainHelper.getDomainById(school_id)/>
		<input type="hidden" name="owner" value="${school_id}">
		<dl class="form" style="width: 500px">
			<dd>
				<label><em class="required">*</em>上课校区： </label>
				<span>
					<input    style="width: 150px"   name="main"    type="text"    placeholder="学校名" value="${sc.name}"  maxlength="30"/>
					<input     style="width: 150px"   name="branch"    type="text"      placeholder="校区" maxlength="50"/>
				</span>
			</dd>
			<dd>
				<label><em class="required">*</em>所在地区： </label>
				<span><i  node-type="city-selector" node-name="area" ></i> </span>
			</dd>
			<dd>
				<label><em class="required">*</em>详细地址： </label>
				<span>
					<input name="address"  maxlength="100"/>
				</span>
			</dd>
			<dd>
				<label><em class="required">*</em>联系方式： </label>
				<span>
					<input type="text"  name="contact"  placeholder="联系人" maxlength="20"/>
					<input type="text"   name="phone" placeholder="联系电话"  maxlength="20"/>
				</span>
			</dd>
			<dd>
				<label>&nbsp;</label>
				<span>
					<button class="button" type="button" onclick="School.saveAddress()">
						保存
					</button></span>
			</dd>
		</dl>
	</form>
	<div>
		<table class="table table-striped table-bordered table-condensed" id="address-list">
			<thead>
				<tr>
					<td>学校名</td>
					<td>校区</td>
					<td>地址</td>
					<td>联系方式</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<#list result as item>
				<tr id="address${item.id}">
					<td>${item.main}</td>
					<td>${item.branch}</td>
					<td><span city="${item.area}" node-type="city-label"></span>${item.address}</td>
					<td>${item.contact}
					<br/>
					${item.phone}</td>
					<td><a href="javascript:;" onclick="School.selectAddress(${item.id})">选择</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:;"  node-type="pop-confirm" data-message="您确定要删除吗?" ok="School.deleteAddress(${item.id})">删除</a></td>
				</tr>
				</#list>
			</tbody>
		</table>
	</div>
</div>
<div class="pop-footer">

</div>