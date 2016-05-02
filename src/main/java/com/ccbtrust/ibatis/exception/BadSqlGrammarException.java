package com.ccbtrust.ibatis.exception;

import java.sql.SQLException;

public class BadSqlGrammarException extends DataAccessException{

	private static final long serialVersionUID = 1L;
	private String sql;

	public BadSqlGrammarException(String sql, SQLException ex) {
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
