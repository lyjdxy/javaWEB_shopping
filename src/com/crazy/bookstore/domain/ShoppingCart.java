package com.crazy.bookstore.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

	private Map<Integer, ShoppingCartItem> books = new HashMap<Integer, ShoppingCartItem>();
	
	/**
	 * �޸�ָ����Ʒ������
	 * @param id
	 * @param quantity
	 */
	public void updateItemQuantity(Integer id, int quantity){
		ShoppingCartItem sci = books.get(id);
		if(sci != null){
			sci.setQuantity(quantity);
		}
	}
	
	public void removeItem(Integer id){
		//remove��ɾ����Ӧkey��valueֵ
		books.remove(id);
	}
	
	/***
	 * ��չ��ﳵ������Map�����е�clear�����������books
	 */
	public void clear(){
		books.clear();
	}
	
	/**
	 * ����Ƿ�Ϊ��
	 * @return
	 */
	public boolean isEmpty(){
		return books.isEmpty();
	}
	
	/**
	 * ��ȡ���ﳵ���ܽ��
	 * @return
	 */
	public float getTotalMoney(){
		float total = 0;
		
		for(ShoppingCartItem sci : getItems()){
			total += sci.getItemMoney();
		}
		
		return total;
	}
	
	/**
	 * ��ȡ���ﳵ������ShoppingCartItem����Ϣ
	 * @return
	 */
	public Collection<ShoppingCartItem> getItems(){
		return books.values();
	}
	
	/**
	 * ���ع��ﳵ����Ʒ��������
	 * @return
	 */
	public int getBookNumber() {
		int total = 0;
		
		for(ShoppingCartItem sci : books.values()){
			total += sci.getQuantity();
		}
		
		return total;
	}
	
	public Map<Integer, ShoppingCartItem> getBooks() {
		return books;
	}
	
	/**
	 * ���鹺�ﳵ���Ƿ���ָ��id����Ʒ
	 * @param id
	 * @return
	 */
	public boolean hasBook(Integer id){
		return books.containsKey(id);
	}
	
	/**
	 * ���ﳵ�����һ����Ʒ
	 */
	public void addBook(Book book){
		//1.��鹺�ﳵ����û�и���Ʒbook�����о�������һ����û���򴴽��µ�ShoppingCartItem�������뵽books��
		ShoppingCartItem sci = books.get(book.getId());
		
		if(sci == null){
			sci = new ShoppingCartItem(book);
			books.put(book.getId(), sci);
		}else{
			sci.increment();
		}
	}
}
