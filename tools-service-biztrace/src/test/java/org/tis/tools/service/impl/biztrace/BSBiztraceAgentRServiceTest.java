package org.tis.tools.service.impl.biztrace;

import java.util.List;

import org.junit.Test;
import org.tis.tools.service.api.biztrace.BiztraceFileInfo;

public class BSBiztraceAgentRServiceTest {

	@Test
	public void test() {
		BiztraceRService biztraceRService = new BiztraceRService() ; 
		List<BiztraceFileInfo> ll = biztraceRService.listBiztraces("127.0.0.1:20883") ;
		for( BiztraceFileInfo info : ll ){
			System.out.println(info);
		}
	}
	
}
