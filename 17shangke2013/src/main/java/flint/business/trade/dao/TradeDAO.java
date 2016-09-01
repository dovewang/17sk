package flint.business.trade.dao;

import java.util.List;
import java.util.Map;

import entity.Order;
import flint.base.BaseDAO;
import flint.business.trade.service.TradeStatus;
import flint.common.Page;

/**
 * 
 * 功能描述：系统自动生成，请修改<br>
 * 
 * 
 * 日 期：2011-7-14 11:47:06<br>
 * 
 * 作 者：Spirit<br>
 * 
 * 版权所有：©mosai<br>
 * 
 * 版 本 ：v0.01<br>
 * 
 * 联系方式：<a href="mailto:dream-all@163.com">dream-all@163.com</a><br>
 * 
 * 修改备注：{修改人|修改时间}<br>
 * 
 * 
 **/
public interface TradeDAO extends BaseDAO<Order, Long> {

	/**
	 * 预付确认， 该方法只限问题模块使用
	 * 
	 * @param orderId
	 */
	void prepay(long orderId);

	/**
	 * 
	 * 
	 * @param orderId
	 * @param seller
	 */
	void updateOrderStatus(long orderId, long buyer, byte status);

	/**
	 * 订单取消
	 * 
	 * @param orderId
	 * @return
	 */
	Order cancle(long orderId);

	/**
	 * 确认付款
	 * 
	 * @param orderId
	 */
	void pay(long orderId);

	/**
	 * 订单查询
	 * 
	 * @param userId
	 * @param type
	 * @param page
	 * @param size
	 * @return
	 */
	Page<Order> list(long userId, byte type, long page, long size);

	/**
	 * 获取订单状态，-1代表订单不存在
	 * 
	 * @param productId
	 * @param productType
	 * @param buyer
	 * @return
	 */
	TradeStatus getTradeStatus(long productId, byte productType, long buyer);

	Map<String, Integer> stat(long userId);

	List<Long> getCourseMembers(long courseId, byte status);


	List<Order> getOrders(long productId, byte productType, byte status);
}
