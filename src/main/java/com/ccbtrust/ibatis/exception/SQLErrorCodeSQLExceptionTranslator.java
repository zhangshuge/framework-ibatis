package com.ccbtrust.ibatis.exception;

import java.sql.SQLException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sql异常代码翻译器
 * @author ciyuan
 *
 */
public class SQLErrorCodeSQLExceptionTranslator {
	protected final Logger logger;
	private SQLErrorCodes sqlErrorCodes;//错误码对象
	public SQLErrorCodeSQLExceptionTranslator(String dbName){
		logger = LoggerFactory.getLogger(SQLErrorCodeSQLExceptionTranslator.class);
		setDatabaseProductName(dbName);
	}
	public SQLErrorCodeSQLExceptionTranslator(SQLErrorCodes sec) {
		logger = LoggerFactory.getLogger(SQLErrorCodeSQLExceptionTranslator.class);
		sqlErrorCodes = sec;
	}

	public void setDatabaseProductName(String dbType) {
		sqlErrorCodes = SQLErrorCodesFactory.getInstance()
				.getErrorCodes(dbType);
	}

	public void setSqlErrorCodes(SQLErrorCodes sec) {
		sqlErrorCodes = sec;
	}

	public SQLErrorCodes getSqlErrorCodes() {
		return sqlErrorCodes;
	}
	
	public DataAccessException translate(String sql, SQLException sqlEx) {
		logger.debug("--->"+sqlEx.getMessage());
		if (sql == null) {
		    sql = "";
		}
		if (sqlErrorCodes != null) {
			String errorCode = null;
			if (sqlErrorCodes.isUseSqlStateForTranslation()) {
			    errorCode = sqlEx.getSQLState();
			} else {
			    errorCode = Integer.toString(sqlEx.getErrorCode());
			}
			if (errorCode != null) {
				if (Arrays.binarySearch(sqlErrorCodes.getBadSqlGrammarCodes(),
						errorCode) >= 0) {
					logTranslation(sql, sqlEx, false);
					return new BadSqlGrammarException(sql, sqlEx);
				}
				if (Arrays.binarySearch(
						sqlErrorCodes.getDataAccessResourceFailureCodes(),
						errorCode) >= 0) {
					logTranslation(sql, sqlEx, false);
					return new DataAccessResourceFailureException(buildMessage(
							sql, sqlEx), sqlEx);
				}
				if (Arrays.binarySearch(
						sqlErrorCodes.getDataIntegrityViolationCodes(),
						errorCode) >= 0) {
					logTranslation(sql, sqlEx, false);
					return new DataIntegrityViolationException(buildMessage(
							sql, sqlEx), sqlEx);
				}
				if (Arrays.binarySearch(sqlErrorCodes.getDeadlockLoserCodes(),
						errorCode) >= 0) {
					logTranslation(sql, sqlEx, false);
					return new DeadlockLoserDataAccessException(buildMessage(
							sql, sqlEx), sqlEx);
				}
			}
		}
		String codes = null;
		if (sqlErrorCodes.isUseSqlStateForTranslation()) {
		    codes = (new StringBuilder()).append("SQL state '")
		    		.append(sqlEx.getSQLState()).append("', error code '")
		    		.append(sqlEx.getErrorCode()).toString();
		} else {
		    codes = (new StringBuilder()).append("Error code '")
		    		.append(sqlEx.getErrorCode()).append("'").toString();
		}
		if (logger.isDebugEnabled()) {
		    logger.debug((new StringBuilder())
		    		.append("Unable to translate SQLException with ")
		    		.append(codes)
		    		.append(", will now try the fallback translator")
		    		.toString());
		}
		return new DataAccessException(codes, sqlEx);
	}
	protected String buildMessage(String sql, SQLException sqlEx) {
		return (new StringBuilder()).append(" SQL [").append(sql).append("]; ")
				.append(sqlEx.getMessage()).toString();
	}
	private void logTranslation(String sql, SQLException sqlEx, boolean custom) {
		if (logger.isDebugEnabled()) {
			String intro = custom ? "Custom translation of" : "Translating";
			logger.debug((new StringBuilder()).append(intro)
					.append(" SQLException with SQL state '")
					.append(sqlEx.getSQLState()).append("', error code '")
					.append(sqlEx.getErrorCode()).append("', message [")
					.append(sqlEx.getMessage()).append("]; SQL was [")
					.append(sql).append("]").toString());
		}
	}
}
