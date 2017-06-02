

##	开发日志

### 待解决
	
	TODO0、生成tools工程
	
		期望的使用方式: 自动生成三个工程，其前缀为tools,业务域名称为jnl，工程名称分别是 tools-facade-jnl、tools-service-jnl、tools-web-jnl
		
		mvn tools:gen-project -Dtype=[facade,service,web] -Dbizmodel.name=jnl -Dprefix=tools
	
	TODO1、清理功能 —— 清理自动生成的代码（完全清理，部分清理）
		支持清理功能包括：
			根据指定文件名匹配清理
			根据模型分类清理
			根据模型生成时间段清理
			根据模型生成源码所在工程清理
			.... 以开发人员的角度思考清理功能，为提供命令使用效率设计
			
	TODO2、设计并实现，dao、biz、controller层的可修改性（解决自动代码修改后，再次生成时，会被覆盖的问题！）
	TODO3、生成对应的单元测试 gen.type 中增加 test 类型的源码生成能力（基础单元测试代码）。
	TODO4、 没有做模型的重复检查，如： 1.xml 2.xml两个模型文件中都定义了 acct，目前没有做报错提示；
	TODO5： 没有生成VO、DTO
	TODO6： 还未生成ui层代码
	TODO7： 还未生成dubbo的service代码
	TODO8： 还因该支持向多个工程输出生成源码，如：所生成的源码，分为facade、service两个层次，存放到两个工程中去。

### 已解决

	
	FIXED1 如何把FreeMarker模版放在jar包中，使用着无需依赖外部目录？

		见：FreeMarkerUtil中两个init开头的方法；

	FIXED2 应该把生成源码的功能块重构为不同的生成策略，提升扩展性；

		见：ASourceCodeGenerator、IGenModelDefine、gen-daoManager
	
	FIXED	TODO0、当前最大的问题，生成的代码，不支持分布式工程结构！！！
	FIXED3 生成的代码，支持分布式工程结构
		
		见开发分支feature_maven_plugin_4_dispro
		
	
## Maven插件，开发经验

*	关于单元测试

不要把单元测试所需的资源和java放在同一个package路径下，建议放去 src/test/resources/META-INF/ 目录下，
因为maven编译时，默认不会编译package目录下非java资源（资源不会到classes路径去，执行单元测试时，会找不到资源）
但是maven会编译放resources目录下的内容到 classes/META-INF 去； 见： Xml22BeanUtilTest.java 中 testParseToBean()

*	如何开发maven插件，参考官网：[扩展maven插件](http://maven.apache.org/guides/plugin/guide-java-plugin-development.html)

## 开发过程中遇到的问题及解决过程

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

问题3： 在父项目中增加了对Dom4j，和Jaxen的依赖管理（dependencyManage），在eclipse中直接运行单元测试通过，但是编译 mvn clean install 之后，在目标工程(tools-core)中运行本插件，如： mvn tools:help 时，报错：
'dependencies.dependency.version' for dom4j:dom4j:jar is missing. @
2 problems were encountered while building the effective model for org.tis:tools-maven-plugin:0.0.1

	原因：
	上面报错，提示找不到dom4j的版本 —— dom4j:dom4j:jar is missing. @
	因为对jar包的依赖管理在 tools 父工程中，需要先把父工程也编译，maven运行时，才能找到正确的依赖关系

	解决：
	编译整体tools项目
	mvn clean install 
	之后在运行通过 mvn tools:help
	
问题4：.......
	
	原因：
	.....
	
	解决： 
	.....
		
问题4：.......
	
	原因：
	.....
	
	解决： 
	.....
	
		
问题4：.......
	
	原因：
	.....
	
	解决： 
	.....
	
		
问题4：.......
	
	原因：
	.....
	
	解决： 
	.....
	
		
问题4：.......
	
	原因：
	.....
	
	解决： 
	.....