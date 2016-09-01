package flint.business.trade.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import entity.Order;
import entity.OrderItem;
import flint.base.BaseService;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.exception.DataAccessException;

/**
 * 
 * 功能描述：系统自动生成，请修改<br>
 * 
 * 日 期：2011-7-13 10:42:25<br>
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
 * 交易流程 order--prepay()
 **/
public interface TradeService extends BaseService {

	/**
	 * 获取订单
	 * 
	 * @param orderId
	 * @return
	 */
	Order getOrder(long orderId);

	/**
	 * 获取某一商品的某个状态的全部订单信息
	 * 
	 * @param productId
	 * @param productType
	 * @param status
	 * @return
	 */
	List<Order> getOrders(long productId, byte productType, byte status);

	/**
	 * 检查账户，返回可用余额
	 * 
	 * @param userId
	 * @param money
	 * @return
	 * @throws DataAccessException
	 */
	int check(long userId, int money) throws DataAccessException;

	/**
	 * 自动订单确认，只限问题模块使用
	 * 
	 * @param sn
	 * @param userId
	 * @param money
	 * @param memo
	 * @return
	 * @throws ApplicationException
	 */
	int autoPrepay(long sn, long userId, int money, String memo)
			throws ApplicationException;

	/**
	 * 
	 * 
	 * @param orderId
	 * @param seller
	 */
	void updateOrderStatus(long orderId, long seller, byte status);

	/**
	 * 订单提交前信息确认
	 * 
	 * @param id
	 * @param type
	 * @return
	 * @throws ApplicationException
	 */
	Map<String, Object> orderConfirm(long id, byte type, long userId)
			throws ApplicationException;

	/**
	 * 生成交易订单
	 * 
	 * @param orderId
	 *            订单编号
	 * @param orderType
	 *            订单类型
	 * @param payType
	 *            支付方式
	 * @param buyer
	 *            买家
	 * @param seller
	 *            卖家
	 * @param money
	 *            总价
	 * @param items
	 *            商品列表
	 * @param memo
	 *            备注
	 * @return
	 * @throws ApplicationException
	 */
	int order(long orderId, byte orderType, byte payType, long buyer,
			long seller, int money, long address, List<OrderItem> items,

			String memo) throws ApplicationException;

	/**
	 * 预付款
	 * 
	 * @param orderId
	 * @param money
	 * @param memo
	 * @return
	 * @throws ApplicationException
	 */
	int prepay(long orderId, int money, String memo)
			throws ApplicationException;

	/**
	 * 交易确认，付款
	 * 
	 * @param orderId
	 * @param money
	 * @param memo
	 * @return
	 * @throws ApplicationException
	 */
	int pay(long orderId, int money, String memo) throws ApplicationException;

	/**
	 * 取消交易
	 * 
	 * @param orderId
	 * @param memo
	 * @throws ApplicationException
	 */
	void cancle(long orderId, String memo) throws ApplicationException;

	/**
	 * 批量取消交易，删除交易下的所有商品，自动退费
	 * 
	 * @param orderIds
	 *            订单号
	 * @param memo
	 *            退费备注
	 * @throws ApplicationException
	 */
	void cancle(Collection<Long> orderIds, String memo)
			throws ApplicationException;

	/**
	 * 取消一批订单中的部分产品,仅限取消课程的时候使用，productId一定是唯一的
	 * 
	 * @param orderIds
	 * @param productId
	 * @param productType
	 * @param memo
	 * @throws ApplicationException
	 */
	void cancle(Collection<Long> orderIds, long productId, byte productType,
			String memo) throws ApplicationException;

	/**
	 * 获取交易的详细项目
	 * 
	 * @param orderId
	 * @return
	 */
	List<OrderItem> getOrderItems(long orderId);

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
	 * 快速订单，即直接生成订单付款并冻结账户资金
	 * 
	 * @param orderId
	 * @param typeQuestion
	 * @param viewer
	 * @param answer
	 * @param price
	 * @param items
	 * @param memo
	 * @throws ApplicationException
	 */
	void fastOrder(long orderId, byte typeQuestion, long viewer, long answer,
			int price, List<OrderItem> items, String memo)
			throws ApplicationException;

	/**
	 * 获取订单及订单中课程、问题等商品的状态
	 * 
	 * @param productId
	 * @param productType
	 * @param userId
	 * @return
	 */
	TradeStatus getTradeStatus(long productId, byte productType, long userId);

	int comment(long orderId, int score, int mark, String comment);

	Map<String, Integer> stat(long userId);

	long order(long productId, byte productType, int total, byte payType,
			long userId, long address, String memo) throws ApplicationException;

	/**
	 * 获取课程的报名用户
	 * 
	 * @param courseId
	 * @param status
	 * @return
	 */
	List<Long> getCourseMembers(long courseId, byte status);

}
