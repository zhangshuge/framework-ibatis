package com.ccbtrust.ibatis;

import java.io.IOException;

/**
 * 数据访问抽象类
 * @author ciyuan
 *
 */
public abstract class AbstractDao {
	public static final String DB_TYPE_DB2 = "DB2";
    public static final String DB_TYPE_ORACLE = "ORACLE";
    public static final String DB_TYPE_MYSQL = "MYSQL";
	private ICommonDao dao;
	/**
	 * 获取map文件
	 * @return
	 */
	protected abstract String getMapFile();
	/**
	 * 获取数据库类型
	 * @return
	 */
	protected abstract String getDbType();
	/**
	 * 获取数据访问对象
	 * @return
	 */
	public ICommonDao getDao(){
		if(dao == null){
			try {
				dao = CommonDaoFactory.getInstance().getDao(getMapFile(), getDbType());
			} catch (IOException e) {
				throw new RuntimeException("加载sql-map-config文件失败");
			}
		}
		return dao;
	}
}
