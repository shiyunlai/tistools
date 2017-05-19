package org.tis.tools.maven.plugin.utils;

import org.junit.Test;

public class FreeMarkerUtilTest {

	@Test
	public void test() {
		System.out.println("this getResource   :"+this.getClass().getResource(""));//得到的是当前类class文件的URI目录。不包括自己！
		System.out.println("this getResource / :"+this.getClass().getResource("/"));//得到的是当前的classpath的绝对URI路径 。
		System.out.println("this classLoader   :"+this.getClass().getClassLoader().getResource(""));//得到的是当前类class文件的URI目录。不包括自己！
		System.out.println("this classLoader / :"+this.getClass().getClassLoader().getResource("/"));//得到的是当前的classpath的绝对URI路径 。
		System.out.println("Class getResource  :"+Class.class.getResource(""));//
		System.out.println("Class getResource /:"+Class.class.getResource("/"));
	}

}
