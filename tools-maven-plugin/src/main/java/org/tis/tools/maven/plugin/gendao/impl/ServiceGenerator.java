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
import org.tis.tools.maven.plugin.utils.FreeMarkerUtil;

/**
 * 
 * XXXService.java业务逻辑类生成器
 * 
 * @author megapro
 * 
 */
public class ServiceGenerator extends ASourceCodeGenerator<BizModel>{

	/**
	 * 生成业务逻辑类（Spring MVC实现，@Service）
	 * @param genModelDef
	 * @param resourcesDir
	 * @param sourceDir
	 * @throws GenDaoMojoException 
	 */
	@Override
	protected void doGen(List<BizModel> genModelDef, String resourcesDir,
			String sourceDir) throws GenDaoMojoException {
		Map map = new HashMap();
		String targetFile = null ; 
		for( BizModel bm : genModelDef ){
			
			map.clear(); 
			map.put("defineFile", bm.getModelDefFile());
			
			//源码package
			//规范： 组织.产品.功能划分(service).类型限制(null).业务域id
			String pBizJava    = CommonUtil.normPackageName(bm.getMainpackage() + ".service" +"."+ bm.getId()); 
			map.put("mainPackage", bm.getMainpackage()) ;
			map.put("bizmodelId", CommonUtil.normPackageName(bm.getId())) ;//业务领域id
			
			String realSourceDir = null ; 
			if( StringUtils.isNotEmpty(bm.getPrjService()) ){
				//如果模型中定义了 prjService ，则将代码生成到指定工程目录下 
				realSourceDir = CommonUtil.replacePrjNameInMaven(sourceDir, bm.getPrjService()) ;
			}else{
				//模型中没有定义 prjSevice ，则代码生成到当前工程
				realSourceDir = sourceDir ;
			}
			
			for( Model m : bm.getModels() ){
				
				map.put("table", m) ; 
				String className = FreeMarkerUtil.capFirst(CommonUtil.line2Hump(m.getId())) ; 
				map.put("poClassNameVar", className) ;//模型PO对象类名
				
				//生成XXBiz.java
				map.put("packageName", pBizJava) ; 
				targetFile = realSourceDir + CommonUtil.package2Path(pBizJava) + className + "Service.java";
				try {
					FreeMarkerUtil.process("Service.java.ftl", map, targetFile);
					addGenFile(targetFile);
				} catch (Exception e) {
					throw new GenDaoMojoException("生成XXXService源码失败!"+targetFile,e) ; 
				}
			}
		}
	}

	@Override
	protected String getGeneratorDescription() {
		return "生成XXXService.java";
	}

}
