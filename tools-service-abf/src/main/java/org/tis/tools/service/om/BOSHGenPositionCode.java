package org.tis.tools.service.om;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.rservice.om.exception.DutyManagementException;
import org.tis.tools.rservice.om.exception.PositionManagementException;
import org.tis.tools.rservice.sys.capable.ISeqnoRService;
import org.tis.tools.service.base.SequenceService;
import org.tis.tools.service.om.exception.OMExceptionCodes;
import org.tis.tools.spi.om.IPositionCodeGenerator;

@Service
public class BOSHGenPositionCode implements IPositionCodeGenerator{

	@Autowired
	SequenceService sequenceService ;

	@Autowired
	ISeqnoRService seqnoRService;

	/**
	 * 生成岗位代码
	 * @param positionType 职务类别
	 * @return
	 * @throws PositionManagementException
	 */
	@Override
	public String genPositionCode(String positionType) throws PositionManagementException {
//		String positionType = parms.get("positionType") ;
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
//		sb.append(toSeqNO(sequenceService.getNextSeqNo(BOSHGenPositionCode.class.getName()))) ;//五位机构顺序号
		sb.append(toSeqNO(seqnoRService.getNextSequence("POSI_CODE", "岗位代码序号")));
		return "POSITION"+sb.toString();
	}
	
	/**
	 * 把当前职务数量转为8位字符串，不足部分以0左补齐
	 * @param totalOrgCount
	 * @return
	 */
	private Object toSeqNO(long totalOrgCount) {
		
		String t = String.valueOf(totalOrgCount);
		
		return org.tis.tools.common.utils.StringUtil.leftPad(t, 8, '0');
		
	}
	
}
