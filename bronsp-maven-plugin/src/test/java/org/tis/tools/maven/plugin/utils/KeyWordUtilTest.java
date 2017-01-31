package org.tis.tools.maven.plugin.utils;

import junit.framework.Assert;

import org.junit.Test;
import org.tis.tools.maven.plugin.utils.KeyWordUtil;

public class KeyWordUtilTest {

	@Test
	public void test() {
		
		Assert.assertEquals("jjava", KeyWordUtil.instance.replace("java")) ;
		Assert.assertEquals("bbreak", KeyWordUtil.instance.replace("break")) ;
		Assert.assertEquals("bronsp", KeyWordUtil.instance.replace("bronsp")) ;
		Assert.assertEquals("bronsp", KeyWordUtil.instance.replace("branch one system plus")) ;
		
		Assert.assertEquals("shiyl", KeyWordUtil.instance.replace("shiyl")) ;
		Assert.assertEquals("beijing", KeyWordUtil.instance.replace("beijing")) ;
		Assert.assertEquals("我们", KeyWordUtil.instance.replace("我们")) ;
		
		KeyWordUtil.instance.addKeyWord("shiyunlai", "syl");
		KeyWordUtil.instance.addKeyWord("shanghai", "sh");
		KeyWordUtil.instance.addKeyWord("password", "******");
		
		Assert.assertEquals("******", KeyWordUtil.instance.replace("password")) ;
		Assert.assertEquals("sh", KeyWordUtil.instance.replace("shanghai")) ;
		Assert.assertEquals("syl", KeyWordUtil.instance.replace("shiyunlai")) ;
		
//		KeyWordUtil.instance.addKeyWord("");
		
	}

}
