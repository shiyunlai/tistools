/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.api;

import java.util.ArrayList;
import java.util.List;

import org.tis.tools.maven.plugin.exception.GenDaoMojoException;

/**
 * 
 * 源码生成器的接口
 * 
 * @author megapro
 *
 */
public abstract class ASourceCodeGenerator<T extends IGenModelDefine> {
	
	List<String> genFiles = new ArrayList<String>() ; 
	
	/**
	 * 执行具体的生成处理
	 * 
	 * @param genModelDef 业务模型定义
	 * @param resourcesDir 配置存放路径
	 * @param sourceDir 源码存放路径
	 * @return 
	 * @throws GenDaoMojoException
	 */
//	protected abstract List<String/* 本次生成的文件名列表 */> doGen(List<T> genModelDef,
//			String resourcesDir, String sourceDir) throws GenDaoMojoException;
	protected abstract void doGen(List<T> genModelDef,
			String resourcesDir, String sourceDir) throws GenDaoMojoException;

	/**
	 * 返回源码生成器信息
	 * 
	 * @return
	 */
	protected abstract String getGeneratorDescription();

	/**
	 * 根据生成规则模型定义执行代码生成
	 * 
	 * @param genModelDef
	 *            生成规则模型定义(必须实现IGeneratorModelDefine接口)
	 * @param resourcesDir
	 *            资源存放目录
	 * @param sourceDir
	 *            源码存放目录
	 */
	public void genSourceCode(List<T> genModelDef, String resourcesDir,
			String sourceDir) throws GenDaoMojoException {
		
		System.out.println("执行：" + getGeneratorDescription());
		
		//FIXME 对doGen方法还应该在做细化抽象，子类实现doGen时还是有很多重复逻辑
		doGen(genModelDef, resourcesDir, sourceDir);
		for (String f : genFiles) {
			System.out.println("生成:" + f);
		}
		// TODO 收集生成的文件，记录生成列表，以便开发clean功能
	}
	
	
	/**
	 * 
	 * <br/>收集生成文件名
	 * <br/>要求子类每生成一个文件时都调用本接口存储
	 * 
	 * @param genedFile 生成的源码文件名称(全路径)
	 */
	protected void addGenFile(String genedFile){
		this.genFiles.add(genedFile) ;
	}

}
