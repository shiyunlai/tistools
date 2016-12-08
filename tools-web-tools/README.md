
### 开发过程

2016-11-30

	开发框架基本成型
	
	后续：
	1、可持续生长：整理开发模型PPT
	2、一个完整的例子：BizLogController中的功能完整的开发

待提高的技术点：

//TODO 分为多层，分布式系统后，异常类的设计，异常机制！？
	统一的错误提示
	明确的业务指示


###	对web-tools工程的重构过程

	1、现有tis-tools.rar解压
	
	2、加入tools项目工程集中，以tools为父工程，POM.xml中加入如下配置：
	
		<groupId>com.bosh.tis</groupId>
	    <artifactId>tools</artifactId>
	    <version>${tis_tools_version}</version>
	
	3、修改pom.xml信息，artifactId为 web-tools；
	
	4、修改工程名称 tis-tools 为 web-tools；
	
	5、在tools工程中引入该模块 <module>web-tools</module> ; 
	
	至此，完成tis-tools加入toolgs工程集的重构；
	
	6、对原工程的jar包依赖进行管理重构：
		
		为了统一tools工程集中使用的jar及版本
		6.1、原工程中需要依赖的jar都整理到父工程 <dependencyManagement>；
		6.2、jar的版本号都在父工程中定义 <properties>；
		6.3、子工程中只需引用 <groupId>和<artifactId>进行jar引用；
		6.4、子工程独自依赖的jar则在子工程pom.xml中单独定义；（建议版本号也定义为 <properties> ）
		
	7、加入dubbo能力所需的jar
		
		pom.xml --> 注释关键字："dubbo 相关jar start"
	
	8、引入devmgr服务
		
		8.1、pom.xml --> 依赖tools-facade-devmgr 工程
		
		8.2、增加以下配置：
			src/main/resources/META-INF/dubbo-consumer.xml
			src/main/resources/META-INF/dubbo-reference-devmgr.xml
			
			注意：如果服务提供者定义了 group 、version，消费者在引用服务时也要设定（dubbo 以 interfacew+group+version唯一确定一个服务）
			
			<dubbo:reference id="devMgrRService" group="devmgr" version="1.0" interface="com.bosh.tis.tools.devmgr.service.api.DevMgrRemoteService" check="false"/>
			
		8.3、修改
			src/main/resources/META-INF/applicationContext-service.xml 引入 dubbo配置文件，内容如下：
			<!-- 引入Dubbo配置 -->
    		<import resource="dubbo-consumer.xml" />
    	
    	8.4、一堆问题 1 － 8 一一解决
		
	10、启动工程 
		
		mvn tomcat7:run
	
	11、执行dubbo服务调用测试
		
		11.1、在com.bos.tis.tools.webapp.controller.BizTest.java中增加getBranchCode方法，
			调用devMgrRService.genBranchCode(BranchType.DEV_BRNACH_BUG)
		
		11.2、在浏览器中访问地址：  http://localhost:8089/tis/testController/gencode
			
			预期输出 :  分支代码： BUG_123
			
		至此，原tools工程的dubbo服务化改造完成！！！
		
### 进行分布式工程切分，实现分布式服务开发和部署运行 web-tools工程作为服务消费者调用其他服务提供者，实现功能
	
	重构 com.bos.tis.tools.util 包路径下的java，根据功能分别存放到 web-boss和 tools-common  ok
	
	完成第一次重构，运行测试 ok
	
	1、bronsp:gen-sdo 生成的代码支持分布式框架 ok
		
	2、接下来启动 tools-web-tools测试， ok
	
	
### 还要进行的重构
	
	//TODO 1：删除无效的jar依赖
	
	//TODO 2：删除无用的web资源，减少包体积 ok
	
	//TODO 3：抽取redis等第三发工具的参数为properties方式 ok
		tools-service-biztrace中
	
	//TODO 4：清理pm ok
	
	//TODO 5：删除无效web开发资源 ok
	
	//TODO 6：进行分布式工程切分，实现分布式服务开发和部署运行 web-tools工程作为服务消费者调用其他服务提供者，实现功能 ok
		业务日志服务 -> tools-service-biztrace
		开发管理服务 -> tools-service-devmgr
		
	//TODO 7：整理tools工程的README.MD，也分 主README 和各工程的README ok
	
	//TODO 8：获取zookeeper节点信息，得知当前有那些biztrace代理服务
	
	//TODO 9：与biztrace代理服务进行点对点服务调用 ok - 暂时扩展dubbo的负载均衡机制实现（更好的应该扩展路由，待研究）
	
	//TODO 10：继续整理移植到 tools-service-biztrace中的代码，恢复原biztrace功能（界面除外）
	//TODO 11：研究下 shiro 框架是否能用作 机构人员权限管理
	
	
	
