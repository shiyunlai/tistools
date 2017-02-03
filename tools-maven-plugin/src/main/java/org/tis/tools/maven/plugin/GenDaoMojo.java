/**
 * 
 */
package org.tis.tools.maven.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.tis.tools.maven.plugin.exception.GenDaoMojoException;
import org.tis.tools.maven.plugin.gendao.BizModel;
import org.tis.tools.maven.plugin.gendao.GenDAOManager;
import org.tis.tools.maven.plugin.gendao.Model;
import org.tis.tools.maven.plugin.gendao.TemplateType;
import org.tis.tools.maven.plugin.gendao.ermaster.ERMasterDefinition;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.ERMasterModel;
import org.tis.tools.maven.plugin.utils.CommonUtil;
import org.tis.tools.maven.plugin.utils.FileUtil;
import org.tis.tools.maven.plugin.utils.KeyWordUtil;
import org.tis.tools.maven.plugin.utils.Xml22BeanUtil;

/**
 * <pre>
 * 生成DAO代码
 * 命令：
 * $mvn bronsp:gen-dao -Dmodel-file=model-org.xml 
 * 
 * 约定模型文件存放在当前工程 model路径下， 如： bronsp-service-org/model/model-org.xml；
 * 
 * 
 * </pre>
 * @author megapro
 *
 */
@Mojo( name = "gen-dao" )
public class GenDaoMojo extends AbstractMojo {
	
	/**
	 * (取自当前工程 POM.xml)工程名称，规定同artifactId
	 */
	@Parameter( defaultValue = "${project.artifactId}")
	private String projectName ; 
	
	/**
	 *  (取自当前工程 POM.xml)建议groupId命名含义包括：公司/组织.产品，如： org.fone.bronsp
	 */
	@Parameter( defaultValue = "${project.groupId}")
	private String groupId ; 
	
	/**
	 *  (取自当前工程 POM.xml)artifactId
	 */
	@Parameter( defaultValue = "${project.artifactId}")
	private String artifactId ; 
	
	/**
	 *  (根据sourceDirect推出)工程路径
	 */
	private String projectDirect ; 
	
	/**
	 *  (取自当前工程 POM.xml)工程源码路径
	 */
	@Parameter( defaultValue = "${project.build.sourceDirectory}")
	private String sourceDirect ; 

	/**
	 *  (取自当前工程 POM.xml)工程资源路径
	 */
	//TODO 为什么获取不到 ${project.build.resources.resource.directory} ？ 
	@Parameter( defaultValue = "${project.build.resources.resource.directory}")
	private String resourcesDirect ; 
	
	
	/**
	 * 指定主package路径
	 * <br/>如：-Dmain.package=org.fone.bronsp
	 * <br/>不指定，系统默认取 ${project.groupId}
	 * <br/>系统自动过滤并转换java保留字 见 {@link KeyWordUtil}
	 * <br/>系统根据生成的源码类型增加“功能模块划分”
	 * <br/>包命名规范为： 公司/组织 . 产品 . 功能模块划分 . 功能类型限定 . 业务域
	 */
	@Parameter( property = "main.package")
    private String mainPackage;
	
	/**
	 * <br/>模型定义文件路径 -Dmodel.file.path
	 * <br/>通过指定全路径，告知程序模型定义文件位置；
	 * <br/>不指定，系统默认为当前工程主路径下 model/ 目录，如： bronsp-service-org/model/
	 */
	@Parameter( property = "model.file.path" )
	private String modelFilePath ; 
	
	
	/**
	 * <br/>指定freemarker模版位置
	 * <br/>不指定，则默认在 bronsp-develop-assembly工程 gendao/templates 目录下 （问题一，见README.md）
	 * <br/>
	 */
	@Parameter( property = "templates.path" )
	private String templatesPath ; 
	
	/**
	 * <br/>模型定义文件类型 -Dmodel.file.type
	 * <br/>一共两种：xml erm
	 * <br/>不指定时默认为：xml
	 * <br/>如： user.xml 只需要 -Dmodel.file=user 即可；
	 * <br/>如果，指定了 -Dmodel.file.type=erm ， 则通过-Dmodel.file=user指定的文件会被识别为 user.erm
	 * <br/>如果，指定了 -Dmodel.file.type=xml ， 则通过-Dmodel.file=user指定的文件会被识别为 user.xml
	 * <br/>但是不支持两种一起指定 不允许 -Dmodel.file.type=xml,erm，将按默认处理
	 */
	@Parameter( property = "model.file.type" ,defaultValue=FILE_SUFFIX_XML)
	private String modelFileType ; 
	
