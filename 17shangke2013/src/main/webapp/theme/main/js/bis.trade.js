/**
 * 
 */
/*报名用户选择方法注入，目前不支持多人报名选择*/
Kiss.addEventListener("order-buyer-select", function(obj) {
	$(obj).delegate("li", "click", function() {
		$(this).addClass('selected').siblings().removeClass("selected");
	});
}).addEventListener("save-user-address", function(obj) {
	$(obj).click(function() {
		var data;
		$("#userAddressForm").postForm({
			handleForm : function(request) {
				data = request;
			},
			success : function(result) {
				if (result.status == 1) {
					$("#order-buyer-select").append('<li addressId=' + result.id + ' id="address' + result.id + '"><div class="name">' + data.getParameter("name") + '</div><div>' + data.getParameter("phone") + '/' + isEmpty(data.getParameter("email"), "") + '</div><div></div></li>')
					$("#newAddressDiv").hide();
					$("#address" + result.id).click();
				}
			}
		});
	});
});

Trade = {};

/**
 * 订购课程
 */
Trade.order = function(id, type) {
	if (!Env.isLogin) {
		$.showLogin();
		return;
	}
	$.formSubmit("/order/confirm.html", {
		id : id,
		type : type
	});
}
/*
 * 订单确认
 */
Trade.submitOrder = function() {
	var obs = $("#order-buyer-select");
	var address = "";
	if (obs.length > 0) {
		var sel = obs.find("li.selected");
		if (sel.length < 0) {
			return;
		} else {
			address = sel.attr("addressId");
		}
	}
	var price=$("#totalPrice").attr("price");
	$.formSubmit("/trade/order.html", {
		productId : $("#productId").val(),
		productType : $("#productType").val(),
		total :(price?price:-1),
		payType : $("#payType").val(),
		key : $("#tradeOnceKey").val(),
		address : address//线下课程专有
	});
}
/*
 *订单取消
 */
Trade.cancleOrder = function(orderId) {

}