/**
 * 
 */
package org.tis.tools.webapp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tis.tools.service.api.biztrace.BiztraceFileInfo;
import org.tis.tools.webapp.util.JSONPropertyStrategyWrapper;
import org.tis.tools.webapp.util.JSONUtils;
import org.tis.tools.webapp.util.JsonDateProcessor;
import org.tis.tools.webapp.util.JsonFileProcessor;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertySetStrategy;

/**
 * @author megapro
 *
 */
public class BizlogControllerTest {

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
	public void test2(){
		String jsonStr = "{\"type\":\"part\",\"logs\":[\"biztrace.log.1\",\"biztrace.log.2\"]}" ;
		
		JSONObject jsonLogFiles = JSONObject.fromObject(jsonStr);
		String type = JSONUtils.getStr(jsonLogFiles, "type") ;
		Assert.assertEquals("part", type); 
		
		JSONArray logsList = jsonLogFiles.getJSONArray("logs") ; 
		List<String> files = (List<String>)JSONSerializer.toJava(logsList) ;
		for( String str : files ){
			System.out.println(str);
		}
	}
	
	
	/**
	 * 对File类型转化为Json时会报错 net.sf.json.JSONException: There is a cycle in the hierarchy!
	 * 方法1: 使用 jsonConfig.setExcludes() 除去logFile属性，避免对File类型进行Json化
	 */
	@Test
	public void test3(){
	
		Map<String, Object> response = new HashMap<String, Object>() ;
		List<String> list = new ArrayList<String>() ; 
		List<BiztraceFileInfo> files = new ArrayList<BiztraceFileInfo>() ; 
		
		list.add("lili") ;
		list.add("uiui") ;
		list.add("yiyi") ;
		list.add("titi") ;
		list.add("aiai") ;
		
		BiztraceFileInfo f1 = new BiztraceFileInfo() ; 
		f1.setFileName("filename1");
		f1.setFilePath("patch");
		f1.setFileSize(123);
		f1.setLastModifedTime("2016-01-01 13:23:32 123");
		f1.setLogFile( new File("文件名称") ); //File 对象转化为Json时会报错 net.sf.json.JSONException: There is a cycle in the hierarchy!
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"logFile"});//过滤掉File类型
		
		files.add(f1) ; 
		
		response.put("files", files) ;
		response.put("list", list) ;
		response.put("retcode", "00000") ;
		response.put("message", "成功") ;
		response.put("success", true) ;
		
		String json = JSONArray.fromObject(response,jsonConfig).toString() ;
		System.out.println("过滤掉File类型===>"+json);
	}
	
	
	/**
	 * 对File类型转化为Json时会报错 net.sf.json.JSONException: There is a cycle in the hierarchy!
	 * 方法2: 使用 jsonConfig.registerJsonValueProcessor() 指定File对象的转化规则
	 */
	@Test
	public void test4(){
		
		Map<String, Object> response = new HashMap<String, Object>() ;
		List<String> list = new ArrayList<String>() ; 
		List<BiztraceFileInfo> files = new ArrayList<BiztraceFileInfo>() ; 
		
		list.add("lili") ;
		list.add("uiui") ;
		list.add("yiyi") ;
		list.add("titi") ;
		list.add("aiai") ;
		
		BiztraceFileInfo f1 = new BiztraceFileInfo() ; 
		f1.setFileName("filename1");
		f1.setFilePath("patch");
		f1.setFileSize(123);
		f1.setLastModifedTime("2016-01-01 13:23:32 123");
		f1.setLogFile( new File("文件名称") ); //File 对象转化为Json时会报错 net.sf.json.JSONException: There is a cycle in the hierarchy!
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(File.class,new JsonFileProcessor());//指定File类型的转化形式
		
		files.add(f1) ; 
		
		response.put("files", files) ;
		response.put("list", list) ;
		response.put("retcode", "00000") ;
		response.put("message", "成功") ;
		response.put("success", true) ;
		
		String json = JSONArray.fromObject(response,jsonConfig).toString() ;
		System.out.println("指定File类型的转化形式===>"+json);
	}

}
