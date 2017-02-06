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
 * mvn tools:gen-dao -Dmodel-file=model-org.xml 
 * 
 * 约定规则：
 * 模型文件存放在当前工程 model路径下， 如： bronsp-service-org/model/model-org.xml；
 * 
 * </pre>
 * @author megapro
 *
 */
@Mojo( name = "gen-dao" )
public class GenDaoMojo extends AbstractMojo {
	
	/**
	 * (自动取值)当前工程POM.xml中的工程名称project.artifactId。
	 */
	@Parameter( defaultValue = "${project.artifactId}")
	private String projectName ; 
	
	/**
	 * (自动取值)当前工程POM.xml中的project.groupId。建议命名含义包括：公司/组织.产品，如： org.fone.bronsp
	 */
	@Parameter( defaultValue = "${project.groupId}")
	private String groupId ; 
	
	/**
	 * (自动取值)当前工程POM.xml中的project.artifactId。
	 */
	@Parameter( defaultValue = "${project.artifactId}" )
	private String artifactId ; 
	
	/**
	 *  (根据sourceDirect推出)工程路径
	 */
	private String projectDirect ; 
	
	/**
	 *  (自动取值)当前工程POM.xml中的工程源码路径project.build.sourceDirectory
	 */
	@Parameter( defaultValue = "${project.build.sourceDirectory}")
	private String sourceDirect ; 

	/**
	 *   (自动取值)当前工程POM.xml中的工程资源路径project.build.resources.resource.directory
	 */
	//TODO 为什么获取不到 ${project.build.resources.resource.directory} ？ 
	@Parameter( defaultValue = "${project.build.resources.resource.directory}")
	private String resourcesDirect ; 
	
	
	/**
	 * -Djust.show，只是暂时模型信息，不要生成源码<br/>
	 * <br/>如：-Djust.show=true
	 * <br/>不配置为false，会生成代码
	 */
	@Parameter( defaultValue = "${just.show}")
	private String justShow = "false"; 
	
	/**
	 * -Dmain.package，指定生成代码所在的主package路径<br/>
	 * <br/>如：-Dmain.package=org.fone.bronsp
	 * <br/>指定生成代码package的方法：
	 * <br/>1、-Dmain.package=指定包路径；
	 * <br/>2、以xml定义模型时，在settings/packageName节点指定；
	 * <br/>3、以ERMaster定义模式是，-Dmain.package=指定包路径
	 * <br/>4、以上1,2,3都不指定，系统默认使用当前工程groupId作为包路径
	 * <br/>注：
	 * <br/>系统自动过滤并转换包路径中的java保留字 见 {@link KeyWordUtil}
	 * <br/>系统根据生成的源码类型增加“功能模块划分”
	 * <br/>包命名规范为： ${main.package} . 功能模块划分 . 功能类型限定 . 业务域。如： org.tis.model.po.jnl
	 */
	@Parameter( property = "main.package")
    private String mainPackage;
	
	/**
	 * -Dmodel.file.path，指定模型文件的全路径，告知程序模型定义文件位置<br/>
	 * <br/>如：-Dmodel.file.path=/Users/megapro/Develop/tis/tools/tools-core/model/model.erm
	 * <br/>不指定，系统默认为当前工程主路径下 model/ 目录，如： bronsp-service-org/model/
	 */
	@Parameter( property = "model.file.path" )
	private String modelFilePath ; 
	
	
	/**
	 * -Dtemplates.path，指定freemarker模版位置（全路径）<br/>
	 * <br/>如：-Dtemplates.path=/Users/megapro/Develop/tis/tools/tools-core/model/templates4erm/biz
	 * <br/>不指定，则默认在 bronsp-develop-assembly工程 gendao/templates 目录下 （问题一，见README.md）
	 */
	@Parameter( property = "templates.path" )
	private String templatesPath ; 
	
	/**
	 * -Dmodel.file.type，告知程序处理那种类型的模型定义文件。<br/>
	 * <br/>如：-Dmodel.file.type=xml
	 * <br/>目前支持两种类型：xml(默认)，erm
	 * <br/>如果，指定了 -Dmodel.file.type=erm ， 则通过-Dmodel.file=user指定的文件会被识别为 user.erm
	 * <br/>如果，指定了 -Dmodel.file.type=xml ， 则通过-Dmodel.file=user指定的文件会被识别为 user.xml
	 * <br/>注：
	 * <br/>不支持两种一起指定，如：不允许 -Dmodel.file.type=xml,erm （此时，将按默认处理）
	 */
	@Parameter( property = "model.file.type" ,defaultValue=FILE_SUFFIX_XML)
	private String modelFileType ; 
	
