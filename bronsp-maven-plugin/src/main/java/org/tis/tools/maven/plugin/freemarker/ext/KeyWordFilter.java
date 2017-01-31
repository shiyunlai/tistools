/**
 * 
 */
package org.tis.tools.maven.plugin.freemarker.ext;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * 生成源码时过滤掉java关键字
 * 
 * @author megapro
 *
 */
public class KeyWordFilter implements TemplateMethodModel {

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 */
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		// TODO Auto-generated method stub
		return null;
	}

}
