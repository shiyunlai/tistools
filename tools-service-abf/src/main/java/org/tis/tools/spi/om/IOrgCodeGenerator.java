/**
 * 
 */
package org.tis.tools.spi.om;

import java.util.Map;

import org.tis.tools.rservice.om.exception.OrgManagementException;

/**
 * 机构代码生成接口
 * 
 * @author megapro
 *
 */
public interface IOrgCodeGenerator {
	public String genOrgCode(Map<String, String> parms) throws OrgManagementException;
}
