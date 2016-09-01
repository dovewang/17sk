package entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_ORDER", primary = { "ORDER_ID" })
public class Order implements Entity {

	/**
	 * 订单编号
	 */
	private long orderId;

	/**
	 * 订单类型：1、问题，2、在线课程，3、线下课程、4、临时辅导
	 */
	private byte orderType;

	/**
	 * 1、在线支付，2、线下支付
	 */
	private byte payType;

	/**
	 * 购买方
	 */
	private long buyer;

	/**
	 * 销售方
	 */
	private long seller;

	/**
	 * 交易总价
	 */
	private int price;

	/**
	 * 交易状态：0预付款，资金冻结，8交易成功；9交易关闭，不同类型的订单交易状态不同
	 */
	private byte status;

	/**
	 * 定单生成时间
	 */
	private int orderTime;

	/**
	 * 订单确认时间
	 */
	private int confirmTime;

	/**
	 * 付款时间
	 */
	private int payTime;

	/**
	 * 成交时间
	 */
	private int doneTime;

	/**
	 * 备注，如：19：00开始交易，19:10 ***调整价格，19:20***交易成功，20:00 订单确认
	 */
	private String memo;

	private List<OrderItem> items;

	public List<OrderItem> getItems() {
		return this.items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public Order() {
	}

	public Order(long orderId) {
		this.orderId = orderId;
	}

	/**
	 * @param long 订单编号
	 */
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return long 订单编号
	 */
	public long getOrderId() {
		return this.orderId;
	}

	/**
	 * @param byte 订单类型：1、问题，2、在线课程，3、线下课程、4、临时辅导
	 */
	public void setOrderType(byte orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return byte 订单类型：1、问题，2、在线课程，3、线下课程、4、临时辅导
	 */
	public byte getOrderType() {
		return this.orderType;
	}

	/**
	 * @param byte 1、在线支付，2、线下支付
	 */
	public void setPayType(byte payType) {
		this.payType = payType;
	}

	/**
	 * @return byte 1、在线支付，2、线下支付
	 */
	public byte getPayType() {
		return this.payType;
	}

	/**
	 * @param long 购买方
	 */
	public void setBuyer(long buyer) {
		this.buyer = buyer;
	}

	/**
	 * @return long 购买方
	 */
	public long getBuyer() {
		return this.buyer;
	}

	/**
	 * @param long 销售方
	 */
	public void setSeller(long seller) {
		this.seller = seller;
	}

	/**
	 * @return long 销售方
	 */
	public long getSeller() {
		return this.seller;
	}

	/**
	 * @param int 交易总价
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return int 交易总价
	 */
	public int getPrice() {
		return this.price;
	}

	/**
	 * @param byte 交易状态：0预付款，资金冻结，8交易成功；9交易关闭，不同类型的订单交易状态不同
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * @return byte 交易状态：0预付款，资金冻结，8交易成功；9交易关闭，不同类型的订单交易状态不同
	 */
	public byte getStatus() {
		return this.status;
	}

	/**
	 * @param int 定单生成时间
	 */
	public void setOrderTime(int orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * @return int 定单生成时间
	 */
	public int getOrderTime() {
		return this.orderTime;
	}

	/**
	 * @param int 订单确认时间
	 */
	public void setConfirmTime(int confirmTime) {
		this.confirmTime = confirmTime;
	}

	/**
	 * @return int 订单确认时间
	 */
	public int getConfirmTime() {
		return this.confirmTime;
	}

	/**
	 * @param int 付款时间
	 */
	public void setPayTime(int payTime) {
		this.payTime = payTime;
	}

	/**
	 * @return int 付款时间
	 */
	public int getPayTime() {
		return this.payTime;
	}

	/**
	 * @param int 成交时间
	 */
	public void setDoneTime(int doneTime) {
		this.doneTime = doneTime;
	}

	/**
	 * @return int 成交时间
	 */
	public int getDoneTime() {
		return this.doneTime;
	}

	/**
	 * @param String
	 *            备注，如：19：00开始交易，19:10 ***调整价格，19:20***交易成功，20:00 订单确认
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return String 备注，如：19：00开始交易，19:10 ***调整价格，19:20***交易成功，20:00 订单确认
	 */
	public String getMemo() {
		return this.memo;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>((int) 12);
		map.put("orderId", this.orderId);
		map.put("orderType", this.orderType);
		map.put("payType", this.payType);
		map.put("buyer", this.buyer);
		map.put("seller", this.seller);
		map.put("price", this.price);
		map.put("status", this.status);
		map.put("orderTime", this.orderTime);
		map.put("confirmTime", this.confirmTime);
		map.put("payTime", this.payTime);
		map.put("doneTime", this.doneTime);
		map.put("memo", this.memo);
		return map;
	}

	@Override
	public Object[] primaryValue() {
		return new Object[] { orderId };
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Order entity = (Order) o;
		return orderId == entity.orderId;
	}

	@Override
	public int hashCode() {
		int c = 0;
		int i = 7;
		for (Object o : primaryValue()) {
			if (o != null) {
				c += (2 ^ i + 1) * o.hashCode();
			} else {
				c += (2 ^ i + 1) * NULL_HASH;
			}
			i++;
		}
		return c;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"orderId\":").append(orderId).append(",");
		sb.append("\"orderType\":").append(orderType).append(",");
		sb.append("\"payType\":").append(payType).append(",");
		sb.append("\"buyer\":").append(buyer).append(",");
		sb.append("\"seller\":").append(seller).append(",");
		sb.append("\"price\":").append(price).append(",");
		sb.append("\"status\":").append(status).append(",");
		sb.append("\"orderTime\":").append(orderTime).append(",");
		sb.append("\"confirmTime\":").append(confirmTime).append(",");
		sb.append("\"payTime\":").append(payTime).append(",");
		sb.append("\"doneTime\":").append(doneTime).append(",");
		sb.append("\"memo\":").append("\"" + memo + "\"");
		sb.append("}");
		return sb.toString();
	}
}
