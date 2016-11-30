/**
 * 
 */
package org.tis.tools.webapp.impl.dubboinfo;

import java.util.ArrayList;
import java.util.List;

import org.tis.tools.webapp.spi.dubboinfo.DubboServiceInfo;
import org.tis.tools.webapp.spi.dubboinfo.IDubboInfoManager;

import com.alibaba.dubbo.common.URL;

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
		String url1 = "dubbo://127.0.0.1:20884/org.tis.tools.service.api.biztrace.IBiztraceRService?anyhost=true&application=tools-service-biztrace&dubbo=2.8.4a&generic=false&group=biztrace&interface=org.tis.tools.service.api.biztrace.IBiztraceRService&methods=listBiztraces,resolveAndAnalyseBiztraceFixed,getResolveProcess&organization=org.tis&owner=shiyl&pid=2054&revision=0.0.1&side=provider&timeout=3000&timestamp=1480295582806&version=1.0" ;
		URL du1 = URL.valueOf(url1);
		DubboServiceInfo info1 = new DubboServiceInfo() ; 
		info1.setHost(du1.getHost());
		info1.setPort(du1.getPort());
		info1.setProtocol(du1.getProtocol());
		info1.setServiceName(du1.getServiceName());
		md.add(info1) ;

		String url2 = "dubbo://127.0.0.1:20885/org.tis.tools.service.api.biztrace.IBiztraceRService?anyhost=true&application=tools-service-biztrace&dubbo=2.8.4a&generic=false&group=biztrace&interface=org.tis.tools.service.api.biztrace.IBiztraceRService&methods=listBiztraces,resolveAndAnalyseBiztraceFixed,getResolveProcess&organization=org.tis&owner=shiyl&pid=2054&revision=0.0.1&side=provider&timeout=3000&timestamp=1480295582806&version=1.0" ;
		du1 = URL.valueOf(url2);
		DubboServiceInfo info2 = new DubboServiceInfo() ; 
		info2.setHost(du1.getHost());
		info2.setPort(du1.getPort());
		info2.setProtocol(du1.getProtocol());
		info2.setServiceName(du1.getServiceName());
		md.add(info2) ;
		
		String url3 = "dubbo://127.0.0.1:20885/org.tis.tools.service.api.biztrace.IBiztraceRService?anyhost=true&application=tools-service-biztrace&dubbo=2.8.4a&generic=false&group=biztrace&interface=org.tis.tools.service.api.biztrace.IBiztraceRService&methods=listBiztraces,resolveAndAnalyseBiztraceFixed,getResolveProcess&organization=org.tis&owner=shiyl&pid=2054&revision=0.0.1&side=provider&timeout=3000&timestamp=1480295582806&version=1.0" ;
		du1 = URL.valueOf(url3);
		DubboServiceInfo info3 = new DubboServiceInfo() ; 
		info3.setHost(du1.getHost());
		info3.setPort(du1.getPort());
		info3.setProtocol(du1.getProtocol());
		info3.setServiceName(du1.getServiceName());
		md.add(info3) ;
		
		return md;
	}
	
}
