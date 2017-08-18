package org.tis.tools.service.om;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.rservice.om.exception.EmployeeManagementException;
import org.tis.tools.service.base.SequenceService;
import org.tis.tools.spi.om.IEmpCodeGenerator;

@Service
public class BOSHGenEmpCode implements IEmpCodeGenerator{
	@Autowired
	SequenceService sequenceService ;

	@Override
	public String genEmpCode(Map<String, String> parms) throws EmployeeManagementException {
		//暂时不用参数.
		//todo
		StringBuffer sb = new StringBuffer() ;
		sb.append(toSeqNO(sequenceService.getNextSeqNo(BOSHGenEmpCode.class.getName()))) ;
		return "EMP"+sb.toString();
	}

	private Object toSeqNO(int totalOrgCount) {

		String t = String.valueOf(totalOrgCount).toString() ;

		return org.tis.tools.common.utils.StringUtil.leftPad(t, 6, '0');

	}

}
