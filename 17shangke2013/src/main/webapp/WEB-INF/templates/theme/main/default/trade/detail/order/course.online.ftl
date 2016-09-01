<h1 class="clearfix mt15">
<div class="step float-right">
	<#if order.status==1>
	<ul>
		<li class="first visted">
			<span><font> 1. </font>报名课程</span><i></i>
		</li>
		<li class=" prev visted">
			<span><font> 2. 订单确认</font></span><i></i>
		</li>
		<li class="active">
			<span><font> 3. 预付款到一起上课</font></span><i></i>
		</li>
		<li >
			<span><font> 4. </font>等待上课</span><i></i>
		</li>
		<li>
			<span><font> 5. </font>确认上课结束并付款</span><i></i>
		</li>
		<li class="last">
			<span><font> 6 </font>评价</span><i></i>
		</li>
	</ul>
	<#elseif  order.status==2>
	<ul>
		<li class="first visted">
			<span><font> 1. </font>报名课程</span><i></i>
		</li>
		<li class="visted">
			<span><font> 2. 订单确认</font></span><i></i>
		</li>
		<li class="prev visted">
			<span><font> 3. 预付款到一起上课</font></span><i></i>
		</li>
		<li class="active">
			<span><font> 4. </font>等待上课</span><i></i>
		</li>
		<li>
			<span><font> 5. </font>确认上课结束并付款</span><i></i>
		</li>
		<li class="last">
			<span><font> 6 </font>评价</span><i></i>
		</li>
	</ul>
	<#elseif  order.status==3>
	<ul>
		<li class="first visted">
			<span><font> 1. </font>报名课程</span><i></i>
		</li>
		<li class="visted">
			<span><font> 2. 订单确认</font></span><i></i>
		</li>
		<li class="visted">
			<span><font> 3. 预付款到一起上课</font></span><i></i>
		</li>
		<li class="prev visted">
			<span><font> 4. </font>等待上课</span><i></i>
		</li>
		<li  class="active">
			<span><font> 5. </font>确认上课结束并付款</span><i></i>
		</li>
		<li class="last">
			<span><font> 6. </font>评价</span><i></i>
		</li>
	</ul>
	<#elseif  order.status==9>
	<ul>
		<li class="first visted">
			<span><font> 1. </font>报名课程</span><i></i>
		</li>
		<li class=" prev visted">
			<span><font> 2. 订单确认</font></span><i></i>
		</li>
		<li class="active">
			<span><font> 3. 预付款到一起上课</font></span><i></i>
		</li>
		<li >
			<span><font> 4. </font>等待上课</span><i></i>
		</li>
		<li>
			<span><font> 5. </font>确认上课结束并付款</span><i></i>
		</li>
		<li class="last">
			<span><font> 6 </font>评价</span><i></i>
		</li>
	</ul>
	</#if>
</div></h1>
<div class="page-body">
	<div class="order-page box">
		<h1 class="title">课程订单编号：${order.orderId}</h1>
		<table align="center"  class="table gray">
			<thead>
				<tr>
					<th>课程名称</th>
					<th width="90">讲师</th>
					<th width="90">课程价格(${MONEY_UNIT})</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<#list orderItems as item>
				<tr id="pid${item.productId}" >
					<td><a href="/course/${item.productId}-${VERSION}.html" target="_blank">${item.productName}</a></td>
					<td><a href="/u/${order.seller}.html" target="_blank"><img usercard="${order.seller}" src="/theme/school/default/images/noface_s.jpg" width="50" height="50" alt=""/></a><a class="text-center" href="/u/${order.seller}.html" target="_blank" usercard="${order.seller}"></a></td>
					<td><span class="money">${item.price}</span></td>
					<td>${item.memo!}</td>
				</tr>
				</#list>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="4"  style="text-align:right" id="totalPrice">合计:<span class="money">${order.price}</span>${MONEY_UNIT}</td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>