	public static final String FILE_SUFFIX_XML = "xml" ; 
	public static final String FILE_SUFFIX_ERM = "erm" ; 
	
	/**
	 * <br/>模型定义文件名 -Dmodel.file
	 * <br/>如果不指定，系统默认将modelFilePath目录下所有 *.xml 模型定义全部生成一遍；
	 * <br/>建议指定模型文件名；
	 * <br/>无需指定后缀名，如： user.xml 只需要 -Dmodel.file=user 即可；
	 */
	@Parameter( property = "model.file" )
	private String modelFileName ; 
	
	/**
	 * 待生成DAO源码的模型定义文件
	 */
	private List<File> modelDefFiles = new ArrayList<File>() ; 
	
	/**
	 * <br/>指定生成哪些模型 -Dfixed.models
	 * <br/>如果不指定，默认所有模型对象都会生成一遍；
	 * <br/>一次指定多个模型对象（通过模型的id指定）时用逗号分隔；
	 * <br/>如： mvn bronsp:gendao -Dfixed.models=orgInfo,roleInfo
	 */
	@Parameter( property = "fixed.models" )
	private String fixedModels ; 
	
	/**
	 * 需要生成DAO代码的业务域模型们
	 */
	private List<BizModel> bizModelList = new ArrayList<BizModel>() ; 
	
	/**
	 * <pre>
	 * 指定生成哪些源码类型
	 * 当前可选类型有：
	 * 	ddl 数据库脚本，目前生成mysql脚本
	 * 	model 模型源码，对应数据库表记录PO，对应界面展示对象VO，对应服务传输对象DTO； 
	 * 	dao 数据访问层，基于mysql，进行业务域内数据的存取操作； 
	 * 	biz 业务逻辑层，业务域内的业务处理逻辑实现；
	 * 	controller 控制层，处理ui请求；
	 * 	ui 界面层，提供交互操作能力; 
	 * 	service 服务层，基于dubbo实现，作为服务提供者对外提供服务能力; 
	 * 多种类型逗号分隔，如： -Dgen.type=model,ui,service 将生成model、ui、service层的源码
	 * 不指定则全部生成
	 * </pre>
	 */
	@Parameter( property = "gen.type" , defaultValue="ddl,model,dao,biz,controller,ui,service") 
	private String  genType;
	
