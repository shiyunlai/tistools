/**
 * 
 */
package org.tis.tools.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.tis.tools.common.utils.helper.classutil.Phone;
import org.tis.tools.common.utils.helper.classutil.User;

import junit.framework.Assert;

/**
 * @author megapro
 *
 */
public class ClassUtilTest {

	@Test
	public void testNewInstanceByClassT() {

		String userClass = "org.tis.tools.common.utils.helper.classutil.User";

		User u = null;
		try {
			u = ClassUtil.newInstance(User.class);
			u.setAge(1);
			u.setName("启元");
			u.addEmail("shi.jinming@foxmail.com");
			u.addEmail("shi.yunlai@foxmail.com");
			u.addPhone(new Phone("联通", "18616559102"));
			u.addPhone(new Phone("电信", "17717889102"));
			u.addPhone(new Phone("移动", "13717669102"));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull(u);
		Assert.assertEquals(userClass, u.getClass().getName());
		
		Assert.assertEquals("启元", u.getName());
		
		Assert.assertEquals(2, u.getEmails().size());
		Assert.assertEquals("shi.jinming@foxmail.com", u.getEmails().get(0));
		
		Assert.assertEquals(3, u.getPhone().size());
		Assert.assertEquals("移动",u.getPhone().get("13717669102").getTelecom());
		Assert.assertEquals("18616559102",u.getPhone().get("18616559102").getNo());
	}

}
