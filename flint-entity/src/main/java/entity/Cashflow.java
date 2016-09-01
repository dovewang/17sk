package entity;

import java.util.HashMap;
import java.util.Map;

import flint.annotation.Table;
import flint.jdbc.Entity;

@Table(name = "TB_CASHFLOW", primary = { "CASHFLOW_ID" })
public class Cashflow implements  Entity{

	/**
	*变更流水号
	*/
	private long   cashflowId;

	/**
	*交易流水号，这里可以是下订单预付款的流水号，充值记录流水号，取现记录流水号，付款流水号，退费流水号
	*/
	private long   tradeId;

	/**
	*初始可用余额
	*/
	private int   oldAvailable;

	/**
	*初始冻结资金
	*/
	private int   oldFrozen;

	/**
	*初始账户余额
	*/
	private int   oldBalance;

	/**
	*现有可用余额
	*/
	private int   newAvailable;

	/**
	*现有冻结资金
	*/
	private int   newFrozen;

	/**
	*现有账户余额
	*/
	private int   newBalance;

	/**
	*收入
	*/
	private int   income;

	/**
	*支出
	*/
	private int   expend;

	/**
	*变更账户，用户账户
	*/
	private long   account;

	/**
	*变更时间
	*/
	private int   time;

	/**
	*变更类型
	*/
	private byte   type;

	/**
	*备注
	*/
	private String   memo;

   
    public Cashflow(){}
    
    public Cashflow(long cashflowId )
    {
           this.cashflowId=cashflowId;
     }
	/**
	 * @param long   变更流水号
	 */
	public void setCashflowId(long cashflowId)
	{
	 	this.cashflowId=cashflowId;
	}
	
	/**
	 * @return long  变更流水号
	 */
	public long getCashflowId()
	{
	 	return this.cashflowId;
	}
	/**
	 * @param long   交易流水号，这里可以是下订单预付款的流水号，充值记录流水号，取现记录流水号，付款流水号，退费流水号
	 */
	public void setTradeId(long tradeId)
	{
	 	this.tradeId=tradeId;
	}
	
	/**
	 * @return long  交易流水号，这里可以是下订单预付款的流水号，充值记录流水号，取现记录流水号，付款流水号，退费流水号
	 */
	public long getTradeId()
	{
	 	return this.tradeId;
	}
	/**
	 * @param int   初始可用余额
	 */
	public void setOldAvailable(int oldAvailable)
	{
	 	this.oldAvailable=oldAvailable;
	}
	
	/**
	 * @return int  初始可用余额
	 */
	public int getOldAvailable()
	{
	 	return this.oldAvailable;
	}
	/**
	 * @param int   初始冻结资金
	 */
	public void setOldFrozen(int oldFrozen)
	{
	 	this.oldFrozen=oldFrozen;
	}
	
	/**
	 * @return int  初始冻结资金
	 */
	public int getOldFrozen()
	{
	 	return this.oldFrozen;
	}
	/**
	 * @param int   初始账户余额
	 */
	public void setOldBalance(int oldBalance)
	{
	 	this.oldBalance=oldBalance;
	}
	
	/**
	 * @return int  初始账户余额
	 */
	public int getOldBalance()
	{
	 	return this.oldBalance;
	}
	/**
	 * @param int   现有可用余额
	 */
	public void setNewAvailable(int newAvailable)
	{
	 	this.newAvailable=newAvailable;
	}
	
	/**
	 * @return int  现有可用余额
	 */
	public int getNewAvailable()
	{
	 	return this.newAvailable;
	}
	/**
	 * @param int   现有冻结资金
	 */
	public void setNewFrozen(int newFrozen)
	{
	 	this.newFrozen=newFrozen;
	}
	
	/**
	 * @return int  现有冻结资金
	 */
	public int getNewFrozen()
	{
	 	return this.newFrozen;
	}
	/**
	 * @param int   现有账户余额
	 */
	public void setNewBalance(int newBalance)
	{
	 	this.newBalance=newBalance;
	}
	
	/**
	 * @return int  现有账户余额
	 */
	public int getNewBalance()
	{
	 	return this.newBalance;
	}
	/**
	 * @param int   收入
	 */
	public void setIncome(int income)
	{
	 	this.income=income;
	}
	
	/**
	 * @return int  收入
	 */
	public int getIncome()
	{
	 	return this.income;
	}
	/**
	 * @param int   支出
	 */
	public void setExpend(int expend)
	{
	 	this.expend=expend;
	}
	
	/**
	 * @return int  支出
	 */
	public int getExpend()
	{
	 	return this.expend;
	}
	/**
	 * @param long   变更账户，用户账户
	 */
	public void setAccount(long account)
	{
	 	this.account=account;
	}
	
	/**
	 * @return long  变更账户，用户账户
	 */
	public long getAccount()
	{
	 	return this.account;
	}
	/**
	 * @param int   变更时间
	 */
	public void setTime(int time)
	{
	 	this.time=time;
	}
	
	/**
	 * @return int  变更时间
	 */
	public int getTime()
	{
	 	return this.time;
	}
	/**
	 * @param byte   变更类型
	 */
	public void setType(byte type)
	{
	 	this.type=type;
	}
	
	/**
	 * @return byte  变更类型
	 */
	public byte getType()
	{
	 	return this.type;
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
	    Map<String, Object> map=new HashMap<String, Object>((int)14);
	    map.put("cashflowId",cashflowId);
	    map.put("tradeId",tradeId);
	    map.put("oldAvailable",oldAvailable);
	    map.put("oldFrozen",oldFrozen);
	    map.put("oldBalance",oldBalance);
	    map.put("newAvailable",newAvailable);
	    map.put("newFrozen",newFrozen);
	    map.put("newBalance",newBalance);
	    map.put("income",income);
	    map.put("expend",expend);
	    map.put("account",account);
	    map.put("time",time);
	    map.put("type",type);
	    map.put("memo",memo);
		return map;
	}
	
	@Override
	public Object[] primaryValue() {
		return new Object[] {cashflowId};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
          Cashflow  entity=(Cashflow)o;
          return cashflowId==entity.cashflowId;
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
	     sb.append("\"cashflowId\":").append(cashflowId).append(",");
	     sb.append("\"tradeId\":").append(tradeId).append(",");
	     sb.append("\"oldAvailable\":").append(oldAvailable).append(",");
	     sb.append("\"oldFrozen\":").append(oldFrozen).append(",");
	     sb.append("\"oldBalance\":").append(oldBalance).append(",");
	     sb.append("\"newAvailable\":").append(newAvailable).append(",");
	     sb.append("\"newFrozen\":").append(newFrozen).append(",");
	     sb.append("\"newBalance\":").append(newBalance).append(",");
	     sb.append("\"income\":").append(income).append(",");
	     sb.append("\"expend\":").append(expend).append(",");
	     sb.append("\"account\":").append(account).append(",");
	     sb.append("\"time\":").append(time).append(",");
	     sb.append("\"type\":").append(type).append(",");
	     sb.append("\"memo\":").append("\""+memo+"\"");
	    sb.append("}");
	    return sb.toString();
	}
}
