<#assign type=messageType!0 />
<!DOCTYPE html>
<html>
	<head>
		<#include "../meta.ftl" />
		<link href="${IMAGE_DOMAIN}/theme/main/skins/default/css/zone.css?${VERSION}" rel="stylesheet" type="text/css" charset="${ENCODING}"/>
	</head>
	<body class="zone-page">
		<#include "header.ftl" />
		<div class="page-container">
			<div class="page-body">
				<div class="page-left">
					<#include "left.ftl" />
				</div>
				<div class="page-main">
					<div class="m10 box">
						<div class="tab-header">
							<ul id="message-tab">
								<li class="active">
									<a href="javascript:;">问题</a>
								</li>
							</ul>
						</div>
						<div>
							<#include "../question/result.list.ftl" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>
<script>
	
</script>