package com.crazy.bookstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.crazy.bookstore.domain.ShoppingCart;

public class BookStoreWebUtils {

	/**
	 * 获取购物车对象，从session中获取，若没有，则创建一个购物车对象放到session中
	 * @param request
	 * @return
	 */
	public static ShoppingCart getShoppingCart(HttpServletRequest request){
		HttpSession session = request.getSession();
		
		ShoppingCart sc = (ShoppingCart) session.getAttribute("ShoppingCart");
		if(sc == null){
			sc = new ShoppingCart();
			session.setAttribute("ShoppingCart", sc);
		}
		return sc;
	}
	
}
