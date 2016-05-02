package com.ccbtrust.ibatis.exception;

import java.sql.SQLException;
/**
 * 
 * Name：DeadlockLoserDataAccessException.java
 * Description：死锁丢失数据异常
 */
public class DeadlockLoserDataAccessException extends DataAccessException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3614181549622909983L;
	private String sql;

	public DeadlockLoserDataAccessException(String sql, SQLException ex) {
		super((new StringBuilder()).append("bad SQL grammar [").append(sql)
				.append("]").toString(), ex);
		this.sql = sql;
	}

	public SQLException getSQLException() {
		return (SQLException) getCause();
	}

	public String getSql() {
		return sql;
	}

}
