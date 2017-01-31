/**
 * 
 */
package org.bronsp.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * <pre>
 * 根据指定的excel.path目录，自动识别excel文件，并生成交互接口定义xsd
 * 默认在当前工程\excel\ 目录下查找，否者可通过 excel.path指定任意目录，如：
 * -Dexcel.path=C:\excel
 * xsd输出到excel目录下的xsd目录
 * 如： c:\excel\xsd\
 * </pre>
 */

@Mojo(name="gen-xsd")
public class GenXsdMojo extends AbstractMojo {
	
	/**
	 * excel文件存放目录
	 * 不指定，默认取： 当前工程所在路径/excel/
	 */
	@Parameter( property = "excel.path" )
	private String excelPath;
	
	/**
	 * 指定excel文件
	 * 不指定，默认取处理所有目录下的excel文件
	 */
	@Parameter( property = "fix.file" )
	private String fixFile;

	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("开始生成xsd......");
	}
	
}
