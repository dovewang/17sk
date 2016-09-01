<!DOCTYPE html>
<html>
	<head>
		<#include "../meta.ftl" />
		<script src="${IMAGE_DOMAIN}/theme/main/js/bis.course.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<script type="text/javascript" src="${IMAGE_DOMAIN}/theme/base/js/jquery.Jcrop.js?${VERSION}"></script>
		<link href="${IMAGE_DOMAIN}/theme/base/css/jquery.Jcrop.css" type="text/css" rel="stylesheet" media="screen">
	</head>
	<body>
		<#include "../header.ftl" />
		<div class="page-container">
			<div class="page-body course-create-layout box">
				<form action="/show/publish.html" method="post" id="showForm" name="showForm" data-validator-url="/v/show.json">
					<#assign show=show! />
					<#assign type=RequestParameters["t"]!show.type?string!"1" />
					<input type="hidden" value="${type}"  name="type"  id="type"/>
					<input type="hidden" name="showId"  id="showId" value="${show.showId!0}"/>
					<#if type=="4"><!-- 转发视频 -->
					<input type="hidden" name="dir"  id="showDir" value="${show.dir!}"/>
					<input type="hidden" name="cover" id="showCover" value="${RequestParameters["c"]!show.cover!}"/>
					<#elseif type=="2"><!-- 上传视频 -->
					<input type="hidden" name="cover" id="showCover" value="${RequestParameters["c"]!show.cover!}"/>
					<input type="hidden" value="${RequestParameters["url"]!show.dir!}"  name="dir"/>
					<#else><!-- 在线视频 -->
					<input type="hidden" value="${RequestParameters["roomId"]!show.roomId!0}"  name="roomId"/>
					<input type="hidden" value="${RequestParameters["url"]!show.dir!}"  name="dir"/>
					</#if>
					<dl class="form input-xxlarge">
						<#if type=="4" && (show.showId!0)==0><!-- 转发视频 （新增时候才有） -->
						<dd>
							<label>转发视频源：</label><span>
							<input name="url"  type="text" id="showURL"/>
							<button class="button" type="button" onclick="Show.getVideo(this)">
								获取数据
							</button> </span>
						</dd>
						<dd>
							<label>&nbsp;</label><span class="mark">只需要填写您视频播放的网页地址。例如：http://v.youku.com/v_show/id_XNDA0NDE0ODA4.html
							<br/>
							(目前仅支持优酷、土豆、新浪、酷六)</span></span>
						</dd>
						</#if>
						<dd>
							<label><em class="required">*</em>标题：</label><span>
								<input name="title"  type="text" id="title"  value="${RequestParameters["subject"]!show.title!}"/>
							</span>
						</dd>
						<#if type=="4"||type=="2"><!-- 转发视频或者上传视频  -->
						<dd>
						<label>封面：</label><span><img id="imageCover" src="${RequestParameters["c"]!show.cover!}" width="300" height="225" alt="" /></span>
						</dd>
						<#else><!-- 在线视频 -->
							<#if show.cover?default("")!=""><!--编辑-->
								<dd><label>封面：</label><span><div id='courseCoverDiv'><img id="imageCover" src="${RequestParameters["c"]!show.cover!}" width="300" height="225" alt="" /></div></span></dd>
							<#else>
								<dd><label>封面：</label><span><div id='courseCoverDiv'></div></span></dd>
								<dd>
									<label><em class="required">*</em>课程封面：</label>
									<span>
										<button  node-type="pop" rel="/cover/select.html" class="button primary large" setting="{id:'cover-upload-pop',cache:false,css:{width:800,top:35}}">
											上传封面
										</button> 
									</span>
								</dd>
							</#if>
							<input type="hidden" id="courseCoverInput" value="${RequestParameters["c"]!show.cover!}"  name="cover"/>
						</#if>
						<dd>
							<dd>
								<label><em class="required">*</em>所属类别：</label>
								<span>
								<input  type="hidden" readonly name="category" id="category" value="${show.category!}"/>
								<span class="select-box box" action-target="category" max="1" style="width:600px"> <#assign show_category=enumHelper.getEnum("show_category") />
									<#list  show_category?keys as key> <a href="javascript:void(0)" action-type="select-item"  action-data="${key}">${show_category[key]}</a> </#list></span>
								</span>
							</dd>
							<dd>
								<label>秀秀分类：</label>
								<span>
									<input name="tag" type="tag" id="tags" value="${show.tag!}"/>
								</span>
							</dd>
							<!--
							<dd><label>标签：</label><span><input name="tags"  type="text" id="tags"/></span></dd>
							-->
							<dd>
								<label><em class="required">*</em>简介：</label><span><textarea name="intro" rows="8" id="intro" node-type="editor" style="width:650px">${show.intro!""}</textarea></span>
							</dd>
							<dd>
								<label>&nbsp;</label><span>
									<button  class="button primary large" type="button" onclick="Show.post()">
										发布秀秀
									</button></span>
							</dd>
					</dl>
				</form>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>
