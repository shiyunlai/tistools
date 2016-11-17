/**
 * 
 */
package org.tis.tools.webapp.impl.dubboinfo;

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
		
		return null ; 
	}
	
}
