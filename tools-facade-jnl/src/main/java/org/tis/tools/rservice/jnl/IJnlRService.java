/**
 * 
 */
package org.tis.tools.rservice.jnl;

import org.tis.tools.model.po.jnl.JnlCustService;

/**
 * <pre>
 * 流水业务域的服务
 * </pre>
 *
 * @autor mega-pro
 *
 */
public interface IJnlRService {

	/**
	 * <pre>
	 * 功能：新建一笔服务流水
	 * 场景：开始一次客户服务时
	 * </pre>
	 * 
	 * @param custNo 客户编号
	 * @param serviceType 服务类型
	 * @return 新建好的客户服务流水记录
	 */
	public JnlCustService createCustomService( String custNo, String serviceType );
	
}
