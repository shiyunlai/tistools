/**
 * 
 */
package org.tis.tools.maven.plugin.gendao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tis.tools.maven.plugin.exception.GenDaoMojoException;
import org.tis.tools.maven.plugin.gendao.api.ASourceCodeGenerator;
import org.tis.tools.maven.plugin.gendao.impl.ControllerGenerator;
import org.tis.tools.maven.plugin.gendao.impl.DDLGenerator;
import org.tis.tools.maven.plugin.gendao.impl.MapperJavaGenerator;
import org.tis.tools.maven.plugin.gendao.impl.MapperXmlGenerator;
import org.tis.tools.maven.plugin.gendao.impl.POGenerator;
import org.tis.tools.maven.plugin.gendao.impl.RServiceGenerator;
import org.tis.tools.maven.plugin.gendao.impl.ServiceGenerator;
import org.tis.tools.maven.plugin.utils.CommonUtil;
import org.tis.tools.maven.plugin.utils.FreeMarkerUtil;


/**
 * 
 * DAO代码生成功能Manager类
 * 
 * @author megapro
 *
 */
public class GenDAOManager {

	public static final GenDAOManager instance = new GenDAOManager() ;
	
	private GenDAOManager() {
	}
	
	/**
	 * 使用默认配置模版
	 * @param templateType
	 */
	public void initWithDef(TemplateType templateType ) {
		FreeMarkerUtil.initDefTemplate(templateType.getPath());
	}

	/**
	 * 使用指定模版
	 * @param templatePath
	 * @throws GenDaoMojoException
	 */
	public void initWithFixed(String templatePath ) throws GenDaoMojoException {
		FreeMarkerUtil.initFixedTemplate(templatePath);
	}
	
	
	/**
	 * <br/>生成指定模型的建表脚本
	 * <br/>每个业务域生成一个.sql文件
	 * <br/>默认生成脚本存放在：工程路径{projectPath}/gendao/sql/ 目录下
	 * @param bizModelList 业务域模型定义
	 * @param resourcesDirect 资源路径
	 * @param sourcePath 源码存放路径
	 * @throws GenDaoMojoException 
	 */
	public void genDDL(List<BizModel> bizModelList,String resourcesDirect, String sourcePath) throws GenDaoMojoException {
		
		ASourceCodeGenerator<BizModel> generator = new DDLGenerator() ; 
		generator.genSourceCode(bizModelList, resourcesDirect, sourcePath);
	}
	
	
	/**
	 * 生成模型的PO、VO、DTO等模型对象（Persistant Object、View Object、Data Transfer Object）
	 * <br/>所有模型均生成在当前工程路径下
	 * <br/>
	 * @param bizModelList 业务域模型们
	 * @param resourcesDirect 资源路径
	 * @param sourcePath 源码存放路径
	 * @throws GenDaoMojoException
	 */
	public void genModel(List<BizModel> bizModelList, String resourcesDirect, String sourcePath) throws GenDaoMojoException {
		
		// 生成模型的PO对象(Persistant Object)
		ASourceCodeGenerator<BizModel> generator = new POGenerator() ; 
		generator.genSourceCode(bizModelList, resourcesDirect, sourcePath);
		
		//TODO * 生成模型的VO对象（Value Object）
		
		//TODO * 生成模型的DTO对象（Data Transfer Object）
	}
	
	
	/**
	 * <br/>生成模型的DAO数据访问处理类（Data Access Object）
	 * <br/>包括：<br/>XXXMapper.java <br/>XXXMapper.xml
	 * @param bizModelList 业务模型们
	 * @param resourcesDirect 资源路径
	 * @param sourcePath 源码路径
	 * @throws GenDaoMojoException
	 */
	public void genDAO(List<BizModel> bizModelList, String resourcesDirect, String sourcePath) throws GenDaoMojoException {
		
		ASourceCodeGenerator<BizModel> generator = new MapperJavaGenerator() ; 
		generator.genSourceCode(bizModelList, resourcesDirect, sourcePath);
		
		generator = new MapperXmlGenerator() ; 
		generator.genSourceCode(bizModelList, resourcesDirect, sourcePath);
	}
	
	
	/**
	 * 生成Biz业务逻辑类（Spring MVC实现，@Service）
	 * @param bizModelList
	 * @param projectPath
	 * @param sourcePath
	 * @throws GenDaoMojoException 
	 */
	public void genBiz(List<BizModel> bizModelList, String projectPath, String sourcePath) throws GenDaoMojoException {
		
		//生成 Service 代码
		ASourceCodeGenerator<BizModel> generator = new ServiceGenerator() ; 
		generator.genSourceCode(bizModelList, projectPath, sourcePath);
		
		//生成 Remote Service 代码
		ASourceCodeGenerator<BizModel> generatorr = new RServiceGenerator() ; 
		generatorr.genSourceCode(bizModelList, projectPath, sourcePath);
	}
	
	
	/**
	 * <br/>生成模型的Controller类（Spring MVC实现，@Controller）
	 * <br/>每个模型独立一个controller类
	 * @param bizModelList 业务模型们
	 * @param projectPath 工程路径
	 * @param sourcePath 源码路径
	 * @throws GenDaoMojoException 
	 */
	public void genController(List<BizModel> bizModelList, String projectPath, String sourcePath) throws GenDaoMojoException {
		ASourceCodeGenerator<BizModel> generator = new ControllerGenerator() ; 
		generator.genSourceCode(bizModelList, projectPath, sourcePath);	
	}
	
	
	/**
	 * 生成dubbo的服务类Service（Dubbo实现，自动注册为Dubbo服务）
	 * @param genDaoConf
	 */
	public void genDubboService(Object genDaoConf) {
		//todo ... XXXService.java
	}
	
	/**
	 * <br/>生成UI相关的资源(基于AngularJS实现前端MVC)
	 * <br/>包括：View、Controller.js、Service.js
	 * 
	 * @param genDaoConf
	 */
	public void genUI(Object genDaoConf) {
		//todo ... 
	}
}
