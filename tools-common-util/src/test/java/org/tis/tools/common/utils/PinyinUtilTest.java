package org.tis.tools.common.utils;

import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PinyinUtilTest {

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" =================== PinyinUtilTest begin ==================== ");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println(" =================== PinyinUtilTest finish =================== ");
	}

	@Test
	public void test() {
		Assert.assertEquals("ceshiruanjian123rrwwwwr", PinyinUtil.convert("测试/软件123RrWWWWr/") ) ;
		Assert.assertEquals("shiyunlai", PinyinUtil.convert("史云来") ) ;
		Assert.assertEquals("moxing", PinyinUtil.convert("模型") ) ;
		Assert.assertEquals("jigou", PinyinUtil.convert("机构") ) ;
		Assert.assertEquals("user", PinyinUtil.convert("user") ) ;
		Assert.assertEquals("123", PinyinUtil.convert("123") ) ;
	}

}
