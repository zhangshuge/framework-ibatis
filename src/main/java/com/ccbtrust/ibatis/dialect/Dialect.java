package com.ccbtrust.ibatis.dialect;
/**
 * 生成数据库方言接口
 * @author ciyuan
 */
public interface Dialect {
	
	public abstract boolean supportsLimit();
	public abstract String getLimitString(String s, boolean flag);
	public abstract String getLimitString(String s, int i, int j);
}
