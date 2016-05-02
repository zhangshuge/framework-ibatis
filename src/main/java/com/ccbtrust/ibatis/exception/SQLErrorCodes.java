package com.ccbtrust.ibatis.exception;
/**
 * 错误代码
 * @author ciyuan
 *
 */
public class SQLErrorCodes {

	private String[] databaseProductNames;
	private boolean useSqlStateForTranslation;
	private String[] badSqlGrammarCodes;
	private String[] dataAccessResourceFailureCodes;
	private String[] dataIntegrityViolationCodes;
	private String[] deadlockLoserCodes;
	public SQLErrorCodes() {
		useSqlStateForTranslation = false;
		badSqlGrammarCodes = new String[0];
		dataAccessResourceFailureCodes = new String[0];
		dataIntegrityViolationCodes = new String[0];
		deadlockLoserCodes = new String[0];
	}
	public void setDatabaseProductName(String databaseProductName) {
		databaseProductNames = (new String[] { databaseProductName });
	}

	public String getDatabaseProductName() {
		return databaseProductNames == null || databaseProductNames.length <= 0 ? null
				: databaseProductNames[0];
	}
	public String[] getDatabaseProductNames() {
		return databaseProductNames;
	}
	public void setDatabaseProductNames(String[] databaseProductNames) {
		this.databaseProductNames = databaseProductNames;
	}
	public boolean isUseSqlStateForTranslation() {
		return useSqlStateForTranslation;
	}
	public void setUseSqlStateForTranslation(boolean useSqlStateForTranslation) {
		this.useSqlStateForTranslation = useSqlStateForTranslation;
	}
	public String[] getBadSqlGrammarCodes() {
		return badSqlGrammarCodes;
	}
	public void setBadSqlGrammarCodes(String[] badSqlGrammarCodes) {
		this.badSqlGrammarCodes = badSqlGrammarCodes;
	}
	public String[] getDataAccessResourceFailureCodes() {
		return dataAccessResourceFailureCodes;
	}
	public void setDataAccessResourceFailureCodes(
			String[] dataAccessResourceFailureCodes) {
		this.dataAccessResourceFailureCodes = dataAccessResourceFailureCodes;
	}
	public String[] getDataIntegrityViolationCodes() {
		return dataIntegrityViolationCodes;
	}
	public void setDataIntegrityViolationCodes(String[] dataIntegrityViolationCodes) {
		this.dataIntegrityViolationCodes = dataIntegrityViolationCodes;
	}
	public String[] getDeadlockLoserCodes() {
		return deadlockLoserCodes;
	}
	public void setDeadlockLoserCodes(String[] deadlockLoserCodes) {
		this.deadlockLoserCodes = deadlockLoserCodes;
	}
	
}
