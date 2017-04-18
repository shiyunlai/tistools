/**
 * 
 */
package org.tis.tools.rservice.sys.capable;

/**
 * <pre>
 * 业务菜单服务
 * </pre>
 * @author megapro
 *
 */
public interface IDictRService {

	/**
	 * 取指定业务字典（dictType）中字典项（dictItem）的实际值
	 * 
	 * @param dictType
	 *            业务字典
	 * @param dictItem
	 *            字典项
	 * @return 实际值
	 * @throws RuntimeException
	 *             取不到值，或取值发生错误时抛出异常
	 */
	String getActualValue(String dictType, String dictItem) throws RuntimeException;
}
