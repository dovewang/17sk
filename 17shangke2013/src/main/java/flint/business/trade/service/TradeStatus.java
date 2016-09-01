package flint.business.trade.service;

import flint.business.constant.TradeConst;

public class TradeStatus {

	private byte orderStatus;// 订单状态
	private byte productStatus;// 订单中的商品状态

	public byte getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(byte orderStatus) {
		this.orderStatus = orderStatus;
	}

	public byte getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(byte productStatus) {
		this.productStatus = productStatus;
	}

	/**
	 * 判断用户是否已经购买过该产品
	 * 
	 * @return
	 */
	public boolean isPayed() {
		return false;
	}

	/**
	 * 用户是否已经预付款
	 * 
	 * @return
	 */
	public boolean isPrepayed() {
		return orderStatus >= TradeConst.ORDER_CONFIRM && orderStatus <= TradeConst.COMMENT;
	}

}
