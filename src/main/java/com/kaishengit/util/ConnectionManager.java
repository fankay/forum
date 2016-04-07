package com.kaishengit.util;

import com.kaishengit.exception.DataAccessException;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    private static BasicDataSource dataSource = new BasicDataSource();

	static {


        dataSource.setDriverClassName(ConfigProp.get("jdbc.driver"));
        dataSource.setUrl(ConfigProp.get("jdbc.url"));
        dataSource.setUsername(ConfigProp.get("jdbc.username"));
        dataSource.setPassword(ConfigProp.get("jdbc.password"));

        dataSource.setInitialSize(5);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(5);
        dataSource.setMaxWaitMillis(5000);

	}

    public static DataSource getDataSource() {
        return dataSource;
    }
	
	public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e,"获取数据库链接异常");
        }
    }
	

}
