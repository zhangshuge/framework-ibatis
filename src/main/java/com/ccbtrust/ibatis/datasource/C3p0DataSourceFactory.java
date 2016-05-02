package com.ccbtrust.ibatis.datasource;

import java.beans.PropertyVetoException;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.engine.datasource.DataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 数据库连接池
 * @author ciyuan
 *
 */
public class C3p0DataSourceFactory implements DataSourceFactory{

	protected static final Logger logger = LoggerFactory.getLogger(C3p0DataSourceFactory.class);
	
	private DataSource dateSource;
	@Override
	public DataSource getDataSource() {
		return dateSource;
	}

	/**
	 * 初始化
	 */
	@Override
	public void initialize(Map map) {
		logger.debug("dataSource数据源初始化开始！");
		ComboPooledDataSource c3 = new ComboPooledDataSource();
		String driverClass = (String) map.get("driverClass");
	    try {
			c3.setDriverClass(driverClass);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("driverClass load failure!");
		}
	    String jdbcUrl = (String) map.get("jdbcUrl");
		c3.setJdbcUrl(jdbcUrl);
		String user = (String) map.get("user");
		c3.setUser(user);
		String password = (String) map.get("password");
		c3.setPassword(password);
		String acquireIncrement = (String) map.get("acquireIncrement");
		if (acquireIncrement != null) {
		    c3.setAcquireIncrement(Integer.parseInt(acquireIncrement));
		}
		String initialPoolSize = (String) map.get("initialPoolSize");
		if (initialPoolSize != null) {
		    c3.setInitialPoolSize(Integer.parseInt(initialPoolSize));
		}
		String maxPoolSize = (String) map.get("maxPoolSize");
		if (maxPoolSize != null) {
		    c3.setMaxPoolSize(Integer.parseInt(maxPoolSize));
		}
		try {
		    String maxIdleTime = (String) map.get("maxIdleTime");
		    if (maxIdleTime != null) {
			c3.setMaxIdleTime(Integer.parseInt(maxIdleTime));
		    }
		} catch (NumberFormatException e1) {
		    c3.setMaxIdleTime(60);
		}
		String minPoolSize = (String) map.get("minPoolSize");
		if (minPoolSize != null) {
		    c3.setMinPoolSize(Integer.parseInt(minPoolSize));
		}
		String maxConnectionAge = (String) map.get("maxConnectionAge");
		if (maxConnectionAge != null) {
		    c3.setMaxConnectionAge(Integer.parseInt(maxConnectionAge));
		}
		try {
		    String maxIdleTimeExcessConnections = (String) map.get("maxIdleTimeExcessConnections");
		    c3.setMaxIdleTimeExcessConnections(Integer.parseInt(maxIdleTimeExcessConnections));
		} catch (NumberFormatException e) {
		}
		String automaticTestTable = (String) map.get("automaticTestTable");
		if (automaticTestTable != null) {
		    c3.setAutomaticTestTable(automaticTestTable);
		}
		try {
		    String checkoutTimeout = (String) map.get("checkoutTimeout");
		    c3.setCheckoutTimeout(Integer.parseInt(checkoutTimeout));
		} catch (NumberFormatException e) {
		    c3.setCheckoutTimeout(60000);
		}
		try {
		    String acquireRetryAttempts = (String) map.get("acquireRetryAttempts");
		    if (acquireRetryAttempts != null) {
			c3.setAcquireRetryAttempts(Integer.parseInt(acquireRetryAttempts));
		    }
		} catch (NumberFormatException e) {
		    c3.setAcquireRetryAttempts(2);
		}
		try {
		    String acquireRetryDelay = (String) map.get("acquireRetryDelay");
		    if (acquireRetryDelay != null) {
			c3.setAcquireRetryDelay(Integer.parseInt(acquireRetryDelay));
		    }
		} catch (NumberFormatException e) {
		    c3.setAcquireRetryDelay(1000);
		}
		try {
		    String connectionTesterClassName = (String) map.get("connectionTesterClassName");
		    if (connectionTesterClassName != null) {
			c3.setConnectionTesterClassName(connectionTesterClassName);
		    } else {
			c3.setConnectionTesterClassName("com.mchange.v2.c3p0.impl.DefaultConnectionTester");
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    throw new RuntimeException(e);
		}
		try {
		    String preferredTestQuery = (String) map.get("preferredTestQuery");
		    if (preferredTestQuery != null) {
			c3.setPreferredTestQuery(preferredTestQuery);
		    } else {
			c3.setPreferredTestQuery("values (current timestamp)");
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    throw new RuntimeException(e);
		}
		dateSource=c3;
	}

}
