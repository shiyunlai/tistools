/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
@XmlRootElement(name = "model_property")
public class ModelProperty {
	
	/**
	 * 属性项名称<br/>
	 * ERMaster中能定义的属性项范围见{@link ModelPropertyEnum}
	 */
	@XmlElement(name="name",required=true)
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
