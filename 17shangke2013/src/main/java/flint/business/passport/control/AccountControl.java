package flint.business.passport.control;

import static kiss.util.Helper.$;

import javax.servlet.http.HttpServletRequest;

import kiss.security.Identity;
import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import entity.Cashflow;
import flint.business.constant.AccountConst;
import flint.business.passport.service.AccountService;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.util.SequenceGenerator;
import flint.web.WebControl;

@Controller
public class AccountControl extends WebControl {

	@Autowired
	private AccountService accountService;

	@RequestMapping("/passport/account/index.html")
	public ModelAndView index(HttpServletRequest request)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView();
		long page = $(request.getParameter("p")).toLong(1);
		mav.addObject("account", accountService.getAccount(getUserId(request)));
		Page<Cashflow> detail = accountService.findAccountDetail(
				this.getUserId(request), page, 15);
		detail.setRequest(request);
		mav.addObject("detail", detail);
		return mav;
	}

	/**
	 * 账户充值
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/passport/account/recharge.html")
	public void recharge(HttpServletRequest request)
			throws ApplicationException {

	}

	/**
	 * 账户提现
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/passport/account/draw.html")
	public void draw(HttpServletRequest request) throws ApplicationException {

	}

	/**
	 * iframe支付界面，需要加密，验证安全性
	 * 
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/passport/account/authz.html")
	public void authz(HttpServletRequest request) throws ApplicationException {
		Identity user = this.getUser(request);
		/* 生成交易为宜的key值 */
		user.setAttribute(AccountConst.AUTHORIZE_ONCE_KEY, SequenceGenerator
				.getUUIDString().replaceAll("-", ""));
		/* 检查账户状态及余额 */
		user.setAttribute("balance",
				accountService.check($(user.getId()).toLong(), 0));
	}

	/**
	 * 授权成功后转向的页面，新的页面将产生新的key值以便验证
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/passport/account/authorize.html", method = RequestMethod.POST)
	public ModelAndView authorize(HttpServletRequest request)
			throws ApplicationException {
		Identity user = this.getUser(request);
		String key = user.removeAttribute(AccountConst.AUTHORIZE_ONCE_KEY);
		String password = request.getParameter(key);
		String netAddress = request.getRemoteAddr();
		/* 授权后转向执行的URL地址，例如付款调用等待 */
		String url = request.getParameter("url");
		long userId = Long.parseLong(user.getId());
		if (key != null
				&& accountService.authorize(userId, password, netAddress) > 0) {
			key = Q.uuid();
			user.setAttribute(AccountConst.AUTHORIZE_TOKEN, key);
			return success(url + "&key=" + key);
		} else {
			return failure("授权码已过期或密码不正确！");
		}
	}

	@RequestMapping("/passport/account/recovery.html")
	public void recovery(HttpServletRequest request)
			throws ApplicationException {

	}
}
