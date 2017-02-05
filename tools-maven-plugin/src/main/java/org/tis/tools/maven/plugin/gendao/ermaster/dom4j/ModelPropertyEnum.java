/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster.dom4j;

/**
 * 
 * ERMaster模型的属性项及其名称定义
 * 
 * @author megapro
 * 
 */
public enum ModelPropertyEnum {

	/** 模型属性定义： 工程名称 */
	MP_PROJECT_NAME("Project Name","工程名称") ,
	
	/** 模型属性定义： 模型名称 */
	MP_MODEL_NAME("Model Name","模型名称"),
	
	/** 模型属性定义： 版本 */
	MP_VERSION("Version","版本"),
	
	/** 模型属性定义： 公司 */
	MP_COMPANY("Company","公司"),
	
	/** 模型属性定义： 作者 */
	MP_AUTHOR("Author","作者"),
	
	/** 模型属性定义： 核心工程名称 */
	MP_PRJ_CODE("prjCore","核心工程名称"),
	
	/** 模型属性定义： web工程名称 */
	MP_PRJ_WEB("prjWeb","web工程名称"),
	
	/** 模型属性定义： 接口定义工程名称 */
	MP_PRJ_FACADE("prjFacade","接口定义工程名称"),

	/** 模型属性定义： 服务实现工程名称 */
	MP_PRJ_SERVICE("prjService","服务实现工程名称");

	private String name ;
	private String desc ; 
	private ModelPropertyEnum(String name, String desc){
		this.name = name ; 
		this.desc = desc ; 
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
