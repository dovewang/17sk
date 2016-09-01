package flint.business.trade.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.Course;
import entity.Order;
import entity.OrderItem;
import entity.UserAddress;
import flint.base.BaseServiceImpl;
import flint.business.constant.ClassroomConst;
import flint.business.constant.CourseConst;
import flint.business.constant.TradeConst;
import flint.business.course.service.CourseService;
import flint.business.passport.service.AccountService;
import flint.business.question.service.QuestionService;
import flint.business.school.service.SchoolService;
import flint.business.trade.dao.TradeDAO;
import flint.business.trade.service.TradeService;
import flint.business.trade.service.TradeStatus;
import flint.business.user.service.UserService;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.exception.DataAccessException;
import flint.util.DateHelper;

/**
 * 
 * 功能描述：系统自动生成，请修改
 * 
 * 日 期：2011-7-13 10:42:25
 * 
 * 作 者：Spirit
 * 
 * 版权所有：©mosai
 * 
 * 版 本 ：v0.01
 * 
 * 联系方式：<a href="mailto:dream-all@163.com">dream-all@163.com</a>
 * 
 * 修改备注：{修改人|修改时间}<br>
 * 
 * 
 **/
@Service
@Transactional
public class TradeServiceImpl extends BaseServiceImpl implements TradeService {
	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private TradeDAO dao;

	@Override
	public int autoPrepay(long sn, long userId, int price, String memo)
			throws ApplicationException {
		dao.prepay(sn);
		return accountService.frozen(sn, userId, price, memo);
	}

	@Override
	public void updateOrderStatus(long orderId, long buyer, byte status) {
		dao.updateOrderStatus(orderId, buyer, status);
	}

	@Override
	public Map<String, Object> orderConfirm(long id, byte type, long userId)
			throws ApplicationException {
		Map<String, Object> map = new HashMap<String, Object>();
		switch (type) {
		case TradeConst.TYPE_COURSE_ONLINE:

		case TradeConst.TYPE_COURSE_LOCAL:
			Course course = courseService.findById(id);
			/* 检查用户是否能够购买 */
			courseService.check(course, userId);
			map.put("product", toOrderItem(course));
			if (type == TradeConst.TYPE_COURSE_LOCAL)
				map.put("address", userService.findAddress(userId));
			break;
		case TradeConst.TYPE_ROOM_TUTOR:
		default:
			throw new ApplicationException("ORDER_ILLEGAL");
		}
		return map;
	}

	@Override
	public long order(long productId, byte productType, int total,
			byte payType, long userId, long address, String memo)
			throws ApplicationException {
		long sn = Long.parseLong(dao.getDateSerialNumber("course"));
		List<OrderItem> items = new ArrayList<OrderItem>();
		switch (productType) {
		case TradeConst.TYPE_COURSE_ONLINE:
		case TradeConst.TYPE_COURSE_LOCAL:
			Course course = courseService.findById(productId);
			OrderItem item = toOrderItem(course);
			item.setOrderId(sn);

			/* 报名用户信息 */
			UserAddress userAddress = null;
			if (productType == TradeConst.TYPE_COURSE_LOCAL && address != 0) {
				item.setBuyerAddressId(address);
				userAddress = userService.getAddress(address);
				item.setBuyerAddress(userService
						.getAddressShortText(userAddress));
			} else {
				item.setBuyerAddressId(userId);// 在线课程设置为用户ID
			}
			item.setStatus(ClassroomConst.JOIN_OK);// 设置报名状态
			items.add(item);
			order(sn, productType, payType, userId, course.getUserId(), total,
					address, items, memo);
			/* 线下支付或者免费课程直接认定为已报名 */
			if (payType == TradeConst.PAY_LOCAL || total == 0)
				courseService.join(course, userId, userAddress,
						course.getUserId());
			break;
		case TradeConst.TYPE_ROOM_TEMP:/* 临时教室 */
		case TradeConst.TYPE_ROOM_TUTOR:

		default:
			throw new ApplicationException("ORDER_ILLEGAL");
		}
		return sn;
	}

	/**
	 * 将课程转换为订单项
	 * 
	 * @param course
	 * @return
	 */
	public OrderItem toOrderItem(Course course) {
		byte type = TradeConst.TYPE_COURSE_ONLINE;
		if (course.getType() == CourseConst.COURSE_TYPE_LOACL) {
			type = TradeConst.TYPE_COURSE_LOCAL;
		}
		OrderItem item = new OrderItem();
		item.setQuantity(1);
		item.setDeliveries(1);
		item.setTotal(1);
		item.setProductType(type);
		item.setOldPrice(course.getPrice());
		item.setPrice(course.getPrice());
		item.setProductId(course.getCourseId());
		item.setProductName(course.getName());
		item.setProductCover(course.getCover());
		if (type == TradeConst.TYPE_COURSE_LOCAL) {
			item.setSellerAddressId(course.getAddressId());
			item.setSellerAddress(schoolService.getAddressFullText(course
					.getAddressId()));
		} else
			item.setMemo("上课时间：" + DateHelper.getNow(course.getStartTime())
					+ "，时长:" + course.getTime() / 60 + "分钟");
		/* 将快照信息使用JSON保存 */
		// item.setSnapshot(JSON.toJSONString(course));
		return item;
	}