	private List<String> genTypes = new ArrayList<String>() ; 
	
	
	/* (non-Javadoc)
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("generate DAO source code for project :"+projectName);
		preGenerateConfiguration() ;
		generateDaoSourceCode() ;
	}

	
	/**
	 * <br/>生成代码前的准备工作
	 * <br/>初始化各种变量，检查生成代码所需的各种资源是否齐备
	 * @throws MojoExecutionException 
	 */
	private void preGenerateConfiguration() throws MojoExecutionException {
		
		
		/*
		 * 工程路径
		 * 根据 sourceDirect 推演出 工程主路径 
		 * 如： sourceDirect="/Users/megapro/Develop/brons/bronsp/bronsp-service-org/src/main/java"
		 * 则，工程主路径为 /Users/megapro/Develop/brons/bronsp/bronsp-service-org/
		 */
		if( null != sourceDirect && !"".equals(sourceDirect) ){
			projectDirect =  sourceDirect.substring(0,sourceDirect.indexOf("src")) + "/"; 
		}else{
			throw new GenDaoMojoException("工程<"+projectName+">缺少源码路径!") ; 
		}
		
		/*
		 * 源码存放路径，补充为／结尾
		 */
		sourceDirect = sourceDirect + "/" ; 

		/*
		 * 资源存放路径，补充为／结尾
		 */
		if( StringUtils.isEmpty(resourcesDirect) ){
			//没有则默认 src/main/resources/ 目录
			resourcesDirect = sourceDirect.substring(0, sourceDirect.indexOf("java/")) + "resources/" ; 
		}else{
			resourcesDirect = resourcesDirect + "/" ; 
		}
		
		/*
		 * 准备 package
		 * 如果不指定，默认：${project.groupId}.${project.artifactId}
		 */
		String defMainPackage = null ; 
		if ( StringUtils.isEmpty(mainPackage) ) {//未通过-Dmain.package指定包路径
			defMainPackage = CommonUtil.normPackageName(groupId);
			getLog().debug("default package is :"+defMainPackage);
		}else{
			mainPackage = CommonUtil.normPackageName(mainPackage) ; 
			getLog().debug("fixed package is :"+mainPackage);
		}
		
		/*
		 * 代码模版位置
		 */
		if( null == templatesPath || "".equals(templatesPath) ){
			//URL url = GenDaoMojo.class.getClassLoader().getResource("/META-INF/templates/biz/") ;
			//templatesPath = projectDirect+"../bronsp-develop-assembly/gendao/templates/biz/" ; 
			getLog().debug("默认源码模版路径");
		}else{
			if( ! new File(templatesPath).exists() ){
				throw new GenDaoMojoException("指定的模版文件路径<"+templatesPath+">不存在!") ; 
			}
			getLog().debug("指定源码模版路径："+ templatesPath);
		}
		
		/*
		 * 模型定义文件存放路径
		 */
		if( null == modelFilePath || "".equals(modelFilePath) ){
			modelFilePath = projectDirect + "model/" ; 
			getLog().debug("默认模型定义文件存放路径："+ modelFilePath);
		}else{
			if( ! new File(modelFilePath).exists() ){
				throw new GenDaoMojoException("模型定义文件路径<"+modelFilePath+">不存在!") ; 
			}
			getLog().debug("指定模型定义文件存放路径："+ modelFilePath);
		}
		
		/*
		 * 收集需要生成DAO源码的模型定义文件
		 */
		if( null == modelFileName || "".equals(modelFileName) ){
			getLog().warn("未指定模型文件时，默认处理目录下所有");
			if( StringUtils.equals(modelFileType, FILE_SUFFIX_XML)){
				modelDefFiles = FileUtil.listFilesBySuffix(new File(modelFilePath), "."+FILE_SUFFIX_XML) ; 
			}else if( StringUtils.equals(modelFileType, FILE_SUFFIX_ERM) ){
				modelDefFiles = FileUtil.listFilesBySuffix(new File(modelFilePath), "."+FILE_SUFFIX_ERM) ; 
			}else{
				throw new GenDaoMojoException("不支持后缀<"+modelFileType+">，请指定有效的模型文件后缀！如：-Dmodel.file.type=xml/erm") ; 
			}
		}else{
			
			String ffile = null ; 
			if( StringUtils.equals(modelFileType, FILE_SUFFIX_XML) ){
				ffile = modelFilePath + modelFileName + "."+FILE_SUFFIX_XML ; 
			}else if( StringUtils.equals(modelFileType, FILE_SUFFIX_ERM) ){
				ffile = modelFilePath + modelFileName + "."+FILE_SUFFIX_ERM ; 
			}else{
				throw new GenDaoMojoException("不支持后缀<"+modelFileType+">，请指定有效的模型文件后缀！如：-Dmodel.file.type=xml/erm") ; 
			}
			
			getLog().debug("指定了模型定义文件名称:"+ffile);
			if( ! new File(ffile).exists() ){
				throw new GenDaoMojoException("模型定义文件<"+ffile+">不存在!") ; 
			}
			
			modelDefFiles.add(new File(ffile) ) ;
		}
		
		if( !(modelDefFiles.size() > 0) ){
			getLog().warn("没有发现任何模型定义文件!" + modelFilePath);
		}
		
		/*
		 * 解析模型定义文件
		 */
		if( StringUtils.equals(modelFileType, FILE_SUFFIX_XML) ){
			//用xml定义模型
			for( File defFile : modelDefFiles ){
				
				BizModel bm = Xml22BeanUtil.xml2Bean(BizModel.class, defFile) ; 
				bm.setModelDefFile(defFile.getPath()) ; 
				
				perMainPackage4BizModel(defMainPackage, bm);
				
				bizModelList.add( bm ) ;
			}
		}
		
		if( StringUtils.equals(modelFileType, FILE_SUFFIX_ERM) ){
			//用ERMaster定义模型
			for( File defFile : modelDefFiles ){
				
				ERMasterModel ermm = new ERMasterModel(defFile) ; 
				
				BizModel bm = new ERMasterDefinition(ermm).getBizModel() ;
				
				perMainPackage4BizModel(defMainPackage, bm);
				
				bizModelList.add( bm ) ;
			}
		}
		
		/*
		 * 过滤掉指定之外的模型
		 */
		if( StringUtils.isNotEmpty(fixedModels) ){
			
			String[] includes = fixedModels.split("\\,");
			
			for( String fixedModelName : includes ){
				
				for( BizModel bm : bizModelList ){
				
					List<Model> newModels = new ArrayList<Model>();
					for( Model m : bm.getModels() ){
						
						if( m.getId().equals(fixedModelName) ){
							newModels.add(m) ; 
						}else{
							// 过滤掉没指定的模型
						}
					}
					bm.setModels(newModels);
				}
			}
		}
		
		/*
		 * 生成哪些源码
		 */
		String [] k = genType.split("\\,") ; 
		genTypes.clear() ;
		for( String s : k ){
			if( StringUtils.equals(modelFileType, FILE_SUFFIX_ERM) && s.equals("ddl") ){
				continue ; //ERMaster定义模型时，直接用ERMaster的能力生成sql，此处不生成ddl
			}
			genTypes.add(s) ;
		}
		
		/*
		 * 参数准备完成，显示一下各参数值
		 */
		showAllConfigurationValue() ; 
	}


