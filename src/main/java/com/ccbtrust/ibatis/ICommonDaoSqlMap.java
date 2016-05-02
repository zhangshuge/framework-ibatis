package com.ccbtrust.ibatis;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccbtrust.ibatis.exception.DataAccessException;
import com.ccbtrust.ibatis.exception.DataIntegrityViolationException;
import com.ccbtrust.ibatis.exception.SQLErrorCodeSQLExceptionTranslator;
import com.ccbtrust.ibatis.page.PageBean;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;

/**
 * 实现公共数据访问接口
 * @author ciyuan
 *
 */
public class ICommonDaoSqlMap implements ICommonDao {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ICommonDaoSqlMap.class);
	private SqlExecutor sqlExecutor;
    private SqlMapClient sqlMapClient;
    private String dbType;
	public ICommonDaoSqlMap(SqlMapClient sqlMap, SqlExecutor sqlExecutor, String dbType) {
		sqlMapClient = sqlMap;
		this.sqlExecutor = sqlExecutor;
		this.dbType = dbType;
		changeSqlExecutor();
	}
	/**
	 * 变更SQL执行器
	 */
	private synchronized void changeSqlExecutor(){
		if(sqlExecutor!=null && (sqlMapClient instanceof ExtendedSqlMapClient)){
			ReflectUtil.setFieldValue(((ExtendedSqlMapClient) sqlMapClient).getDelegate(), "sqlExecutor", SqlExecutor.class, sqlExecutor);
		}
	}
	@Override
	public Object save(String s, Object obj) throws DataAccessException {
		Object n = null;
		try {
			n = sqlMapClient.insert(s, obj);
		} catch (SQLException e) {
			DataAccessException e2 = (new SQLErrorCodeSQLExceptionTranslator(dbType)).translate(e.getMessage(), e);
			if (e2 instanceof DataIntegrityViolationException) {
				throw new DataAccessException(e2.getMessage(), e);
			} else {
				throw e2;
			}
		}
		return n;
	}
	@Override
	public int update(String s, Object obj) throws DataAccessException {
		int i;
		try {
			i = sqlMapClient.update(s, obj);
		} catch (SQLException e) {
			throw (new SQLErrorCodeSQLExceptionTranslator(dbType)).translate(e.getMessage(), e);
		}
		return i;
	}
	@Override
	public int delete(String s, Object obj) throws DataAccessException {
		int i;
		try {
			i = sqlMapClient.update(s, obj);
		} catch (SQLException e) {
			throw (new SQLErrorCodeSQLExceptionTranslator(dbType)).translate(e.getMessage(), e);
		}
		return i;
	}
	@Override
	public int deleteAll(String s, Map map) throws DataAccessException {
		int i;
		try {
			i = sqlMapClient.delete(s, map);
		} catch (SQLException e) {
			throw (new SQLErrorCodeSQLExceptionTranslator(dbType)).translate(e.getMessage(), e);
		}
		return i;
	}
	@Override
	public List find(String s) throws DataAccessException {
		List list;
		try {
			list = sqlMapClient.queryForList(s);
		} catch (SQLException e) {
			throw (new SQLErrorCodeSQLExceptionTranslator(dbType)).translate(e.getMessage(), e);
		}
		return list;
	}
	@Override
	public List find(String s, Map map) throws DataAccessException {
		List list;
		try {
		    list = sqlMapClient.queryForList(s, map);
		} catch (SQLException e) {
		    throw (new SQLErrorCodeSQLExceptionTranslator(dbType)).translate(e.getMessage(), e);
		}
		return list;
	}
	@Override
	public List find(String s, Object obj) throws DataAccessException {
		List list;
		try {
		    list = sqlMapClient.queryForList(s, obj);
		} catch (SQLException e) {
		    throw (new SQLErrorCodeSQLExceptionTranslator(dbType)).translate(e.getMessage(), e);
		}
		return list;
	}
	@Override
	public void find(String s, PageBean pageBean, Map map)
			throws DataAccessException {
		try {
			/*查询总条数*/
			Integer total = (Integer) sqlMapClient.queryForObject((new StringBuilder()).append(s).append("_count").toString(), map);
			if(total != null && total.intValue()>0){
				pageBean.setTotalRecords(total.intValue());
				List list = sqlMapClient.queryForList(s, map, pageBean.getRsFirstNumber()-1, pageBean.getLength());
				pageBean.setResults(list);
			}else{
				pageBean.setTotalRecords(0);
			}
		} catch (SQLException e) {
			throw (new SQLErrorCodeSQLExceptionTranslator(dbType)).translate(e.getMessage(), e);
		}
	}
	@Override
	public void find(String s, PageBean pageBean, Object obj)
			throws DataAccessException {
		try {
		    Integer total = (Integer) sqlMapClient.queryForObject((new StringBuilder()).append(s).append("_count").toString(), obj);
		    if (total != null && total.intValue() > 0) {
		    	pageBean.setTotalRecords(total.intValue());
			List rs = sqlMapClient.queryForList(s, obj, pageBean.getRsFirstNumber() - 1, pageBean.getLength());
				pageBean.setResults(rs);
		    } else {
		    	pageBean.setTotalRecords(0);
		    }
		} catch (SQLException e) {
		    throw (new SQLErrorCodeSQLExceptionTranslator(dbType)).translate(e.getMessage(), e);
		}
	}
	@Override
	public void find(String s, String countSql, PageBean pagebean, Object obj)
			throws DataAccessException {
		try {
		    Integer total = (Integer) sqlMapClient.queryForObject(countSql, obj);
		    if (total != null && total.intValue() > 0) {
		    	pagebean.setTotalRecords(total.intValue());
			List rs = sqlMapClient.queryForList(s, obj, pagebean.getRsFirstNumber() - 1, pagebean.getLength());
				pagebean.setResults(rs);
		    } else {
		    	pagebean.setTotalRecords(0);
		    }
		} catch (SQLException e) {
		    throw (new SQLErrorCodeSQLExceptionTranslator(dbType)).translate(e.getMessage(), e);
		}
	}
	@Override
	public Object get(String s) throws DataAccessException {
		Object obj;
		try {
			obj = sqlMapClient.queryForObject(s);
		} catch (SQLException e) {
			 throw (new SQLErrorCodeSQLExceptionTranslator(dbType)).translate(e.getMessage(), e);
		}
		return obj;
	}
	@Override
	public Object get(String s, Serializable id)
			throws DataAccessException {
		Object obj;
		try {
			obj = sqlMapClient.queryForObject(s,id);
		} catch (SQLException e) {
			 throw (new SQLErrorCodeSQLExceptionTranslator(dbType)).translate(e.getMessage(), e);
		}
		return obj;
	}
	@Override
	public Object get(String s, Map map) throws DataAccessException {
		Object obj;
		try {
		    obj = sqlMapClient.queryForObject(s, map);
		} catch (SQLException e) {
		    throw (new SQLErrorCodeSQLExceptionTranslator(dbType)).translate(e.getMessage(), e);
		}
		return obj;
	}
	@Override
	public Object get(String s, Object o) throws DataAccessException {
		Object obj;
		try {
		    obj = sqlMapClient.queryForObject(s, o);
		} catch (SQLException e) {
		    throw (new SQLErrorCodeSQLExceptionTranslator(dbType)).translate(e.getMessage(), e);
		}
		return obj;
	}
	@Override
	public void txBegin() throws DataAccessException {
		try {
			sqlMapClient.startTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		}
	}
	@Override
	public void txCommit() throws DataAccessException {
		try {
			sqlMapClient.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		}
	}
	@Override
	public void txEnd() throws DataAccessException {
		try {
		    sqlMapClient.endTransaction();
		} catch (SQLException e) {
		    e.printStackTrace();
		    throw new DataAccessException(e.getMessage(), e);
		}
	}
}
