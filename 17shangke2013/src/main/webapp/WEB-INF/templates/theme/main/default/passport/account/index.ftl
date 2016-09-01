<!DOCTYPE html>
<html>
	<head>
		<#include "../../meta.ftl" />
		<link href="${IMAGE_DOMAIN}/theme/main/skins/default/css/zone.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
	</head>
	<body class="zone-page">
		<#include "../../user/header.ftl" />
		<div class="page-container">
			<div class="page-body">
				<div class="page-left">
					<#include "../../user/left.ftl" />
				</div>
				<div class="page-main">
					<div class="box m15"  id="my-account">
						<h1 class="title">支付账户管理</h1>
						<dl>
							<dd>
								总&nbsp;资&nbsp;产：<span class="money">${account.balance}</span>${MONEY_UNIT}
							</dd>
							<dd>
								冻结资金：<span class="money">${account.frozen}</span>${MONEY_UNIT}
							</dd>
							<dd>
								可用余额：<span class="money">${account.available}</span>${MONEY_UNIT} <a href="${SECURE_DOMAIN}/account/draw.html">提现 </a>| <a href="${SECURE_DOMAIN}/account/recharge.html">充值 </a>
							</dd>
						</dl>
						<h1 class="title">账户明细</h1>
						<table class="table">
							<thead>
								<tr>
									<th>交易流水号</th>
									<th>收入(${MONEY_UNIT})</th>
									<th>支出(${MONEY_UNIT})</th>
									<th>冻结金额(${MONEY_UNIT})</th>
									<th>可用余额(${MONEY_UNIT})</th>
									<th>发生时间</th>
									<th>备注</th>
								</tr>
							</thead>
							<tbody>
								<#list detail.result as c >
								<tr>
									<td>${c.tradeId}</td>
									<td class="money" align="right">${c.income}</td>
									<td class="gray1" align="right">${c.expend}</td>
									<td align="right">${c.newFrozen}</td>
									<td class="money" align="right">${c.newAvailable}</td>
									<td>${flint.timeToDate(c.time,"yyyy-MM-dd HH:mm:ss")}</td>
									<td>${c.memo}</td>
								</tr>
								</#list>
							</tbody>
						</table>
						<@flint.pagination detail,"","","" />
					</div>
				</div>
			</div>
		</div>
		<#include "../../footer.ftl"/>
	</body>
</html>