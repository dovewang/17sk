package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_TRADE", primary = {})
public class Trade implements Entity {

	/**
	 * 流水号
	 */
	private String tradeId;

	/**
	 * 商品编号
	 */
	private long productId;

	/**
	 * 商品类型，目前只有课程和问题两种类型
	 */
	private byte productType;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 购买方
	 */
	private long buyer;

	/**
	 * 销售方
	 */
	private long seller;

	/**
	 * 交易开始时间，交易建立时间
	 */
	private int tradeTime;

	/**
	 * 初始价格
	 */
	private int price;

	/**
	 * 价格折扣
	 */
	private int discount;

	/**
	 * 交易价格
	 */
	private int salePrice;

	/**
	 * 交易状态，0、未付款，1：已付款等待老师确认，2：报名成功（可以上课，实际金额没有到老师账户，只是被冻结）3:课程已结束学生付款8：交易成功；9
	 * 交易关闭
	 */
	private byte status;

	/**
	 * 订单确认时间，付款或交易关闭时间
	 */
	private int confirmTime;

	/**
	 * 备注，如：19：00开始交易 19:10 ***调整价格 19:20***交易成功 20:00 订单确认
	 */
	private String memo;

	/**
	 * @param String
	 *            流水号
	 */
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	/**
	 * @return String 流水号
	 */
	public String getTradeId() {
		return this.tradeId;
	}

	/**
	 * @param long 商品编号
	 */
	public void setProductId(long productId) {
		this.productId = productId;
	}

	/**
	 * @return long 商品编号
	 */
	public long getProductId() {
		return this.productId;
	}

	/**
	 * @param byte 商品类型，目前只有课程和问题两种类型
	 */
	public void setProductType(byte productType) {
		this.productType = productType;
	}

	/**
	 * @return byte 商品类型，目前只有课程和问题两种类型
	 */
	public byte getProductType() {
		return this.productType;
	}

	/**
	 * @param String
	 *            商品名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return String 商品名称
	 */
	public String getProductName() {
		return this.productName;
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
	 * @param int 交易开始时间，交易建立时间
	 */
	public void setTradeTime(int tradeTime) {
		this.tradeTime = tradeTime;
	}

	/**
	 * @return int 交易开始时间，交易建立时间
	 */
	public int getTradeTime() {
		return this.tradeTime;
	}

	/**
	 * @param int 初始价格
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return int 初始价格
	 */
	public int getPrice() {
		return this.price;
	}

	/**
	 * @param int 价格折扣
	 */
	public void setDiscount(int discount) {
		this.discount = discount;
	}

	/**
	 * @return int 价格折扣
	 */
	public int getDiscount() {
		return this.discount;
	}

	/**
	 * @param int 交易价格
	 */
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	/**
	 * @return int 交易价格
	 */
	public int getSalePrice() {
		return this.salePrice;
	}

	/**
	 * @param byte
	 *        交易状态，0、未付款，1：已付款等待老师确认，2：报名成功（可以上课，实际金额没有到老师账户，只是被冻结）3:课程已结束学生付款8
	 *        ：交易成功；9交易关闭
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * @return byte
	 *         交易状态，0、未付款，1：已付款等待老师确认，2：报名成功（可以上课，实际金额没有到老师账户，只是被冻结）3:课程已结束学生付款8
	 *         ：交易成功；9交易关闭
	 */
	public byte getStatus() {
		return this.status;
	}

	/**
	 * @param int 订单确认时间，付款或交易关闭时间
	 */
	public void setConfirmTime(int confirmTime) {
		this.confirmTime = confirmTime;
	}

	/**
	 * @return int 订单确认时间，付款或交易关闭时间
	 */
	public int getConfirmTime() {
		return this.confirmTime;
	}

	/**
	 * @param String
	 *            备注，如：19：00开始交易 19:10 ***调整价格 19:20***交易成功 20:00 订单确认
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return String 备注，如：19：00开始交易 19:10 ***调整价格 19:20***交易成功 20:00 订单确认
	 */
	public String getMemo() {
		return this.memo;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>((int) 13);
		map.put("tradeId", tradeId);
		map.put("productId", productId);
		map.put("productType", productType);
		map.put("productName", productName);
		map.put("buyer", buyer);
		map.put("seller", seller);
		map.put("tradeTime", tradeTime);
		map.put("price", price);
		map.put("discount", discount);
		map.put("salePrice", salePrice);
		map.put("status", status);
		map.put("confirmTime", confirmTime);
		map.put("memo", memo);
		return map;
	}

	@Override
	public Object[] primaryValue() {
		return new Object[] {};
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"tradeId\":").append("\"" + tradeId + "\"").append(",");
		sb.append("\"productId\":").append(productId).append(",");
		sb.append("\"productType\":").append(productType).append(",");
		sb.append("\"productName\":").append("\"" + productName + "\"").append(",");
		sb.append("\"buyer\":").append(buyer).append(",");
		sb.append("\"seller\":").append(seller).append(",");
		sb.append("\"tradeTime\":").append(tradeTime).append(",");
		sb.append("\"price\":").append(price).append(",");
		sb.append("\"discount\":").append(discount).append(",");
		sb.append("\"salePrice\":").append(salePrice).append(",");
		sb.append("\"status\":").append(status).append(",");
		sb.append("\"confirmTime\":").append(confirmTime).append(",");
		sb.append("\"memo\":").append("\"" + memo + "\"");
		sb.append("}");
		return sb.toString();
	}
}
