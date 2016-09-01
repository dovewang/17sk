<h1 class="order-title clearfix"> <#--在线支付-->
<#if order.payType==1>
<div class="step float-right">
	<#if  order.status==1>
	<ul>
		<li class="first visted">
			<span><font> 1. </font>报名课程</span><i></i>
		</li>
		<li class=" prev visted">
			<span><font> 2. 订单确认</font></span><i></i>
		</li>
		<li class="active">
			<span><font> 3. </font>等待上课</span><i></i>
		</li>
		<li>
			<span><font> 4. </font>确认上课结束并付款</span><i></i>
		</li>
		<li class="last">
			<span><font> 5. </font>评价</span><i></i>
		</li>
	</ul>
	<#elseif  order.status==2>
	<ul>
		<li class="first visted">
			<span><font> 1. </font>报名课程</span><i></i>
		</li>
		<li class=" visted ">
			<span><font> 2. 订单确认 </font></span><i></i>
		</li>
		<li class="prev visted">
			<span><font> 3. </font>等待上课</span><i></i>
		</li>
		<li  class="active">
			<span><font> 4. </font>确认上课结束并付款</span><i></i>
		</li>
		<li class="last">
			<span><font> 5. </font>评价</span><i></i>
		</li>
	</ul>
	<#elseif  order.status==9>
	<ul>
		<li class="first visted">
			<span><font> 1. </font>报名课程</span><i></i>
		</li>
		<li class=" visted ">
			<span><font> 2. 订单确认 </font></span><i></i>
		</li>
		<li class="visted">
			<span><font> 3. </font>等待上课</span><i></i>
		</li>
		<li  class="prev visted">
			<span><font> 4. </font>确认上课结束并付款</span><i></i>
		</li>
		<li class="last active">
			<span><font> 5. </font>评价</span><i></i>
		</li>
	</ul>
	</#if>
</div> <#else>
<#--线下支付-->
<div class="step float-right">
	<ul>
		<li class="first visted">
			<span><font> 1. </font>报名课程</span><i></i>
		</li>
		<li class=" visted ">
			<span><font> 2. 订单确认</font></span><i></i>
		</li>
		<li class="prev visted">
			<span><font> 3. </font>等待上课</span><i></i>
		</li>
		<li  class="last active ">
			<span><font> 4. </font>确认上课结束并评价</span><i></i>
		</li>
	</ul>
</div> </#if> </h1>
<div class="page-body">
	<div class="order-page box">
		<div class="order-header"><i class="icon icon-tail"></i>课程订单编号：${order.orderId}</div>
		<table align="center"  class="table">
			<thead>
				<tr>
					<th>课程名称</th>
					<th>学校</th>
					<th width="90">课程价格(
					${MONEY_UNIT}
					)</th>
					<th>优惠信息</th>
					<th>付款方式</th>
					<th>上课报到地址</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<#list orderItems as item>
				<tr id="pid${item.productId}" >
					<td >
					<image src="${imageHelper.resolve(item.productCover!,"s120,90")}"  alt="${item.productName}" width="120" height="90" />
					<div>
						<a href="/course/${item.productId}-${VERSION}.html" target="_blank"> ${item.productName} </a>
					</div></td>
					<td><a href="/u/${order.seller}.html" target="_blank"><img usercard="${order.seller}" src="/theme/school/default/images/noface_s.jpg" width="50" height="50" alt=""/></a>
					<div>
						<a class="text-center" href="/u/${order.seller}.html" target="_blank" usercard="${order.seller}"></a>
					</div></td>
					<td><span class="money"> <#if item.price=-1>协议价格<#else>${item.price} </#if></span></td>
					<td></td>
					<td>线下支付</td>
					<td>${item.address!}</td>
					<td>${order.memo!}</td>
				</tr>
				</#list>
			</tbody>
             <#if order.price!=-1>
			<tfoot>
				<tr>
					<td colspan="7"  style="text-align:right" id="totalPrice">合计:<span class="money"> ${order.price} </span> ${MONEY_UNIT}</td>
				</tr>
			</tfoot>
            </#if>
		</table>
	</div>
</div>
