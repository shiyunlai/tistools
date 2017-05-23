/**
 * 
 */
package org.tis.tools.common.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * 各种“对象”操作工具
 * 
 * @author megapro
 *
 */
public class ObjectUtil {

	/**
	 * 进行两个对象间同名属性拷贝赋值
	 * 
	 * @param source
	 *            源对象
	 * @param copyTo
	 *            被赋值对象
	 */
	public static void copyAttributes(Object source, Object copyTo) {
		try {
			BeanUtils.copyProperties(copyTo, source);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
