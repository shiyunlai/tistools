/**
 * 
 */
package org.tis.tools.service.om;

import java.util.Map;

import org.tis.tools.rservice.om.exception.OrgManagementException;

/**
 * @author megapro
 *
 */
public interface IGenOrgCode {
	
	/**
	 * 生成机构代码.</br>
	 * 通过parms指定所需的参数值.
	 * 
	 * @param parms 生成机构代码所需的参数对
	 * @return 机构代码
	 * @throws OrgManagementException
	 */
	public String genOrgCode(Map<String,String> parms) throws OrgManagementException ; 
}
