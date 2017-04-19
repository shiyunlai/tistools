/**
 * 
 */
package org.tis.tools.spi.om;

import java.util.Map;

import org.tis.tools.rservice.om.exception.OrgManagementException;

/**
 * 机构代码生成器接口
 * 
 * @author megapro
 *
 */
public interface IOrgCodeGenerator {

	/**
	 * 根据传入的参数，生成机构代码
	 * 
	 * @param parms
	 *            参数
	 * @return 机构代码
	 * @throws OrgManagementException
	 */
	public String genOrgCode(Map<String, String> parms) throws OrgManagementException;
}
