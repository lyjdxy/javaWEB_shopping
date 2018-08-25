package com.crazy.bookstore.service;

import com.crazy.bookstore.dao.AccountDAO;
import com.crazy.bookstore.dao.impl.AccountDAOIml;
import com.crazy.bookstore.domain.Account;

public class AccountService {
	
	private AccountDAO accountDAO = new AccountDAOIml();
	
	public Account getAccount(int accountId){
		return accountDAO.get(accountId);
	}
	
}
