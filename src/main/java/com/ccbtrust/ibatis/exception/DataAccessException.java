package com.ccbtrust.ibatis.exception;
/**
 * 数据库访问异常
 * @author ciyuan
 *
 */
public class DataAccessException extends RuntimeException{

	private static final long serialVersionUID = -6558299059257111016L;
	public DataAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
