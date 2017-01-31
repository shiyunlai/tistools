/**
 * 
 */
package org.tis.tools.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * <pre>
 * 根据自动源码清单清理代码
 * -Dclean.all=true 清理所有自动生成的代码；不指定，则不清理自动生成但可手动的源码。
 * </pre>
 * @author megapro
 *
 */
@Mojo(name="clean")
public class CleanMojo extends AbstractMojo {

	/**
	 * "true"  表示清理所有自动生成的源码
	 * "false" 不清理自动生成但可手动的源码（默认）
	 */
	@Parameter( property = "clean.all", defaultValue = "false" )
	private String cleanAll ; 
	
	
	/* (non-Javadoc)
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		// TODO Auto-generated method stub
		System.out.println("敬请期待～");
		
	}

}
