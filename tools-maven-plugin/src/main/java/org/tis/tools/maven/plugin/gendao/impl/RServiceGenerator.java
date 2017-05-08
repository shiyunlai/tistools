/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tis.tools.maven.plugin.exception.GenDaoMojoException;
import org.tis.tools.maven.plugin.gendao.BizModel;
import org.tis.tools.maven.plugin.gendao.Model;
import org.tis.tools.maven.plugin.gendao.api.ASourceCodeGenerator;
import org.tis.tools.maven.plugin.utils.CommonUtil;
import org.tis.tools.maven.plugin.utils.FileUtil;
import org.tis.tools.maven.plugin.utils.FreeMarkerUtil;

/**
 * <pre>
 * 
 * 远程服务代码生成器
 * 
 * 生成：I*RService.java
 * 生成：*RServiceImpl.java
 * 
 * </pre>
 * @author megapro
 *
 */
public class RServiceGenerator extends ASourceCodeGenerator<BizModel> {


	@Override
	protected void doGen(List<BizModel> genModelDef, String resourcesDir, String sourceDir) throws GenDaoMojoException {
		
		Map map = new HashMap();
		String targetFile = null ; 
		
		for( BizModel bm : genModelDef ){
			
			map.clear(); 
			map.put("defineFile", bm.getModelDefFile());
			
			//源码package
			//规范： 组织.产品.技术分层(rservice).业务域id.业务域分层
			String javaPackage    = CommonUtil.normPackageName(bm.getMainpackage() + ".rservice" + "." + bm.getId() + ".basic"); 
			map.put("mainPackage", bm.getMainpackage()) ;
			map.put("bizmodelId", CommonUtil.normPackageName(bm.getId())) ;//业务领域id
			
			String realSourceDirApi = null ; //远程服务接口定义
			if( StringUtils.isNotEmpty(bm.getPrjFacade()) ){
				//如果模型中定义了 prjFacade ，则将代码生成到指定工程目录下 
				realSourceDirApi = CommonUtil.replacePrjNameInMaven(sourceDir, bm.getPrjFacade()) ;
			}else{
				//模型中没有定义 prjFacade ，则代码生成到当前工程
				realSourceDirApi = sourceDir ;
			}
			
			if( FileUtil.isNotExistPath(realSourceDirApi) ){
				System.out.println("不生成以下业务域的源码，因为源码路径不存在：" + realSourceDirApi);
				System.out.println(bm.toStringSimple());
				continue ; //找不到源码存放路径的不生成，避免多定义的模型生成非Maven工程路径，难手工清理
			}
			
			String realSourceDirImpl = null ; //远程服务实现
			if( StringUtils.isNotEmpty(bm.getPrjService()) ){
				//如果模型中定义了 prjService ，则将代码生成到指定工程目录下 
				realSourceDirImpl = CommonUtil.replacePrjNameInMaven(sourceDir, bm.getPrjService()) ;
			}else{
				//模型中没有定义 prjSevice ，则代码生成到当前工程
				realSourceDirImpl = sourceDir ;
			}
			
			for( Model m : bm.getModels() ){
				
				map.put("table", m) ; 
				String className = FreeMarkerUtil.capFirst(CommonUtil.line2Hump(m.getId())) ; 
				map.put("poClassName", className) ;//模型PO对象类名
				map.put("packageName", javaPackage) ; 

				//生成 IXXRService.java
				String apiClassName = "I" + className + "RService" ;
				map.put("apiClassName", apiClassName) ; 
				targetFile = realSourceDirApi + CommonUtil.package2Path(javaPackage) + apiClassName + ".java";
				try {
					FreeMarkerUtil.process("RServiceApi.java.ftl", map, targetFile);
					addGenFile(targetFile);
				} catch (Exception e) {
					throw new GenDaoMojoException("生成<"+targetFile+">源码失败!"+targetFile,e) ; 
				}
				
				//生成 XXRServiceImpl.java
				String implClassName = className + "RServiceImpl" ;
				map.put("implClassName", implClassName) ; 
				targetFile = realSourceDirImpl + CommonUtil.package2Path(javaPackage) + implClassName + ".java";
				try {
					FreeMarkerUtil.process("RServiceImpl.java.ftl", map, targetFile);
					addGenFile(targetFile);
				} catch (Exception e) {
					throw new GenDaoMojoException("生成<"+targetFile+">源码失败!"+targetFile,e) ; 
				}
			}
		}
	}

	@Override
	protected String getGeneratorDescription() {
		return "生成对象的基础远程服务代码: I*RService.java,*RServiceImpl.java";
	}

}
