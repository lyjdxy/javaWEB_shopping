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
	 * ����ĳ����Ʒ�ڹ��ﳵ�е���Ǯ��
	 * @return
	 */
	public float getItemMoney() {
		return book.getPrice() * quantity;
	}

	public void increment() {
		this.quantity++;
	}
	
	
	
	
}
