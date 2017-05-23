/**
 * 
 */
package org.tis.tools.base.exception.i18;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * 异常资源缓存.</br>
 * 
 * @author megapro
 *
 */
public class ExceptionResourceCache {

	private static Map<Locale, Properties> localePropertiesCache = new HashMap<Locale, Properties>();
	private static Properties defaultProperties = new Properties();

	public static void setDefaultProperties(Properties props) {
		if (props != null) {
			defaultProperties.putAll(props);
		}
	}
	
	public static Properties getDefaultProperties() {
		return defaultProperties;
	}

	public static Properties getLocaleProperties(Locale locale) {
		return locale == null ? null : localePropertiesCache.get(locale);
	}

	public static Properties getProperties(Locale locale) {
		return locale == null ? getDefaultProperties() : localePropertiesCache.get(locale);
	}

	public static void addLocaleProperties(Locale locale, Properties prop) {
		Properties existProperties = localePropertiesCache.get(locale);
		if (null == existProperties) {
			existProperties = new Properties();
			localePropertiesCache.put(locale, existProperties);
		}
		if (null != prop) {
			existProperties.putAll(prop);
		}
	}

	public static void removeLocaleProperties(Locale locale) {
		if (locale != null) {
			localePropertiesCache.remove(locale);
		}
	}

	public static void clear() {
		localePropertiesCache.clear();
		defaultProperties = null;
	}

}
