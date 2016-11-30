package org.tis.tools.service.impl.biztrace;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
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
