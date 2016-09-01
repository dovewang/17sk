package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_ORDER_LOG", primary = { "ID" })
public class OrderLog implements  Entity{

	/**
	*主键
	*/
	private long   id;

	/**
	*订单编号
	*/
	private long   orderId;

	/**
	*日志类型：订单创建、订单修改、订单支付、课程退订、上课、课程退款、订单发货、订单完成、订单作废
	*/
	private byte   type;

	/**
	*用户编号
	*/
	private long   userId;

	/**
	*信息
	*/
	private String   info;

	/**
	*处理时间
	*/
	private int   dateline;

	/**
	*备注
	*/
	private String   memo;

   
    public OrderLog(){}
    
    public OrderLog(long id )
    {
           this.id=id;
     }
	/**
	 * @param long   主键
	 */
	public void setId(long id)
	{
	 	this.id=id;
	}
	
	/**
	 * @return long  主键
	 */
	public long getId()
	{
	 	return this.id;
	}
	/**
	 * @param long   订单编号
	 */
	public void setOrderId(long orderId)
	{
	 	this.orderId=orderId;
	}
	
	/**
	 * @return long  订单编号
	 */
	public long getOrderId()
	{
	 	return this.orderId;
	}
	/**
	 * @param byte   日志类型：订单创建、订单修改、订单支付、课程退订、上课、课程退款、订单发货、订单完成、订单作废
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  日志类型：订单创建、订单修改、订单支付、课程退订、上课、课程退款、订单发货、订单完成、订单作废
	 */
	public byte getType()
	{
	 	return this.type;
	}
	/**
	 * @param long   用户编号
	 */
	public void setUserId(long userId)
	{
	 	this.userId=userId;
	}
	
	/**
	 * @return long  用户编号
	 */
	public long getUserId()
	{
	 	return this.userId;
	}
	/**
	 * @param String   信息
	 */
	public void setInfo(String info)
	{
	 	this.info=info;
	}
	
	/**
	 * @return String  信息
	 */
	public String getInfo()
	{
	 	return this.info;
	}
	/**
	 * @param int   处理时间
	 */
	public void setDateline(int dateline)
	{
	 	this.dateline=dateline;
	}
	
	/**
	 * @return int  处理时间
	 */
	public int getDateline()
	{
	 	return this.dateline;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)7);
	    map.put("id",this.id);
	    map.put("orderId",this.orderId);
	    map.put("type",this.type);
	    map.put("userId",this.userId);
	    map.put("info",this.info);
	    map.put("dateline",this.dateline);
	    map.put("memo",this.memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {id};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          OrderLog  entity=(OrderLog)o;
          return id==entity.id;
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
	
	
	public String toString(){
	    StringBuilder sb=new StringBuilder("{");
	     sb.append("\"id\":").append(id).append(",");
	     sb.append("\"orderId\":").append(orderId).append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"userId\":").append(userId).append(",");
	     sb.append("\"info\":").append("\""+info+"\"").append(",");
	     sb.append("\"dateline\":").append(dateline).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
