/**
 * 
 */
package org.tis.tools.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tis.tools.common.utils.helper.classutil.Phone;
import org.tis.tools.common.utils.helper.classutil.User;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import junit.framework.Assert;

/**
 * 
 * FormattingUtil 工具类的单元测试 
 * 
 * @author megapro
 *
 */
public class FormattingUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" =================== FormattingUtilTest begin ==================== ");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println(" =================== FormattingUtilTest finish =================== ");
	}

	@Test
	public void test() {
		
		User u = new User() ; 
		u.setAge(27);
		u.setName("阳光");
		List<String> emails = new ArrayList<String>();
		emails.add("shi.yunlai@foxmail.com") ; 
		emails.add("shi.yunlai@gmail.com") ; 
		emails.add("shi.yunlai@chinamworld.com") ; 
		u.setEmails(emails);
		
		Map<String, Phone> phones = new HashMap<String, Phone>() ; 
		Phone p1 = new Phone().setNo("186-1655-9102").setTelecom("联通") ; 
		Phone p2 = new Phone().setNo("186-1655-9103").setTelecom("电信") ; 
		Phone p3 = new Phone().setNo("186-1655-9104").setTelecom("移动") ; 
		phones.put("工作号码", p1 ) ;
		phones.put("家庭号码", p2 ) ;
		phones.put("游戏号码", p3 ) ;
		
		u.setPhone(phones);
		
		String uJson = FormattingUtil.instance().toJsonString(u) ; 
		System.out.println(uJson);
		System.out.println(uJson.length());
		
		System.out.println(FormattingUtil.instance().formatJsonString(uJson));
		
		//借助fastjson对uJson的结构进行检查，是否满足JSON结构
		JSONObject jsonObject = JSONObject.parseObject(uJson) ; 
		Assert.assertEquals("阳光", jsonObject.get("name"));
		Assert.assertEquals(27, jsonObject.get("age"));
		
		JSONArray emailJson = jsonObject.getJSONArray("emails") ; 
		Assert.assertTrue(emailJson instanceof JSONArray);
		Assert.assertEquals(3, emailJson.size());
		Assert.assertEquals("shi.yunlai@foxmail.com", emailJson.get(0));
		
		JSONObject phoneJson = jsonObject.getJSONObject("phone") ; 
		Assert.assertTrue(phoneJson.getJSONObject("工作号码") instanceof JSONObject);
		Assert.assertTrue(phoneJson.getJSONObject("游戏号码") instanceof JSONObject);
		
		JSONObject homePhoneJson = phoneJson.getJSONObject("家庭号码") ; 
		Assert.assertEquals("186-1655-9103", homePhoneJson.get("no")); 
		Assert.assertEquals("电信", homePhoneJson.get("telecom")); 
	}
}
