/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tis.tools.maven.plugin.exception.GenDaoMojoException;
import org.tis.tools.maven.plugin.gendao.BizModel;
import org.tis.tools.maven.plugin.gendao.api.ASourceCodeGenerator;
import org.tis.tools.maven.plugin.utils.CommonUtil;
import org.tis.tools.maven.plugin.utils.FreeMarkerUtil;

/**
 * 
 * 生成模型的建表脚本</br>
 * 
 * 每个模型生成独立的.sql文件</br>
 * 
 * @author megapro
 *
 */
public class DDLGenerator extends ASourceCodeGenerator<BizModel> {

	private static final String TEMPLATE_DDL_SQL_FTL = "ddl.sql.ftl";
	private static final String AUTOGEN_SQL_PATH = "autogen/sql/";//数据库脚本存放位置

	/**
	 * <br/>生成指定模型的建表脚本
	 * <br/>每个业务域生成一个.sql文件
	 * <br/>默认生成脚本存放在：工程路径{projectPath}/gendao/sql/ 目录下
	 * @param genModelDef 业务域模型定义
	 * @param resourcesDir 资源路径
	 * @param sourceDir 源码存放路径
	 * @throws GenDaoMojoException 
	 */
	@Override
	protected void doGen(List<BizModel> genModelDef,
			String resourcesDir, String sourceDir) throws GenDaoMojoException {
		
		Map map = new HashMap();
		
		// ddl脚本，生成在当前工程中
		String projectPath = CommonUtil.getProjectPathBySource(sourceDir) ; 
		
		for(  BizModel bm : genModelDef ){
			
			map.put("defineFile", bm.getModelDefFile());
			
			if( bm.getModels().size() <= 0 ){
				continue ; 
			}
			
			System.out.println("开始生成建表脚本，业务域：["+bm.getId()+" : "+ bm.getName() +"]");
			map.put("tables", bm.getModels());
			map.put("bizmodelId", CommonUtil.normPackageName(bm.getId())) ;//业务领域id
			try {
				String targetFile = projectPath + AUTOGEN_SQL_PATH + bm.getId() + ".sql" ; 
				FreeMarkerUtil.process(TEMPLATE_DDL_SQL_FTL, map, targetFile );
				addGenFile(targetFile) ; 
			} catch (Exception e) {
				e.printStackTrace();
				throw new GenDaoMojoException("生成业务域<"+bm.getId()+">的数据库脚本失败！", e) ; 
			}
		}
	}

	@Override
	protected String getGeneratorDescription() {
		
		return "生成DDL模型建表脚本";
	}
}
