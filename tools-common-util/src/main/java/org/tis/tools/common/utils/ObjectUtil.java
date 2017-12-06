/**
 * 
 */
package org.tis.tools.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.Manifest;

/**
 * 
 * 对Object“对象”的常用操作工具
 * 
 * @author megapro
 *
 */
public class ObjectUtil {

	private static Logger logger = LoggerFactory.getLogger(ObjectUtil.class);
	
	/** 空数组对象 */
	public static final Object[] NULL_OBJECTS = new Object[0];

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

	/**
	 * 因为不需要实例，所以构造函数为私有<BR>
	 * 参见Singleton模式<BR>
	 *
	 * Only one instance is needed,so the default constructor is private<BR>
	 * Please refer to singleton design pattern.
	 */
	private ObjectUtil() {
		super();
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 * @return true 为空 false 不为空
	 */
	public static boolean isNull(Object obj) {
		if (obj != null) {
			return false;
		}
		return true;
	}

	/**
	 * 根据类名创建一个对象
	 * 
	 * @param classFullName
	 *            全路径类名
	 * @return 对应类型的实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createObject(String classFullName) {

		if (null == classFullName || "".compareToIgnoreCase(classFullName) == 0) {
			return null;
		}

		try {
			Class<?> clazz = ClassUtil.getClass(ClassUtil.getDefaultClassLoader(), classFullName);
			Object obj = clazz.newInstance();
			return ((T) obj);
		} catch (Exception e) {
			logger.error("实例化类<"+classFullName+">出错！",e);
			return (null);
		}
	}

	/**
	 * 复制一个对象，支持数组和实现了Clone接口的类。<BR>
	 *
	 * Copy a object,this method support array and the class which support
	 * clone.<BR>
	 *
	 * @param r_Value
	 * @return
	 */
	public static Object copyValue(Object r_Value) {
		if (null == r_Value) {
			return r_Value;
		}

		Class<? extends Object> t_Type = r_Value.getClass();

		if (t_Type.isArray()) {
			int t_Length = Array.getLength(r_Value);
			Object[] t_Objects = (Object[]) Array.newInstance(t_Type.getComponentType(), t_Length);
			for (int i = 0; i < t_Length; i++) {
				Object t_Object = Array.get(r_Value, i);
				t_Objects[i] = copyValue(t_Object);
			}

			return t_Objects;
		} else {
			return copySingleValue(r_Value);
		}
	}

	/**
	 * 如果一个对象支持clone方法，而且clone方法是public的，就直接通过调用clone方法进行深层复制。<BR>
	 * 否则还是只返回value。<BR>
	 *
	 * @param value
	 */
	private static Object copySingleValue(Object value) {
		try {
			Method method = value.getClass().getMethod("clone", (Class[]) null);

			if ((null != method) && ((method.getModifiers() & Modifier.PUBLIC) != 0)) {
				return method.invoke(value, ArrayUtil.NULL_OBJECTS);
			} else {
				return value;
			}
		} catch (Exception e) {
			return value;
		}
	}

	/**
	 * Return a hex string form of an object's identity hash code.
	 * 
	 * @param obj
	 *            the object
	 * @return the object's identity code in hex
	 */
	public static String getIdentityHexString(Object obj) {
		return Integer.toHexString(System.identityHashCode(obj));
	}

	/**
	 * Resolve object instance from byte array.
	 *
	 * @param bytes
	 *            byte array.
	 * @param loader
	 *            specify class loader
	 * @return if success, return corresponding object instance, otherwise,
	 *         return null.
	 */
	public static Object bytes2Object(byte[] bytes, ClassLoader loader) {
		ByteArrayInputStream instrem = new ByteArrayInputStream(bytes);
		SpecifyLoaderObjectInputStream oin = null;
		try {
			oin = new SpecifyLoaderObjectInputStream(instrem, loader);
			return oin.readObject();
		} catch (Throwable error) {
			error.printStackTrace();
		} finally {
			try {
				oin.close();
			} catch (Throwable ignored) {
			}
			try {
				instrem.close();
			} catch (IOException ignored) {
			}
		}
		return null;
	}

	/**
	 * Serialize object to byte array.
	 *
	 * @param object
	 *            target object is wanted to serialize.
	 * @return if success, return corresponding byte array,otherwise,return
	 *         null.
	 */
	public static byte[] object2Bytes(Object object) {
		ByteArrayOutputStream byteOut = null;
		ObjectOutputStream objectOut = null;
		try {
			byteOut = new ByteArrayOutputStream();
			objectOut = new ObjectOutputStream(byteOut);
			objectOut.writeObject(object);
			return byteOut.toByteArray();
		} catch (Throwable error) {
			error.printStackTrace();
		} finally {
			try {
				byteOut.close();
			} catch (IOException ignored) {
			}
			try {
				objectOut.close();
			} catch (IOException ignored) {
			}
		}
		return null;
	}

	static class SpecifyLoaderObjectInputStream extends ObjectInputStream {
		private ClassLoader loader;

		public SpecifyLoaderObjectInputStream(InputStream in, ClassLoader loader) throws IOException {
			super(in);
			this.loader = loader;
		}

		protected Class<?> resolveClass(ObjectStreamClass desc) throws ClassNotFoundException, IOException {
			try {
				return Class.forName(desc.getName(), false, loader);
			} catch (ClassNotFoundException ce) {
				return super.resolveClass(desc);
			}
		}
	}

	/**
	 * Determine if the given objects are equal, returning <code>true</code> if
	 * both are <code>null</code> or <code>false</code> if only one is
	 * <code>null</code>.
	 * <p>
	 * Compares arrays with <code>Arrays.equals</code>, performing an equality
	 * check based on the array elements rather than the array reference.
	 * 
	 * @param o1
	 *            first Object to compare
	 * @param o2
	 *            second Object to compare
	 * @return whether the given objects are equal
	 * @see java.util.Arrays#equals
	 */
	public static boolean nullSafeEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		if (o1.equals(o2)) {
			return true;
		}
		if (o1 instanceof Object[] && o2 instanceof Object[]) {
			return Arrays.equals((Object[]) o1, (Object[]) o2);
		}
		if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
			return Arrays.equals((boolean[]) o1, (boolean[]) o2);
		}
		if (o1 instanceof byte[] && o2 instanceof byte[]) {
			return Arrays.equals((byte[]) o1, (byte[]) o2);
		}
		if (o1 instanceof char[] && o2 instanceof char[]) {
			return Arrays.equals((char[]) o1, (char[]) o2);
		}
		if (o1 instanceof double[] && o2 instanceof double[]) {
			return Arrays.equals((double[]) o1, (double[]) o2);
		}
		if (o1 instanceof float[] && o2 instanceof float[]) {
			return Arrays.equals((float[]) o1, (float[]) o2);
		}
		if (o1 instanceof int[] && o2 instanceof int[]) {
			return Arrays.equals((int[]) o1, (int[]) o2);
		}
		if (o1 instanceof long[] && o2 instanceof long[]) {
			return Arrays.equals((long[]) o1, (long[]) o2);
		}
		if (o1 instanceof short[] && o2 instanceof short[]) {
			return Arrays.equals((short[]) o1, (short[]) o2);
		}
		return false;
	}

	/**
	 * 从properties输入流中读取Properties对象
	 * 
	 * @param in
	 * @return
	 */
	public static Properties readPropertiesFromInputStream(InputStream in) throws IOException {
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			throw e;
		}
		return props;
	}

	/**
	 * 从properties文件中读取Properties对象
	 * 
	 * @param propertyFilepath
	 * @return
	 */
	public static Properties readPropertiesFromFile(String propertyFilepath) throws IOException {
		Properties props = null;
		InputStream in = null;
		try {
			in = new FileInputStream(new File(propertyFilepath));
			props = readPropertiesFromInputStream(in);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
		return props;
	}

	/**
	 * 从properties文件中读取Properties对象
	 * 
	 * @param propertyFilepath
	 * @return
	 */
	public static Manifest readManifestFromFile(String propertyFilepath) throws IOException {
		Manifest props = null;
		InputStream in = null;
		try {
			in = new FileInputStream(new File(propertyFilepath));
			props = new Manifest(in);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
		return props;
	}

	/**
	 * 将对象装换为map
	 * @param bean
	 * @return
	 */
	public static <T> Map<String, Object> beanToMap(T bean) {
		Map<String, Object> map = Maps.newHashMap();
		if (bean != null) {
			BeanMap beanMap = BeanMap.create(bean);
			for (Object key : beanMap.keySet()) {
				map.put(key+"", beanMap.get(key));
			}
		}
		return map;
	}

	/**
	 * 将map装换为javabean对象
	 * @param map
	 * @param bean
	 * @return
	 */
	public static <T> T mapToBean(Map<String, Object> map,T bean) {
		BeanMap beanMap = BeanMap.create(bean);
		beanMap.putAll(map);
		return bean;
	}

	/**
	 * 将List<T>转换为List<Map<String, Object>>
	 * @param objList
	 * @return
	 * @throws IOException
	 */
	public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
		List<Map<String, Object>> list = Lists.newArrayList();
		if (objList != null && objList.size() > 0) {
			Map<String, Object> map = null;
			T bean = null;
			for (int i = 0,size = objList.size(); i < size; i++) {
				bean = objList.get(i);
				map = beanToMap(bean);
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 将List<Map<String,Object>>转换为List<T>
	 * @param maps
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps,Class<T> clazz) throws InstantiationException, IllegalAccessException {
		List<T> list = Lists.newArrayList();
		if (maps != null && maps.size() > 0) {
			Map<String, Object> map = null;
			T bean = null;
			for (int i = 0,size = maps.size(); i < size; i++) {
				map = maps.get(i);
				bean = clazz.newInstance();
				mapToBean(map, bean);
				list.add(bean);
			}
		}
		return list;
	}
}
