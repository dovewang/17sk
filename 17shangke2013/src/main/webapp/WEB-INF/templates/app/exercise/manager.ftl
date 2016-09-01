<!DOCTYPE html>
<html>
	<head>
		<#include "/global.ftl" />
		<link href="${IMAGE_DOMAIN}/theme/room/css/default.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
		<script src="${IMAGE_DOMAIN}/theme/base/js/bis.exercise.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
	</head>
	<body class="notebook">
		<div class="page-container">
			<div class="page-header">
				<span class="float-right search"> <label>关键字:</label>
					<input type="text" name="q" id="q"  value="" placeholder="请输入关键字" onkeydown="if(event.keyCode == 13){DM.search();}"/>
					<button  type="button" class="button" onclick="DM.search()"></button> </span>
			</div>
			<div class="page-body">
				<div class="page-left">
					<div  class="tab-header"  id="eTab" node-type="tabnavigator">
						<ul>
							<li>
								<a href="#!/exercise/elist.html?tag=all" remote="true" view="elist" page="true" autoload="true">全部</a>
							</li>
							<li class="tag-add">
								<a href="javascript:void(0)" onclick="addTag()"><i></i></a>
							</li>
						</ul>
					</div>
				</div>
				<div class="page-right">
					<div class="ez-box" style="height:400px" id="elist"></div>
				</div>
			</div>
			<div class="page-footer"></div>
		</div>
	</body>
</html>
