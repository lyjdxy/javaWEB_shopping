package com.crazy.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.crazy.bookstore.web.ConnectionContext;
import com.crazy.bookstore.dao.Dao;
import com.crazy.bookstore.db.JDBCUtils;
import com.crazy.bookstore.utils.ReflectionUtils;

public class BaseDAO<T> implements Dao<T> {

	private QueryRunner queryRunner = new QueryRunner();
	
	private Class<T> clazz;
	
	public BaseDAO() {
		clazz = ReflectionUtils.getSuperGenericType(getClass());//获得父类的泛型参数类型，即Dao<T>中的T类型
	}

	@Override
	public long insert(String sql, Object... args) {
		long id = -1;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			//connection = JDBCUtils.getConnection();
			
			//使用事务和ThreadLocal之后，这些操作都是在同一个connection中，所以DAO的测试类也需要创建connection对象才能使用
			connection = ConnectionContext.getInstance().get();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					preparedStatement.setObject(i + 1, args[i]);
				}
			}

			preparedStatement.executeUpdate();

			//获取生成的主键值
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(resultSet, preparedStatement);
			
			//也不需要释放connection连接了
			//JDBCUtils.release(connection);
			//一下所有操作都是
		}

		return id;
	}

	@Override
	public void update(String sql, Object... args) {
		Connection connection = null;

		try {
			//connection = JDBCUtils.getConnection();
			connection = ConnectionContext.getInstance().get();
			queryRunner.update(connection, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//JDBCUtils.release(connection);
		}

	}

	@Override
	public T query(String sql, Object... args) {
		Connection connection = null;

		try {
			//connection = JDBCUtils.getConnection();
			connection = ConnectionContext.getInstance().get();
			return queryRunner.query(connection, sql, new BeanHandler<T>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//JDBCUtils.release(connection);
		}

		return null;
	}

	@Override
	public List<T> queryForList(String sql, Object... args) {
		Connection connection = null;

		try {
			//connection = JDBCUtils.getConnection();
			connection = ConnectionContext.getInstance().get();
			return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//JDBCUtils.release(connection);
		}

		return null;
	}

	@Override
	public <V> V getSingleVal(String sql, Object... args) {
		Connection connection = null;

		try {
			//connection = JDBCUtils.getConnection();
			connection = ConnectionContext.getInstance().get();
			return (V)queryRunner.query(connection, sql, new ScalarHandler(), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//JDBCUtils.release(connection);
		}

		return null;
	}

	@Override
	public void batch(String sql, Object[]... params) {
		Connection connection = null;

		try {
			//connection = JDBCUtils.getConnection();
			connection = ConnectionContext.getInstance().get();
			queryRunner.batch(connection, sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//JDBCUtils.release(connection);
		}


	}

}
