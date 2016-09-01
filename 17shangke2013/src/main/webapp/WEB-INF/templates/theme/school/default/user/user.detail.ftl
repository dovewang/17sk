<form  id="extendsForm"  method="post" action="/user/updateExtends.html"  >
	<dl class="form label80">
		<dd>
			<label> 自我介绍： </label>
	  <span> <textarea id="intro"  name="intro" class="input l300" rows="5"  >${userx.intro!""}</textarea> </span></dd>
		<dd>
			<label> QQ： </label>
			<span>
				<input type="text" id="qq" name="qq" class="input  l300"  value="${userx.qq!""}"   />
			</span>
		</dd>
		<dd>
			<label> MSN： </label>
			<span>
				<input type="text" id="msn" name="msn" class="input l300"  value="${userx.msn!""}"   />
			</span>
		</dd>
		<dd>
			<label> 个人主页： </label>
			<span>
				<input type="text" id="homePage" name="homePage"  class="input l300" value="${userx.home!""}"    />
			</span>
		</dd>
		<dd>
			<label> 博&nbsp;&nbsp;&nbsp;&nbsp;客： </label>
			<span>
				<input type="text" id="blog" name="blog"  value="${userx.blog!""}" class="input l300"     />
			</span>

		</dd>
		<dd>
			<label> 生&nbsp;&nbsp;&nbsp;&nbsp;日： </label>
			<span>
				<input id="birthday"   name="birthday" value="${birthday!""}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'1900-01-01',maxDate:'{%y-6}-%M-%d'})" class="input l300" type="date"/>
			</span>
		</dd>
		<dd>
			<label> 省： </label>
			<span>
				<input type="hidden" id="provinceH" name="provinceH" value="${userx.province}"  />
				<select id="province" name="province"></select> 市：
				<input type="hidden" id="cityH" name="cityH" value="${userx.city}"  />
				<select id="city" name="city"></select>
				<input type="hidden" id="townH" name="townH" value="${userx.town}"  />
				区： <select id="town" name="town"></select> </span>
		</dd>
		<dd>
			<label> 路： </label>
			<span>
				<input type="text" id="road" name="road" class="input l300"  value="${userx.road!""}"    />
			</span>
		</dd>
		<dd>
			<label> 楼&nbsp;&nbsp;&nbsp;&nbsp;栋： </label>
			<span>
				<input type="text" id="ban" name="ban" class="input l300"  value="${userx.ban!""}"   />
			</span>
		</dd>
		<dd >
			<label> 详细地址： </label>
			<span>
				<input type="text" id="address" name="address" class="input l300" value="${userx.address!""}"    />
			</span>
		</dd>
		<dd >
			<label>&nbsp;</label>
			<span>
				<button type="button"   onclick="User.updateExtends()"   class="button save"  >
				</button> </span>
		</dd>
	</dl>
</form>