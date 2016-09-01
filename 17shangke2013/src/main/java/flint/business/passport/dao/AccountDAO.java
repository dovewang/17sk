package flint.business.passport.dao;

import entity.Account;
import entity.Cashflow;
import flint.base.BaseDAO;
import flint.common.Page;
import flint.exception.DataAccessException;

/**
 * 
 * 功能描述：系统账号和现金兑换的比例为10:1<br>
 * 
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
public interface AccountDAO extends BaseDAO<Account, Long> {

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
	 * 账户授权
	 * 
	 * @param userId
	 * @param password
	 * @param netAddress
	 * @return
	 */
	int authorize(long userId, String password, String netAddress);

	/**
	 * 获得用户账户信息
	 * 
	 * @param userId
	 * @return
	 */
	Account getAccount(long userId);

	/**
	 * 冻结账户资金，一般指预付款
	 * 
	 * @param sequence
	 * @param userId
	 * @param money
	 * @return 可用余额
	 * @throws DataAccessException
	 */
	int frozen(long sequence, long userId, int money, String memo) throws DataAccessException;

	/**
	 * 解冻账户资金，一般值取消交易
	 * 
	 * @param sequence
	 * @param userId
	 * @param money
	 * @return 可用余额
	 * @throws DataAccessException
	 */
	int unfrozen(long sequence, long userId, int money, String memo) throws DataAccessException;

	/**
	 * 提现
	 * 
	 * @param userId
	 * @param money
	 * @return 可用余额
	 */
	int draw(long userId, int money, String memo) throws DataAccessException;

	/**
	 * 充值
	 * 
	 * @param userId
	 * @param money
	 * @param type
	 *            充值方式
	 * @param memo
	 * @return 可用余额
	 * @throws DataAccessException
	 */
	int recharge(long userId, int money, byte type, String memo) throws DataAccessException;

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
	 * @return 可用余额
	 */
	int pay(long sequence, long form, long to, int money, String memo) throws DataAccessException;

	/**
	 * 退费
	 * 
	 * @param sequence
	 * @param userId
	 * @param money
	 * @param memo
	 * @return 可用余额
	 * @throws DataAccessException
	 */
	int refund(long sequence, long userId, int money, String memo) throws DataAccessException;

	/**
	 * 账户资金流动记录表
	 * 
	 * @param sequence
	 * @param account
	 * @param type
	 * @param newBalance
	 * @param newFrozen
	 * @param newAvailable
	 * @param income
	 * @param expend
	 * @param memo
	 * @throws DataAccessException
	 */
	void cashflow(long sequence, Account account, byte type, int newBalance, int newFrozen, int newAvailable, int income, int expend, String memo) throws DataAccessException;

	Page<Cashflow> findAccountDetail(long userId, long page, int size);

}
