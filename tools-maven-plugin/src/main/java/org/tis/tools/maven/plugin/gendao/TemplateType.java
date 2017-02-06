/**
 * 
 */
package org.tis.tools.maven.plugin.gendao;

/**
 * 
 * 源码模版类型
 * 
 * @author megapro
 *
 */
public enum TemplateType {
	
	BIZ   ("biz",    "/META-INF/templates/biz/") , 
	ACTION("action", "/META-INF/templates/action/") ,
	UI    ("ui",     "/META-INF/templates/ui/") ,

	BIZ_ERM   ("biz4erm",    "/META-INF/templates4erm/biz/") , 
	ACTION_ERM("action4erm", "/META-INF/templates4erm/action/") ,
	UI_ERM    ("ui4erm",     "/META-INF/templates4erm/ui/") 
	
	; 
	
	//默认模版位置
	private String path ; 
	//模版类型
	private String type ; 
	
	private TemplateType(String type, String path){
		this.path = path ; 
		this.type = type ; 
	}
	
	public String getType() {
		return this.type ; 
	}
	
	public String getPath(){
		return this.path ; 
	}
}

