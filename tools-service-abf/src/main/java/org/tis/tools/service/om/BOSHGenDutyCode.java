package org.tis.tools.service.om;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.rservice.om.exception.DutyManagementException;
import org.tis.tools.service.base.SequenceService;
import org.tis.tools.service.om.exception.OMExceptionCodes;
import org.tis.tools.spi.om.IDutyCodeGenerator;

public class BOSHGenDutyCode implements IDutyCodeGenerator{

	@Autowired
	SequenceService sequenceService ;
	
	@Override
	public String genDutyCode(Map<String, String> parms) throws DutyManagementException {
		String dutyType = parms.get("dutyType") ;
		if(StringUtils.isEmpty(dutyType)) {
			throw new DutyManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_DUTYCODE,new Object[]{"dutytype"}) ; 
		}
		/**
		 * <pre>
		 * 职务代码规则：
		 * 1.共10位；
		 * 2.组成结构： 职务套别(两位) + 序号(八位)
		 * 序号：全行范围内职务数量顺序排号
		 * </pre>
		 */
		StringBuffer sb = new StringBuffer() ;
		
		// 机构等级
		sb.append(dutyType) ;
		sb.append(toSeqNO(sequenceService.getNextSeqNo(BOSHGenDutyCode.class.getName()))) ;//五位机构顺序号
		return null;
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
