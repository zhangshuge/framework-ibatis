package com.ccbtrust.ibatis.executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccbtrust.ibatis.dialect.Dialect;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.RequestScope;
/**
 *
 * 针对特定数据库的sql执行器
 * @author ciyuan
 *
 */
public class LimitSqlExecutor extends SqlExecutor {
	
	private static final Logger logger = LoggerFactory.getLogger(LimitSqlExecutor.class);
	private Dialect dialect;
	private boolean enableLimit;
	
	public LimitSqlExecutor(Dialect dialect){
		this.dialect=dialect;
		enableLimit=true;
	}
	
	public Dialect getDialect() {
		return dialect;
    }

    public boolean isEnableLimit() {
    	return enableLimit;
    }

    public void setEnableLimit(boolean enableLimit) {
    	this.enableLimit = enableLimit;
    }
    /**
     * 查询执行SQL
     */
    public void executeQuery(RequestScope request, Connection conn, String sql, Object[] parameters, int skipResults, int maxResults,
    	    RowHandlerCallback callback) throws SQLException{
    	if ((skipResults != SqlExecutor.NO_SKIPPED_RESULTS || maxResults != SqlExecutor.NO_MAXIMUM_RESULTS) && supportsLimit()) {
    		 sql = dialect.getLimitString(sql, skipResults, maxResults);
    		 skipResults = NO_SKIPPED_RESULTS;
    		 maxResults = NO_MAXIMUM_RESULTS;
    	}
    	logger.debug(sql);
    	logger.debug(Arrays.toString(parameters));
    	super.executeQuery(request, conn, sql, parameters, skipResults, maxResults, callback);
    }
    
    /**
     * 是否支持特定数据库优化
     * @return
     */
    public boolean supportsLimit() {
		if (enableLimit && dialect != null) {
		    return dialect.supportsLimit();
		} else {
		    return false;
		}
    }
    
}
