package org.tis.tools.spi.om;

import org.tis.tools.rservice.om.exception.PositionManagementException;

import java.util.Map;

/**
 * 职务代码生成器接口
 * 
 * @author megapro
 *
 */
public interface IPositionCodeGenerator {
	/**
	 * 根据传入的参数，生成职务代码
	 * 
	 * @param parms
	 *            参数
	 * @return 职务代码
	 * @throws PositionManagementException
	 */
	public String genPositionCode(Map<String, String> parms) throws PositionManagementException;
}
