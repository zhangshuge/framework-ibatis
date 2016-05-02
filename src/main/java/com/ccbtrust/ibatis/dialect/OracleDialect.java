package com.ccbtrust.ibatis.dialect;
/**
 * 针对oracle方言处理
 * @author ciyuan
 *
 */
public class OracleDialect implements Dialect{

	@Override
	public boolean supportsLimit() {
		return true;
	}

	@Override
	public String getLimitString(String sql, boolean hasOffset) {
		sql.trim();
		boolean isForUpdate = false;
		if(sql.toLowerCase().endsWith( "fro update")){
			sql = sql.substring(0, sql.length()-11);
			isForUpdate = true;
		}
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (hasOffset) {
		    pagingSelect
		    		.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
		    pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (hasOffset) {
		    pagingSelect.append(" ) row_ ) where rownum_ <= ? and rownum_ > ?");
		} else {
		    pagingSelect.append(" ) where rownum <= ?");
		}   
		if (isForUpdate) {
		    pagingSelect.append(" for update");
		}
		return pagingSelect.toString();
	}

	@Override
	public String getLimitString(String sql, int offset, int limitj) {
		boolean hasOffset = true;
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect
				.append("select * from ( select row_.*, rownum rownum_ from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ ) ");
		if (hasOffset) {
		    pagingSelect.append((new StringBuilder())
		    		.append(" where rownum_ > ").append(offset)
		    		.append(" and rownum_ < =").append(offset + limitj)
		    		.toString());
		}
		return pagingSelect.toString();
	}

}
