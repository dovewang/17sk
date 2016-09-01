package flint.business.trade.control;

import static kiss.util.Helper.$;

import javax.servlet.http.HttpServletRequest;

import kiss.security.Identity;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import flint.business.constant.AccountConst;
import flint.business.constant.TradeConst;
import flint.business.trade.service.TradeService;
import flint.exception.ApplicationException;
import flint.util.NumberHelper;
import flint.web.WebControl;

@Controller
public class TradeControl extends WebControl {

	@Autowired
	private TradeService tradeService;

	/**
	 * 交易管理首页
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/trade/index.html", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView();
		mav.addObject("stat", tradeService.stat(this.getUserId(request)));
		return mav;
	}

	/**
	 * 订单列表页
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/trade/list.html", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView();
		byte type = NumberHelper.toByte(request.getParameter("t"));
		long page = NumberHelper.toLong(request.getParameter("p"));
		long userId = getUserId(request);
		mav.addObject(RESULT, tradeService.list(userId, type, page, 10));
		return mav;
	}

	/**
	 * 查看交易详细
	 * 
	 * @param request
	 * @param orderId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/trade/detail/order.html", method = RequestMethod.GET)
	public ModelAndView detail(HttpServletRequest request, long orderId)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView();
		mav.addObject("order", tradeService.getOrder(orderId));
		mav.addObject("orderItems", tradeService.getOrderItems(orderId));
		return mav;
	}

	@RequestMapping(value = "/trade/success.html", method = RequestMethod.GET)
	public ModelAndView success(HttpServletRequest request, long orderId)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView();
		return mav;
	}

	/**
	 * 交易评价页面
	 * 
	 * @param request
	 * @param orderId
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/trade/comment.html", method = RequestMethod.GET)
	public void comment(HttpServletRequest request, long orderId)
			throws ApplicationException {
                /*目前交易评价暂时没有统一起来*/
	}

	/**
	 * 评价分为4个等级： 1、有用 2、
	 * 
	 * @param request
	 * @param orderId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/trade/doComment.html", method = RequestMethod.POST)
	public ModelAndView doComment(HttpServletRequest request, long orderId)
			throws ApplicationException {
		int score = $(request.getParameter("score")).toInteger();
		int mark = $(request.getParameter("mark")).toInteger(1);// 评价默认为有用
		if (tradeService.comment(orderId, score, mark,
				$(request.getParameter("comment")).isEmpty("")) > 0) {
			return success("评价成功");
		}
		return failure("网络忙，请稍后再试");
	}

	/* ================订单相关处理=================== */

	/**
	 * 订单确认界面，根据不同的类型生成不同的订单确认界面
	 * @param request
	 * @param id
	 * @param type
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/order/confirm.html")
	public ModelAndView orderConfirm(HttpServletRequest request, long id,
			byte type) throws ApplicationException {
		ModelAndView mav = new ModelAndView();
		Identity user = this.getUser(request);
		user.setAttribute(TradeConst.TRADE_ONCE_KEY, Q.uuid());
		mav.addAllObjects(tradeService.orderConfirm(id, type,
				Long.parseLong(user.getId())));
		return mav;
	}

	/**
	 * 订单确认，根据产品的类型，支付的方式生成不通类型的订单，转向不同页面
	 * 
	 * <pre>
	 *   1、 线上支付需要转向预付款界面
	 *   2、线下支付直接生成订单，用户确认信息即可
	 * </pre>
	 * 
	 * @param request
	 * @param orderId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/trade/order.html", method = RequestMethod.POST)
	public ModelAndView order(HttpServletRequest request, long productId,
			byte productType, int total, byte payType)
			throws ApplicationException {
		String key = request.getParameter("key");
		Identity user = this.getUser(request);
		String k = user.getAttribute(TradeConst.TRADE_ONCE_KEY);
		long address = $(request.getParameter("address")).toLong();
		if (!k.equals(key)) {
			return failureView("请按正常流程提交订单");
		}
		String memo = request.getParameter("memo");
		/* 判断前台是否使用其他方式付款，例如积分等，使用其他支付方式目前暂不实现 */
		long orderId = tradeService.order(productId, productType, total,
				payType, Long.parseLong(user.getId()), address, memo);
		/* 在线支付模式 */
		if (total > 0 && (payType == TradeConst.PAY_ONLINE)) {
			return new ModelAndView(
					redirectView("/trade/prepay.confirm.html?orderId="
							+ orderId + "&key=" + key));
		} else {// 线下支付模式
			return new ModelAndView(redirectView("/order/success.html?orderId="
					+ orderId + "&key=" + key));
		}
	}

	/**
	 * 订单提交成功
	 * 
	 * @param request
	 * @param orderId
	 * @param key
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/order/success.html", method = RequestMethod.GET)
	public void orderSuccess(HttpServletRequest request, long orderId,
			String key) throws ApplicationException {
		// Subject user = this.getUser(request);
		// String k = user.getAttribute(TradeConst.TRADE_ONCE_KEY);
		// if (k.endsWith(key)) {
		// return new ModelAndView();
		// } else {
		// return this.failureView("您的订单存在安全风险，请查看订单状态或稍后重试");
		// }
	}

	/**
	 * 订单预付款确认界面:type=prepay，订单确认付款界面:type=pay
	 * 
	 * @param request
	 * @param orderId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/trade/{type}.confirm.html", method = RequestMethod.GET)
	public ModelAndView payConfirm(@PathVariable("type") String type,
			HttpServletRequest request, long orderId)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView();
		mav.addObject("order", tradeService.getOrder(orderId));
		mav.addObject("orderItems", tradeService.getOrderItems(orderId));
		return mav;
	}

	/**
	 * 交易预付款
	 * 
	 * @param request
	 * @param orderId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/trade/prepay.html")
	public ModelAndView prepay(HttpServletRequest request, long orderId, int fee)
			throws ApplicationException {
		/* 获取授权码 */
		Identity user = this.getUser(request);
		String key = request.getParameter("key");
		String vkey = user.removeAttribute(AccountConst.AUTHORIZE_TOKEN);
		if (vkey == null || !vkey.equals(key)) {
			return this.failureView("您的订单存在安全风险，请稍候再试");
		} else {
			tradeService.prepay(orderId, fee, "预付款");
		}
		return new ModelAndView(
				this.redirectView("/order/success.html?orderId=" + orderId
						+ "&key=" + key));
	}

	/**
	 * 确认付款
	 * 
	 * @param request
	 * @param orderId
	 * @param fee
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/trade/pay.html")
	public ModelAndView pay(HttpServletRequest request, long orderId, int fee)
			throws ApplicationException {
		/* 获取授权码 */
		Identity user = this.getUser(request);
		String key = request.getParameter("key");
		String vkey = user.removeAttribute(AccountConst.AUTHORIZE_TOKEN);
		if (vkey == null || !vkey.equals(key)) {
			return this.failureView("您的订单存在安全风险，请稍候再试");
		} else {
			tradeService.pay(orderId, fee, "付款确认");
		}
		return new ModelAndView(
				this.redirectView("/trade/success.html?orderId=" + orderId
						+ "&key=" + key));
	}

	/**
	 * 撤销订单
	 * 
	 * @param request
	 * @param orderId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/trade/cancle.html", method = RequestMethod.POST)
	public ModelAndView cancle(HttpServletRequest request, long orderId)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView();
		return mav;
	}

	/**
	 * 申述
	 * 
	 * @param request
	 * @param orderId
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/trade/appeal.html", method = RequestMethod.GET)
	public ModelAndView appeal(HttpServletRequest request, long orderId)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
}