### quick start
	
	mvn clean 
	maven update project ( by eclipse )
	mvn install
	mvn tomcat7:run 
	
	访问地址： http://localhost:8089/tis/tools/index.html
	
### 编译过程解决过的问题：

－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	错误1： java.util.concurrent.ExecutionException: org.apache.catalina.LifecycleException: Failed to start component [StandardEngine[Tomcat].StandardHost[localhost].StandardContext[/tis]]
	
	原因：使用了错误的servlet版本
	
	解决： javax.servlet-api 的版本使用 3.0.1
	
		<dependency>
	      <groupId>javax.servlet</groupId>
	      <artifactId>javax.servlet-api</artifactId>
	      <version>3.0.1</version>
	      <scope>provided</scope>
	    </dependency>
	
－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	错误2：java.lang.IncompatibleClassChangeError: class org.springframework.core.type.classreading.ClassMetadataReadingVisitor has interface org.springframework.asm.ClassVisitor as super class
	
	原因：同一个工程中的Spring版本要统一
	
	解决：统一Spring版本（本工程中升级统一为 4.2.4.RELEASE）
	
－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	错误3：mvn clean install 时报错
		[ERROR] /Users/megapro/Develop/tis/tools/web-tools/src/main/java/com/bos/tis/tools/webapp/controller/BizLogHandleController.java:[19,23] 错误: 程序包bos.tis.biztrace不存在
		[ERROR] /Users/megapro/Develop/tis/tools/tools-web-tools/src/main/java/com/bos/tis/tools/webapp/controller/ListCheckController.java:[20,31] 程序包bos.tis.lpctools.entity不存在
	
	原因：
		$$ 可能原因1：
		maven工程只有 src/main/java/为主源码路径
		但是开发时新增了src/main/biztrace/、src/main/lpctools/两个源码路径
		
		$$ 可能原因2：
		工程中指定了maven编译的jdk版本，与实际工程依赖的jdk版本不一致
	
	解决：
		$$ 针对可能原因2:
			在pom.xml中找到 maven-compiler-plugin 插件
			升级编译插件，修改 <version>2.4</version> 为 <version>3.1</version>
			升级编译jdk版本，修改 <source>和<target>为1.8
			（修改前为1.6，工程使用的jdk为1.8: 工程—>右键—>Java Build Path—>Libraries —> JRE System Library —> 双击 —> Execution environment —> 当前选择为： JavaSE-1.8(Java SE 8)
			然后再执行 mvn clean install	
			仍然报错，排除原因2；
		
		$$ 针对原因1
		
			在工程下 mvc compile ，报错如旧，证明这个原因可能性更大
			通过命令 mvn help:effective-pom | grep lpctools 没有找到 lpctools 字样，进一步证明maven编译时没认为src/main/lpctools为源码路径
			百度：如何maven设置多个源码路径？
			
			在POM.xml中增加源码目录插件，请查找注释关键字： "指定多个源代码目录、多个资源文件目录 start"
			
			执行 mvn compile -X -e ，终于SUCCESS  (期间解决了 错误8)
			
			继续执行编译 mvn clean install -X -e ，终于SUCCESS通过 （3小时啊，才搞定！）
		
			终于可以继续Dubbo改造了...
			
