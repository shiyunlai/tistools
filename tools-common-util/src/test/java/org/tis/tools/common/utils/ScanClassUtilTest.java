/**
 * 
 */
package org.tis.tools.common.utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

/**
 * 对ScanClassUtil的单元测试
 * @author megapro
 *
 */
public class ScanClassUtilTest {

	/**
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws Exception
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
