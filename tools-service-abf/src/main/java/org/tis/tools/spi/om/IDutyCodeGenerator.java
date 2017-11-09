package org.tis.tools.spi.om;

import org.tis.tools.rservice.om.exception.DutyManagementException;

/**
 * 职务代码生成器接口
 * 
 * @author megapro
 *
 */
public interface IDutyCodeGenerator {
	/**
	 * 根据传入的参数，生成职务代码
	 * 
	 * @param dutyType 职务类型
	 *            参数
	 * @return 职务代码
	 * @throws DutyManagementException
	 */
	String genDutyCode(String dutyType) throws DutyManagementException;
}
