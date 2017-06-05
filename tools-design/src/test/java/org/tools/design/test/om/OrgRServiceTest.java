/**
 * 
 */
package org.tools.design.test.om;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.rservice.om.capable.IOrgRService;
import org.tis.tools.rservice.sys.basic.ISysDictRService;
import org.tis.tools.rservice.sys.capable.IDictRService;
import org.tools.design.SpringJunitSupport;

import junit.framework.Assert;

/**
 * 
 * 单元测试：测试OM组织模块中机构（Organization）概念对象的管理服务功能
 * 
 * @author megapro
 * 
 */
public class OrgRServiceTest extends SpringJunitSupport{
	
	@Autowired
	IOrgRService orgRService;
	
	@Autowired
	ISysDictRService sysDictRService ; 
	
	
	/*
	 * 测试数据: 生成机构代码所需的数据
	 */
	private static String orgDegree = ""; //机构级别
	private static String areaCode = "" ; //区域代码
	private static String orgType = "" ; //机构类型
	
	
	@Before
	public void before(){
		//增加机构数据
		sysDictRService.insert(null);
	}
	
	@After
    public void after(){
		sysDictRService.delete(null);
    }
	
	/**
	 * <pre>
	 * 案例1:生成机构代码成功
	 * 判断：机构代码满足既定规则
	 * 机构代码规则：
	 * 1.共10位；
	 * 2.组成结构： 机构等级(两位) + 地区码(三位) + 序号(五位)
	 * </pre>
	 */
	@Test
	public void genOrgCodeSucc() {
		
		//调用生成机构代码
		String orgCodeStr = orgRService.genOrgCode(areaCode, orgDegree, orgType) ;
		
		Assert.assertNotNull("成功生成机构代码不能为空", orgCodeStr);
		Assert.assertEquals("机构代码共10位",10, orgCodeStr.length());
		Assert.assertEquals("前两位是机构等级",orgCodeStr.substring(0, 2), orgDegree);
		Assert.assertEquals("三四五位是地区码",orgCodeStr.substring(3, 5), areaCode);
		
	}
	
	/**
	 * 案例2:生成机构代码失败，缺少所需的业务字典
	 */
	@Test
	public void genFailureCase() {
		
	}
}
