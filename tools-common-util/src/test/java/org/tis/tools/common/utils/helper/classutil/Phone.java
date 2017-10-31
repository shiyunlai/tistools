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
	 * 
	 */
	private static final long serialVersionUID = 5469732463694203824L;
	
	/**
	 * 号码
	 */
	private String no;
	/**
	 * 运营商
	 */
	private String telecom;

	public Phone() {
		
	}
	
	public Phone(String telecom, String no){
		this.no = no ; 
		this.telecom = telecom ; 
	}
	
	public String getNo() {
		return no;
	}

	public Phone setNo(String no) {
		this.no = no;
		return this ; 
	}

	public String getTelecom() {
		return telecom;
	}

	public Phone setTelecom(String telecom) {
		this.telecom = telecom;
		return this ; 
	}
}
