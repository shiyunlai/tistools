/**
 * 
 */
package org.tis.tools.spi.om;

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
	 * @param orgDegree 机构等级
	 * @param areaCode  地区码
	 * @return
	 * @throws OrgManagementException
	 */
	String genOrgCode(String orgDegree, String areaCode) throws OrgManagementException;
}
