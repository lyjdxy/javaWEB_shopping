package com.crazy.bookstore.domain;

public class ShoppingCartItem {

	private Book book;
	private int quantity;
	
	public ShoppingCartItem(Book book) {
		this.book = book;
		this.quantity = 1;
	}
	
	public Book getBook() {
		return book;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * 返回某个商品在购物车中的总钱数
	 * @return
	 */
	public float getItemMoney() {
		return book.getPrice() * quantity;
	}

	public void increment() {
		this.quantity++;
	}
	
	
	
	
}