	public static final String FILE_SUFFIX_XML = "xml" ; 
	public static final String FILE_SUFFIX_ERM = "erm" ; 
	
	/**
	 *  -Dmodel.file，指定模型定义文件名<br/>
	 *  
	 * <br/>如果不指定，系统默认将modelFilePath目录下所有 *.xml 模型定义全部生成一遍；
	 * <br/>建议指定模型文件名；
	 * <br/>注：
	 * <br/>无需指定后缀名，如： user.xml 只需要 -Dmodel.file=user 即可。
	 */
	@Parameter( property = "model.file" )
	private String modelFileName ; 
	
	/**
	 * 待生成DAO源码的模型定义文件
	 */
	private List<File> modelDefFiles = new ArrayList<File>() ; 
	
	/**
	 * -Dfixed.models，指定生成哪些模型<br/>
	 * <br/>如： -Dfixed.models=orgInfo,roleInfo ，系统只会生成 模型id为orgInfo,roleInfo的程序代码
	 * <br/>如果不指定，默认所有模型对象都会生成一遍；
	 * <br/>逗号分隔多个模型对象；
	 */
	@Parameter( property = "fixed.models" )
	private String fixedModels ; 
	
	/**
	 * 模型文件中定义的所有业务域模型们
	 */
	private List<BizModel> bizModelAllinMoedlFile = new ArrayList<BizModel>() ; 
	
	/**
	 * 需要生成DAO代码的业务域模型们
	 */
	List<BizModel> bizModelNeedGen = new ArrayList<BizModel>() ;
	
	/**
	 * （因为缺少源码接收工程）不生成DAO代码的业务模型们
	 */
	private List<BizModel> filteredModelList = new ArrayList<BizModel>() ; 
	
	/**
	 * <pre>
	 * 
	 * -Dgen.type， 指定生成哪些类型的代码
	 * 
	 * 如： -Dgen.type=model,ui,service 将只生成model、ui、service层的源码
	 * 不指定则全部生成
	 * 
	 * 当前可选类型有：
	 * 	ddl 数据库脚本，目前生成mysql脚本
	 * 	model 模型源码，对应数据库表记录PO，对应界面展示对象VO，对应服务传输对象DTO； 
	 * 	dao 数据访问层，基于mysql，进行业务域内数据的存取操作； 
	 * 	biz 业务逻辑层，业务域内的业务处理逻辑实现；
	 * 	controller 控制层，处理ui请求；
	 * 	ui 界面层，提供交互操作能力; 
	 * 	service 服务层，基于dubbo实现，作为服务提供者对外提供服务能力; 
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

		if( !"true".equals( justShow ) ){
			generateDaoSourceCode() ;
		}
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
				
				bizModelAllinMoedlFile.add( bm ) ;
			}
		}
		
		if( StringUtils.equals(modelFileType, FILE_SUFFIX_ERM) ){
			//用ERMaster定义模型
			for( File defFile : modelDefFiles ){
				
				ERMasterModel ermm = new ERMasterModel(defFile) ; 
				
				List<BizModel> bms = new ERMasterDefinition(ermm).getBizModels() ;
				
				for( BizModel bm : bms ){
					perMainPackage4BizModel(defMainPackage, bm);
					bizModelAllinMoedlFile.add( bm ) ;
				}
			}
		}
		
		/*
		 * 收集实际需要生成源码的业务域
		 * 1、过滤掉没有facade、service工程接收源码的模型—— 避免生成暂时无所归属的代码
		 * 2、过滤掉指定之外的模型
		 */
		String[] fixedModelsList = {} ; 
		if( StringUtils.isNotEmpty(fixedModels) ){
			fixedModelsList = fixedModels.split("\\,");
		}
		
