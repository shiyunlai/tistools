/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * 
 * 模型属性信息(可手工修改.erm文件增加属性)
 * 
 * 在.erm文件中的Xpath位置为： settings/model_properties/model_properties/model_property
 * 
 &ltsettings>
	 &ltmodel_properties>
		 &ltmodel_properties>
			....
			&ltmodel_property>
				&ltname>Project Name</name>
				&ltvalue>branchplus</value>
			&lt/model_property>
			
			....repeating....
			
		&lt/model_properties>
	 &lt/model_properties>
 &lt/settings>
 * </pre>
 * @author megapro
 *
 */
public class ModelProperty {
	
	/** 模型属性定义： 工程名称 */
	public static final String MP_PROJECT_NAME = "Project Name" ;
	/** 模型属性定义： 模型名称 */
	public static final String MP_MODEL_NAME = "Model Name" ;
	/** 模型属性定义： 版本 */
	public static final String MP_VERSION = "Version" ;
	/** 模型属性定义： 公司 */
	public static final String MP_COMPANY = "Company" ;
	/** 模型属性定义： 作者 */
	public static final String MP_AUTHOR = "Author" ;
	/** 模型属性定义： 核心工程名称 */
	public static final String MP_PRJ_CODE = "prjCore" ;
	/** 模型属性定义： web工程名称 */
	public static final String MP_PRJ_WEB = "prjWeb" ;
	/** 模型属性定义： 接口定义工程名称 */
	public static final String MP_PRJ_FACADE = "prjFacade" ;
	/** 模型属性定义： 服务实现工程名称 */
	public static final String MP_PRJ_SERVICE = "prjService" ;
	
	@XmlElement(name="name",required=false)
	private String name ;
	
	@XmlElement(name="value")
	private String value ;

	@XmlTransient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
	public String getValue() {
		
		if( StringUtils.isEmpty(value) ){
			return "" ;
		}
		
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
