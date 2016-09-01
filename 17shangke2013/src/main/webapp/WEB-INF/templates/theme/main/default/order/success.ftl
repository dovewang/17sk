<#assign TITLE="报名成功-"/>
<#assign CATEGORY=""/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<#include "../meta.ftl" />
	</head>
	<body>
		<#include "../header.ftl" />
		<div class="page-container">
           <div class="box">
             <div class="order-success">恭喜您，报名成功！</div>
              <!--课程信息-->
             <div class="">
           
             </div>
           </div>
           <!--分享
           <div class="share-box">             
             <textarea></textarea>
             <button class="button primary large">分享</button>
           </div>
           -->
           <div class="p15">
           <a class="button"  href="/trade/detail/order.html?orderId=${RequestParameters["orderId"]!}">查看详细</a>
           </div>
        </div>        
		<#include "../footer.ftl"/>
	</body>
</html>