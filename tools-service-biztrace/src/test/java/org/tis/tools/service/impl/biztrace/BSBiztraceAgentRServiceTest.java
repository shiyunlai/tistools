package org.tis.tools.service.impl.biztrace;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.tis.tools.service.api.biztrace.BiztraceFileInfo;

public class BSBiztraceAgentRServiceTest {

	@Test
	public void test() {
		BiztraceRService biztraceRService = new BiztraceRService() ; 
		List<BiztraceFileInfo> ll = biztraceRService.listBiztraces("127.0.0.1:20883") ;
		for( BiztraceFileInfo info : ll ){
			System.out.println(info);
		}
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
