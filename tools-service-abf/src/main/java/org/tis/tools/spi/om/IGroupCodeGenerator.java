/**
 * 
 */
package org.tis.tools.spi.om;

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
	 * @param groupType
	 *            工作组类型
	 * @return 机构代码
	 * @throws GroupManagementException
	 */
	String genGroupCode(String groupType) throws GroupManagementException;
}
