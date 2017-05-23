/**
 * 
 */
package org.tis.tools.base.exception.i18;

import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.tis.tools.core.MetainfResources;
import org.tis.tools.core.MetainfResources.ILoadAction;
import org.tis.tools.core.RuntimeConstants;

import java.util.Properties;

/**
 * 
 * 异常国际化帮助类.<br>
 * 
 * @author megapro
 *
 */
public class ExceptionMessageHelper {
	
	/**
	 * 异常信息定义文件前缀标识.</br>
	 */
	private static final String EXCEPTION_RESOURCE_PREFIX = "ExceptionResource";
	
	/**
	 * 异常信息定义文件后缀标识
	 */
	private static final String EXCEPTION_RESOURCE_SUFFIX = ".properties";

	/**
	 * 默认国际化资源
	 */
	private static final String DEFAULT_EXCEPTION_RESOURCE = "ExceptionResource.properties";

	//加载标志
	private static boolean loaded = false;

	/**
	 * 获取国际化的异常信息，根据{@link Locale#getDefault()}返回对应的国际化信息.<br>
	 *
	 * @param key
	 *            异常编码
	 * @return 对应的异常信息
	 */
	public static String getMessage(String key) {
		return getMessage(key, Locale.getDefault());
	}

	/**
	 * 获取国际化的异常信息.<br>
	 *
	 * @param key
	 *            异常编码
	 * @param locale
	 *            语言
	 * @return 对应的异常信息
	 */
	public static String getMessage(String key, Locale locale) {
		if (!loaded) {
			loadMessageResources(Thread.currentThread().getContextClassLoader());
		}
		String message = null;
		Properties prop = ExceptionResourceCache.getLocaleProperties(locale);
		if (null != prop) {
			message = prop.getProperty(key);
		}

		if (null == message) {
			prop = ExceptionResourceCache.getDefaultProperties();
			if (null != prop) {
				message = prop.getProperty(key);
			}
		}
		return message;
	}

	/**
	 * 格式化字符串，根据给定的参数信息.<br>
	 *
	 * @param s
	 *            需要格式化的字符串
	 * @param params
	 *            参数信息
	 * @return 格式化后的字符串
	 */
	public static String format(String s, Object[] params) {
		if (!loaded) {
			loadMessageResources(Thread.currentThread().getContextClassLoader());
		}
		String message = s;
		if (message == null) {
			return "";
		}
		if (params != null && params.length > 0) {
			message = new MessageFormat(message).format(params);
		}
		return message;
	}

	/**
	 * 加载类加载器能找到的异常资源文件.<br>
	 *
	 * @param classLoader
	 *            类加载器
	 */
	public static synchronized void loadMessageResources(ClassLoader classLoader) {
		if (loaded) {
			return;
		}
		Map<String, Locale> localeResources = getAvailableLocaleResources();
		for (Entry<String, Locale> entry : localeResources.entrySet()) {
			loadMessageResource(classLoader, entry.getKey(), entry.getValue());
		}
		loaded = true;
	}

	private static Map<String, Locale> getAvailableLocaleResources() {
		Map<String, Locale> result = new HashMap<String, Locale>();
		Locale[] availableLocales = Locale.getAvailableLocales();
		String resource = null;
		for (int i = 0; i < availableLocales.length; i++) {
			resource = RuntimeConstants.TOOLS_I18_RESOURCES_PATH + EXCEPTION_RESOURCE_PREFIX + "_" + availableLocales[i].toString() + EXCEPTION_RESOURCE_SUFFIX;
			result.put(resource, availableLocales[i]);
		}
		result.put(RuntimeConstants.TOOLS_I18_RESOURCES_PATH + DEFAULT_EXCEPTION_RESOURCE, null);
		return result;
	}

	private static void loadMessageResource(ClassLoader classLoader,
			String messageResource, final Locale locale) {
		MetainfResources.load(classLoader, messageResource, new ILoadAction() {
			public Object run(int idx, String metaInfResource, URL url)
					throws Exception {
				register(url, locale);
				return null;
			}
		});
	}

	private static void register(URL url, Locale locale) throws Exception {
		InputStream in = null;
		try {
			in = url.openStream();
			Properties prop = new Properties();
			prop.load(in);
			if (null == locale) {
				ExceptionResourceCache.setDefaultProperties(prop);
			} else {
				ExceptionResourceCache.addLocaleProperties(locale, prop);
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					// ignore
				}
			}
		}
	}
}
