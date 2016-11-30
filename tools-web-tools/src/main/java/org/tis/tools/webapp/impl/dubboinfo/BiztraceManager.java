/**
 * 
 */
package org.tis.tools.webapp.impl.dubboinfo;

import java.util.ArrayList;
import java.util.List;

import org.tis.tools.webapp.spi.dubboinfo.DubboServiceInfo;
import org.tis.tools.webapp.spi.dubboinfo.IDubboInfoManager;

/**
 * @author megapro
 *
 */
public class BiztraceManager implements IDubboInfoManager {
	
	public static BiztraceManager instance = new BiztraceManager() ; 
	
	private BiztraceManager(){
		
	}
	
	/**
	 * 获取所有biztrace代理服务信息列表
	 * @return
	 */
	public List<DubboServiceInfo> getBiztraceProviderList() {
		
		//TODO 
		//解析当前应用目录下 dubbo/output/tools-web-tools.cache 
		//.cache文件实时更新，因此每次获取都做一次解析
		//获取“biztrace/org.tis.tools.service.api.biztrace.IBiztraceRService”服务对应的服务提供者信息
		
		return mockData() ; 
	}

	private List<DubboServiceInfo> mockData() {

		List<DubboServiceInfo> md = new ArrayList<DubboServiceInfo>() ; 
		DubboServiceInfo info1 = new DubboServiceInfo() ; 
		info1.setHost("127.0.0.1");
		info1.setPort(8079);
		info1.setProtocol("dubbo");
		info1.setServiceName("biztrace_mocker_1");
		md.add(info1) ;

		DubboServiceInfo info2 = new DubboServiceInfo() ; 
		info2.setHost("127.0.0.1");
		info2.setPort(8089);
		info2.setProtocol("dubbo");
		info2.setServiceName("biztrace_mocker_2");
		md.add(info2) ;
		
		DubboServiceInfo info3 = new DubboServiceInfo() ; 
		info3.setHost("127.0.0.1");
		info3.setPort(8099);
		info3.setProtocol("dubbo");
		info3.setServiceName("biztrace_mocker_3");
		md.add(info3) ;
		
		return md;
	}
	
}
