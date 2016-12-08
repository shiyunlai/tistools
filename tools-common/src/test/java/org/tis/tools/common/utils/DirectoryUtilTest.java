/**
 * 
 */
package org.tis.tools.common.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

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

	
	@Test
	public void test2(){
		// D:\git\daotie\daotie
		String str = System.getProperty("user.dir") ; 
		System.out.println(str);
		
		 // 第一种：获取类加载的根路径   D:\git\daotie\daotie\target\classes
        File f = new File(this.getClass().getResource("/").getPath());
        System.out.println(f);
        
        // 获取当前类的所在工程路径; 如果不加“/”  获取当前类的加载目录  D:\git\daotie\daotie\target\classes\my
        File f2 = new File(this.getClass().getResource("").getPath());
        System.out.println(f2);
        
        // 第二种：获取项目路径    D:\git\daotie\daotie
        File directory = new File("");// 参数为空
        String courseFile=null;
		try {
			courseFile = directory.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(courseFile);
	}
}
