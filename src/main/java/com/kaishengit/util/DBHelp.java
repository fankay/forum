package com.kaishengit.util;

import java.sql.SQLException;

import com.kaishengit.exception.DataAccessException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBHelp {

	private static Logger logger = LoggerFactory.getLogger(DBHelp.class);


    /**
     * 执行insert语句，并返回自动增长主键的值
     * @param sql
     * @param params
     * @return
     */
	public static Long insert(String sql,Object... params) {
        QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
        try {
            Long result = queryRunner.insert(sql,new ScalarHandler<Long>(),params);
			logger.debug("SQL:{}",sql);
			return result;
        } catch (SQLException e) {
            e.printStackTrace();
			logger.error("执行{}异常",sql);
            throw new DataAccessException(e,"执行" + sql + "出现异常");
        }
    }


	/**
	 * 用来执行insert update delete语句
	 */
	public static void update(String sql,Object... params) {
		QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
		try {
			queryRunner.update(sql,params);
			logger.debug("SQL:{}",sql);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("执行{}异常",sql);
			throw new DataAccessException(e,"SQL:" + sql);
		}

	}

	/**
	 * 用来执行查询(select)语句
	 */
	public static <T> T query(String sql,ResultSetHandler<T> handler,Object... params) {
		QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
		try {
			T t = queryRunner.query(sql,handler,params);
			logger.debug("SQL:{}",sql);
			return t;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("执行{}异常",sql);
			throw new DataAccessException(e,"SQL:" + sql);
		}
	}
}
