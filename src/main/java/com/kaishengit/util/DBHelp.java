package com.kaishengit.util;

import java.sql.SQLException;

import com.kaishengit.exception.DataAccessException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class DBHelp {


    /**
     * 执行insert语句，并返回自动增长主键的值
     * @param sql
     * @param params
     * @return
     */
	public static Long insert(String sql,Object... params) {
        QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
        try {
            return queryRunner.insert(sql,new ScalarHandler<Long>(),params);
        } catch (SQLException e) {
            e.printStackTrace();
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
