package flint.business.passport.service.impl;

import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Account;
import entity.Cashflow;
import flint.base.BaseServiceImpl;
import flint.business.constant.AccountConst;
import flint.business.passport.dao.AccountDAO;
import flint.business.passport.service.AccountService;
import flint.common.Page;
import flint.exception.ApplicationException;
import flint.exception.DataAccessException;

/**
 * 
 * 功能描述：系统自动生成，请修改
 * 
 * 日 期：2011-5-5 12:43:54
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
public class AccountServiceImpl extends BaseServiceImpl implements
		AccountService {

	@Autowired
	private AccountDAO dao;

	public AccountDAO getDao() {
		return dao;
	}

	public void setDao(AccountDAO dao) {
		this.dao = dao;
	}

	@Override
	public int create(long userId, String password) {
		Account account = new Account();
		account.setUserId(userId);
		account.setPassword(password);
		account.setStatus(AccountConst.STATUS_OK);
		account.setCreateTime(Q.now());
		int result = dao.save(account);
		/* 初始账户，赠送点数,等于或小于0，则不赠送 */
		return result;
	}

	@Override
	public void give(long userId, int coins, String memo) {
		Account account = this.getAccount(userId);
		if (coins > 0) {
			dao.cashflow(Long.parseLong(dao.getDateSerialNumber("gift")),
					account, AccountConst.GIFT, coins, 0, coins, coins, 0, memo);
		}
	}

	@Override
	public int frozen(long sequence, long userId, int money, String memo)
			throws ApplicationException {
		return dao.frozen(sequence, userId, money, memo);
	}

	@Override
	public int unfrozen(long sequence, long userId, int money, String memo)
			throws ApplicationException {
		return dao.unfrozen(sequence, userId, money, memo);
	}

	@Override
	public int draw(long userId, int money, String memo)
			throws ApplicationException {
		return dao.draw(userId, money, memo);
	}

	@Override
	public int recharge(long userId, int money, byte type, String memo)
			throws ApplicationException {
		return dao.recharge(userId, money, type, memo);
	}

	@Override
	public int pay(long sequence, long from, long to, int money, String memo)
			throws ApplicationException {
		return dao.pay(sequence, from, to, money, memo);
	}

	@Override
	public int authorize(long userId, String password, String netAddress) {
		return dao.authorize(userId, password, netAddress);
	}

	@Override
	public int check(long userId, int money) throws DataAccessException {
		return dao.check(userId, money);
	}

	@Override
	public Account getAccount(long userId) {
		Account account = dao.getAccount(userId);
		account.setPassword(null);
		return account;
	}

	@Override
	public Page<Cashflow> findAccountDetail(long userId, long page, int size) {
		return dao.findAccountDetail(userId,page,size);
	}

}