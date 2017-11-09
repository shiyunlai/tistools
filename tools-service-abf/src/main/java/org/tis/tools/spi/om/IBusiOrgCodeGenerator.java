/**
 * 
 */
package org.tis.tools.spi.om;

import org.tis.tools.rservice.om.exception.BusiOrgManagementException;

/**
 * 业务机构代码生成器接口
 * 
 * @author megapro
 *
 */
public interface IBusiOrgCodeGenerator {

	/**
	 * <pre>
	 * 生成一个未被使用的业务机构代码。
	 * parms中需要指定的参数对包括：
	 * </pre>
	 * @param nodeType 节点类型
	 * @param busiDomain 业务条线
	 * @return
	 * @throws BusiOrgManagementException
	 */
	String genBusiOrgCode(String nodeType, String busiDomain) throws BusiOrgManagementException;
}
