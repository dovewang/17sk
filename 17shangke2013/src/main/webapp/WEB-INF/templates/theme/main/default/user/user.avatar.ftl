<!--原始图-->
<style type="text/css">
	#avatarDiv {
		padding: 20px;
		margin-top: 10px;
		position: relative;
	}
	#picture_original {
		height: 300px;
		width: 300px;
		position: absolute;
		overflow: hidden;
	}
	#picture_original img {
		max-height: 300px;
		max-width: 300px;
	}
	#picture_180 {
		height: 180px;
		width: 180px;
		overflow: hidden;
		position: absolute;
		left: 350px;
		top: 70px;
	}
	#picture_180_text {
		position: absolute;
		left: 350px;
		top: 255px;
		line-height: 16px;
		color: #999;
		text-align: center;
		width: 180px;
	}
	#picture_75 {
		height: 75px;
		width: 75px;
		overflow: hidden;
		position: absolute;
		left: 600px;
		top: 41px;
	}
	#picture_75_text {
		position: absolute;
		left: 590px;
		top: 118px;
		line-height: 16px;
		color: #999;
		text-align: center;
		width: 95px;
	}
	#picture_50 {
		height: 50px;
		width: 50px;
		overflow: hidden;
		position: absolute;
		left: 615px;
		top: 188px;
	}
	#picture_50_text {
		position: absolute;
		left: 600px;
		top: 240px;
		line-height: 16px;
		color: #999;
		text-align: center;
		width: 80px;
	}
</style>
<#assign avatar=user.face!"/theme/school/default/images/noface_o.jpg" />
<div >
	<form id="avatarForm" method="post" enctype="multipart/form-data" action="/user/uploadAvatar.html">
		<button  type="button" class="button primary  large" node-type="upload-button"  id="avatar_file"  name="avatar" onchange="User.uploadAvatar()"> 选择图片 </button>
		<button onclick="User.saveAvatar()" type="button" class="button large">
			更新头像
		</button>
	</form>
</div>
<div id="avatarDiv">
	<div id="picture_original"><img src="/theme/school/default/images/noface_o.jpg" />
	</div>
	<div id="picture_180"><img src="${avatar}" height="180" width="180"/>
	</div>
	<div id="picture_180_text">
		大尺寸头像,180X180像素
	</div>
	<div id="picture_75"><img src="${avatar}" height="75" width="75"/>
	</div>
	<div id="picture_75_text">
		中尺寸头像
		<br/>
		75X75像素
		<br/>
		(自动生成)
	</div>
	<div id="picture_50"><img src="${avatar}" height="50" width="50"/>
	</div>
	<div id="picture_50_text">
		小尺寸头像
		<br/>
		50X50像素
		<br/>
		(自动生成)
	</div>
</div>
<script type="application/javascript">
	$(function() {
		Avatar.create();
	})
</script>