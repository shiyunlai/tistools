/**
 * 
 */
package org.tis.tools.spi.om;

import java.util.Map;

import org.tis.tools.rservice.om.exception.GroupManagementException;

/**
 * 机构代码生成器接口
 * 
 * @author megapro
 *
 */
public interface IGroupCodeGenerator {

	/**
	 * 根据传入的参数，生成机构代码
	 * 
	 * @param parms
	 *            参数
	 * @return 机构代码
	 * @throws GroupManagementException
	 */
	public String genGroupCode(Map<String, String> parms) throws GroupManagementException;
}
