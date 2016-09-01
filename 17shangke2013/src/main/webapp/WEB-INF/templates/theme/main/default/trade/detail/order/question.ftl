<h1 class="clearfix m15">
<div class="step float-right">
	<#if order.status==1>
	<ul>
		<li class="first prev visted">
			<span><font> 1. </font>发布问题到${SITE_NAME}</span><i></i>
		</li>
		<li class="active">
			<span><font> 2. 等待老师解答问题</font></span><i></i>
		</li>
		<li>
			<span><font> 3. </font>查看答案</span><i></i>
		</li>
		<li>
			<span><font> 4. </font>付款给解答人</span><i></i>
		</li>
		<li class="last">
			<span><font> 5. </font>评价</span><i></i>
		</li>
	</ul>
	<#elseif order.status==2>
	<ul>
		<li class="first visted">
			<span><font> 1. </font>发布问题到${SITE_NAME}</span><i></i>
		</li>
		<li class="visted">
			<span><font> 2. 等待老师解答问题</font></span><i></i>
		</li>
		<li  class="visted prev">
			<span><font> 3. </font>查看答案</span><i></i>
		</li>
		<li class="active">
			<span><font> 4. </font>付款给解答人</span><i></i>
		</li>
		<li class="last">
			<span><font> 5. </font>评价</span><i></i>
		</li>
	</ul>
	<#elseif  order.status==9>
	<ul>
		<li class="first visted">
			<span><font> 1. </font>发布问题到${SITE_NAME}</span><i></i>
		</li>
		<li class="visted">
			<span><font> 2. 等待老师解答问题</font></span><i></i>
		</li>
		<li  class="visted">
			<span><font> 3. </font>查看答案</span><i></i>
		</li>
		<li class="visted prev">
			<span><font> 4. </font>付款给解答人</span><i></i>
		</li>
		<li class="last active">
			<span><font> 5. </font>评价</span><i></i>
		</li>
	</ul>
	</#if>
</div></h1>
<div class="page-body">
	<div class=" box">
		<h1 class="title">问题订单编号：${order.orderId}</h1>
		<table align="center"  class="table trade-table">
			<thead>
				<tr>
					<th width="200">问题</th>
					<th>提问人</th>
					<th>解答人</th>
					<th>报酬(${MONEY_UNIT})</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<#list orderItems as item>
				<tr id="pid${item.productId}" >
					<td><a href="/qitem-${item.productId}.html" target="_blank">${item.productName}</a></td>
					<td><a href="/u/${order.buyer}.html" target="_blank"> <img usercard="${order.buyer}" src="/theme/school/default/images/noface_s.jpg" width="50" height="50" alt=""/></a>
					<br/>
					<a class="text-center" href="/u/$${order.buyer}html" target="_blank" usercard="${order.buyer}"></a></td>
					<td> <#if order.seller==0>
					待确认
					<#else> <a href="/u/${order.seller}.html" target="_blank"><img usercard="${order.seller}" src="/theme/school/default/images/noface_s.jpg" width="50" height="50" alt=""/></a>
					<br/>
					<a class="text-center" href="/u/${order.seller}.html" target="_blank" usercard="${order.seller}"></a> </#if> </td>
					<td><span class="money">${item.price}</span></td>
					<td>${item.memo!}&nbsp;</td>
				</tr>
				</#list>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="5"  style="text-align:right" id="totalPrice">合计:<span class="money">${order.price}</span>${MONEY_UNIT}</td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>