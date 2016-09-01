<!DOCTYPE html>
<html>
	<head>
		<#include "../../meta.ftl" /> <script src="${IMAGE_DOMAIN}/theme/main/js/bis.course.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
	</head>
	<body>
		<#include "../../header.ftl" />
		<div class="page-container">
			<div class="page-body">
				<div class="page-left course-guide">
					<#if __USER.hasRole("ENTERPRISE,ADMIN") >
					<#--学校用户特有的--> <h2>线下课程</h2>
					<div class="box" style="position:relative;">
						<img src="/theme/main/default/images/course_g4.jpg" />
						<a class="button primary large"  href="/course/manager/3/create.html" style="position:absolute;right:50px; top:80px;">创建线下课程</a>
						<div class="text-left">
							1、创建在线课程是为了方便您指定自己的课程计划，同时可以邀请您的学生或好友来参加您的课程以获得收益。
							<br/>
							2、课程创建的第一步信息必须填写完整，方便您的管理和学员的查找，文档资料上传和练习部分可以根据自己的需要填写，也可以直接跳过，快速发布。
							<br/>
							3、 关于课程发布协议，<a href="javascript:void(0)" onclick="$('#course_agreement').toggle()">点击查看</a>。
							<#include "/agreement/course.ftl"/>
						</div>
					</div>
					</#if>
					<#if __USER.hasRole("TEACHER,ENTERPRISE,ADMIN") >
					<h2>上传或转发视频课件</h2>
					<div class="box" style="position:relative;">
						<!--html4上传-->
						<img src="/theme/main/default/images/course_g1.jpg"  />
						<a class="button primary large"  href="/course/manager/4/create.html" style="position:absolute;right:180px; top:120px;">转发视频课程</a>
						<div id="video_upload"  style="position:absolute;right:20px; top:120px;">
							<form id="videoUploadForm"  action="/upload/video.html" method="post" enctype="multipart/form-data" >
								<input name="progress" value="true" type="hidden"/>
								<input name="flag" value="video" type="hidden"/>
								<a class="button primary large" node-type="upload-button"  name="file" onchange="Course.uploadVideo(this);">
									请选择视频文件
								</a>
							</form>
						</div>
						<div id="uploadProgress" class="progress progress-info progress-striped active hide" >
							<div class="bar" id="uploadProgress_bar"></div>
						</div>
						<div class="text-left">
							1、为了提高您上传文件的的效率，强烈建议您先将文件格式转换flv,f4v,mp4；
							<br/>
							2、如果您不知道如何转换视频<a href="#">，请点这里</a>。
							<br/>
							3、 关于视频上传协议，<a href="javascript:void(0)" onclick="$('#video_agreement').toggle()">点击查看</a>。
							<#include "/agreement/video.ftl"/>
						</div>
					</div>
					<h2>在线课程</h2>
					<div class="box" style="position:relative;">
						<img src="/theme/main/default/images/course_g3.jpg"/>
						<a class="button primary large"  href="/course/manager/1/create.html" style="position:absolute;right:50px; top:80px;">创建在线课程</a>
						<div class="text-left">
							1、创建在线课程是为了方便您指定自己的课程计划，同时可以邀请您的学生或好友来参加您的课程以获得收益。
							<br/>
							2、课程创建的第一步信息必须填写完整，方便您的管理和学员的查找，文档资料上传和练习部分可以根据自己的需要填写，也可以直接跳过，快速发布。
							<br/>
							3、 关于课程发布协议，<a href="javascript:void(0)" onclick="$('#course_agreement').toggle()">点击查看</a>。
							<#include "/agreement/course.ftl"/>
						</div>
					</div>
					</#if>
					<h2>I讲台</h2>
                    <a name="show-guide"></a>
					<div class="box" style="position:relative;">
						<img src="/theme/main/default/images/course_g2.jpg"/>
						<a class="button primary large"  href="/show/form.html?t=4" style="position:absolute;right:280px; top:100px;">转发视频</a>
						<#if flint.isLogin()>
						<div id="show_upload"  style="position:absolute;right:150px; top:100px;">
							<form id="showUploadForm"  action="/upload/video.html" method="post" enctype="multipart/form-data" >
								<input name="progress" value="true" type="hidden"/>
								<input name="flag" value="show" type="hidden"/>
								<a class="button primary large" node-type="upload-button"  name="file" onchange="Show.upload(this)">
									上传我的视频
								</a>
							</form>
						</div>
						<div id="uploadProgress" class="progress progress-info progress-striped active hide" >
							<div class="bar" id="uploadProgress_bar"></div>
						</div>
						<#else> <a class="button primary large" security="true" style="position:absolute;right:150px; top:100px;">上传我的视频</a>
						</#if> 
						<a class="button primary large" href="/abc/form.html?t=3" style="position:absolute;right:50px; top:100px;" node-type="pop" setting="{id:'create-room-pop',effect:'mac',css:{width:500}}">在线秀秀</a>
						<div class="text-left">
							1、爱讲台是为了展示您自己，为自己获得人气，会有更多的学员来关注参加课程，以获得更好的收益。
							<br/>
							2、您也可以直接参加有主题的活动，会获得一定的收益，优秀的作品将会列入本站精品课程。<a href="#">点击这里查看详情</a>
							<br/>
							3、 关于爱讲台发布协议，<a href="javascript:void(0)" onclick="$('#show_agreement').toggle()">点击查看</a>。
							<#include "/agreement/show.ftl"/>
						</div>
					</div>
				</div>
				<div class="page-right">
					<div class="box text-left"  style="margin-top:40px">
						<h2 class="p5">创建课程说明</h2>
						我们针对每位同学的实际情况配备有针对性，辅助性的优秀导师，导师将针对你的个性，爱好，理想和学习状态，为你提供个性化的心灵引导，及时帮助你发现和化解成长中的问题，让导师成为你理想信念的引导者，学习方法的指导者，兴趣特长的发掘者，习惯养成的帮助者。
					</div>
					<div class="box text-left">
						<h2  class="p5">疑问帮助</h2>
						1、<a href="#">导师如何收费？</a>
						<br />
						2、<a href="#">我们的服务 </a>
						<br />
						3、<a href="#">使用规则是怎样的？ </a>
						<br />
						4、<a href="#">怎样成为正式用户？ </a>
					</div>
				</div>
			</div>
		</div>
		<#include "../../footer.ftl"/>
	</body>
</html>