/**
 * 
 */
package org.tools.design.provider.service;

import java.util.List;

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

	/**
	 * 模拟Consumer调用 OM业务域提供的服务
	 * @throws Exception
	 */
	public void callOMRService() throws Exception {

		//查询机构
		testQueryChilds("CN02100001");
		
		//新增机构
		String newOrgCode = testGenOrgCode() ;
		testCreateRootOrg(newOrgCode) ;
		
		//拷贝机构（浅）
		String copyNewOrgCode = testGenOrgCode() ;
		testCopyOrg(newOrgCode,copyNewOrgCode)  ;
		
		//在机构下新增岗位
		
		//在机构下新增工作组
		
		//在机构下新增员工
		
		//指定机构的角色
		
		//深度复制
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
	
	private void testQueryChilds(String orgCode ){
		System.out.println("查询机构 orgCode="+orgCode+"的下级机构");
		List<OmOrg> orgs = orgRService.queryChilds(orgCode) ; 
		for( OmOrg o : orgs ){
			System.out.println(o);
		}
	}
}
