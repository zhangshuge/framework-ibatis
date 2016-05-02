package com.ccbtrust.ibatis.dialect;
/**
 * 
 * <pre>
 * Name：DB2Dialect.java
 * Description：针对DB2的方言
 */
public class DB2Dialect implements Dialect {

	public String getLimitString(String sql, boolean hasOffset) {
		StringBuffer pagingSelect = transSql(sql);
		if (hasOffset) {
		    pagingSelect.append("between ?+1 and ?");
		} else {
		    pagingSelect.append("<= ?");
		}
		return pagingSelect.toString();
	}

	public String getLimitString(String sql, int offset, int limit) {
		boolean hasOffset = true;
		StringBuffer pagingSelect = transSql(sql);
		if (hasOffset) {
		    pagingSelect.append((new StringBuilder()).append("between ")
		    		.append(offset + 1).append(" and ").append(offset + limit)
		    		.toString());
		} else {
		    pagingSelect.append("<= ?");
		}
		return pagingSelect.toString();
	}

	
	private StringBuffer transSql(String sql) {
		int startOfSelect = sql.toLowerCase().indexOf("select");
		StringBuffer pagingSelect = (new StringBuffer(sql.length() + 100))
				.append(sql.substring(0, startOfSelect))
				.append("select * from ( select ").append(getRowNumber(sql));
		if (hasDistinct(sql)) {
		    pagingSelect.append(" row_.* from ( ")
		    		.append(sql.substring(startOfSelect)).append(" ) as row_");
		} else {
		    pagingSelect.append(sql.substring(startOfSelect + 6));
		}
		pagingSelect.append(" ) as temp_ where rownumber_ ");
		return pagingSelect;
	}

	public boolean supportsLimit() {
		return true;
	}
/**
 * 
 * @param sql
 * @return
 */
	private String getRowNumber(String sql) {
		StringBuffer rownumber = (new StringBuffer(50))
				.append("rownumber() over(");
		int orderByIndex = sql.toLowerCase().indexOf("order by");
		if (orderByIndex > 0 && !hasDistinct(sql)) {
		    rownumber.append(sql.substring(orderByIndex));
		}
		rownumber.append(") as rownumber_,");
		return rownumber.toString();
	}
/**
 * 
 * @param sql
 * @return
 */
	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct") >= 0;
	}
}
