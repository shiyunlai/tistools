package org.tis.tools.spi.om;

import org.tis.tools.rservice.om.exception.EmployeeManagementException;

import java.util.Map;

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
	 * @throws EmployeeManagementException
	 */
	public String genEmpCode(Map<String, String> parms) throws EmployeeManagementException;
}
