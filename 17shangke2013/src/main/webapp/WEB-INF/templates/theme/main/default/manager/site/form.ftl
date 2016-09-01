<div class="pop-header">
	<a href="#" class="close" node-type="pop-close">×</a>
	<h3>学校信息</h3>
</div>
<div class="pop-body">
	<#if add?exists>
	<form id="schoolForm" method="post" action="/manager/site/create.html" enctype="multipart/form-data" data-validator-url="/v/manager.addSchool.json">
		<input name="status" type="hidden"  value="1" />
		<dl class="form label120 input-260">
			<dd>
				<label>学校名称：</label>
				<span>
					<input name="name" type="text" id="name" placeholder="请您输入学校名称" />
				</span>
			</dd>
			<dd>
				<label>学校简介：</label>
				<span>
				<textarea id="intro" name="intro" node-type="editor" style="width:480px;height:150px;"></textarea> 
				</span>
			</dd>
			<dd>
				<label>本校主站网站：</label>
				<span>
					<div class="input-prepend">
						<i class="add-on">http://</i>
						<input name="homepage" type="text" id="homepage"    placeholder="请输入您学校的官方网站" style="width:208px"/>
					</div> </span>
			</dd>
			<dd>
				<label>网站LOGO：</label>
				<span>
					<input type="file" name="logoFile" id="logoFile" readOnly="true"/>
				</span>
			</dd>
			<dd>
				<label>本站地址：</label>
				<span>
					<div class="input-prepend input-append">
						<i class="add-on">http://</i>
						<input name="domain" type="text" id="domain"  style="width:100px" />
						<i class="add-on">.17shangke.com</i>
					</div> </span>
			</dd>
			<dd>
				<label>移动版：</label>
				<span>
					<div class="input-prepend input-append">
						<i class="add-on">http://</i>
						<input name="mdomain" type="text" id="mdomain"   style="width:90px"  readonly="readonly"/>
						<i class="add-on">.m.17shangke.com</i>
					</div> </span>
			</dd>
			<dd>
				<label>网站模板：</label>
				<span>
					<input type="hidden" name="themeId" value="2" />
					<input value="默认风格" disabled="disabled" type="text"/>
				</span>
			</dd>
			<dd>
				<label>组织机构代码证编号：</label>
				<span>
					<input name="cert" placeholder="请输入您学校的组织机构代码证编号" type="text" id="cert"  />
					</span>
			</dd>
			<dd>
				<label>选择组织机构代码证：</label>
				<span>
					<input type="file" name="file" id="file" readOnly="true"/>
				</span>
			</dd>
			<dd>
				<label>联系人：</label>
				<span>
					<input name="contact" type="text" id="contact"    placeholder="请输入联系人"/>
				</span>
			</dd>
			<dd>
				<label>联系电话：</label>
				<span>
					<input name="phone" type="text" id="phone"    placeholder="请输入联系人的电话"/>
				</span>
			</dd>
			<dd>
				<label>联系地址：</label>
				<span>
					<input name="address" type="text" id="address"   placeholder="请输入联系的地址"/>
				</span>
			</dd>
			<dd>
				<label>联系人邮箱：</label>
				<span>
					<input name="email" type="text" id="email"   placeholder="管理员登录账户" />
				</span>
			</dd>
			<dd>
				<label>传真：</label>
				<span>
					<input name="fax" type="text" id="fax"   />
				</span>
			</dd>
		</dl>
	</form>
	<#else>
	<form id="schoolForm" method="post" action="/manager/site/update.html" enctype="multipart/form-data" data-validator-url="/v/manager.editSchool.json">
		<input name="schoolId" type="hidden" id="schoolId" value="${school.schoolId}" />
		<input name="status" type="hidden"  value="1" />
		<input name="createTime" type="hidden"  value="${school.createTime!}" />
		<input name="certDir" type="hidden" value="${school.certDir!}"/>
		<input name="logo" type="hidden" value="${school.logo!}"/>
		<dl class="form label120 input-260">
			<dd>
				<label>学校名称：</label>
				<span>
					<input name="name" type="text" id="name" old-data="${school.name}" value="${school.name}" placeholder="请您输入学校名称" />
				</span>
			</dd>
			<dd>
				<label>学校简介：</label><span><textarea id="intro" name="intro" node-type="editor" style="width:480px;height:150px;">${school.intro!}</textarea> </span>
			</dd>
			<dd>
				<label>本校主站网站：</label>
				<span>
					<div class="input-prepend">
						<i class="add-on">http://</i>
						<input name="homepage" type="text" id="homepage" value="${school.homepage!}"  placeholder="请输入您学校的官方网站"/>
					</div> 
				</span>
			</dd>
			<dd>
				<label>网站LOGO：</label>
				<span>
					<input type="file" name="logoFile" id="logoFile" readOnly="true"/>
					<div><img  width="170" height="48" src="${school.logo!}"/>
					</div> </span>
			</dd>
			<dd>
				<label>本站地址：</label>
				<span>
					<div class="input-prepend input-append">
						<i class="add-on">http://</i>
						<input name="domain" type="text" id="domain" old="${school.domain!}" value="${school.domain!}" style="width:100px" />
						<i class="add-on">.17shangke.com</i>
					</div> </span>
			</dd>
			<dd>
				<label>移动版：</label>
				<span>
					<div class="input-prepend input-append">
						<i class="add-on">http://</i>
						<input name="mdomain" type="text" id="mdomain" value="${school.domain!}" style="width:90px" />
						<i class="add-on">.m.17shangke.com</i>
					</div> </span>
			</dd>
			<dd>
				<label>网站模板：</label>
				<span>
					<input type="hidden" name="themeId" value="${school.themeId}" />
					<input value="默认风格" disabled="disabled" type="text"/>
				</span>
			</dd>
			<dd>
				<label>组织机构代码证编号：</label>
				<span>
					<input name="cert" placeholder="请输入您学校的组织机构代码证编号" type="text" id="cert" value="${school.cert!}" />
			</dd>
			<dd>
				<label>选择组织机构代码证：</label>
				<span>
					<input type="file" name="file" id="file" readOnly="true"/>
				</span>
			</dd>
			<dd>
				<label>联系人：</label>
				<span>
					<input name="contact" type="text" id="contact" value="${school.contact!}"  placeholder="请输入联系人"/>
				</span>
			</dd>
			<dd>
				<label>联系电话：</label>
				<span>
					<input name="phone" type="text" id="phone" value="${school.phone!}"  placeholder="请输入联系人的电话"/>
				</span>
			</dd>
			<dd>
				<label>联系地址：</label>
				<span>
					<input name="address" type="text" id="address" value="${school.address!}"  placeholder="请输入联系的地址"/>
				</span>
			</dd>
			<dd>
				<label>联系人邮箱：</label>
				<span>
					<input name="email" type="text" id="email" value="${school.email!}"  placeholder="管理员登录账户" readOnly/>
				</span>
			</dd>
			<dd>
				<label>传真：</label>
				<span>
					<input name="fax" type="text" id="fax" value="${school.fax!}" />
				</span>
			</dd>
		</dl>
	</form>
	</#if>
</div>
<div class="pop-footer">
	<button  class="btn btn-primary" node-type="form-submit" form-id="schoolForm" success="location.reload();">
		保存
	</button>
	<button class="btn" node-type="pop-close">
		取消
	</button>
</div>