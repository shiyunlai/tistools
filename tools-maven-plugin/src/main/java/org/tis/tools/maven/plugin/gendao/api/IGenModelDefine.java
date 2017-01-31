/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.api;

/**
 * 
 * <br/>自动代码“生成规则模型定义”的接口
 * 
 * <br/>规定name,id为必须属性
 * 
 * @author megapro
 *
 */
public interface IGenModelDefine {
	
	/**
	 * 获得该模型的名称
	 * @return
	 */
	public String getName() ; 
	
	/**
	 * 获得该模型的id
	 * @return
	 */
	public String getId() ; 
}
