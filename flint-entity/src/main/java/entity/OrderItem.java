package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_ORDER_ITEM", primary = {  })
public class OrderItem implements  Entity{

	/**
	*订单号
	*/
	private long   orderId;

	/**
	*商品编号
	*/
	private long   productId;

	/**
	*商品图标
	*/
	private String   productCover;

	/**
	*商品名称
	*/
	private String   productName;

	/**
	*商品类型，目前有问题，在线课程和线下课程3种
	*/
	private byte   productType;

	/**
	*商品状态，如课程，未开课，已开课，已结课，问题，无人解答，有人解答，未选择答案，已选择，退费等
	*/
	private byte   status;

	/**
	*优惠编号，0，代表无优惠
	*/
	private long   favorId;

	/**
	*原始价格
	*/
	private int   oldPrice;

	/**
	*商品价格
	*/
	private int   price;

	/**
	*商品快照，用户可能会修改原来的商品信息，这里以交易时的状态为准
	*/
	private String   snapshot;

	/**
	*购买总数
	*/
	private int   quantity;

	/**
	*预计发货数量
	*/
	private int   deliveries;

	/**
	*总发货量
	*/
	private int   total;

	/**
	*学校上课地址
	*/
	private String   sellerAddress;

	/**
	*学校地址编号
	*/
	private long   sellerAddressId;

	/**
	*报名用户地址，线下课程使用，地址与用户账户无关
	*/
	private String   buyerAddress;

	/**
	*用户地址编号
	*/
	private long   buyerAddressId;

	/**
	*在线上课的教室编号
	*/
	private long   roomId;

	/**
	*手机验证码
	*/
	private String   code;

	/**
	*二维码，线下课程专有，验证二位信息
	*/
	private String   code2d;

	/**
	*子商品编号，例如问题，这里填写答案的编号，商品填写问题的编号
	*/
	private long   subId;

	/**
	*
	*/
	private String   f1;

	/**
	*
	*/
	private String   f2;

	/**
	*
	*/
	private String   f3;

	/**
	*
	*/
	private String   f4;

	/**
	*
	*/
	private String   f5;

	/**
	*
	*/
	private String   f6;

	/**
	*
	*/
	private String   f7;

	/**
	*
	*/
	private String   f8;

	/**
	*
	*/
	private String   f9;

	/**
	*
	*/
	private String   f10;

	/**
	*备注
	*/
	private String   memo;

   
	/**
	 * @param long   订单号
	 */
	public void setOrderId(long orderId)
	{
	 	this.orderId=orderId;
	}
	
	/**
	 * @return long  订单号
	 */
	public long getOrderId()
	{
	 	return this.orderId;
	}
	/**
	 * @param long   商品编号
	 */
	public void setProductId(long productId)
	{
	 	this.productId=productId;
	}
	
	/**
	 * @return long  商品编号
	 */
	public long getProductId()
	{
	 	return this.productId;
	}
	/**
	 * @param String   商品图标
	 */
	public void setProductCover(String productCover)
	{
	 	this.productCover=productCover;
	}
	
	/**
	 * @return String  商品图标
	 */
	public String getProductCover()
	{
	 	return this.productCover;
	}
	/**
	 * @param String   商品名称
	 */
	public void setProductName(String productName)
	{
	 	this.productName=productName;
	}
	
	/**
	 * @return String  商品名称
	 */
	public String getProductName()
	{
	 	return this.productName;
	}
	/**
	 * @param byte   商品类型，目前有问题，在线课程和线下课程3种
	 */
	public void setProductType(byte productType)
	{
	 	this.productType=productType;
	}
	
	/**
	 * @return byte  商品类型，目前有问题，在线课程和线下课程3种
	 */
	public byte getProductType()
	{
	 	return this.productType;
	}
	/**
	 * @param byte   商品状态，如课程，未开课，已开课，已结课，问题，无人解答，有人解答，未选择答案，已选择，退费等
	 */
	public void setStatus(byte status)
	{
	 	this.status=status;
	}
	
	/**
	 * @return byte  商品状态，如课程，未开课，已开课，已结课，问题，无人解答，有人解答，未选择答案，已选择，退费等
	 */
	public byte getStatus()
	{
	 	return this.status;
	}
	/**
	 * @param long   优惠编号，0，代表无优惠
	 */
	public void setFavorId(long favorId)
	{
	 	this.favorId=favorId;
	}
	
	/**
	 * @return long  优惠编号，0，代表无优惠
	 */
	public long getFavorId()
	{
	 	return this.favorId;
	}
	/**
	 * @param int   原始价格
	 */
	public void setOldPrice(int oldPrice)
	{
	 	this.oldPrice=oldPrice;
	}
	
	/**
	 * @return int  原始价格
	 */
	public int getOldPrice()
	{
	 	return this.oldPrice;
	}
	/**
	 * @param int   商品价格
	 */
	public void setPrice(int price)
	{
	 	this.price=price;
	}
	
