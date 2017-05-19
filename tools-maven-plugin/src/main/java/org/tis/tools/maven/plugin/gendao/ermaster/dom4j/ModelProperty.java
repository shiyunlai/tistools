/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster.dom4j;

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
	
	/**
	 * 属性项名称<br/>
	 * ERMaster中能定义的属性项范围见{@link ModelPropertyEnum}
	 */
	private String name ;
	
	private String value ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		
		if( StringUtils.isEmpty(value) ){
			return "" ;
		}
		
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((ModelProperty)obj).getName().equals(this.getName()) ;
	}

	@Override
	public String toString() {
		return "ModelProperty [name=" + name + ", value=" + value + "]";
	}
}
