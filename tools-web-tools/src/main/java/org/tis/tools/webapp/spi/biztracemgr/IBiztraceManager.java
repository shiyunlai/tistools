/**
 * 
 */
package org.tis.tools.webapp.spi.biztracemgr;

import java.util.List;

/**
 * @author megapro
 *
 */
public interface IBiztraceManager {

	/**
	 * 获取所有biztrace代理服务信息列表
	 * @return
	 */
	public List<BiztraceAgentInfo> getBiztraceAgentList() ; 
	
}
