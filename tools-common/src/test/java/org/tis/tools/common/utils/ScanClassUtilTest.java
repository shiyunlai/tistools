/**
 * 
 */
package org.tis.tools.common.utils;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 对ScanClassUtil的单元测试
 * @author megapro
 *
 */
public class ScanClassUtilTest {

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
	public void testGetClasses() {
		
		Set<Class<?>> clazzes = ScanClassUtil.getClasses("org.tis.tools.common.utils") ;
		
		for( Class c : clazzes ){
			System.out.println(c.getName());
		}
	}

}
