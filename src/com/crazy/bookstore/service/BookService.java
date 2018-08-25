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

	//�ڽ��˲���ʱ����Ҫͬʱ�Ķ������ݺܶ࣬���׷����߳�����
	//��ʹ������ThreadLocal��Filter��ϵķ�����ʹ������ݿ������һ��connection�������ͬһ�������н��У������쳣ʱ����ع���д�����ݿ����д��
	//ҵ�񷽷�. 
	public void cash(ShoppingCart shoppingCart, String username,
			String accountId) {
		
		//1. ���� mybooks ���ݱ���ؼ�¼�� salesamount �� storenumber
		bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		
		//int i = 10 / 0;//��������ʱ�����Ի᲻��ʹ����д�뷢���쳣
		
		//2. ���� account ���ݱ�� balance
		accountDAO.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
		
		//3. �� trade ���ݱ����һ����¼���������ֶ�tradeid(������)��userid��tradetime
		Trade trade = new Trade();
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(userDAO.getUser(username).getUserId());
		tradeDAO.insert(trade);
		
		//4. �� tradeitem ���ݱ���� n ����¼
		Collection<TradeItem> items = new ArrayList<>();
		for(ShoppingCartItem sci: shoppingCart.getItems()){
			TradeItem tradeItem = new TradeItem();
			
			tradeItem.setBookId(sci.getBook().getId());
			tradeItem.setQuantity(sci.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());
			
			items.add(tradeItem);
		}
		tradeItemDAO.batchSave(items);
		
		//5. ��չ��ﳵ
		shoppingCart.clear();
	}
}
