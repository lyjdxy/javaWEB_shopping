package com.crazy.bookstore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import com.crazy.bookstore.dao.AccountDAO;
import com.crazy.bookstore.dao.TradeDAO;
import com.crazy.bookstore.dao.TradeItemDAO;
import com.crazy.bookstore.dao.UserDAO;
import com.crazy.bookstore.dao.impl.AccountDAOIml;
import com.crazy.bookstore.dao.impl.TradeDAOImpl;
import com.crazy.bookstore.dao.impl.TradeItemDAOImpl;
import com.crazy.bookstore.dao.impl.UserDAOImpl;
import com.crazy.bookstore.domain.ShoppingCartItem;
import com.crazy.bookstore.domain.Trade;
import com.crazy.bookstore.domain.TradeItem;
import com.crazy.bookstore.dao.BookDAO;
import com.crazy.bookstore.dao.impl.BookDAOImpl;
import com.crazy.bookstore.domain.Book;
import com.crazy.bookstore.domain.ShoppingCart;
import com.crazy.bookstore.web.CriteriaBook;
import com.crazy.bookstore.web.Page;

public class BookService {

	private BookDAO bookDAO = new BookDAOImpl();
	
	public Page<Book> getPage(CriteriaBook cb){
		return bookDAO.getPage(cb);
	}

	public Book getBook(int id) {
		return bookDAO.getBook(id);
	}

	public boolean addToCart(int id, ShoppingCart sc) {
		Book book = bookDAO.getBook(id);
		
		if(book != null){
			sc.addBook(book);
			return true;
		}
		return false;
	}

	public void removeItemFromShoppingCart(ShoppingCart sc, int id) {
		sc.removeItem(id);
	}

	public void clearShoppingCart(ShoppingCart sc) {
		sc.clear();
	}

	public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {
		sc.updateItemQuantity(id, quantity);
	}
	
	private AccountDAO accountDAO = new AccountDAOIml();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();

	//在结账操作时，需要同时改动的数据很多，容易发生线程问题
	//故使用事务、ThreadLocal和Filter结合的方法，使多个数据库操作绑定一个connection对象和在同一个事务中进行，发生异常时事务回滚不写入数据库否则写入
	//业务方法. 
	public void cash(ShoppingCart shoppingCart, String username,
			String accountId) {
		
		//1. 更新 mybooks 数据表相关记录的 salesamount 和 storenumber
		bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		
		//int i = 10 / 0;//发生错误时，测试会不会使数据写入发生异常
		
		//2. 更新 account 数据表的 balance
		accountDAO.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
		
		//3. 向 trade 数据表插入一条记录，有三个字段tradeid(自增长)，userid，tradetime
		Trade trade = new Trade();
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(userDAO.getUser(username).getUserId());
		tradeDAO.insert(trade);
		
		//4. 向 tradeitem 数据表插入 n 条记录
		Collection<TradeItem> items = new ArrayList<>();
		for(ShoppingCartItem sci: shoppingCart.getItems()){
			TradeItem tradeItem = new TradeItem();
			
			tradeItem.setBookId(sci.getBook().getId());
			tradeItem.setQuantity(sci.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());
			
			items.add(tradeItem);
		}
		tradeItemDAO.batchSave(items);
		
		//5. 清空购物车
		shoppingCart.clear();
	}
}
