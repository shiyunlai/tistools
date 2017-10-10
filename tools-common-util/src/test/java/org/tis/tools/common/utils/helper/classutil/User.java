/**
 * 
 */
package org.tis.tools.common.utils.helper.classutil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author megapro
 *
 */
public class User implements IAbc, Serializable{

	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 年龄
	 */
	private int age;
	/**
	 * 个人邮箱
	 */
	private List<String> emails = new ArrayList<String>();
	/**
	 * 手机号码
	 */
	private Map<String, Phone> phone = new HashMap<String, Phone>();

	public void addPhone( Phone p ){
		this.phone.put(p.getNo(), p) ; 
	}
	
	public void addEmail( String email ) {
		this.emails.add(email) ; 
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public Map<String, Phone> getPhone() {
		return phone;
	}

	public void setPhone(Map<String, Phone> phone) {
		this.phone = phone;
	}
}