	/**
	 * 为每个BizModel模型设置源码生成package包路径
	 * @param defMainPackage 缺省包路径
	 * @param bm
	 */
	private void perMainPackage4BizModel(String defMainPackage, BizModel bm) {
		
		//如果当前bm中已经有mainpackage信息，则无需设置
		if( StringUtils.isNotEmpty(bm.getMainpackage()) ){
			return ; 
		}
		
		//以-Dmain.package传入的主包路径为准
		if( StringUtils.isNotEmpty(mainPackage) ) {
			bm.setMainpackage(mainPackage);
		}
		
		//如果model.xml和－D都未设置，则使用默认包路径
		if( StringUtils.isEmpty(bm.getMainpackage()) ){
			bm.setMainpackage(defMainPackage);
		}
	}
	
	private void showAllConfigurationValue() {
		getLog().info("======================= gen-dao info ======================");
		getLog().info("工程名称:"+projectName); 
		getLog().info("工程路径:"+projectDirect); 
		getLog().info("模型定义路径:"+modelFilePath); 
		if(!StringUtils.isEmpty(fixedModels)) { getLog().info("指定生成模型范围:"+fixedModels); }
		getLog().info("生成源码的模型包括:"+showModelList(bizModelList));
		getLog().info("源码的主package:"+mainPackage);
		getLog().info("生成源码类型包括:"+genType);
		getLog().info("===========================================================");
	}
	
	
	private String showModelList(List<BizModel> bizModelList) {
		StringBuffer sb = new StringBuffer() ; 
		sb.append("\n");
		for ( BizModel bm : bizModelList ){
			sb.append(bm.toString()) ;
		}
		return sb.toString();
	}

	
	/**
	 * 开始生成源码
	 * 
	 * @throws GenDaoMojoException
	 *             生成源码失败时抛出异常，终止后续处理。
	 */
	private void generateDaoSourceCode() throws GenDaoMojoException {
		
		getLog().info("atuo generate source code start.....") ; 
		
		//初始化freemarker模版
		if( StringUtils.isEmpty( templatesPath )){
			GenDAOManager.instance.initWithDef(TemplateType.BIZ); 
		}else{
			GenDAOManager.instance.initWithFixed(templatesPath);
		}
		
		if( genTypes.contains("ddl") ){
			//生成数据库脚本
			GenDAOManager.instance.genDDL(bizModelList, resourcesDirect, sourceDirect);
		}
		
		if( genTypes.contains("model") ){
			
			//生成模型PO、VO、DTO
			GenDAOManager.instance.genModel(bizModelList, resourcesDirect, sourceDirect);
		}
		
		if( genTypes.contains("dao") ){
			
			//生成dao层代码
			GenDAOManager.instance.genDAO(bizModelList, resourcesDirect, sourceDirect);
		}
		
		if( genTypes.contains("biz") ){
			
			//生成biz业务层代码(Service，Remote Service)
			GenDAOManager.instance.genBiz(bizModelList, resourcesDirect, sourceDirect);
		}
		
		if( genTypes.contains("controller") ){
			
			//生成controller代码
			GenDAOManager.instance.genController(bizModelList, resourcesDirect, sourceDirect);
		}

		if( genTypes.contains("ui") ){
			
			//TODO 生成ui代码 
		}
		
		if( genTypes.contains("service") ){
			
			//TODO 生成提供者serveice代码 
		}
		
		getLog().info("auto generate source code finish!") ; 
	}

}
