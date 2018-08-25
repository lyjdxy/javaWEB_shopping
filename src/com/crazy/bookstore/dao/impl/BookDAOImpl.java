package com.crazy.bookstore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.crazy.bookstore.dao.BookDAO;
import com.crazy.bookstore.domain.Book;
import com.crazy.bookstore.domain.ShoppingCartItem;
import com.crazy.bookstore.web.CriteriaBook;
import com.crazy.bookstore.web.Page;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO{

	@Override
	public Book getBook(int id) {
		String sql = "select id, author, title, price, publishingDate, salesAmount, "
				+ "storeNumber, remark from mybooks where id = ?";
		return query(sql, id);
	}

	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		Page page = new Page(cb.getPageNo());
		
		page.setTotalItemNumber(getTotalBookNumber(cb));
		
		//У��pageNo�ĺϷ���,��Ϊ��CriteriaBook���е�pageNo����û�к�Page����һ������У��
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb, 3));
		return page;
	}

	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		String sql = "select count(id) from mybooks where price >= ? and price <= ?";
		return getSingleVal(sql, cb.getMinPrice(), cb.getMaxPrice());
	}

	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		String sql = "select id, author, title, price, publishingDate, salesAmount, "
				+ "storeNumber, remark from mybooks where price >= ? and price <= ? limit ?,?";
		return queryForList(sql, cb.getMinPrice(), cb.getMaxPrice(), (cb.getPageNo() - 1) * pageSize, pageSize);
	}

	@Override
	public int getStoreNumber(Integer id) {
		String sql = "select storeNumber from mybooks where id = ?";
		return getSingleVal(sql, id);
	}

	//����֮���޸Ķ�Ӧ���������ʹ洢��
	@Override
	public void batchUpdateStoreNumberAndSalesAmount(
			Collection<ShoppingCartItem> items) {
		String sql = "UPDATE mybooks SET salesAmount = salesAmount + ?, " +
				"storeNumber = storeNumber - ? " +
				"WHERE id = ?";
		Object [][] params = null;
		params = new Object[items.size()][3];
		//Collectionû��get��������ҪתΪList
		List<ShoppingCartItem> scis = new ArrayList<>(items);
		for(int i = 0; i < items.size(); i++){
			params[i][0] = scis.get(i).getQuantity();
			params[i][1] = scis.get(i).getQuantity();
			params[i][2] = scis.get(i).getBook().getId();
		}
		batch(sql, params);
	}
}
