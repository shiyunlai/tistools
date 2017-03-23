/**
 * 
 */
package org.tis.tools.rservice.jnl;

import org.apache.commons.lang.StringUtils;
import org.tis.tools.model.po.jnl.JnlCustService;
import org.tis.tools.rservice.jnl.exception.JnlRServiceException;

/**
 * @author megapro
 *
 */
public class JnlRServiceImpl implements IJnlRService {
	
	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.jnl.IJnlRService#createCustomService(java.lang.String, java.lang.String)
	 */
	@Override
	public JnlCustService createCustomService(String custNo, String serviceType) {
		
		if( StringUtils.isEmpty( custNo ) ){
			throw new JnlRServiceException("新建客户服务流水时必须指定客户号!") ;
		}
		
		if( StringUtils.isEmpty( serviceType ) ){
			throw new JnlRServiceException("新建客户服务流水时必须指定服务类型!") ;
		}
		
		JnlCustService jcs = new JnlCustService() ; 
		jcs.setGuid("123132132123");
		jcs.setCustNo(custNo);
		jcs.setServiceType(serviceType);
		jcs.setServiceStatus("1");
		jcs.setStartTime("20170322115045876");
		
		return jcs;
	}

}
