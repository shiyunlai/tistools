package org.tis.tools.service.impl.biztrace;

import java.util.List;

import org.junit.Test;
import org.tis.tools.service.api.biztrace.BiztraceFileInfo;

public class BSBiztraceAgentRServiceTest {

	//@Test //FIXME 使用了disconf之后，本单元测试中取 BS部署路径的地方不对了， 如何进行disconf的单元测试
	public void test() {
		BiztraceRService biztraceRService = new BiztraceRService() ; 
		List<BiztraceFileInfo> ll = biztraceRService.listBiztraces("127.0.0.1:20883") ;
		for( BiztraceFileInfo info : ll ){
			System.out.println(info);
		}
	}
	
}
