package org.tis.tools.spi.om;

import org.tis.tools.rservice.om.exception.EmployeeManagementException;

/**
 * 员工代码生成器接口
 * 
 * @author megapro
 *
 */
public interface IEmpCodeGenerator {
	/**
	 * 根据传入的参数，生成员工代码
	 *
	 * @return 员工代码
	 * @throws EmployeeManagementException
	 */
	public String genEmpCode() throws EmployeeManagementException;
}
