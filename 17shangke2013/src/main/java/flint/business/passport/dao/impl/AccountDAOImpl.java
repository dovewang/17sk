package flint.business.passport.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import entity.Account;
import entity.AccountLog;
import entity.Cashflow;
import flint.base.BaseDAOImpl;
import flint.business.constant.AccountConst;
import flint.business.passport.dao.AccountDAO;
import flint.common.Page;
import flint.exception.DataAccessException;
import flint.jdbc.RowSetHandler;
import flint.util.DateHelper;

/**
 * 
 * 功能描述：系统自动生成，请修改<br>
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
 * 账户常量:{@link #flint.business.constant.AccountConst}
 * 
 **/
@Repository
public class AccountDAOImpl extends BaseDAOImpl<Account, Long> implements
		AccountDAO {

	@Override
	public int check(long userId, int money) throws DataAccessException {
		Account account = getAccount(userId);
		if (account.getStatus() != AccountConst.STATUS_OK) {
			throw new DataAccessException("您的账户暂时不能支付，如有问题请咨询客服人员。");
		}
		if (account.getAvailable() >= money) {
			return account.getAvailable();
		} else {
			return -2;
		}
	}

	@Override
	public int frozen(long sequence, long userId, int money, String memo)
			throws DataAccessException {
		Account account = getAccount(userId);
		if (account.getAvailable() >= money) {
			int newfrozen = account.getFrozen() + money;
			int newbalance = account.getBalance();
			int newavailable = account.getAvailable() - money;
			cashflow(sequence, account, AccountConst.FRONE, newbalance,
					newfrozen, newavailable, 0, 0, memo);
			return newavailable;
		} else {
			/* 可用余额不足 */
			return -2;
		}
	}

	@Override
	public int unfrozen(long sequence, long userId, int money, String memo)
			throws DataAccessException {
		Account account = getAccount(userId);
		/* 判断冻结的资金 */
		if (account.getFrozen() >= money) {
			/* 记录账户变化 */
			int newfrozen = account.getFrozen() - money;
			int newbalance = account.getBalance();
			int newavailable = account.getAvailable() + money;
			cashflow(sequence, account, AccountConst.UNFRONE, newbalance,
					newfrozen, newavailable, 0, 0, memo);
			return newavailable;
		} else {
			/* 不可能发生的事:解冻的资金大于冻结的资金 */
			return -2;
		}
	}

	@Override
	public int draw(long userId, int money, String memo)
			throws DataAccessException {
		Account account = getAccount(userId);
		if (account.getAvailable() >= money) {
			int newfrozen = account.getFrozen();
			int newbalance = account.getBalance() - money;
			int newavailable = account.getAvailable() - money;
			/* 记录提现流水表 */
			long sequence = Long.parseLong(getDateSerialNumber("draw"));
			/* 记录账户变化,并更新 */
			cashflow(sequence, account, AccountConst.DRAW, newbalance,
					newfrozen, newavailable, 0, money, memo);
			return newavailable;
		} else {
			/* 可用余额不足 */
			return -2;
		}
	}

	@Override
	public int recharge(long userId, int money, byte type, String memo)
			throws DataAccessException {
		/* 记录账户变化,并更新账户 */
		Account account = getAccount(userId);
		int newfrozen = account.getFrozen();
		int newbalance = account.getBalance() + money;
		int newavailable = account.getAvailable() + money;
		/* 记录充值流水表 */
		long sequence = Long.parseLong(getDateSerialNumber("recharge"));
		update("insert into TB_DRAW(DRAW_ID,ACCOUNT,AMOUNT,MONEY,DATETIME,TYPE,MEMO) values(?,?,?,?,?,?,?)",
				sequence, userId, money * AccountConst.RATE, money,
				DateHelper.getNowTime(), type, memo);
		/* 记录账户变化,并更新 */
		cashflow(sequence, account, AccountConst.RECHARGE, newbalance,
				newfrozen, newavailable, money, 0, memo);

		return newavailable;
	}

	@Override
	public int pay(long sequence, long form, long to, int money, String memo)
			throws DataAccessException {
		Account account = getAccount(form);
		/* 判断冻结的资金是否满足条件 */
		if (account.getFrozen() >= money) {
			/* 支出账户 */
			/* 记录账户变化 */
			int newfrozen = account.getFrozen() - money;
			int newbalance = account.getBalance() - money;
			int newavailable = account.getAvailable();
			int result = newavailable;

			cashflow(sequence, account, AccountConst.PAYMENT, newbalance,
					newfrozen, newavailable, 0, money, memo);
			/* 收入账户， 发生交易时需要收取佣金，目前设置的佣金账户 ,阶梯佣金？？？ */
			// String c=configHelper.getString("commission", "", defaultValue);
			int commission = 0;
			int amount = money - commission;
			Account accountB = getAccount(to);
			newfrozen = accountB.getFrozen();
			newbalance = accountB.getBalance() + amount;
			newavailable = accountB.getAvailable() + amount;
			cashflow(sequence, accountB, AccountConst.INCOME, newbalance,
					newfrozen, newavailable, amount, 0, memo);

			/* 系统佣金账户，账户默认为0 */
			Account income = getAccount(0);
			newfrozen = income.getFrozen();
			newbalance = income.getBalance() + commission;
			newavailable = income.getAvailable() + commission;
			cashflow(sequence, income, AccountConst.INCOME, newbalance,
					newfrozen, newavailable, commission, 0, memo);
			return result;
		} else {
			/* 不可能发生的事:解冻的资金大于冻结的资金，交易存在异常 */
			throw new DataAccessException("账户异常，请稍后再试");
		}
	}

	@Override
	public int refund(long sequence, long userId, int money, String memo)
			throws DataAccessException {
		return 0;
	}

	@Override
	public Account getAccount(long userId) {
		return findFirst(Account.class,
				"select  *  from TB_ACCOUNT  where user_id=?", userId);
	}

	@Override
	public int authorize(final long userId, String password,
			final String netAddress) {
		int result = this
				.query("select  status  from TB_ACCOUNT  where user_id=? and password=?",
						new RowSetHandler<Integer>() {
							@Override
							public Integer populate(ResultSet rs)
									throws SQLException {
								int c = 0;
								String memo = "";
								if (rs.next()) {
									if (rs.getByte(1) != AccountConst.STATUS_OK) {
										c = -1;
										memo = "账户状态异常";
									}
									/* 检查账户是否被冻结 */
									// count("select count(1) from TB_ACCOUNT_LOG where check_time>? and status!=1 ",
									// DateHelper.getTime(new Date(), 0, 0, 0,
									// -1, 0, 0));
									c = 1;
									memo = "验证成功";
								} else {
									c = -2;
									memo = "用户密码错误";
								}
								AccountLog log = new AccountLog();
								log.setAccount(userId);
								log.setCheckType(AccountConst.CHECK_TYPE_PASSWORD);
								log.setCheckTime(DateHelper.getNowTime());
								log.setNetAddress(netAddress);
								log.setStatus((byte) c);
								log.setMemo(memo);
								save(log);
								/* 密码错误 */
								return c;
							}

						}, userId, password);

		return result;
	}

	@Override
	public void cashflow(long sequence, Account account, byte type,
			int newBalance, int newFrozen, int newAvailable, int income,
			int expend, String memo) throws DataAccessException {
		/* 记录账户变化 */
		Cashflow cashflow = new Cashflow();
		cashflow.setTradeId(sequence);
		cashflow.setCashflowId(Long.parseLong(getDateSerialNumber("cashflow")));
		cashflow.setOldAvailable(account.getAvailable());
		cashflow.setOldBalance(account.getBalance());
		cashflow.setOldFrozen(account.getFrozen());
		cashflow.setNewAvailable(newAvailable);
		cashflow.setNewBalance(newBalance);
		cashflow.setNewFrozen(newFrozen);
		cashflow.setTime(DateHelper.getNowTime());
		cashflow.setType(type);
		cashflow.setAccount(account.getUserId());
		cashflow.setIncome(income);
		cashflow.setExpend(expend);
		cashflow.setMemo(memo);
		save(cashflow);
		/* 更新账户 */
		update("update TB_ACCOUNT set balance=?,frozen=?,available=?  where user_id=? ",
				newBalance, newFrozen, newAvailable, account.getUserId());
	}

	@Override
	public Page<Cashflow> findAccountDetail(long userId, long page, int size) {
		return this.findPage(Cashflow.class,
				"select * from TB_CASHFLOW where account=? order by time desc",
				"select count(1) from TB_CASHFLOW where account=?", page, size,
				userId);
	}
}
