package com.crazy.bookstore.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.crazy.bookstore.dao.AccountDAO;
import com.crazy.bookstore.dao.impl.AccountDAOIml;
import com.crazy.bookstore.domain.Account;

public class AccountDAOTest {

	AccountDAO accountDAO = new AccountDAOIml();
	
	@Test
	public void testGet() {
		Account account = accountDAO.get(666);
		System.out.println(account.getBalance()); 
	}

	@Test
	public void testUpdateBalance() {
		accountDAO.updateBalance(666, 50);
	}

}
