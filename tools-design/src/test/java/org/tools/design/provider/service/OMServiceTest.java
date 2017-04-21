/**
 * 
 */
package org.tools.design.provider.service;

import org.tis.tools.model.po.om.OmOrg;
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

		//新增机构
		String newOrgCode = testGenOrgCode() ;
		testCreateRootOrg(newOrgCode) ;
		
		//拷贝机构（浅）
		String copyNewOrgCode = testGenOrgCode() ;
		testCopyOrg(newOrgCode,copyNewOrgCode)  ;
	}

	private String testGenOrgCode() {
		System.out.println("获取机构代码");
		String orgCode = orgRService.genOrgCode("021", "4", "90");
		System.out.println("orgCode is :" + orgCode);
		return orgCode ; 
	}
	
	private void testCreateRootOrg(String orgCode) {
		
		System.out.println("新增根节点");
		OmOrg rootOrg = orgRService.createRootOrg(orgCode, "OMServiceTest", "90", "CN") ;
		System.out.println("新根节点：\n"+rootOrg);
	}
	
	private void testCopyOrg(String copyFrom, String copyTo) {
		System.out.println("拷贝机构 copyFrom="+copyFrom+" copyTo="+copyTo);
		OmOrg copied = orgRService.copyOrg(copyFrom, copyTo);
		System.out.println("拷贝所得机构：\n"+copied);
	}
}
