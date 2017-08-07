package org.tis.tools.spi.om;

import java.util.Map;

import org.tis.tools.rservice.om.exception.EmployeeManagementException;

/**
 * 员工代码生成器接口
 * 
 * @author megapro
 *
 */
public interface IEmpCodeGenerator {
	/**
	 * 根据传入的参数，生成机构代码
	 * 
	 * @param parms
	 *            参数
	 * @return 机构代码
	 * @throws OrgManagementException
	 */
	public String genEmpCode(Map<String, String> parms) throws EmployeeManagementException;
}
