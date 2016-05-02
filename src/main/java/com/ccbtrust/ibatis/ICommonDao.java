package com.ccbtrust.ibatis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ccbtrust.ibatis.exception.DataAccessException;
import com.ccbtrust.ibatis.page.PageBean;

/**
 * 数据访问公共接口
 * @author ciyuan
 *
 */
public interface ICommonDao {
	/**
	 * 保存对象
	 * @param s
	 * @param obj
	 * @return
	 * @throws DataAccessException
	 */
	public abstract Object save(String s,Object obj) throws DataAccessException;
	/**
	 * 更新对象
	 * @param s
	 * @param obj
	 * @return
	 * @throws DataAccessException
	 */
	public abstract int update(String s,Object obj) throws DataAccessException;
	/**
	 * 删除对象
	 * @param s
	 * @param obj
	 * @return
	 * @throws DataAccessException
	 */
	public abstract int delete(String s,Object obj) throws DataAccessException;
	/**
	 * 
	 * @param 删除全部对象
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public abstract int deleteAll(String s, Map map) throws DataAccessException;
	/**
	 * 查询对象集合
	 * @param s
	 * @return
	 * @throws DataAccessException
	 */
	public abstract List find(String s) throws DataAccessException;
	/**
	 * 查询对象集合
	 * @param s
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public abstract List find(String s,Map map) throws DataAccessException;
	/**
	 * 查询对象集合
	 * @param s
	 * @param obj
	 * @return
	 * @throws DataAccessException
	 */
	public abstract List find(String s,Object obj) throws DataAccessException;
	/**
	 * 分页查询对象集合
	 * @param s
	 * @param pageBean
	 * @param map
	 * @throws DataAccessException
	 * 统计条数select标签id="xxx_count"
	 */
	public abstract void find(String s,PageBean pageBean,Map map) throws DataAccessException;
	/**
	 * 分页查询对象集合
	 * @param s
	 * @param pageBean
	 * @param obj
	 * @throws DataAccessException
	 * 统计条数select标签id="xxx_count"
	 */
	public abstract void find(String s,PageBean pageBean,Object obj) throws DataAccessException;
	/**
	 * 分页查询对象集合-自定义sqlMap查询标签ID
	 * @param s
	 * @param countSql
	 * @param pagebean
	 * @param obj
	 * @throws DataAccessException
	 */
	public abstract void find(String s, String countSql, PageBean pagebean, Object obj) throws DataAccessException;
	/**
	 * 查询单条对象
	 * @param s
	 * @return
	 * @throws DataAccessException
	 */
	public abstract Object get(String s) throws DataAccessException;
	/**
	 * 查询单条对象
	 * @param s
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public abstract Object get(String s,Serializable id) throws DataAccessException;
	/**
	 * 查询单条对象
	 * @param s
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public abstract Object get(String s,Map map) throws DataAccessException;
	/**
	 * 查询单条对象
	 * @param s
	 * @param obj
	 * @return
	 * @throws DataAccessException
	 */
	public abstract Object get(String s,Object obj) throws DataAccessException;
	 /**
     * 事物开始
     * 
     * @throws DataAccessException
     */
    public abstract void txBegin() throws DataAccessException;

    /**
     * 事物提交
     * 
     * @throws DataAccessException
     */
    public abstract void txCommit() throws DataAccessException;

    /**
     * 事物结束
     * 
     * @throws DataAccessException
     */
    public abstract void txEnd() throws DataAccessException;

	
}
