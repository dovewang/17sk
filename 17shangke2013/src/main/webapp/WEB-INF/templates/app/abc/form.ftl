<#include "/conf/config.ftl"/>
<#assign  subjectId=RequestParameters["i"]!"0" />
<#assign  subjectType=RequestParameters["t"]!"0" />
<#assign  subject=RequestParameters["s"]!"" />
<#assign  price=RequestParameters["p"]!"0" />
<#assign  callback=RequestParameters["callback"]! />
<div class="pop-header">
	<a node-type='pop-close' class='close' href='javascript:;'>×</a>
	<h3>创建课室</h3>
</div>
<div class="pop-body" id="room-create-div">
	<form action="/abc/create.html" method="post" onsubmit="return false;" id="createRoomForm" data-validator-url="/v/room.create.json" data-change-validate="false">
		<input name="type" value="${subjectType}"  type="hidden"/>
		<input name="subjectId" value="${subjectId}"  type="hidden"/>
		<dl class="form input-large">
			<dd>
				<label>主题：</label>
				<span>
					<input type="text" name="subject" id="subject" class="input" style="width:320px" value="${subject}" maxlength="100"/>
				</span>
			</dd>
			<!--
			<dd>
			<label>邀请用户：</label><span>
			<input type="text" name="inv" id="inv" class="input"/><a   id="user-select-pop2" class="button"  href="/user/select.html" title="辅导老师" node-type="popup"  popW="500">选择</a>
			</span>
			</dd>
			-->
			<dd>
				<label>开始时间：</label>
				<span>
					<input name="startTime"  type="date"  id="startTime" value="立即" min="d"/>
				</span>
			</dd>
			<dd>
				<label>预计时长：</label>
				<span>
					<input name="time" type="number" class="input" style="width:120px;" id="time" value="30" min="10" max="120"/>
					分钟</span>
			</dd>
			<#if FEE_MODLE>
			<dd>
				<label>课程费用：</label>
				<span>
					<input name="price"  class="input"  class="input" id="price" value="${price}"  type="number" style="width:120px;" maxlength="8"/>
				</span>
			</dd>
			</#if>
			<dd>
				<label>密码：</label>
				<span>
					<input type="password" name="password" id="password" class="input"/>
				</span>
			</dd>
			<dd>
				<label>密码确定：</label>
				<span>
					<input type="password" name="repassword" id="repassword" class="input"/>
				</span>
			</dd>
		</dl>
	</form>
</div>
<div class="pop-footer">
	<#if callback=="">
	<#if subjectType=="0">
	<button onclick="$.createRoom();return false;"class="button large " >
		创建
	</button>
	<button onclick="$.createRoom(true);return false;" class="button large primary">
		快速创建
	</button>
	<#elseif subjectType=="1">
	<button node-type="pop-close"  class="button large ">
		取消
	</button>
	<button onclick="$.createRoom(true);return false;" class="button large primary">
		开始解答
	</button>
	<#else>
	<button onclick="$.createRoom(true,'${callback}');return false;" class="button large primary">
		创建教室
	</button>
	</#if>
	<#else>
	<button onclick="$.createRoom(true,'${callback}');return false;" class="button large primary">
		创建教室
	</button>
	</#if>
</div>