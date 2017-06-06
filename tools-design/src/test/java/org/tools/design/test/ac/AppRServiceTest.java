/**
 * 
 */
package org.tools.design.test.ac;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.rservice.ac.capable.IAppRService;
import org.tis.tools.rservice.om.capable.IOrgRService;
import org.tis.tools.rservice.sys.basic.ISysDictRService;
import org.tis.tools.rservice.sys.capable.IDictRService;
import org.tools.design.SpringJunitSupport;

import junit.framework.Assert;

/**
 * 
 * 单元测试：测试AC权限管理（Appliction）概念对象的管理服务功能
 * 
 * @author megapro
 * 
 */
public class AppRServiceTest extends SpringJunitSupport{
	
	@Autowired
	IAppRService appRService;
	
	/*
	 * 测试数据: 生成机构代码所需的数据
	 */
	private static String appCode = "APP0001"; //应用代码
	private static String appName = "ZZC" ; //应用名称
	private static String appType = "local" ; //应用类型
	private static String appDesc = "zzc" ; //描述
	private static String isOpen = "Y" ; //是否开通
	private static String openDate = "2017/06/06" ; //开通时间
	private static String url = "http://www.baidu.com/appserver" ; //地址
	private static String ipAddr = "127.0.0.1" ; //IP地址
	private static String ipPort = "8083" ; //IP端口
	
	
	@Before
	public void before(){
		//增加机构数据
	
	}
	
	@After
    public void after(){
//		sysDictRService.delete(null);
		
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
		
		AcApp app = appRService.createAcApp(appCode, appName, appType, appDesc, isOpen, openDate, url, ipAddr, ipPort);
		System.out.println("GUID响应："+app.getGuid());
		//调用生成机构代码
//		String orgCodeStr = orgRService.genOrgCode(areaCode, orgDegree, orgType) ;
//		
//		Assert.assertEquals("成功生成机构代码不能为空",10, orgCodeStr.length());
//		Assert.assertEquals("机构代码共10位",10, orgCodeStr.length());
//		Assert.assertEquals("前两位是机构等级",orgCodeStr.substring(0, 2), orgDegree);
//		Assert.assertEquals("三四五位是地区码",orgCodeStr.substring(3, 5), areaCode);
		
	}
	
	/**
	 * 案例2:生成机构代码失败，缺少所需的业务字典
	 */
	@Test
	public void genFailureCase() {
		
	}
	
	
	
	
	
}
