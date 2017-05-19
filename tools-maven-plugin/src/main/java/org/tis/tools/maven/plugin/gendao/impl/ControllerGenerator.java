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
 * 
 * Controller.java源码生成器
 * 
 * @author megapro
 *
 */
public class ControllerGenerator extends ASourceCodeGenerator<BizModel> {

	/**
	 * <br/>生成模型的Controller类（Spring MVC实现，@Controller）
	 * <br/>每个模型独立一个controller类
	 * @param genModelDef 业务模型们
	 * @param resourcesDir 工程路径
	 * @param sourceDir 源码路径
	 * @throws GenDaoMojoException 
	 */
	@Override
	protected void doGen(List<BizModel> genModelDef, String resourcesDir,
			String sourceDir) throws GenDaoMojoException {
		
		Map map = new HashMap();
		String targetFile = null ; 
		boolean isDisPrj = false; //默认为单工程结构
		for( BizModel bm : genModelDef ){
			map.clear(); 
			
			//源码package
			//规范： 组织.产品.功能划分(webapp).类型限制(controller).业务域id //TODO 应该吧这些带有规范性的命名抽取独立为util工具类，方便维护
			String pControllerJava    = CommonUtil.normPackageName(bm.getMainpackage() + ".webapp"+".controller" + "." + bm.getId() ) ; 
			map.put("mainPackage", bm.getMainpackage()) ;
			map.put("bizmodelId", CommonUtil.normPackageName(bm.getId())) ;//业务领域id
			
			if( StringUtils.isNotEmpty(bm.getPrjFacade()) && !StringUtils.equalsIgnoreCase(sourceDir, bm.getPrjFacade()) ){
				isDisPrj = true ; //定义了 prjFacade，同时 不是当前工程，则说明为分布式工程结构
			}
			map.put("isDisPrj", isDisPrj) ;//是否为分布式工程结构

			String realSourceDir ="" ;
			if( StringUtils.isNotEmpty(bm.getPrjWeb())){
				// 如果模型中定义了 prjWeb ，则将代码生成到指定工程目录下 
				realSourceDir = CommonUtil.replacePrjNameInMaven(sourceDir, bm.getPrjWeb()) ;
			}else{
				// 模型中没有定义 prjWeb ，则代码生成到当前工程
				realSourceDir = sourceDir ; 
			}
			
			if( FileUtil.isNotExistPath(realSourceDir) ){
				System.out.println("不生成以下业务域的源码，因为源码路径不存在：" + realSourceDir);
				System.out.println(bm.toStringSimple());
				continue ; //找不到源码存放路径的不生成，避免多定义的模型生成非Maven工程路径，难手工清理
			}
			
			for( Model m : bm.getModels() ){
				
				map.put("table", m) ;
				map.put("defineFile", bm.getModelDefFile());
				String className = FreeMarkerUtil.capFirst(CommonUtil.line2Hump(m.getId())) ; 
				map.put("poClassName", className) ;//Controller对象类名
				
				//生成XXMapper.java
				map.put("packageName", pControllerJava) ; 
				targetFile = realSourceDir + CommonUtil.package2Path(pControllerJava) + className + "Controller.java";
				try {
					FreeMarkerUtil.process("Controller.java.ftl", map, targetFile);
					addGenFile(targetFile);
				} catch (Exception e) {
					throw new GenDaoMojoException("生成XXXController.java源码失败!"+targetFile,e) ; 
				}
			}
		}		
	}

	@Override
	protected String getGeneratorDescription() {
		return "生成XXXController.java";
	}

}
