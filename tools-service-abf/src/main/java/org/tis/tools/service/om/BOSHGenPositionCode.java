package org.tis.tools.service.om;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.rservice.om.exception.DutyManagementException;
import org.tis.tools.rservice.om.exception.PositionManagementException;
import org.tis.tools.service.base.SequenceService;
import org.tis.tools.service.om.exception.OMExceptionCodes;
import org.tis.tools.spi.om.IPositionCodeGenerator;

import java.util.Map;

@Service
public class BOSHGenPositionCode implements IPositionCodeGenerator{

	@Autowired
	SequenceService sequenceService ;
	
	@Override
	public String genPositionCode(Map<String, String> parms) throws PositionManagementException {
		String positionType = parms.get("positionType") ;
		if(StringUtils.isEmpty(positionType)) {
			throw new DutyManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_POSITION,new Object[]{"positionType"}) ;
		}
		/**
		 * <pre>
		 * 岗位代码规则：
		 * 1.共10位；
		 * 2.组成结构： 岗位类别(两位) + 序号(八位)
		 * 序号：全行范围内职务数量顺序排号
		 * </pre>
		 */
		StringBuffer sb = new StringBuffer() ;
		
		// 机构等级
		sb.append(positionType) ;
		sb.append(toSeqNO(sequenceService.getNextSeqNo(BOSHGenPositionCode.class.getName()))) ;//五位机构顺序号
		return "POSITION"+sb.toString();
	}
	
	/**
	 * 把当前职务数量转为8位字符串，不足部分以0左补齐
	 * @param totalOrgCount
	 * @return
	 */
	private Object toSeqNO(int totalOrgCount) {
		
		String t = String.valueOf(totalOrgCount).toString() ;
		
		return org.tis.tools.common.utils.StringUtil.leftPad(t, 8, '0');
		
	}
	
}
