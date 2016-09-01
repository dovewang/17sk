<#include "/conf/config.ftl" />
<#if result.totalCount==0>
<div  class="placeholder-box">
	对不起，没有找到相关的订单！
</div>
<#else>
<table  class="table table-separator" id="trade-order-list">
	<thead>
		<tr>
			<th width="200">名称</th>
			<th>单价(${moneyUnit})</th>
			<th>总价(${moneyUnit})</th>
			<th>交易状态</th>
			<th>交易操作</th>
			<th>其他操作</th>
		</tr>
	</thead>
	<#list result.result as  order>
	<tbody>
		<tr class="separator">
			<td colspan="6"></td>
		</tr>
		<#assign item_size=order.items?size/>
		<#assign trade_status=enumHelper.getLabel("trade_status",order.status?j_string)!/>
		<#if order.orderType==1>
		<tr class="order-header">
			<td colspan="6"><span>订单编号：
				${order.orderId} </span><span>订单类型：
				${enumHelper.getLabel("order_type",order.orderType?j_string)} </span><span>成交时间：
				${flint.timeToDate(order.orderTime,"yyyy-MM-dd HH:mm")}
				<#if order.buyer?string==USER_ID> </span><span>解答人：<a href="/u/${order.seller}.html" target="_blank" usercard="${order.seller}"></a></span> <#else> <span>查看人：<a href="/u/${order.buyer}.html" target="_blank" usercard="${order.buyer}"></a></span></td>
			</#if>
			</td>
		</tr>
		<#list order.items as item>
		<tr>
			<td  style="text-align:left"><a href="/qitem-${item.productId}.html" target="_blank"> ${item.productName} </a></td>
			<td><span class="money">${item.price}</span></td>
			<td rowspan="${item_size}"><span class="money"> ${order.price} </span></td>
			<td><#if order.status==2>待付款确认<#else>
			${trade_status} </#if></td>
			<td><a  href="/trade/detail/order.html?orderId=${order.orderId}" target="_blank">交易详情</a>
			<br/>
			<#--如果是买家-->
			<#if order.buyer?string==USER_ID>
			<#if order.status==1> <a href="/trade/prepay.confirm.html?orderId=${order.orderId}" class="button primary"  target="_blank">预付款</a>
			<a  target="_blank" href="/trade/cancle.html?orderId=${order.orderId}" class="button">撤销</a><#elseif  order.status==2> <a  target="_blank" href="/trade/pay.confirm.html?orderId=${order.orderId}" class="button primary">付款</a><a  target="_blank" href="/trade/appeal.html?orderId=${order.orderId}" class="button">申诉</a> <#elseif  order.status==9> 
			<a  target="_blank" href="/trade/comment.html?orderId=${order.orderId}" class="button" >评价</a> </#if>
			<#elseif   order.seller?string==USER_ID >
			<#--如果是卖家-->
			</#if> </td>
			<td><a >备忘</a></td>
		</tr>
		</#list>
		<!--课程类型订单，这里只有线下课程允许线下支付，其他均为线上支付-->
		<#elseif order.orderType==2>
		<tr class="order-header">
			<td colspan="6"><span>订单编号：
				${order.orderId} </span><span>订单类型：
				${enumHelper.getLabel("order_type",order.orderType?j_string)} </span>
				 <#if order.status==0> <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> <#else> <span>成交时间：
				${flint.timeToDate(order.orderTime,"yyyy-MM-dd HH:mm")} </span> </#if>
			<#if order.buyer?string==USER_ID> <span>老师：<a href="/u/${order.seller}.html" target="_blank" usercard="${order.seller}"></a></span></td>
			<#else> <span>学生：<a href="/u/${order.buyer}.html" target="_blank" usercard="${order.buyer}"></a></span>
			</td>
			</#if>
		</tr>
		<#list order.items as item>
		<tr>
			<td style="text-align:left"><a href="/course/${item.productId}-${VERSION}.html" target="_blank"> ${item.productName} </a></td>
			<td><span class="money"> ${item.price} </span></td>
			<td rowspan="${item_size}"><span class="money"> ${order.price} </span></td>
			<td>${trade_status}</td>
			<td><a  href="/trade/detail/order.html?orderId=${order.orderId}" target="_blank">交易详情</a>
			<br/>
			<#--如果是买家-->
			<#if order.buyer?string==USER_ID>
			<#if order.status==1> <a href="/trade/prepay.confirm.html?orderId=${order.orderId}" class="button primary"  target="_blank">预付款</a><a  target="_blank" href="/trade/cancle.html?orderId=${order.orderId}" class="button">撤销</a> <#elseif  order.status==3> <a  target="_blank" href="/trade/pay.confirm.html?orderId=${order.orderId}" class="button primary">付款</a><a  target="_blank" href="/trade/appeal.html?orderId=${order.orderId}" class="button">申诉</a> <#elseif  order.status==9> <a  target="_blank" href="/trade/comment.html?orderId=${order.orderId}" class="button" >评价</a> </#if>
			<#elseif   order.seller?string==USER_ID >
			<#--如果是卖家-->
			</#if> </td>
			<td><a >备忘</a></td>
		</tr>
		<tr>
			<td colspan="6"  class="order-address">上课状态：${enumHelper.getLabel("user_class_status",item.status?j_string)}
				<#if item.roomId!=0>
				    <#if (item.status=1||item.status=2)>
				            <a href="/abc/abc.html?roomId=${item.roomId}" target="_blank">进入教室</a>
				       <#else>
				        	<a href="/v/id_${item.roomId}.html" target="_blank">观看录像</a>
				    </#if>
				</#if>
			</td>
		</tr>
		</#list>
		<#elseif order.orderType==3>
		<tr class="order-header">
			<td colspan="6"><span>订单编号：
				${order.orderId} </span><span>订单类型：
				${enumHelper.getLabel("order_type",order.orderType?j_string)} </span> <#if order.status==0> <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> <#else> <span>成交时间：
				${flint.timeToDate(order.orderTime,"yyyy-MM-dd HH:mm")} </span> </#if>
		</tr>
		<#list order.items as item>
		<tr>
			<td style="text-align:left"><a href="/course/${item.productId}-${VERSION}.html" target="_blank"> ${item.productName} </a></td>
			<td><span class="money"> 
                    <#if item.price=-1>协议价格<#else>${item.price}</#if>
            </span></td>
			<td rowspan="${item_size}"><span class="money"> <#if order.price=-1>协议价格<#else>${order.price}</#if> </span></td>
			<td>
			<div class="gray1">
				(<#if order.payType==1>在线支付<#else>线下支付</#if>)
			</div></td>
			<td><a  href="/trade/detail/order.html?orderId=${order.orderId}" target="_blank">交易详情</a>
			<br/>
			<#--如果是买家-->
			<#if order.buyer?string==USER_ID>
			<#if (order.status==1)&&(order.payType==1)> <a href="/trade/prepay.confirm.html?orderId=${order.orderId}" class="button primary"  target="_blank">预付款</a><a  target="_blank" href="/trade/cancle.html?orderId=${order.orderId}" class="button">撤销</a> <#elseif  order.status==3> <a  target="_blank" href="/trade/pay.confirm.html?orderId=${order.orderId}" class="button primary">付款</a><a  target="_blank" href="/trade/appeal.html?orderId=${order.orderId}" class="button">申诉</a> <#elseif  order.status==9> <a  target="_blank" href="/trade/comment.html?orderId=${order.orderId}" class="button" >评价</a> </#if>
			<#elseif   order.seller?string==USER_ID >
			<#--如果是卖家-->

			</#if> </td>
			<td>备忘</td>
		</tr>
		<tr>
			<td colspan="6"  class="order-address">上课地址：
			${item.sellerAddress}
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			报名用户：
			${item.buyerAddress!"<span class='gray1'>(未知)</span>"}</td>
		</tr>
		</#list>
		<#--教室类型订单-->
		<#elseif order.orderType==4>
		<tr class="order-header">
			<td colspan="6"><span>订单编号：
				${order.orderId} </span><span>订单类型：
				${enumHelper.getLabel("order_type",order.orderType?j_string)} </span><span>成交时间：
				${flint.timeToDate(order.orderTime,"yyyy-MM-dd HH:mm")} </span> <#if (order.seller!=0)> <span>主讲人：<a href="/u/${order.seller}.html" target="_blank" usercard="${order.seller}"></a></span></#if></td>
		</tr>
		<#list order.items as item>
		<tr>
			<td  style="text-align:left"><a href="/abc/abc.html?roomId=${item.productId}" target="_blank"> ${item.productName} </a></td>
			<td><span class="money"> ${item.price} </span></td>
			<td rowspan="${item_size}"><span class="money"> ${order.price} </span></td>
			<td>${trade_status}
			<br/>
			<#--如果是买家-->
			<#if order.buyer?string==USER_ID>
			<#if order.status==1><a href="/trade/prepay.confirm.html?orderId=${order.orderId}" class="button primary"  target="_blank">预付款</a><a  target="_blank" href="/trade/cancle.html?orderId=${order.orderId}" class="button">撤销</a> <#elseif  order.status==3> <a  target="_blank" href="/trade/pay.confirm.html?orderId=${order.orderId}" class="button primary">付款</a><a  target="_blank" href="/trade/appeal.html?orderId=${order.orderId}" class="button">申诉</a> <#elseif  order.status==9> <a  target="_blank" href="/trade/comment.html?orderId=${order.orderId}" class="button" >评价</a> </#if>
			<#elseif   order.seller?string==USER_ID >
			<#--如果是卖家-->

			</#if> </td>
			<td><a  href="/trade/detail/order.html?orderId=${order.orderId}" target="_blank">交易详情</a></td>
			<td><a >备忘</a></td>
		</tr>
		<tr>
			<td colspan="6"  class="order-address">上课状态：${enumHelper.getLabel("user_class_status",item.status?j_string)}
				<#if item.roomId!=0>
				   <a href="/v/id_${item.roomId}.html" target="_blank">观看录像</a>
				</#if>
			</td>
		</tr>
		</#list>
		</#if>
	</tbody>

	</#list>
</table>
</#if>