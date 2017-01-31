package org.bronsp.maven.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.model.Build;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * <pre>
 * 测试Maven扩展：输出工程信息，且在输出信息前加上前缀 prefix
 * -Dprefix=AAAAAA 指定前缀为 AAAAAA
 * </pre>
 */

@Mojo(name = "test")
public class TestMojo extends AbstractMojo {

	/**
	 * project 表示该插件持有一个到MavenProject的引用，当客户方在执行该插件时，这里的project字段便表示客户工程。
	 * 这里我们并没有对project进行初始化，但是“@Parameter( defaultValue = "${project}")"”中的${project}即表示当前的客户工程，
	 * Maven在运行时会通过依赖注入自动将客户工程对象赋给project字段
	 */
	@Parameter( defaultValue = "${project}")
	private MavenProject project;

	
	/**
	 * 可通过 -Dprefix=uuuu 设置prefix的值
	 * 不设置将默认为 ttttttt
	 */
	@Parameter( property = "prefix", defaultValue = "ttttttttt" )
	private String prefix;

	public void execute() throws MojoExecutionException {

		/*
		 * 输出当前工程信息 
		 */
		getLog().info("==========================");
		getLog().info("The TIS Tools maven plugin test .....Project build info:");
		Build build = project.getBuild();
		String outputDirectory = build.getOutputDirectory();
		String sourceDirectory = build.getSourceDirectory();
		String testOutputDirectory = build.getTestOutputDirectory();
		String testSourceDirectory = build.getTestSourceDirectory();
		String[] info = { outputDirectory, sourceDirectory,testOutputDirectory, testSourceDirectory };
		for (String item : info) {
			getLog().info("\t" + prefix + "   " + item);
		}
		getLog().info("=======================");
		getLog().info("你还可以试试： mvn tools:test -Dprefix=uuuuu ");
		getLog().info("=======================");
		
	}
}
