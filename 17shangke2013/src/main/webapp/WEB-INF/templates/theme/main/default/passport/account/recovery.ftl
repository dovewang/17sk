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
						<h1 class="title">找回支付密码<span class="gray" style="font-size:14px; font-weight:normal;">(如果您是第一次使用支付，默认的支付密码为登录密码，您可以点这里<a href="#">修改支付密码</a>)</span></h1>
						<form action=" " id="accountRecoveryForm">
							<dl class="form label120">
								<dd>
									<label>请输入您的注册邮箱：</label><span>
										<input  type="text" name="email"/>
										</span>
								</dd>
                                <dd>
									<label>请输入您的登录密码：</label><span>
										<input  type="password" name="password"/>
										</span>
								</dd>
								<dd>
									<label> &nbsp;</label><span>
										 <button class="button primary xlarge">找回支付密码</button>
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