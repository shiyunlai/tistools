/**
 * 
 */
package org.tis.tools.common.utils;

import java.util.List;

import org.junit.Test;
import org.tis.tools.common.utils.helper.classutil.IAbc;
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
	
	/**
	 * 测试getAllClassByInterface()：查找接口的实现类
	 */
	@Test
	public void testGetAllClassByInterface(){
		
		List<Class<IAbc>> classes = ClassUtil.getAllClassByInterface(IAbc.class) ; 
		
		/*
		 * 在IAbc同包路径及子路径下只有三个IAbc的实现类
		 */
		Assert.assertEquals(5, classes.size()) ; 
		
		/*
		 * 就测试案例来说，实现类的package只能是 org.tis.tools.common.utils.helper.classutil
		 */
		String packagePath = "org.tis.tools.common.utils.helper.classutil" ; 
		Assert.assertEquals(packagePath, classes.get(0).getName().substring(0, packagePath.length()));
		
		/*
		 * 展示一下有哪些实现类
		 */
		for( Class c : classes ){
			
			System.out.println(c.getName());
		}
	}
	
	/**
	 * 测试getAllClassByInterface()：查找抽象类的子类 
	 */
	@Test
	public void testGetAllClassByInterface2(){
		
		List<Class<User>> classes = ClassUtil.getAllClassByInterface(User.class) ; 
		
		/*
		 * 与User同包路径及子路径下只有2个User的实现类
		 */
		Assert.assertEquals(2, classes.size()) ; 
	}
	
	/**
	 * 测试getAllClassByInterface(class, path)：指定路径下查找接口的实现类
	 */
	@Test
	public void testGetAllClassByInterface3() {

		String packagePath = ClassUtil.class.getPackage().getName();
		System.out.println(packagePath);

		// 在 org.tis.tools.common.utils 目录及子目录下查找
		List<Class<IAbc>> classes = ClassUtil.getAllClassByInterface(IAbc.class, packagePath);
		
		/*
		 * 总共有7个
		 */
		Assert.assertEquals(7, classes.size());
	}
	
}
