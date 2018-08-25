package com.crazy.bookstore.test;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.crazy.bookstore.domain.ShoppingCartItem;
import com.crazy.bookstore.dao.BookDAO;
import com.crazy.bookstore.dao.impl.BookDAOImpl;
import com.crazy.bookstore.domain.Book;
import com.crazy.bookstore.web.CriteriaBook;
import com.crazy.bookstore.web.Page;

public class BookDAOTest {
	
	private BookDAO bookDAO = new BookDAOImpl();

	@Test
	public void testGetBook() {
		Book book = bookDAO.getBook(5);
		System.out.println(book);
	}

	@Test
	public void testGetPage() {
		CriteriaBook cb = new CriteriaBook(50, 60, 50);
		Page<Book> page = bookDAO.getPage(cb);
		
		System.out.println("当前页 ： "+page.getPageNo());
		System.out.println("总页数 ： "+page.getTotalPageNumber());
		System.out.println("list ： "+page.getList());
		System.out.println("前一页 ： "+page.getPrevPage());
	}

	@Test
	public void testGetStoreNumber() {
		int storeNumber = bookDAO.getStoreNumber(33);
		System.out.println(storeNumber);
	}
	
	@Test
	public void testBatchUpdateStoreNumberAndSalesAmount(){
		Collection<ShoppingCartItem> items = new ArrayList<>();
		
		Book book = bookDAO.getBook(1);
		ShoppingCartItem sci = new ShoppingCartItem(book);
		sci.setQuantity(10);
		items.add(sci);
		
		book = bookDAO.getBook(2);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(11);
		items.add(sci);
		
		book = bookDAO.getBook(3);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(12);
		items.add(sci);
		
		book = bookDAO.getBook(4);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(14);
		items.add(sci);
		
		bookDAO.batchUpdateStoreNumberAndSalesAmount(items);
	}

}
