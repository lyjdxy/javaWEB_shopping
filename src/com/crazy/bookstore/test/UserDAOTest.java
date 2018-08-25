package com.crazy.bookstore.test;

import org.junit.Test;

import com.crazy.bookstore.dao.UserDAO;
import com.crazy.bookstore.dao.impl.UserDAOImpl;
import com.crazy.bookstore.domain.User;

public class UserDAOTest {

	private UserDAO userDAO = new UserDAOImpl();
	
	@Test
	public void testGetUser() {
		User user = userDAO.getUser("AAA");
		System.out.println(user); 
	}

}
