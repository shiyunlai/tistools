package org.tis.tools.spi.om;

import java.util.Map;

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
	 * @param parms
	 *            参数
	 * @return 职务代码
	 * @throws DutyManagementException
	 */
	public String genDutyCode(Map<String, String> parms) throws DutyManagementException;
}
