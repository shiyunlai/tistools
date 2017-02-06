package org.tis.tools.maven.plugin.gendao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;
import org.tis.tools.maven.plugin.gendao.api.IGenModelDefine;
import org.tis.tools.maven.plugin.utils.PinyinUtil;

/**
 * 
 * 某个业务域所有的模型定义（每个定义文件解析后形成一个BizModel）
 * 
 * @author megapro
 *
 */
@XmlRootElement(name = "bizmodel")
public class BizModel implements IGenModelDefine{

	/**
	 * 业务域ID必须设置
	 */
	@XmlAttribute(required=true)
	private String id;

	/**
	 * 业务域名称
	 */
	@XmlAttribute(required=false)
	private String name;
	
	/**
	 * 本业务域的package
	 * </br>主package路径
	 */
	@XmlAttribute(required=false)
	private String mainpackage ;
	
	/**
	 * 业务域描述信息
	 */
	@XmlElement(required=false)
	private String desc ; 
	
	/**
	 * web工程
	 */
	@XmlElement(name="prjWeb",required=false)
	private String prjWeb ; 
	/**
	 * 模型定义工程
	 */
	@XmlElement(name="prjCore",required=false)
	private String prjCore ; 
	/**
	 * 服务接口定义工程
	 */
	@XmlElement(name="prjFacade",required=false)
	private String prjFacade ; 
	/**
	 * 服务能力提供工程
	 */
	@XmlElement(name="prjService",required=false)
	private String prjService ; 
	 
	
	/**
	 * models模型所在的定义文件名
	 */
	private String modelDefFile ; 
	
	/**
	 * 某个业务域中的模型定义
	 */
	@XmlElementWrapper(name = "models")
	@XmlElement(name = "model")
	private List<Model> models = new ArrayList<Model>();
	
	@XmlTransient
	public String getName() {
		return name;
	}

	@XmlTransient
	public String getId() {
		if (StringUtils.isNotEmpty(id)) {
			return id;
		}
		return PinyinUtil.convert(name);
	}

	@XmlTransient
	public String getDesc() {
		return desc;
	}
	
	@XmlTransient
	public String getPrjWeb() {
		return prjWeb;
	}
	@XmlTransient
	public String getPrjCore() {
		return prjCore;
	}
	@XmlTransient
	public String getPrjFacade() {
		return prjFacade;
	}
	@XmlTransient
	public String getPrjService() {
		return prjService;
	}

	@XmlTransient	
	public List<Model> getModels() {
		return models;
	}

	@XmlTransient
	public String getMainpackage() {
		return mainpackage;
	}

	/**
	 * </br>主package路径
	 * @param mainpackage
	 */
	public void setMainpackage(String mainpackage) {
		this.mainpackage = mainpackage;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public void setPrjWeb(String prjWeb) {
		this.prjWeb = prjWeb;
	}
	public void setPrjCore(String prjCore) {
		this.prjCore = prjCore;
	}
	public void setPrjFacade(String prjFacade) {
		this.prjFacade = prjFacade;
	}
	public void setPrjService(String prjService) {
		this.prjService = prjService;
	}

	public void setModels(List<Model> models) {
		this.models = models;
	}
	
	public String getModelDefFile() {
		return this.modelDefFile ; 
	}
	
	public void setModelDefFile(String path) {
		this.modelDefFile =path;
	}
	
	public String toStringSimple(){
		StringBuffer sb = new StringBuffer();
		sb.append("id:").append(id);
		sb.append("\tdesc:").append(desc);
		sb.append("\tpackage:").append(mainpackage);
		sb.append("\tmodel file:").append(modelDefFile.substring(modelDefFile.indexOf(File.separator))).append("\n");
		sb.append("\t").append("prjCore: ").append(this.prjCore).append("\n") ;
		sb.append("\t").append("prjFacade: ").append(this.prjFacade).append("\n") ;
		sb.append("\t").append("prjService: ").append(this.prjService).append("\n") ;
		sb.append("\t").append("prjWeb: ").append(this.prjWeb).append("\n") ;
		return sb.toString() ; 
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer(toStringSimple());
		sb.append("\n\t") ;
		sb.append("包括如下模型定义：").append("\n") ;
		for( Model m : models ){
			sb.append("\t").append("-- ").append(m.toString()).append("\n"); 
		}
		return sb.toString();
	}
}
