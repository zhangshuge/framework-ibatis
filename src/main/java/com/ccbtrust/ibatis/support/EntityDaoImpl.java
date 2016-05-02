package com.ccbtrust.ibatis.support;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.ccbtrust.ibatis.AbstractDao;
import com.ccbtrust.ibatis.exception.DataAccessException;
import com.ccbtrust.ibatis.page.PageBean;

public class EntityDaoImpl<T> extends AbstractDao implements EntityDao<T> {

	protected static final String POSTFIX_FIND = ".Find";
    protected static final String POSTFIX_SELECT_PRIAMARYKEY = ".Get";
    protected static final String POSTFIX_SELECT_PRIAMARYKEYMAP = ".GetMap";
    protected static final String POSTFIX_INSERT = ".Insert";
    protected static final String POSTFIX_UPDATE = ".Update";
    protected static final String POSTFIX_DELETE = ".Delete";
    protected Class<?> entityClass;
	@Autowired
    private Environment environment;
	public EntityDaoImpl() {
		entityClass = getSuperClassGenricType(getClass(), 0);
    }
	private static Class<?> getSuperClassGenricType(Class<?> clazz, int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
		    return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
		    return Object.class;
		}
		if (!(params[index] instanceof Class)) {
		    return Object.class;
		} else {
		    return (Class<?>) params[index];
		}
    }
	@Override
	protected String getMapFile() {
		return environment.getProperty("sql.map.config.file","SqlMapConfig.xml");
	}

	@Override
	protected String getDbType() {
		return environment.getProperty("db.type", DB_TYPE_ORACLE);
	}

	@Override
	public List<T> findBy(Object obj) throws DataAccessException {
		return getDao().find((new StringBuilder(String.valueOf(entityClass.getSimpleName()))).append(POSTFIX_FIND).toString(), obj);
	}

	@Override
	public void pagedFindBy(PageBean pageBean, Object obj)
			throws DataAccessException {
		getDao().find((new StringBuilder(String.valueOf(entityClass.getSimpleName()))).append(POSTFIX_FIND).toString(), pageBean, obj);
	}

	@Override
	public void pagedFindBy(String statementName, String countStatementName,
			PageBean pageObj, Object obj) throws DataAccessException {
		getDao().find(statementName, countStatementName, pageObj, obj);
	}

	@Override
	public Object insert(Object obj) throws DataAccessException {
		return getDao().save((new StringBuilder(String.valueOf(entityClass.getSimpleName()))).append(POSTFIX_INSERT).toString(), obj);
	}

	@Override
	public int update(Object obj) throws DataAccessException {
		return getDao().update((new StringBuilder(String.valueOf(entityClass.getSimpleName()))).append(POSTFIX_UPDATE).toString(), obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getByKey(Serializable obj) throws DataAccessException {
		return (T) getDao().get((new StringBuilder(String.valueOf(entityClass.getSimpleName()))).append(POSTFIX_SELECT_PRIAMARYKEY).toString(), obj);
	}

	@Override
	public int delete(Object t) throws DataAccessException {
		return getDao().delete((new StringBuilder(String.valueOf(entityClass.getSimpleName()))).append(POSTFIX_DELETE).toString(), t);
	}

	@Override
	public void txBegin() throws DataAccessException {
		getDao().txBegin();
	}

	@Override
	public void txCommit() throws DataAccessException {
		getDao().txCommit();
	}

	@Override
	public void txEnd() throws DataAccessException {
		getDao().txEnd();
	}

}
