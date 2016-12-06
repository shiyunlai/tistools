/**
 * 
 */
package org.tis.tools.webapp.controller;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tis.tools.base.web.util.JSONUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

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

}
