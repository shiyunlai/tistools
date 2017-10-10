/**
 * 
 */
package org.tis.tools.common.utils.helper.classutil;

import java.io.Serializable;

/**
 * @author megapro
 *
 */
public class Phone implements IAbc,Serializable {

	/**
	 * 号码
	 */
	private String no;
	/**
	 * 运营商
	 */
	private String telecom;

	public Phone(String telecom, String no){
		this.no = no ; 
		this.telecom = telecom ; 
	}
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getTelecom() {
		return telecom;
	}

	public void setTelecom(String telecom) {
		this.telecom = telecom;
	}
}
