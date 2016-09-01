<h1 class="order-title clearfix">
<div class="step float-right">
	<ul>
		<li class="first prev visted">
			<span><font> 1. </font>报名课程</span><i></i>
		</li>
		<li class="active">
			<span><font> 2. 订单确认</font></span><i></i>
		</li>
		<li>
			<span><font> 3. </font>等待上课</span><i></i>
		</li>
		<li>
			<span><font> 4. </font>确认上课结束并付款</span><i></i>
		</li>
		<li class="last">
			<span><font> 5. </font>评价</span><i></i>
		</li>
	</ul>
</div></h1>
<div class="page-body">
	<div class="order-page box">
		<h1 class="order-confirm-title">请您核对订单信息</h1>
		<div class="order-split-bar">
			确认上课人员信息
		</div>
		<div>
			<#if (address?size>0)>
			<ul class="order-buyers" node-type="order-buyer-select" id="order-buyer-select">
				<#list address as ad>
				<li <#if ad_index==0>class="selected"</#if> addressId="${ad.id}">
					<div class="name">
						${ad.name}
					</div>
					<div>
						${ad.phone}
						/
						${ad.email!}
					</div>
					<div>
						${ad.address}
					</div>
				</li>
				</#list>
			</ul>
			<div class="clear"></div>
			<div class="text-left" style="padding:15px">
				<button class="button primary" onClick="$('#newAddressDiv').show()">
					使用新的用户
				</button>
			</div>
			<div id="newAddressDiv" style="display:none">
				<#include "../user/address.form.ftl"/>
			</div>
			<#else>
			<ul class="order-buyers" node-type="order-buyer-select" id="order-buyer-select"></ul>
			<#include "../user/address.form.ftl"/>
			</#if>
		</div>
		<div class="order-split-bar">
			确认报名课程信息
		</div>
		<input type="hidden" id="productType" value="${product.productType}"/>
		<input type="hidden" id="productId" value="${product.productId}"/>
		<table align="center"  class="table">
			<thead>
				<tr>
					<th>课程名称</th>
					<th>学校</th>
					<th>课程价格(
					${MONEY_UNIT}
					)</th>
					<th>优惠信息</th>
					<th>付款方式</th>
					<th>上课报到地址</th>
				</tr>
			</thead>
			<tbody>
				<tr id=pid${product.productId}" >
				<td ><image src="${imageHelper.resolve(product.productCover!,"s120,90")}"  alt="${product.productName}" width="120" height="90" />
				<div> <a href="/course/${product.productId}-${VERSION}.html" target="_blank">
				${product.productName}
				</a> </div></td>
				<td><a href="/u/.html" target="_blank"><img  src="/theme/school/default/images/noface_s.jpg" width="50" height="50" alt=""/></a>
				<div> <a class="text-center" href="/u/html" target="_blank" usercard=""></a> </div></td>
				<td><span class="money">
				<#if product.price=-1>协议价格<#else>${product.price}</#if>
				</span></td>
				<td></td>
				<td><select  name="payType"   disabled="disabled" id="payType">
				<option value="1" >在线支付</option>
				<option value="2" selected="selected">线下支付</option>
				</select></td>
				<td>${product.sellerAddress}</td>
				</tr>
			</tbody>
			<#if product.price!=-1>
			<tfoot>
				<tr>
					<td colspan="7"  style="text-align:right">合计:<span class="money"  id="totalPrice"  price="${product.price}"> ${product.price} </span> ${MONEY_UNIT}</td>
				</tr>
			</tfoot>
			</#if>
		</table>
	</div>
</div>
