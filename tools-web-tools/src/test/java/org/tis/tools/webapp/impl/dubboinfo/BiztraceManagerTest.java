/**
 * 
 */
package org.tis.tools.webapp.impl.dubboinfo;

import static org.junit.Assert.*;

import org.apache.commons.lang.StringEscapeUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author megapro
 *
 */
public class BiztraceManagerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testResolveCacheFIle() {
		String str   = "override\\://172.20.10.9";
		String str2  = StringEscapeUtils.unescapeJava(str)  ; 
		System.out.println("转前:"+str);
		System.out.println("转后:"+str2);
	}

}
