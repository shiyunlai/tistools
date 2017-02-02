/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author megapro
 *
 */
@XmlRootElement(name = "model_properties")
public class ModelProperties {

	/**
	 * 模型属性
	 */
	@XmlElement(name = "model_property")
	private List<ModelProperty> modelProperties = new ArrayList<ModelProperty>();
	
	@XmlTransient
	public List<ModelProperty> getModelProperties() {
		return modelProperties;
	}

	public void setModelProperties(List<ModelProperty> modelProperties) {
		this.modelProperties = modelProperties;
	}
}
