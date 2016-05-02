package com.ccbtrust.ibatis.exception;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据访问错误码工厂
 * @author ciyuan
 *
 */
public class SQLErrorCodesFactory {
	private static final Logger logger = LoggerFactory.getLogger(SQLErrorCodesFactory.class);
	private static final SQLErrorCodesFactory INSTANCE = new SQLErrorCodesFactory();
	private final Map<String, SQLErrorCodes> errorCodesMap;
	
	public SQLErrorCodesFactory(){
		/*定义错误码集合*/
		Map<String, SQLErrorCodes> errorCodes = new HashMap<String, SQLErrorCodes>();
		SQLErrorCodes db2 = new SQLErrorCodes();
		db2.setBadSqlGrammarCodes(new String[] { "-204", "-206", "-301", "-408" });
		db2.setDataAccessResourceFailureCodes(new String[] { "-904" });
		db2.setDatabaseProductName("DB2");
		db2.setDataIntegrityViolationCodes(new String[] { "-803" });
		db2.setDeadlockLoserCodes(new String[] { "-911", "-913" });
		errorCodes.put("DB2", db2);
		SQLErrorCodes mysql = new SQLErrorCodes();
		mysql.setBadSqlGrammarCodes(new String[] { "1054", "064", "1146" });
		mysql.setDataAccessResourceFailureCodes(new String[] { "1" });
		mysql.setDatabaseProductName("MYSQL");
		mysql.setDataIntegrityViolationCodes(new String[] { "1062" });
		mysql.setDeadlockLoserCodes(new String[] { "1213" });
		errorCodes.put("MYSQL", mysql);
		SQLErrorCodes oracle = new SQLErrorCodes();
		oracle.setBadSqlGrammarCodes(new String[] { "900", "903", "904", "917",
				"936", "942", "17006" });
		oracle.setDataAccessResourceFailureCodes(new String[] { "17002",
				"17447" });
		oracle.setDatabaseProductName("ORACLE");
		oracle.setDataIntegrityViolationCodes(new String[] { "1", "1400",
				"1722", "2291" });
		oracle.setDeadlockLoserCodes(new String[] { "60" });
		errorCodes.put("ORACLE", oracle);
		if (logger.isInfoEnabled()) {
		    logger.info((new StringBuilder()).append("SQLErrorCodes loaded: ")
		    		.append(errorCodes.keySet()).toString());
		}
		errorCodesMap = errorCodes;
	}
	public static SQLErrorCodesFactory getInstance() {
		return INSTANCE;
	}
	public SQLErrorCodes getErrorCodes(String dbName){
		SQLErrorCodes sec;
		label0:{
			sec = (SQLErrorCodes) errorCodesMap.get(dbName);
			if (sec != null) {
			    break label0;
			}
			Iterator<SQLErrorCodes> it = errorCodesMap.values().iterator();
			SQLErrorCodes candidate;
			do {
				if (!it.hasNext()) {
				    break label0;
				}
				candidate = it.next();
			} while (candidate.getDatabaseProductName().indexOf(dbName) <= -1);
			sec = candidate;
		}
		if (sec != null) {
			if (logger.isDebugEnabled()) {
				logger.debug((new StringBuilder())
			    		.append("SQL error codes for '").append(dbName)
			    		.append("' found").toString());
			}
			return sec;
		}
		if (logger.isDebugEnabled()) {
			logger.debug((new StringBuilder()).append("SQL error codes for '")
		    		.append(dbName).append("' not found").toString());
		}
		return new SQLErrorCodes();
	}
}
