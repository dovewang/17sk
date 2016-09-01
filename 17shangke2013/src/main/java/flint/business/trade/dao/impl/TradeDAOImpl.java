package flint.business.trade.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiss.util.Q;

import org.springframework.stereotype.Repository;

import entity.Order;
import entity.OrderItem;
import flint.base.BaseDAOImpl;
import flint.business.constant.TradeConst;
import flint.business.trade.dao.TradeDAO;
import flint.business.trade.service.TradeStatus;
import flint.common.Page;
import flint.jdbc.RowMapper;
import flint.jdbc.RowSetHandler;
import flint.util.StringHelper;

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
@Repository
public class TradeDAOImpl extends BaseDAOImpl<Order, Long> implements TradeDAO {

	@Override
	public List<Order> getOrders(long productId, byte productType, byte status) {
		return this
				.find("SELECT o.* from TB_ORDER o,TB_ORDER_ITEM i where o.ORDER_ID=i.ORDER_ID  and i.PRODUCT_ID=? and i.PRODUCT_TYPE=? and o.`STATUS`=? ",
						productId, productType, status);
	}

	@Override
	public void prepay(long orderId) {
		update("update TB_ORDER set status=? ,done_time=?  where order_id=? ",
				TradeConst.ORDER_CONFIRM, Q.now(), orderId);
	}

	@Override
	public void pay(long orderId) {
		this.update(
				"update TB_ORDER set status=? ,pay_time=? where order_id=? ",
				TradeConst.PAYMENY, Q.now(), orderId);
	}

	@Override
	public void updateOrderStatus(long orderId, long buyer, byte status) {
		this.update(
				"update TB_ORDER set status=? ,done_time=?,buyer=?  where order_id=? ",
				status, Q.now(), buyer, orderId);
	}

	@Override
	public Order cancle(long orderId) {
		this.update(
				"update TB_ORDER set status=? ,confirm_time=? where order_id=? ",
				TradeConst.CLOSE, Q.now(), orderId);
		return findById(orderId);
	}

	@Override
	public Page<Order> list(long userId, byte type, long page, long size) {
		Page<Order> orders = null;
		if (type == 0) {
			orders = findPage(
					"select   * from TB_ORDER where (buyer=? or seller=?  ) order by order_time desc",
					page, size, userId, userId);
		} else {
			orders = findPage(
					"select   * from TB_ORDER where order_type=? and (buyer=? or seller=?)   order by order_time desc",
					page, size, type, userId, userId);
		}

		if (orders.getTotalCount() > 0) {
			List<Order> os = orders.getResult();
			String[] ids = new String[os.size()];
			int i = 0;
			for (Order o : os) {
				ids[i] = String.valueOf(o.getOrderId());
				i++;
			}
			/* 查询子项目，一尺查询出后，再内存中处理 */
			List<OrderItem> items = findByWhere(OrderItem.class,
					"  where order_id in(" + StringHelper.join(ids) + ") ");
			Map<Long, List<OrderItem>> map = new HashMap<Long, List<OrderItem>>();
			for (OrderItem item : items) {
				List<OrderItem> a = map.get(item.getOrderId());
				if (a == null) {
					a = new ArrayList<OrderItem>();
				}
				a.add(item);
				map.put(item.getOrderId(), a);
			}

			for (Order o : os) {
				o.setItems(map.get(o.getOrderId()));
			}
		}
		return orders;
	}

	@Override
	public TradeStatus getTradeStatus(long productId, byte productType,
			long buyer) {
		String sql = "select o.status as ostatus,i.status as pstatus from TB_ORDER o,TB_ORDER_ITEM i where i.ORDER_ID=o.ORDER_ID and i.PRODUCT_ID=? and i.PRODUCT_TYPE=? and o.BUYER=? ";
		return this.query(sql, new RowSetHandler<TradeStatus>() {
			@Override
			public TradeStatus populate(ResultSet rs) throws SQLException {
				TradeStatus status = new TradeStatus();
				if (rs.next()) {
					status.setOrderStatus(rs.getByte(1));
					status.setProductStatus(rs.getByte(2));
					return status;
				}
				status.setOrderStatus((byte) -1);
				status.setProductStatus((byte) -1);
				return status;
			}
		}, productId, productType, buyer);

	}

	@Override
	public List<Long> getCourseMembers(long productId, byte status) {
		String sql = "select o.BUYER  from TB_ORDER o,TB_ORDER_ITEM i where i.ORDER_ID=o.ORDER_ID and i.PRODUCT_ID=? and i.PRODUCT_TYPE in(?,?) and o.`STATUS`>0 and i.status=? ";
		return query(sql, new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong(1);
			}
		}, productId, TradeConst.TYPE_COURSE_LOCAL,
				TradeConst.TYPE_COURSE_ONLINE, status);
	}

	@Override
	public Map<String, Integer> stat(long userId) {
		return this
				.query("select STATUS from TB_ORDER  where  BUYER=? and  TB_ORDER.`STATUS`<? ",
						new RowSetHandler<Map<String, Integer>>() {

							@Override
							public Map<String, Integer> populate(ResultSet rs)
									throws SQLException {
								Map<String, Integer> map = new HashMap<String, Integer>();
								int pays = 0;
								int confirms = 0;
								int comments = 0;
								while (rs.next()) {
									byte s = rs.getByte(1);
									if (s == TradeConst.ORDER) {
										confirms++;
									} else if (s == TradeConst.ORDER_CONFIRM) {
										pays++;
									} else if (s == TradeConst.PAYMENY) {
										comments++;
									}
								}
								map.put("pays", pays);
								map.put("confirms", confirms);
								map.put("comments", comments);
								return map;
							}

						}, userId, TradeConst.CLOSE);
	}
}
