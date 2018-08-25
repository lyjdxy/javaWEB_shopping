package com.crazy.bookstore.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

	private Map<Integer, ShoppingCartItem> books = new HashMap<Integer, ShoppingCartItem>();
	
	/**
	 * 修改指定商品的数量
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
		//remove：删除对应key的value值
		books.remove(id);
	}
	
	/***
	 * 清空购物车，调用Map集合中的clear方法，清空了books
	 */
	public void clear(){
		books.clear();
	}
	
	/**
	 * 检测是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return books.isEmpty();
	}
	
	/**
	 * 获取购物车的总金额
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
	 * 获取购物车中所有ShoppingCartItem的信息
	 * @return
	 */
	public Collection<ShoppingCartItem> getItems(){
		return books.values();
	}
	
	/**
	 * 返回购物车中商品的总数量
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
	 * 检验购物车中是否有指定id的商品
	 * @param id
	 * @return
	 */
	public boolean hasBook(Integer id){
		return books.containsKey(id);
	}
	
	/**
	 * 向购物车中添加一件商品
	 */
	public void addBook(Book book){
		//1.检查购物车中有没有该商品book，若有就数量加一，若没有则创建新的ShoppingCartItem，并加入到books中
		ShoppingCartItem sci = books.get(book.getId());
		
		if(sci == null){
			sci = new ShoppingCartItem(book);
			books.put(book.getId(), sci);
		}else{
			sci.increment();
		}
	}
}
