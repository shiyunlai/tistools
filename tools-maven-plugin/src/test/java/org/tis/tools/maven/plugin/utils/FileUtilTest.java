/**
 * 
 */
package org.tis.tools.maven.plugin.utils;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

/**
 * @author megapro
 *
 */
public class FileUtilTest {

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
	public void test() {
		
		String existPath    = "/Users/megapro/Develop/tis/tools/tools-maven-plugin/src/test/java/org/tis/tools/maven/plugin/utils" ; 
		String notExistPath = "/kkkk/megapro/Develop/tis/tools/tools-maven-plugin/src/test/java/org/tis/tools/maven/plugin/utils" ; 
		
		Assert.assertEquals(true, FileUtil.isNotExistPath(notExistPath));
		Assert.assertEquals(false, FileUtil.isNotExistPath(existPath));
		
	}

}
