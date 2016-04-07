package com.kaishengit.util;

import java.sql.SQLException;

import com.kaishengit.exception.DataAccessException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

public class DBHelp {

	/**
	 * 用来执行insert update delete语句
	 */
	public static void update(String sql,Object... params) {
		QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
		try {
			queryRunner.update(sql,params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e,"SQL:" + sql);
		}

	}

	/**
	 * 用来执行查询(select)语句
	 */
	public static <T> T query(String sql,ResultSetHandler<T> handler,Object... params) {
		QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
		try {
			return queryRunner.query(sql,handler,params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e,"SQL:" + sql);
		}
	}
}
