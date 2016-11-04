
## 功能列表

### 根据接口Excel文件，自动生成交换所需接口描述 xsd ，输出文件存放到excel同目录下xsd目录

1. 默认查找当前工程excel目录下所有文件，执行xsd生成

	mvn ttt:gen-xsd

1. 指定excel文件路径

	mvn ttt:gen-xsd -Dexcel.path=C:\excel\

1. 只处理指定的excel文件
	
	mvn ttt:gen-xsd -Dfix.file=Exg_ABCD.xsl

### 查看本插件的帮助信息 (help对应的 HelpMojo.java 为 mvn plugin:helpmojo 自动生成 )

	mvn ttt:help


## TODO list

	//TODO 增加xsd自动生成功能 @大本  GenXsdMojo


## 如何使用tools-maven-plugin插件

	vi setting.xml

	<pluginGroups>
	...
		<pluginGroup>org.tis</pluginGroup>
	...
	</pluginGroups>

	在pom文件位置执行：

	mvn tools:test


## 问题

* 问题1：pom.xml文件中 <packaging> 节点上提示错误信息

	Plugin execution not covered by lifecycle configuration: org.apache.maven.plugins:maven-plugin-plugin:3.2:descriptor (execution: default-descriptor, phase: generate-resources)

	原因：未明

	解决：在pom.xml的OverView视图下，点击eclipse中的错误提示，根据提示选择即可；

* 问题2： 利用 maven-plugin-plugin的 goalPrefix ，将tools前缀修改为 ttt后，执行mvn ttt:test 时报错:

	[ERROR] Could not find goal 'test' in plugin org.tis:tools-maven-plugin:0.0.1 among available goals touch -> [Help 1]
org.apache.maven.plugin.MojoNotFoundException: Could not find goal 'test' in plugin org.tis:tools-maven-plugin:0.0.1 among available goals touch

	居然报错，找不到 test这个执行目标了 （goal）

	原因：maven-plugin-plugin 需要使用3.2及以上版本，才能与maven-plugin-annotations兼容，识别@Mojo。。。这些注解

	过程：查看了tools-maven-plugin-0.0.1.jar 发现其中的plugin.xml <goals>节点为空，也就是意味着 maven-plugin-plugin的加入，导致maven没有能够正确的 通过 @Mojo这些注释收集到 goal 信息
	.... 一顿百度，无人记录这样的问题....

	解决：
	在 http://mvnrepository.com/上查找 maven-plugin-plugin 这个插件，发现最新版本已经3.5了，目前使用人数较多的是 3.2版本，
	果断修改为3.2
	``` xml
	<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-plugin-plugin</artifactId>
				<version>3.2</version> <!-- 啃爹啊， 3.2之前的版本不能识别 @Mojo  -->
				<configuration>
					<!-- 为本插件指定前缀 -->
					<goalPrefix>ttt</goalPrefix>
				</configuration>
			</plugin>
	```

问题3： ...

	原因：

	解决：
