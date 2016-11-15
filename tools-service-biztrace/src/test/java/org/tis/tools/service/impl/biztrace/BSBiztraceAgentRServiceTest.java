package org.tis.tools.service.impl.biztrace;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.tis.tools.service.api.biztrace.BiztraceFileInfo;

public class BSBiztraceAgentRServiceTest {

	@Test
	public void test() {
		BiztraceRService biztraceRService = new BiztraceRService() ; 
		List<BiztraceFileInfo> ll = biztraceRService.listBiztraces() ;
		for( BiztraceFileInfo info : ll ){
			System.out.println(info);
		}
	
	}
}
