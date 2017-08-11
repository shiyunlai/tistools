/**
 * 
 */
package org.tis.tools.spi.om;

import org.tis.tools.rservice.om.exception.BusiOrgManagementException;
import org.tis.tools.rservice.om.exception.OrgManagementException;

import java.util.Map;

/**
 * 机构代码生成器接口
 * 
 * @author megapro
 *
 */
public interface IBusiOrgCodeGenerator {

	/**
	 * 根据传入的参数，生成机构代码
	 * 
	 * @param parms
	 *            参数
	 * @return 机构代码
	 * @throws OrgManagementException
	 */
	public String genBusiOrgCode(Map<String, String> parms) throws BusiOrgManagementException;
}