	@Override
	@Transactional
	public int order(long orderId, byte orderType, byte payType, long buyer,
			long seller, int money, long address, List<OrderItem> items,
			String memo) throws ApplicationException {
		Order order = new Order();
		order.setOrderType(orderType);
		order.setBuyer(buyer);
		order.setSeller(seller);
		order.setOrderTime(Q.now());
		order.setStatus(TradeConst.ORDER);
		order.setPayType(payType);
		order.setOrderId(orderId);
		order.setPrice(money);
		/* 检查优惠策略 */

		/* 检查单项资金 */
		int price = 0;
		for (OrderItem item : items) {
			price += item.getPrice();
		}
		/* 商品资金验证异常 */
		if (price != money) {
			throw new ApplicationException("ORDER_MONEY_PAIR");
		}

		/* 价格为0不需要预付款，直接定义为交易成功 */
		if (price == 0) {
			order.setStatus(TradeConst.PAYMENY);
		}

		/* 保存订单信息 */
		dao.save(items);
		return dao.save(order);
	}

	@Override
	@Transactional
	public void cancle(long orderId, String memo) throws ApplicationException {
		Order order = dao.cancle(orderId);
		/* 取消解冻资金 */
		accountService.unfrozen(orderId, order.getBuyer(), order.getPayTime(),
				memo);
	}

	@Override
	@Transactional
	public void cancle(Collection<Long> orderIds, String memo)
			throws ApplicationException {
		for (long orderId : orderIds) {
			cancle(orderId, memo);
		}
	}

	@Override
	public void cancle(Collection<Long> orderIds, long productId,
			byte productType, String memo) throws ApplicationException {
		for (long orderId : orderIds) {
			cancle(orderId, memo);
		}
	}

	@Override
	public Order getOrder(long orderId) {
		return dao.findById(orderId);
	}

	@Override
	public List<Order> getOrders(long productId, byte productType, byte status) {
		return dao.getOrders(productId, productType, status);
	}

	@Override
	@Transactional
	public int pay(long orderId, int money, String memo)
			throws ApplicationException {
		/* 不需要付款的 */
		if (money == 0) {
			return 1;
		}
		Order order = getOrder(orderId);
		if (order.getStatus() >= TradeConst.PAYMENY) {
			throw new ApplicationException("ORDER_PAYED");
		}
		dao.pay(orderId);
		return accountService.pay(orderId, order.getBuyer(), order.getSeller(),
				money, memo);
	}

	@Override
	public int prepay(long orderId, int money, String memo)
			throws ApplicationException {
		/* 不需要付款的 */
		if (money == 0) {
			return 1;
		}
		Order order = getOrder(orderId);
		if (order.getStatus() >= TradeConst.ORDER_CONFIRM) {
			throw new ApplicationException("ORDER_PREPAYED");
		}

		/* 由于问题模块查看答案是自动预付款，这里只判断是否是课程，预付后才代表报名成功 */
		if (order.getOrderType() == TradeConst.TYPE_COURSE_ONLINE
				|| order.getOrderType() == TradeConst.TYPE_COURSE_LOCAL) {
			OrderItem item = this.getOrderItems(orderId).get(0);
			Course course = courseService.findById(item.getProductId());
			/* 下线课程用户报名信息，可能不是本人，主要是为了想报名用户发送邮件等信息 */
			UserAddress userAddress = null;
			if (order.getOrderType() == TradeConst.TYPE_COURSE_LOCAL) {
				userAddress = userService.getAddress(item.getBuyerAddressId());
			}
			courseService.join(course, order.getBuyer(), userAddress,
					course.getUserId());
		}

		dao.prepay(orderId);
		return accountService.frozen(orderId, order.getBuyer(),
				order.getPrice(), memo);
	}

	@Override
	public List<OrderItem> getOrderItems(long orderId) {
		return dao
				.findByWhere(OrderItem.class, "   where order_id=? ", orderId);
	}

	@Override
	public Page<Order> list(long userId, byte type, long page, long size) {
		return dao.list(userId, type, page, size);
	}

	@Override
	public int check(long userId, int money) throws DataAccessException {
		return accountService.check(userId, money);
	}

	@Override
	@Transactional
	public void fastOrder(long orderId, byte type, long viewer, long answer,
			int price, List<OrderItem> items, String memo)
			throws ApplicationException {
		/* 生成订单 */
		this.order(orderId, type, TradeConst.PAY_ONLINE, viewer, answer, price,
				0, items, memo);
		this.prepay(orderId, price, memo);
	}

	@Override
	public int comment(long orderId, int score, int mark, String comment) {
		Order order = this.getOrder(orderId);
		userService.addCredit(order.getSeller(), mark);
		userService.addScore(order.getSeller(), score);
		List<OrderItem> items = order.getItems();
		OrderItem item = items.get(0);
		if (order.getOrderType() == TradeConst.TYPE_QUESTION) {
			return questionService.comment(item.getProductId(),
					order.getBuyer(), score, mark, comment);
		} else if (order.getOrderType() == TradeConst.TYPE_COURSE_ONLINE
				|| order.getOrderType() == TradeConst.TYPE_COURSE_LOCAL) {
			return courseService.comment(item.getProductId(),
					order.getSeller(), score, mark, comment);
		}
		return 1;
	}

	@Override
	public Map<String, Integer> stat(long userId) {
		return dao.stat(userId);
	}

	@Override
	public TradeStatus getTradeStatus(long productId, byte productType,
			long buyer) {
		return dao.getTradeStatus(productId, productType, buyer);
	}

	@Override
	public List<Long> getCourseMembers(long courseId, byte status) {
		return dao.getCourseMembers(courseId, status);
	}

}