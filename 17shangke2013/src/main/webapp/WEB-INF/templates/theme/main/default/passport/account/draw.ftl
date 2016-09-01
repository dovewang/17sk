<!DOCTYPE html>
<html>
	<head>
		<#assign SECURE=true/>
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
					<div class="box m10"  id="my-account">
						<h1 class="title">账户提现</h1>
						<form action="/payment/alipay.to.html">
							<dl class="form">
								<dd>
									<label>请输入提取的金额：</label><span>
										<input  type="text" name="money"/>
										元</span>
								</dd>
								<dd>
									<label> &nbsp;</label><span>
										目前暂不支持提现功能
									</span>
								</dd>
							</dl>
						</form>
					</div>
				</div>
			</div>
		</div>
		<#include "../../footer.ftl"/>
	</body>
</html>