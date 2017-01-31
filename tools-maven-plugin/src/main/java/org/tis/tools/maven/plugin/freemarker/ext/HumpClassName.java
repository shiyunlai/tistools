/**
 * 
 */
package org.tis.tools.maven.plugin.freemarker.ext;

import java.util.List;

import org.tis.tools.maven.plugin.utils.CommonUtil;
import org.tis.tools.maven.plugin.utils.FreeMarkerUtil;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * 采用驼峰规则进行类名命名
 * 
 * @author megapro
 *
 */
public class HumpClassName implements TemplateMethodModel {

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 */
	@Override
	public String exec(List arguments) throws TemplateModelException {
		//转换为驼峰
		String className = CommonUtil.line2Hump(arguments.get(0).toString()) ; 
		//类名,首字母大写
		return FreeMarkerUtil.capFirst(className) ;
	}
}
