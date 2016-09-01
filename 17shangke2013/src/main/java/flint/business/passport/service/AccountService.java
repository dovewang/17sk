package flint.business.passport.service;

import entity.Account;
import entity.Cashflow;
import flint.base.BaseService;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.exception.DataAccessException;

/**
 * 
 * 功能描述：系统自动生成，请修改<br>
 * 
 * 日 期：2011-5-5 12:43:54<br>
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
public interface AccountService extends BaseService {

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
	 * 创建用户账户
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	int create(long userId, String password);

	/**
	 * 冻结资金
	 * 
	 * @param sequence
	 * @param userId
	 * @param money
	 * @param memo
	 * @return
	 * @throws ApplicationException
	 */
	int frozen(long sequence, long userId, int money, String memo)
			throws ApplicationException;

	/**
	 * 解冻资金
	 * 
	 * @param sequence
	 * @param userId
	 * @param money
	 * @param memo
	 * @return
	 * @throws ApplicationException
	 */
	int unfrozen(long sequence, long userId, int money, String memo)
			throws ApplicationException;

	/**
	 * 取现
	 * 
	 * @param userId
	 * @param money
	 * @param memo
	 * @return
	 * @throws ApplicationException
	 */
	int draw(long userId, int money, String memo) throws ApplicationException;

	/**
	 * 充值
	 * 
	 * @param userId
	 * @param money
	 * @param type
	 * @param memo
	 * @return
	 * @throws ApplicationException
	 */
	int recharge(long userId, int money, byte type, String memo)
			throws ApplicationException;

	/**
	 * 付款
	 * 
	 * @param sequence
	 *            交易流水
	 * @param form
	 *            支出账户
	 * @param to
	 *            收入账户
	 * @param money
	 *            金额
	 * @param memo
	 *            备注
	 * @return
	 * @throws ApplicationException
	 */
	int pay(long sequence, long form, long to, int money, String memo)
			throws ApplicationException;

	/**
	 * 账户授权
	 * 
	 * @param userId
	 * @param password
	 * @param netAddress
	 * @return
	 */
	int authorize(long userId, String password, String netAddress);

	/**
	 * 获取账户信息
	 * 
	 * @param userId
	 * @return
	 */
	Account getAccount(long userId);

	void give(long account, int coins, String memo);

	Page<Cashflow> findAccountDetail(long userId, long page, int size);

}
