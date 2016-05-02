package com.ccbtrust.ibatis.exception;

import java.sql.SQLException;

public class DataAccessResourceFailureException extends DataAccessException{

	private static final long serialVersionUID = 7845173300222340456L;
	private String sql;

	public DataAccessResourceFailureException(String sql, SQLException ex) {
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
