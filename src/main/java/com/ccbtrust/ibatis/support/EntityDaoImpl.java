package com.ccbtrust.ibatis.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.ccbtrust.ibatis.AbstractDao;

public class EntityDaoImpl<T> extends AbstractDao implements EntityDao<T> {

	@Autowired
    private Environment environment;

	@Override
	protected String getMapFile() {
		return environment.getProperty("sql.map.config.file","SqlMapConfig.xml");
	}

	@Override
	protected String getDbType() {
		return environment.getProperty("db.type", DB_TYPE_ORACLE);
	}

}
