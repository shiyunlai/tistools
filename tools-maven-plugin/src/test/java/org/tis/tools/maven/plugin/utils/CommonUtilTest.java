package org.tis.tools.maven.plugin.utils;

import junit.framework.Assert;

import org.junit.Test;
import org.tis.tools.maven.plugin.exception.GenDaoMojoException;
import org.tis.tools.maven.plugin.utils.CommonUtil;

public class CommonUtilTest {

	@Test
	public void testNormPackageName() {
		
		Assert.assertEquals("abal.ds.dl.kb", CommonUtil.normPackageName(" Abal_ds Dl.kb ")); 
		Assert.assertEquals("abal.ds.dlkb", CommonUtil.normPackageName(" Abal_ds Dlkb ")); 
		Assert.assertEquals("uuuu.abab", CommonUtil.normPackageName(" UUUU abab")); 
		Assert.assertEquals("aaaa", CommonUtil.normPackageName("Aaaa")); 
		Assert.assertEquals("a.b.c.d", CommonUtil.normPackageName("A B C D")); 
		Assert.assertEquals("abal.dsdlk.er23.uuuu.abab", CommonUtil.normPackageName("Abal_dsDlk-Er23 UUUU abab")); 
		Assert.assertEquals("jjava.ttry.ccatch.ffor.sswitch", CommonUtil.normPackageName("java.try-catch for_switch")); 
	}

	@Test
	public void testPackage2Path(){
		
		Assert.assertEquals("abc/vnm/yui/", CommonUtil.package2Path("abc.vnm.yui"));
		Assert.assertEquals("abc/vn/m/yui/", CommonUtil.package2Path("abc.vn.m.yui"));
		Assert.assertEquals("abc/vn/", CommonUtil.package2Path("abc.vn"));
		Assert.assertEquals("abc/", CommonUtil.package2Path("abc"));
	}
	
	@Test
	public void testHump2Line() {
		
		Assert.assertEquals("",CommonUtil.hump2Line("") ) ;
		Assert.assertEquals("a_b_c",CommonUtil.hump2Line("ABC") ) ;
		Assert.assertEquals("abc_xx_uy_b",CommonUtil.hump2Line("AbcXxUyB") ) ;
		Assert.assertEquals("abc_xx__uy_b",CommonUtil.hump2Line("AbcXx_UyB") ) ;//驼峰中国年有下划线，回被保留
		Assert.assertEquals("abc_xx__u.y_b",CommonUtil.hump2Line("AbcXx_U.yB") ) ;//驼峰中有点号，回被保留
		Assert.assertEquals("abc_xx__u-y_b",CommonUtil.hump2Line("AbcXx_U-yB") ) ;//驼峰中有中横行，回被保留
		Assert.assertEquals("org_fone_bronsp",CommonUtil.hump2Line("OrgFoneBronsp") ) ;
		Assert.assertEquals("org_fone_bronsp",CommonUtil.hump2Line("orgFoneBronsp") ) ;
		Assert.assertEquals("tb_gnl",CommonUtil.hump2Line("tbGnl") ) ;
	}
	
	@Test
	public void testLine2Hump() {
		
		Assert.assertEquals("",CommonUtil.line2Hump("") ) ;
		Assert.assertEquals("abcdfr",CommonUtil.line2Hump("abcdfr") ) ;
		Assert.assertEquals("abCBuhAddTui",CommonUtil.line2Hump("ab.c_buh-add.tui") ) ;
		Assert.assertEquals("orgFoneBronsp",CommonUtil.line2Hump("org.fone.bronsp") ) ;
		Assert.assertEquals("tbGnl",CommonUtil.line2Hump("tb_gnl") ) ;
	}
	
	@Test
	public void testGetProjectPathBySource() {
		
		Assert.assertEquals(null,CommonUtil.getProjectPathBySource(null) ) ;
		Assert.assertEquals("",CommonUtil.getProjectPathBySource("") ) ;
		Assert.assertEquals("ajdfklasfdl",CommonUtil.getProjectPathBySource("ajdfklasfdl") ) ;
		Assert.assertEquals("/Users/megapro/Develop/brons/bronsp/bronsp-maven-plugin/",
				CommonUtil.getProjectPathBySource("/Users/megapro/Develop/brons/bronsp/bronsp-maven-plugin/src/main/java/org/fone/bronsplus/") ) ;
		Assert.assertEquals("/",CommonUtil.getProjectPathBySource("/src/") ) ;
	}
	
	@Test
	public void testReplacePrjNameInMaven() throws GenDaoMojoException{
		String mavenPrjName = "/Users/megapro/Develop/brons/bronsp/bronsp-maven-plugin/src/main/java" ; 
		String expected = "/Users/megapro/Develop/brons/bronsp/test-prj/src/main/java" ; 
		Assert.assertEquals(expected, CommonUtil.replacePrjNameInMaven(mavenPrjName, "test-prj") ); 
		
		//TODO 测试抛异常的情况
	}
}
