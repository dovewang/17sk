<form  id="extendsForm"  method="post" action="/user/updateExtends.html" data-validator-url="/v/user.detail.json">
	<dl class="form label80 input-xlarge">
		<dd>
			<label> 自我介绍： </label>
	  <span> <textarea id="intro"  name="intro"  rows="5"  >${userx.intro!""}</textarea> </span></dd>
		<dd>
			<label> QQ： </label>
			<span>
				<input type="text" id="qq" name="qq"  value="${userx.qq!""}"   />
			</span>
		</dd>
		<dd>
			<label> MSN： </label>
			<span>
				<input type="text" id="msn" name="msn"   value="${userx.msn!""}"   />
			</span>
		</dd>
		<dd>
			<label> 个人主页： </label>
			<span>
				<input type="text" id="home" name="home"   value="${userx.home!""}"    />
			</span>
		</dd>
		<dd>
			<label> 博&nbsp;&nbsp;&nbsp;&nbsp;客： </label>
			<span>
				<input type="text" id="blog" name="blog"  value="${userx.blog!""}"     />
			</span>

		</dd>
		<dd>
			<label> 生&nbsp;&nbsp;&nbsp;&nbsp;日： </label>
			<span>
				<input id="birthday"   name="birthday" value="${birthday!""}"  type="date" min="y[1900],m[1],d[1]" format="yyyy-mm-dd" max="y-4" start="y[1980]"/>
			</span>
		</dd>
		<dd >
			<label> 详细地址： </label>
			<span>
				<input type="text" id="address" name="address"  value="${userx.address!""}"    />
			</span>
		</dd>
		<dd >
			<label>&nbsp;</label>
			<span>
				<button type="button"   onclick="User.updateExtends()"   class="button primary large"  >保存信息</button> </span>
		</dd>
	</dl>
</form>