	/**
	 * @return int  商品价格
	 */
	public int getPrice()
	{
	 	return this.price;
	}
	/**
	 * @param String   商品快照，用户可能会修改原来的商品信息，这里以交易时的状态为准
	 */
	public void setSnapshot(String snapshot)
	{
	 	this.snapshot=snapshot;
	}
	
	/**
	 * @return String  商品快照，用户可能会修改原来的商品信息，这里以交易时的状态为准
	 */
	public String getSnapshot()
	{
	 	return this.snapshot;
	}
	/**
	 * @param int   购买总数
	 */
	public void setQuantity(int quantity)
	{
	 	this.quantity=quantity;
	}
	
	/**
	 * @return int  购买总数
	 */
	public int getQuantity()
	{
	 	return this.quantity;
	}
	/**
	 * @param int   预计发货数量
	 */
	public void setDeliveries(int deliveries)
	{
	 	this.deliveries=deliveries;
	}
	
	/**
	 * @return int  预计发货数量
	 */
	public int getDeliveries()
	{
	 	return this.deliveries;
	}
	/**
	 * @param int   总发货量
	 */
	public void setTotal(int total)
	{
	 	this.total=total;
	}
	
	/**
	 * @return int  总发货量
	 */
	public int getTotal()
	{
	 	return this.total;
	}
	/**
	 * @param String   学校上课地址
	 */
	public void setSellerAddress(String sellerAddress)
	{
	 	this.sellerAddress=sellerAddress;
	}
	
	/**
	 * @return String  学校上课地址
	 */
	public String getSellerAddress()
	{
	 	return this.sellerAddress;
	}
	/**
	 * @param long   学校地址编号
	 */
	public void setSellerAddressId(long sellerAddressId)
	{
	 	this.sellerAddressId=sellerAddressId;
	}
	
	/**
	 * @return long  学校地址编号
	 */
	public long getSellerAddressId()
	{
	 	return this.sellerAddressId;
	}
	/**
	 * @param String   报名用户地址，线下课程使用，地址与用户账户无关
	 */
	public void setBuyerAddress(String buyerAddress)
	{
	 	this.buyerAddress=buyerAddress;
	}
	
	/**
	 * @return String  报名用户地址，线下课程使用，地址与用户账户无关
	 */
	public String getBuyerAddress()
	{
	 	return this.buyerAddress;
	}
	/**
	 * @param long   用户地址编号
	 */
	public void setBuyerAddressId(long buyerAddressId)
	{
	 	this.buyerAddressId=buyerAddressId;
	}
	
	/**
	 * @return long  用户地址编号
	 */
	public long getBuyerAddressId()
	{
	 	return this.buyerAddressId;
	}
	/**
	 * @param long   在线上课的教室编号
	 */
	public void setRoomId(long roomId)
	{
	 	this.roomId=roomId;
	}
	
	/**
	 * @return long  在线上课的教室编号
	 */
	public long getRoomId()
	{
	 	return this.roomId;
	}
	/**
	 * @param String   手机验证码
	 */
	public void setCode(String code)
	{
	 	this.code=code;
	}
	
	/**
	 * @return String  手机验证码
	 */
	public String getCode()
	{
	 	return this.code;
	}
	/**
	 * @param String   二维码，线下课程专有，验证二位信息
	 */
	public void setCode2d(String code2d)
	{
	 	this.code2d=code2d;
	}
	
	/**
	 * @return String  二维码，线下课程专有，验证二位信息
	 */
	public String getCode2d()
	{
	 	return this.code2d;
	}
	/**
	 * @param long   子商品编号，例如问题，这里填写答案的编号，商品填写问题的编号
	 */
	public void setSubId(long subId)
	{
	 	this.subId=subId;
	}
	
	/**
	 * @return long  子商品编号，例如问题，这里填写答案的编号，商品填写问题的编号
	 */
	public long getSubId()
	{
	 	return this.subId;
	}
	/**
	 * @param String   
	 */
	public void setF1(String f1)
	{
	 	this.f1=f1;
	}
	
	/**
	 * @return String  
	 */
	public String getF1()
	{
	 	return this.f1;
	}
	/**
	 * @param String   
	 */
	public void setF2(String f2)
	{
	 	this.f2=f2;
	}
	
	/**
	 * @return String  
	 */
	public String getF2()
	{
	 	return this.f2;
	}
	/**
	 * @param String   
	 */
	public void setF3(String f3)
	{
	 	this.f3=f3;
	}
	
	/**
	 * @return String  
	 */
	public String getF3()
	{
	 	return this.f3;
	}
	/**
	 * @param String   
	 */
	public void setF4(String f4)
	{
	 	this.f4=f4;
	}
	
	/**
	 * @return String  
	 */
	public String getF4()
	{
	 	return this.f4;
	}
	/**
	 * @param String   
	 */
	public void setF5(String f5)
	{
	 	this.f5=f5;
	}
	
	/**
	 * @return String  
	 */
	public String getF5()
	{
	 	return this.f5;
	}
	/**
	 * @param String   
	 */
	public void setF6(String f6)
	{
	 	this.f6=f6;
	}
	
