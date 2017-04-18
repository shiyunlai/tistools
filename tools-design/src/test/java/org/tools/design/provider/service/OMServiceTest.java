/**
 * 
 */
package org.tools.design.provider.service;

import org.tis.tools.rservice.om.capable.IOrgRService;

/**
 * @author megapro
 *
 */
public class OMServiceTest {

	IOrgRService orgRService;

	/**
	 * @return the orgRService
	 */
	public IOrgRService getOrgRService() {
		return orgRService;
	}

	/**
	 * @param orgRService
	 *            the orgRService to set
	 */
	public void setOrgRService(IOrgRService orgRService) {
		this.orgRService = orgRService;
	}

	public void start() throws Exception {

		testGenOrgCode() ;
	}

	/**
	 * 测试：获取机构代码
	 */
	private void testGenOrgCode() {
		String orgCode = orgRService.genOrgCode("021", "4", "90");
		System.out.println("orgCode is :" + orgCode);		
	}
}
