/**
 * 
 */
package org.tis.tools.core;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.core.exception.ExceptionCodes;

/**
 * 
 * META-INF下相关资源加载.<br>
 * 
 * @author megapro
 *
 */
public class MetainfResources {

	/**
	 * 资源具体的加载动作接口.<br>
	 * 用于
	 * {@link MetainfResources#load(ClassLoader, String, org.tis.tools.core.MetainfResources.ILoadAction)}
	 * 作为回调接口.
	 *
	 * @author megapro
	 */
	public static interface ILoadAction {

		/**
		 * 具体的加载行为.<br>
		 *
		 * @param index
		 *            类路径下搜索的资源的顺序.<br>
		 * @param metainfResource
		 *            META-INF的资源
		 * @param metainfUrl
		 *            META-INF的资源的位置
		 * @return 加载结果
		 * @throws Throwable
		 *             加载时的异常
		 */
		Object run(int index, String metainfResource, URL metainfUrl) throws Throwable;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(MetainfResources.class);

	/**
	 * 加载指定类路径下的META-INF的资源.<br>
	 *
	 * @param classLoader
	 *            类路径
	 * @param metainfResource
	 *            META-INF的资源
	 * @param action
	 *            资源具体的加载动作接口
	 * @return 加载结果
	 */
	public static Map<URL, Object> load(ClassLoader classLoader, String metainfResource, ILoadAction action) {
		Map<URL, Object> ret = new LinkedHashMap<URL, Object>();
		Enumeration<URL> metainfUrls = null;
		try {
			metainfUrls = classLoader.getResources(metainfResource);
		} catch (IOException e) {
			throw new ToolsRuntimeException(ExceptionCodes.FIND_META_INF_RESOURCE_ERROR,
					new String[] { metainfResource, classLoader.toString() }, e);
		}
		if (null != metainfUrls) {
			int index = -1;
			while (metainfUrls.hasMoreElements()) {
				URL metainfUrl = metainfUrls.nextElement();
				try {
					index += 1;
					Object actionRet = action.run(index, metainfResource, metainfUrl);
					ret.put(metainfUrl, actionRet);
				} catch (Throwable e) {
					logger.error("Load metainf resource ''{0}'' error.", new String[] { metainfUrl.toExternalForm() },
							e);
				}
			}
		}
		return ret;
	}
}