		for( BizModel bm : bizModelAllinMoedlFile ){
			
			// 1
			if( isNotExistSourceProject(bm) ) {
				filteredModelList.add(bm) ;
				continue ;//缺少接收自动生成源码的工程时，不生成该业务域模型源码
			}else{
				bizModelNeedGen.add(bm) ;
			}
			
			// 2
			if( fixedModelsList.length == 0 ){
				continue ; // 没有指定过滤模型
			}
			
			List<Model> newModels = new ArrayList<Model>();
			for( Model m : bm.getModels() ){
				
				for(String fixedModelName : fixedModelsList){
					if( m.getId().equalsIgnoreCase(fixedModelName) ){
						newModels.add(m) ; 
					}else{
						// 过滤掉没指定的模型
					}
				}
			}
			bm.setModels(newModels);
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
	 * 判断bm模型对应的工程是否不齐全
	 * <br/>如果bm模型指定的 service,facade 两个工程只要其中一个不存在 则认为不齐全
	 * @param bm
	 * @return false 齐全 true 不齐全
	 */
	private boolean isNotExistSourceProject(BizModel bm) {
		
		try {
			
			if( StringUtils.isNotEmpty(bm.getPrjService()) ){
				String prjServiceDir = CommonUtil.replacePrjNameInMaven(sourceDirect, bm.getPrjService()) ;
				if( FileUtil.isNotExistPath(prjServiceDir) ){
					getLog().warn("不生成源码！因为模型<"+bm.getId()+":"+bm.getName()+"> 对应的Service源码工程<"+prjServiceDir+">不存在!");
					return true ; 
				}
			}
			
			if( StringUtils.isNotEmpty(bm.getPrjFacade()) ){
				String prjFacadeDir = CommonUtil.replacePrjNameInMaven(sourceDirect, bm.getPrjFacade()) ;
				if( FileUtil.isNotExistPath(prjFacadeDir) ){
					getLog().warn("不生成源码！因为模型<"+bm.getId()+":"+bm.getName()+"> 对应的Facade源码工程<"+prjFacadeDir+">不存在!");
					return true ; 
				}
			}
			
		} catch (Exception e) {
			
			getLog().error("判断bm模型对应的工程是否齐全时出错，将跳过模型<"+bm.getId()+">的源码生成！",e);
			return true ; 
		}
		
		return false;//两个工程都存在
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
		getLog().info("模型文件类型:"+modelFileType); 
		getLog().info("模型文件路径:"+modelFilePath); 
		getLog().info("代码模版路径:"+templatesPath); 
		getLog().info("生成源码类型包括:"+genTypes);
		if( StringUtils.isNotEmpty(fixedModels) ) { getLog().info("只生成其中的:"+fixedModels); }
		getLog().info("业务模型定义有:"+showModelList(bizModelAllinMoedlFile));
		getLog().info("其中不生成源码的业务域有: "+filteredModelList.size() +" 个");
		if( filteredModelList.size() > 0 ){getLog().info("他们是："+showModelList(filteredModelList)) ; }
		getLog().info("===========================================================");
	}
	
	
	private String showModelList(List<BizModel> bizModelList) {
		StringBuffer sb = new StringBuffer() ; 
		sb.append("\n");
		for ( BizModel bm : bizModelList ){
			if( "true".equals(justShow) ){
				sb.append(bm.toString()) ;//只展示信息时，展示详细模型信息
			}else{
				sb.append(bm.toStringSimple()) ;
			}
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
			
			// 根据模型文件类型选择模版
			if( this.modelFileType.equals(FILE_SUFFIX_XML) ){
				
				GenDAOManager.instance.initWithDef(TemplateType.BIZ); 
			}else{
				
				//使用针对ERM模型的模版文件生成源码
				GenDAOManager.instance.initWithDef(TemplateType.BIZ_ERM); 
			}
		}else{
			GenDAOManager.instance.initWithFixed(templatesPath);
		}
		
		if( genTypes.contains("ddl") ){
			//生成数据库脚本
			GenDAOManager.instance.genDDL(bizModelAllinMoedlFile, resourcesDirect, sourceDirect);
		}
		
		if( genTypes.contains("model") ){
			
			//生成模型PO、VO、DTO
			GenDAOManager.instance.genModel(bizModelAllinMoedlFile, resourcesDirect, sourceDirect);
		}
		
		if( genTypes.contains("dao") ){
			
			//生成dao层代码
			GenDAOManager.instance.genDAO(bizModelAllinMoedlFile, resourcesDirect, sourceDirect);
		}
		
		if( genTypes.contains("biz") ){
			
			//生成biz业务层代码(Service，Remote Service)
			GenDAOManager.instance.genBiz(bizModelAllinMoedlFile, resourcesDirect, sourceDirect);
		}
		
		if( genTypes.contains("controller") ){
			
			//生成controller代码
			GenDAOManager.instance.genController(bizModelAllinMoedlFile, resourcesDirect, sourceDirect);
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
