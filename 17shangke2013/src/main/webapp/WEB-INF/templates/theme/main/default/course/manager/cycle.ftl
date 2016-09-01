<!DOCTYPE html>
<html>
	<head>
		<#include "../../meta.ftl" />
		<script src="${IMAGE_DOMAIN}/theme/main/js/bis.course.js?${VERSION}" type="text/javascript" charset="${ENCODING}"></script>
	</head>
	<body>
		<#include "../../header.ftl" />
		<div class="page-container">
			<div class="page-body course-manager-layout">
				<#include "edit.header.ftl" />
				<div class="page-main">
					<div class="page-left">
						<#include "manager.left.ftl"/>
					</div>
					<div class="page-right box" id="main-box">
						<form  action="/course/manager/cycle.save.html" method="post" id="cycleForm" onsubmit="return false;">
							<input type="hidden" name="courseId" value="${course.courseId}"/>
							<input type="hidden" name="type" value="${course.type}"/>
							<dl class="form input-xxlarge">
								<dd>
									<label><em class="required">*</em>标题：</label>
									<span>
										<input type="text" name="title" maxlength="150">
									</span>
								</dd>
								<dd>
									<label>简介：</label>
									<span> 										<textarea maxlength="1000" name="intro"></textarea> </span>
								</dd>
								<dd>
									<label><em class="required">*</em>开课时间：</label>
									<span>
										<input type="text" maxlength="150" name="time">
									</span>
								</dd>
								<dd>
									<label><em class="required">*</em>课程价格：</label>
									<span>
										<input type="number" maxlength="5" name="price">
										元 </span>
								</dd>
								<dd>
									<label>最大人数：</label>
									<span>
										<input type="number" maxlength="3" name="capacity">
										人 <span class="gray">（不填代表无限制）</span></span>
								</dd>
								<dd>
									<label>授课老师：</label>
									<span>
										<input type="text" maxlength="150" name="teacher">
									</span>
								</dd>
								<dd>
									<label>&nbsp;</label>
									<span>
										<button class="button"  type="button" onclick="Course.saveCycle()">
											保存
										</button> </span>
								</dd>
							</dl>
						</form>
						<table class="table">
							<thead>
								<tr>
									<th>标题</th>
									<th>开课时间</th>
									<th>课程价格</th>
									<th>最大人数</th>
									<th>授课老师</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>暑假班</td>
									<td>7月5号--9月10好</td>
									<td>200元</td>
									<td>无限制</td>
									<td>小王</td>
									<td>
									<div class="link-group">
										<a href=""><i class="icon icon-edit"></i>编辑</a><a href=""><i class="icon icon-trash"></i>删除</a>
									</div></td>
								</tr>
								<tr>
									<td>数学单科</td>
									<td>7月5号--9月10好</td>
									<td>200元</td>
									<td>无限制</td>
									<td>小王</td>
									<td>
									<div class="link-group">
										<a href=""><i class="icon icon-edit"></i>编辑</a><a href=""><i class="icon icon-trash"></i>删除</a>
									</div></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		</div>
		<#include "../../footer.ftl"/>
	</body>
</html>
<script type="text/javascript">Course.loadDocument("${course.courseId}");</script>