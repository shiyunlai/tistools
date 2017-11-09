package org.tis.tools.service.om;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.rservice.om.exception.EmployeeManagementException;
import org.tis.tools.rservice.sys.capable.ISeqnoRService;
import org.tis.tools.service.base.SequenceService;
import org.tis.tools.spi.om.IEmpCodeGenerator;

@Service
public class BOSHGenEmpCode implements IEmpCodeGenerator{
	@Autowired
	SequenceService sequenceService ;

	@Autowired
	ISeqnoRService seqnoRService;

	@Override
	public String genEmpCode() throws EmployeeManagementException {
		//暂时不用参数.
		//todo
		StringBuffer sb = new StringBuffer() ;
//		sb.append(toSeqNO(sequenceService.getNextSeqNo(BOSHGenEmpCode.class.getName()))) ;
		sb.append(toSeqNO(seqnoRService.getNextSequence("EMP_CODE", "员工代码序号")));
		return "EMP"+sb.toString();
	}

	private Object toSeqNO(long totalOrgCount) {

		String t = String.valueOf(totalOrgCount) ;

		return org.tis.tools.common.utils.StringUtil.leftPad(t, 6, '0');

	}

}
