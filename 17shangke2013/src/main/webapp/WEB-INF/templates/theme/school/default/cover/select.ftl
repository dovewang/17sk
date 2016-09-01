<div class="pop-header" style="padding:0">
	<a node-type='pop-close' class='close' href='javascript:;'>×</a>
	<div class="tab-header" node-type="tabnavigator" style="border-top:none; border-left:none; border-right:none" mode="hash">
		<ul>
			<li>
				<a href="#1" autoload="true" remote="false" view="#coverUploadView">封面上传</a>
			</li>
			<li>
				<a href="#!/cover/list.html?t=0" remote="true" view="#coverSelectView">默认图片</a>
			</li>
			<li>
				<a href="#3" remote="false" view="#coverOnlineView">在线制图</a>
			</li>
		</ul>
	</div>
</div>
<div class="pop-body">
	<div style="height:550px;">
		<div id="coverUploadView" style="display:none">
			<div class="text-left">
				<form action="/upload/image.html" method="post" enctype="multipart/form-data" id="coverUploadForm">
					<input type="hidden" name="w" value="400"/>
					<input type="hidden" name="h" value="300"/>
					<a class="button primary large"  node-type="upload-button"   id="cover_file"  name="filedata"  onchange="Course.uploadCover()"> 封面上传</a>
				</form>
			</div>
			<div id="cover-area" class="clearfix">
				<div id="cover-source">
					<h2>课程封面图</h2>
					<p>
						仅支持jpg、png、bmp格式图片，且文件小于1M
					</p>
				</div>
				<div id="cover-preview">
					<h2>封面预览</h2>
				</div>
			</div>
			<div>
				<button class="button primary large" node-type="pop-close" onClick="Course.saveCover()">
					保存
				</button>
				<button class="button large" node-type="pop-close">
					取消
				</button>
			</div>
		</div>
		<div id="coverSelectView" style="display:none"></div>
		<div style="display:none;" id="coverOnlineView">
			<iframe  src="/theme/kiss/editor/plugins/image/eidtor.jsp?before=Course.editCoverBefore&after=Course.editCoverAfter" frameborder="0"  width="100%"   style="height:500px"></iframe>
		</div>
	</div>
</div>
