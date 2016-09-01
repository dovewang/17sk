<!DOCTYPE html>
<html>
	<head>
		<#include "../../theme/main/default/meta.ftl" />
		<script src="${IMAGE_DOMAIN}/editor/xheditor.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
		<script src="${IMAGE_DOMAIN}/theme/base/js/bis.exercise.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
	</head>
	<#assign eid=RequestParameters["eid"]!"0" />
	<#assign type=RequestParameters["st"]!"1" />
	<body>
		<div class="tab-box clearfix">
			<div class="tab-header">
				<ul>
					<#if TYPE==0>
					<li class="active">
						<a href="javascript:void(0)">我的题库</a>
					</li>
					<li>
						<a href="?t=1&eid=${eid}&st=${type}">校内题库</a>
					</li>
					<#else>
					<li>
						<a href="?t=0&eid=${eid}&st=${type}">我的题库</a>
					</li>
					<li class="active">
						<a href="javascript:void(0)">校内题库</a>
					</li>
					</#if>
				</ul>
				<span class="floatRight" style="padding-right:30px"> 
					<a href="/exercise/template.html?type=1&eid=${eid}&cb=reloadQ" node-type="pop" id="exercise" >添加单选题</a>| 
					<a href="/exercise/template.html?type=2&eid=${eid}&cb=reloadQ" node-type="pop" id="exercise" >添加多选题</a>| 
					<a href="/exercise/template.html?type=3&eid=${eid}&cb=reloadQ" node-type="pop" id="exercise" >添加判断题</a>| 
					<a href="/exercise/template.html?type=4&eid=${eid}&cb=reloadQ" node-type="pop" id="exercise">添加填空题</a>| 
					<a href="/exercise/template.html?type=5&eid=${eid}&cb=reloadQ" node-type="pop" id="exercise">添加简答计算题</a>
					</span>
			</div>
			<div class="tab-body" id="myQbankView" style="display: block">
				<div class="filter-box box">
					<dl>
						<#assign grade=kindHelper.getGrades()/>
						<dt>
							<@flint.condition condition,"?t="+TYPE+"&p=1&c=@",0,"0","所有年级"/>
						</dt>
						<dd>
							<#list grade?keys as key><@flint.condition condition,"?t="+TYPE+"&p=1&c=@",0,key,grade[key]/> </#list>
						</dd>
					</dl>
					<dl>
						<dt>
							<@flint.condition condition,"?t="+TYPE+"&p=1&c=@",1,"0","全部学科"/>
						</dt>
						<dd>
							<#if condition[0]!="0">
							<#assign kind=kindHelper.getKind(condition[0])/>
							<#list kind as k>
							<@flint.condition condition,"?t="+TYPE+"&p=1&c=@",1,""+k.kind,k.name/>
							</#list>
							<#else>
							<#assign kinds=kindHelper.getKinds()/>
							<#list kinds?keys as key>
							<@flint.condition condition,"?t="+TYPE+"&p=1&c=@",1,key,kinds[key]/>
							</#list>
							</#if>
						</dd>
					</dl>
					<#--知识点-->
					<#assign  knowledgePath=condition[3] />
					<#assign knowledge=knowledgeHelper. getKnowledge(condition[0]?number,condition[1]?number,0) />
					<dl>
						<dd style="width:100%">
							<#list knowledge as k>
							<@flint.condition condition,"?t="+TYPE+"&p=1&c=@",3,k.knowledgeId?string,k.knowledge/>
							</#list>
						</dd>
					</dl>
				</div>
				<div style="height:350px;overflow:scroll">
					<#include "list.ftl" />
				</div>
			</div>
		</div>
		</div>
	</body>
</html>
<script type="text/javascript">
	function bindEditor() {
		$("#exercise_popup .editor").xheditor(flint.editor);
	}

	function reloadQ() {
		location.reload();
	}
</script>