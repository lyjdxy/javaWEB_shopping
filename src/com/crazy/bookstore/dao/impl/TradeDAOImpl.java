package com.crazy.bookstore.dao.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import com.crazy.bookstore.dao.TradeDAO;
import com.crazy.bookstore.domain.Trade;

public class TradeDAOImpl extends BaseDAO<Trade> implements TradeDAO {

	@Override
	public void insert(Trade trade) {
		String sql = "INSERT INTO trade (userid, tradetime) VALUES " +
				"(?, ?)";
		long tradeId = insert(sql, trade.getUserId(), trade.getTradeTime());
		//插入之后获得的tradeId加到对应的对象中去
		trade.setTradeId((int)tradeId);
	}

	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
		String sql = "SELECT tradeId, userId, tradeTime FROM trade " +
				"WHERE userId = ? ORDER BY tradeTime DESC";
		return new LinkedHashSet(queryForList(sql, userId));
	}

}
