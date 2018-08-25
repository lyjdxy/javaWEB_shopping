package com.crazy.bookstore.web;

import java.sql.Connection;

public class ConnectionContext {
	
	private ConnectionContext(){}
	
	private static ConnectionContext instance = new ConnectionContext();
	
	public static ConnectionContext getInstance() {
		return instance;
	}//单例模式

	//将线程于连接绑定
	private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
			
	public void bind(Connection connection){
		connectionThreadLocal.set(connection);
	}
	
	public Connection get(){
		return connectionThreadLocal.get();
	}
	
	public void remove(){
		connectionThreadLocal.remove();
	}
	
}
