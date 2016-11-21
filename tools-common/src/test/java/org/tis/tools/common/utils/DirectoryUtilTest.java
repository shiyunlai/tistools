/**
 * 
 */
package org.tis.tools.common.utils;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tis.tools.common.utils.DirectoryUtil;

/**
 * @author megapro
 *
 */
public class DirectoryUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" =================== DirectoryUtilTest begin ==================== ");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println(" =================== DirectoryUtilTest finish =================== ");
	}
	
	@Test
	public void test() {
		System.out.println("getAppMainDirectory:" +DirectoryUtil.getAppMainDirectory());
		System.out.println("getAppMainDirectory:" +DirectoryUtil.getAppMainDirectory()+"/../../");
//		System.out.println("getClassDirectory:"+DirectoryUtil.getClassDirectory(DirectoryUtilTest.class));
		System.out.println("getClassRootDirectory:"+DirectoryUtil.getClassRootDirectory(DirectoryUtilTest.class));
	}

}
