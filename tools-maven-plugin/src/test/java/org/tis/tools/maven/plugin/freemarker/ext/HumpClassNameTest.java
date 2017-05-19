package org.tis.tools.maven.plugin.freemarker.ext;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.tis.tools.maven.plugin.freemarker.ext.HumpClassName;

import freemarker.template.TemplateModelException;

public class HumpClassNameTest {

	HumpClassName h = null ; 
	List<String> k = new ArrayList<String>();
	
	@Before
	public void preTest(){
		h = new HumpClassName(); 
	}
	
	@Test
	public void test() throws TemplateModelException {
		k.add("ab_cdfrGG_GKkk");
		Assert.assertEquals("AbCdfrggGkkk", h.exec(k));
	}

}
