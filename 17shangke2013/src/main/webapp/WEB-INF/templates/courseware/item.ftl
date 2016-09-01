<#include "/conf/config.ftl" />
<link href="/theme/doc/doc.css?${VERSION}" rel="stylesheet" type="text/css">
<#assign  groupId=RequestParameters["g"]/>
<#assign  role_domain="group"+groupId+":"/>
<#assign  isManager=__USER.hasRole(role_domain+"teacher,"+role_domain+"admin,"+role_domain+"master")/>
<#if isManager>
<div id="sectionTemplate" class="hide">
	<form action="/courseware/section.add.html" method="post" id="lesson-##lessonId##-section-##index##Form">
		<input type="hidden" name="lessonId" value="##lessonId##"/>
		<dl class="form">
			<dd>
				<label>标题：</label>
				<span>
					<input type="text" name="title" style="width:400px">
				</span>
			</dd>
			<dd>
				<label>简介：</label>
				<span> 					<textarea name="intro" style="width:400px"></textarea> </span>
			</dd>
		</dl>
		<button  type="button" class="button primary" onclick="Courseware.addSection(##lessonId##,##index##)">
			新增
		</button>
	</form>
</div>
<div id="chapterTemplate" class="hide">
	<form action="/courseware/chapter.add.html" method="post" id="chapter-##sectionId##-##index##Form">
		<input type="hidden" name="lessonId" value="##lessonId##"/>
		<input type="hidden" name="sectionId" value="##sectionId##"/>
		<dl class="form">
			<dd>
				<label>标题：</label><span>
					<input type="text" name="title" style="width:500px">
				</span>
			</dd>
		</dl>
		<button  type="button" class="button primary" onclick="Courseware.addChapter(##lessonId##,##sectionId##,##index##)">
			新增
		</button>
	</form>
</div>
</#if>
<div  class="doc-body">
	<!--大纲版-->
	<div class="doc-item" id="lesson${lesson.lessonId}" lessonId="${lesson.lessonId}"  groupId="${groupId}">
		<h2>${lesson.lesson}<span class="link-group"><a href="javascript:;" node-type="history.back">返回</a><#if isManager>|<a href="javascript:;"><i class="icon icon-setting"></i>设置</a>| <a href="javascript:;" onclick="Courseware.addSectionShow(${lesson.lessonId})">新增章节</a></#if><!--|<a href="javascript:;">时间轴版</a>|<a href="javascript:;">大纲结构版</a>|<a href="#">归档</a>--></span></h2>
		<div class="doc-widget">
			<div  class="outline">
				<ul>
					<#list lesson.sections as s>
					<li id="section${s.sectionId}">
						<div class="section">
							<h3>第${s_index+1}部分&nbsp;&nbsp;${s.title}<#if isManager><span> <a href="javascript:;" onclick="Courseware.addChapterShow(${lesson.lessonId},${s.sectionId})"><i class="icon icon-add"></i></a><a href="javascript:;"><i class="icon icon-edit"></i></a><a href="javascript:;" onclick="Courseware.deleteSection(this,${s.sectionId},${lesson.lessonId})"><i class="icon icon-trash"></i></a></span></#if></h3>
						</div>
						<ul  class="chapters" id="chapters${s.sectionId}">
							<#list s.chapters as chapter>
							<li id="chapter${chapter.chapterId}">
								<h3>第${chapter_index+1}课&nbsp;&nbsp;<a href="/courseware/chapter/${chapter.chapterId}.html?g=${groupId}" node-type="link" data-target="#doc-content">${chapter.title}</a><#if isManager><span><a href="javascript:;"><i class="icon icon-edit"></i></a><a href="javascript:;" onclick="Courseware.deleteChapter(this,${chapter.chapterId},${lesson.lessonId})"><i class="icon icon-trash"></i></a></span></#if></h3>
							</li>
							</#list>
						</ul>
					</li>
					</#list>
				</ul>
			</div>
			<div class="doc-content" id="doc-content">
				<div class="doc-widget">
					${lesson.intro}
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</div>
