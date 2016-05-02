package com.ccbtrust.ibatis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author ciyuan
 *
 */
public class ReflectUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ReflectUtil.class);
	/**
	 * 设置域值
	 * @param target
	 * @param fname
	 * @param ftype
	 * @param fvalue
	 */
	public static void setFieldValue(Object target, String fname, Class ftype, Object fvalue){
		if (target == null || fname == null || 0 == fname.length() || fvalue != null && !ftype.isAssignableFrom(fvalue.getClass())) {
		    return;
		}
		Class clazz = target.getClass();
		try {
			Method method = clazz.getDeclaredMethod(
				    (new StringBuilder()).append("set").append(Character.toUpperCase(fname.charAt(0))).append(fname.substring(1)).toString(),
				    new Class[] { ftype });
			if(Modifier.isPublic(method.getModifiers())){
				method.setAccessible(true);
			}
			method.invoke(target, new Object[] { fvalue });
		} catch (Exception e) {
			try {
					Field field = clazz.getDeclaredField(fname);
					if (!Modifier.isPublic(field.getModifiers())) {
					    field.setAccessible(true);
					}
					field.set(target, fvalue);
			    } catch (Exception fe) {
			    	logger.error(fe.getMessage());
			    }
			}
	}
}
