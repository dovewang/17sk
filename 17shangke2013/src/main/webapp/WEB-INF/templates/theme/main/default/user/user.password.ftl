<form id="passwordForm" action="/user/changePassword.html"  data-validator-url="/v/user.password.json">
	<dl class="form label80 input-xlarge">
		<dd>
			<label><em class="required">*</em> 旧&nbsp;密&nbsp;码：</label><span>
				<input type="password" id="oldpassword"   name="oldpassword"  class="password" />
			</span>
		</dd>
		<dd>
			<label><em class="required">*</em> 新&nbsp;密&nbsp;码：</label><span>
				<input type="password" id="newpassword" name="newpassword"   class="password"/>
			</span>
		</dd>
		<dd>
			<label><em class="required">*</em> 密码确认：</label><span>
				<input type="password" id="repeartpassword" name="repeartpassword"   class="password"/>
			</span>
		</dd>
		<dd>
			<label>&nbsp;</label><span> <button type="button" class="button primary large"   onclick="User.updatePassword()" >更新密码</button></span>
		</dd>
	</dl>
</form>