	/**
	 * @return String  
	 */
	public String getF6()
	{
	 	return this.f6;
	}
	/**
	 * @param String   
	 */
	public void setF7(String f7)
	{
	 	this.f7=f7;
	}
	
	/**
	 * @return String  
	 */
	public String getF7()
	{
	 	return this.f7;
	}
	/**
	 * @param String   
	 */
	public void setF8(String f8)
	{
	 	this.f8=f8;
	}
	
	/**
	 * @return String  
	 */
	public String getF8()
	{
	 	return this.f8;
	}
	/**
	 * @param String   
	 */
	public void setF9(String f9)
	{
	 	this.f9=f9;
	}
	
	/**
	 * @return String  
	 */
	public String getF9()
	{
	 	return this.f9;
	}
	/**
	 * @param String   
	 */
	public void setF10(String f10)
	{
	 	this.f10=f10;
	}
	
	/**
	 * @return String  
	 */
	public String getF10()
	{
	 	return this.f10;
	}
	/**
	 * @param String   备注
	 */
	public void setMemo(String memo)
	{
	 	this.memo=memo;
	}
	
	/**
	 * @return String  备注
	 */
	public String getMemo()
	{
	 	return this.memo;
	}

   @Override
	public Map<String, Object> toMap() {
	    Map<String, Object> map=new HashMap<String, Object>((int)32);
	    map.put("orderId",this.orderId);
	    map.put("productId",this.productId);
	    map.put("productCover",this.productCover);
	    map.put("productName",this.productName);
	    map.put("productType",this.productType);
	    map.put("status",this.status);
	    map.put("favorId",this.favorId);
	    map.put("oldPrice",this.oldPrice);
	    map.put("price",this.price);
	    map.put("snapshot",this.snapshot);
	    map.put("quantity",this.quantity);
	    map.put("deliveries",this.deliveries);
	    map.put("total",this.total);
	    map.put("sellerAddress",this.sellerAddress);
	    map.put("sellerAddressId",this.sellerAddressId);
	    map.put("buyerAddress",this.buyerAddress);
	    map.put("buyerAddressId",this.buyerAddressId);
	    map.put("roomId",this.roomId);
	    map.put("code",this.code);
	    map.put("code2d",this.code2d);
	    map.put("subId",this.subId);
	    map.put("f1",this.f1);
	    map.put("f2",this.f2);
	    map.put("f3",this.f3);
	    map.put("f4",this.f4);
	    map.put("f5",this.f5);
	    map.put("f6",this.f6);
	    map.put("f7",this.f7);
	    map.put("f8",this.f8);
	    map.put("f9",this.f9);
	    map.put("f10",this.f10);
	    map.put("memo",this.memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {};
	}

	
	public String toString(){
	    StringBuilder sb=new StringBuilder("{");
	     sb.append("\"orderId\":").append(orderId).append(",");
	     sb.append("\"productId\":").append(productId).append(",");
	     sb.append("\"productCover\":").append("\""+productCover+"\"").append(",");
	     sb.append("\"productName\":").append("\""+productName+"\"").append(",");
	     sb.append("\"productType\":").append(productType).append(",");
	     sb.append("\"status\":").append(status).append(",");
	     sb.append("\"favorId\":").append(favorId).append(",");
	     sb.append("\"oldPrice\":").append(oldPrice).append(",");
	     sb.append("\"price\":").append(price).append(",");
	     sb.append("\"snapshot\":").append("\""+snapshot+"\"").append(",");
	     sb.append("\"quantity\":").append(quantity).append(",");
	     sb.append("\"deliveries\":").append(deliveries).append(",");
	     sb.append("\"total\":").append(total).append(",");
	     sb.append("\"sellerAddress\":").append("\""+sellerAddress+"\"").append(",");
	     sb.append("\"sellerAddressId\":").append(sellerAddressId).append(",");
	     sb.append("\"buyerAddress\":").append("\""+buyerAddress+"\"").append(",");
	     sb.append("\"buyerAddressId\":").append(buyerAddressId).append(",");
	     sb.append("\"roomId\":").append(roomId).append(",");
	     sb.append("\"code\":").append("\""+code+"\"").append(",");
	     sb.append("\"code2d\":").append("\""+code2d+"\"").append(",");
	     sb.append("\"subId\":").append(subId).append(",");
	     sb.append("\"f1\":").append("\""+f1+"\"").append(",");
	     sb.append("\"f2\":").append("\""+f2+"\"").append(",");
	     sb.append("\"f3\":").append("\""+f3+"\"").append(",");
	     sb.append("\"f4\":").append("\""+f4+"\"").append(",");
	     sb.append("\"f5\":").append("\""+f5+"\"").append(",");
	     sb.append("\"f6\":").append("\""+f6+"\"").append(",");
	     sb.append("\"f7\":").append("\""+f7+"\"").append(",");
	     sb.append("\"f8\":").append("\""+f8+"\"").append(",");
	     sb.append("\"f9\":").append("\""+f9+"\"").append(",");
	     sb.append("\"f10\":").append("\""+f10+"\"").append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
