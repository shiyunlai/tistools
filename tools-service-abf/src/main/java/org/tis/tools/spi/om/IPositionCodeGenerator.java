package org.tis.tools.spi.om;

import org.tis.tools.rservice.om.exception.PositionManagementException;

/**
 * 岗位代码生成器接口
 * 
 * @author megapro
 *
 */
public interface IPositionCodeGenerator {
	/**
	 * 根据传入的参数，生成岗位代码
	 * 
	 * @param positionType
	 *            岗位类型
	 * @return 职务代码
	 * @throws PositionManagementException
	 */
	String genPositionCode(String positionType) throws PositionManagementException;
}
