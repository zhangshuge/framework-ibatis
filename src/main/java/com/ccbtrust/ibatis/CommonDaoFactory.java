package com.ccbtrust.ibatis;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccbtrust.ibatis.dialect.DB2Dialect;
import com.ccbtrust.ibatis.dialect.Dialect;
import com.ccbtrust.ibatis.dialect.MySQLDialect;
import com.ccbtrust.ibatis.dialect.OracleDialect;
import com.ccbtrust.ibatis.dialect.SQLServerDialect;
import com.ccbtrust.ibatis.executor.LimitSqlExecutor;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;

/**
 *  数据访问对象工厂类
 * @author ciyuan
 */
public class CommonDaoFactory {

	 private static final Logger logger = LoggerFactory.getLogger(CommonDaoFactory.class);
	 private static CommonDaoFactory instance = new CommonDaoFactory();//定义类对象实例
	 private Map<String,ICommonDao> daoMap;//定义访问数据对象
	 private final ReentrantLock lock = new ReentrantLock();
	 private CommonDaoFactory(){
		 daoMap = new HashMap<String,ICommonDao>();
	 }
	 public static CommonDaoFactory getInstance(){
		 return instance;
	 }
	 /**
	  * 获取数据访问对象
	  * @param mapFile
	  * @param dbType
	  * @return
	 * @throws IOException 
	  */
	 public ICommonDao getDao(String mapFile,String dbType) throws IOException{
		 ICommonDao dao;
		 String key;
		 key = (new StringBuilder()).append(dbType).append("-").append(mapFile).toString();
		 dao = daoMap.get(key);
		 if(dao != null){
			 return dao;
		 }else{
			 lock.lock();
			 dao = createDao(mapFile,dbType);
			 daoMap.put(key, dao);
			 lock.unlock();
			 return dao;
		 }
	 }
	 /**
	  * 创建Dao
	  * @param resource
	  * @param dbType
	  * @return
	  * @throws IOException
	  */
	 private synchronized ICommonDao createDao(String resource,String dbType) throws IOException{
		 SqlMapClient sqlMap = newSqlMapClient(resource);
		 Dialect dialect = null;//处理数据库方言
		 if("DB2".equals(dbType)){
			 dialect = new DB2Dialect();
		 }else if("MYSQL".equals(dbType)){
			 dialect = new MySQLDialect();
		 }else if("ORACLE".equals(dbType)){
			 dialect = new OracleDialect();
		 }else if("SQLSERVER".equals(dbType)){
			 dialect = new SQLServerDialect();
		 }else{
			 throw new IllegalArgumentException(
					 (new StringBuilder()).append("The database type: ").append(dbType).append(" can not create")
					    .toString());
		 }
		 SqlExecutor sqlExe = new LimitSqlExecutor(dialect);
		 ICommonDao commonDao = new ICommonDaoSqlMap(sqlMap, sqlExe, dbType);
		 return commonDao;
	 }
	 /**
	  * 创建SqlMapClient客户端
	  * @param resource
	  * @return
	  * @throws IOException
	  */
	 private synchronized SqlMapClient newSqlMapClient(String resource) throws IOException{
		 Reader reader;
		 try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		 SqlMapClient sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
		 return sqlMap;
	 }
}
