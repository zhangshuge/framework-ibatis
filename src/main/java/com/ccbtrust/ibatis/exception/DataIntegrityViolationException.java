/**
	create by chenzheng at 2013-9-29
 */

package com.ccbtrust.ibatis.exception;

import java.sql.SQLException;
/**
 * 
 * Name：DataIntegrityViolationException.java
 * Description：违反数据完整性异常
 */
public class DataIntegrityViolationException extends DataAccessException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7504728751470198864L;
	private String sql;

	public DataIntegrityViolationException(String sql, SQLException ex) {
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
