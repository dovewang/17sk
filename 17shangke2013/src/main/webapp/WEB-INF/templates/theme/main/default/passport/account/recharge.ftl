<!DOCTYPE html>
<html>
	<head>
		<#assign SECURE=true/>
		<#include "../../meta.ftl" />
        <link href="${IMAGE_DOMAIN}/theme/main/skins/default/css/zone.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
	</head>
	<body class="zone-page">
		<div class="page-container">
			<div class="page-body">
				<div class="page-left">
					<#include "../../user/left.ftl" />
				</div>
				<div class="page-main">
					<div class="box m10"  id="my-account">
						<h1 class="title">账户充值</h1>
						<form action="/payment/alipay.to.html">
							<dl class="form">
								<dd>
									<label>请输入充值金额：</label><span>
										<input  type="text" name="money"/>
										元</span>
								</dd>
								<dd>
									<label> &nbsp;</label><span>
										<input type="image"  src="${SITE_DOMAIN}/theme/base/images/alipay.png" />
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