－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	错误4：mvn clean install 时报错
		[ERROR] Failed to execute goal on project tools-web-tools: Could not resolve dependencies for project org.tis:tools-web-tools:war:0.0.1: Failed to collect dependencies at org.tis:tools-facade-devmgr:jar:0.0.1: Failed to read artifact descriptor for org.tis:tools-facade-devmgr:jar:0.0.1: Failure to find org.tis:tools:pom:${tis_tools_version} in https://repo.maven.apache.org/maven2 was cached in the local repository, resolution will not be reattempted until the update interval of central has elapsed or updates are forced -> [Help 1]
		...
		Caused by: org.apache.maven.project.DependencyResolutionException: Could not resolve dependencies for project org.tis:tools-web-tools:war:0.0.1: Failed to collect dependencies at org.tis:tools-facade-devmgr:jar:0.0.1
	
	原因：问题本身描述得很清楚""
	
	解决：
	
		注释掉  父工程pom.xml中 moduls
		
		父工程版本号不使用变量：见警告6
		
		重新编译 这个整个父工程，加入 -U  
		
			mvn clean install -U  
		
		再编译 tools-web-tools
		

－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	错误5：mvn clean install 是报错
		[ERROR] Failed to execute goal on project tools-web-tools: Could not resolve dependencies for project org.tis:tools-web-tools:war:0.0.1: Failed to collect dependencies at org.tis:tools-facade-devmgr:jar:0.0.1: Failed to read artifact descriptor for org.tis:tools-facade-devmgr:jar:0.0.1: Could not find artifact org.tis:tools:pom:${tis_tools_version} in central (https://repo.maven.apache.org/maven2) -> [Help 1]
	
	原因：错误原因同 错误4
	
	解决：

－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	警告6：[WARNING] 'version' contains an expression but should be a constant. @ org.tis:tools:${tis_tools_version}, /Users/megapro/Develop/tis/tools/pom.xml, line 9, column 11
	
	原因：不建议在版本号中使用变量定义 ${xxx_version}
	
	解决：把把版本号缓成实际值
		子工程可以不用版本号，默认maven会统一采用其父工程的版本号

－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	错误7：
		.. was cached in the local repository, resolution will not be reattempted until the update interval of nexus has elapsed or updates are forced -> [Help 1]
	
	原因：有些jar包没有完全下载完成，在编译的时候找不到jar包
	
	解决：	
		修改 setting.xml增加
		<profile> 
		  <id>nexus</id> 
		  <!--Enable snapshots for the built in central repo to direct --> 
		  <!--all requests to nexus via the mirror --> 
		  <repositories> 
		    <repository> 
		      <id>central</id> 
		      <url>http://central</url> 
		      <releases><enabled>true</enabled><updatePolicy>always</updatePolicy></releases> 
		      <snapshots><enabled>true</enabled><updatePolicy>always</updatePolicy></snapshots> 
		    </repository> 
		  </repositories> 
		<pluginRepositories> 
		    <pluginRepository> 
		      <id>central</id> 
		      <url>http://central</url> 
		      <releases><enabled>true</enabled><updatePolicy>always</updatePolicy></releases> 
		      <snapshots><enabled>true</enabled><updatePolicy>always</updatePolicy></snapshots> 
		    </pluginRepository> 
		  </pluginRepositories> 
	 </profile> 
	
－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	错误8：处理多个源码目录时，pom.xml文件报错：
	
		Description	Resource	Path	Location	Type
Plugin execution not covered by lifecycle configuration: org.codehaus.mojo:build-helper-maven-plugin:1.7:add-source (execution: add-source, phase: generate-sources)	pom.xml	/tools-web-tools	line 618	Maven Project Build Lifecycle Mapping Problem
	
	原因：不明，百度后方法如下
	
	解决：
		鼠标到报错地方，根据eclipse提示，选择“ ....ignored in Eclipse build”
		之后选择本工程名称，确定
		eclipse在pom.xml文件中自动加入了 <pluginManagement> ... </pluginManagement>
		错误提示消失
	
－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	清理工程9：
	
	原因：原始工程中包括了很多用不上的metronic模版，为了工程瘦身进行了清理
	
	清理步骤：
		//TODO @曹冬前 
		
－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	问题：如何实现 JSONArray.fromObject(responseMsg).toString()  时，只对指定属性的json化？
	
	目的：有些对象的属性信息可以被过滤掉
	
	解决：参考 JsonDateProcessor 实践一下
	
	
－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	错误：
	
	原因：
	
	解决：
－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	错误：
	
	原因：
	
	解决：
－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	错误：
	
	原因：
	
	解决：
－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	错误：
	
	原因：
	
	解决：






    