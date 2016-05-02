package com.ccbtrust.ibatis.support;

import java.io.Serializable;
import java.util.List;

import com.ccbtrust.ibatis.exception.DataAccessException;
import com.ccbtrust.ibatis.page.PageBean;

/**
 * 实体访问接口
 * @author ciyuan
 *
 * @param <T>
 */
public interface EntityDao<T> {
	/**
	 * 无分页机制查询  Find
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	List<T> findBy(Object obj) throws DataAccessException;

	/**
	 * 分页机制查询  Find
	 * @param pagebean
	 * @param obj
	 * @throws Exception
	 */
	void pagedFindBy(PageBean pageBean, Object obj)throws DataAccessException;
	
	/**
	 * 分页机制查询  Find
	 * @param pagebean
	 * @param obj
	 * @throws Exception
	 */
	void pagedFindBy(String statementName, String countStatementName, PageBean pageObj, Object obj)throws DataAccessException;

	/**
	 * 单条新增  Insert
	 *  <selectKey resultClass="int" type="pre" keyProperty="roleCode" > 
	 * 		SELECT seq_cm_temp_id.NEXTVAL AS VALUE FROM DUAL 
	 *  </selectKey> 
	 * 如果使用了上述 标签  可以返回序列的value  否则返回null
	 * @param obj
	 * @throws Exception
	 */
	Object insert(Object obj)throws DataAccessException;
	/**
	 * 单条更改 Update
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	int update(Object obj)throws DataAccessException;
	/**
	 * 按主键查询  Get
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	T getByKey(Serializable obj)throws DataAccessException;
	
	/**
	 * 删除
	 * @param t
	 * @return
	 * @throws DataAccessException
	 */
	int delete(Object t) throws DataAccessException;
	
	/**
	 * 开启事务
	 * @throws DataAccessException
	 */
	public void txBegin() throws DataAccessException;

	/**
	 * 提交事务
	 * @throws DataAccessException
	 */
	public void txCommit() throws DataAccessException ;

	/**
	 * 结束事务
	 * @throws DataAccessException
	 */
	public void txEnd() throws DataAccessException;
}
