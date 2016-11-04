
按以下步骤：新工程将纳入良好的maven构建管理体系内！！！

动手前说明：

	1、目前以Main方式启动dubbo服务提供者；
	
	2、Dubbo服务消费者系统类型，一般有：
		Web工程，如：在Spring MVC 中的Controller中调用服务
		桌面程序，如：在TWS中以RESTFul方式调用服务提供者的服务
		服务消费者之间，以RPC方式相互调用
	
	
## 新建一个服务提供者工程步骤如下：（例子：参加 tools-service-devmgr 工程）

1、 在父工程 tools的pom上增加模块，如： service-new；

2、 在service-new工程中新建 src/main/assembly 目录，并且拷贝 assembly.xml到该目录下（内容无需修改）；

3、 在service-new工程中新建 src/main/resources目录，并且拷贝*.xml 和 *.properties到目录下（根据工程意图修改其中的配置内容）；
	spring-context.xml Spring的配置参数
	spring-mybatis.xml mybatis的配置参数
	spring-*.xml       通过Spring集成更多功能时的配置文件
	dubbo-provider.xml 作为dubbo服务提供这的配置参数
	
	*.properties 作为如上.xml文件中抽取出来的配置属性文件（//TODO 预留将来通过disconf管理）
	
4、 	在src/test/java目录下新建 测试启动类，可直接拷贝 RunServiceDevmgr.java ，重构名称即可（无需其他修改，在eclipse中启动调试时使用）。

5、 调整service-new中的POM.xml,统一工程管理方式

5.1、删除pom.xml中以下节点（删除后maven自动取parent的值代替）:
	project-->groupId
	project-->version
	
5.2、建议每个工程的artifactId前缀都采用统一名称，如： xxx-service-trans、xxx-service-org
	
	注：5.1、5.2两个步骤同样适用于新建普通的jar工程(maven-archetype-quickstart)上

5.3、在pom.xml中加入以下build片段（本工程范围内使用，无需调整），统一安装介质形式：Main方式启动
	
	...
	
	<!-- beg:编译输出部署安装包(以Main方式启动的dubbo服务提供者可重用以下build内容) -->
	<build>
		<plugins>
			<!-- 为部署包准备shell脚本：从 common-assembly 工程中解压得到 -->
			<plugin>
				<artifactId>maven-dependency-plugin</artifactId>
				<executions>
					<execution>
						<id>unpack</id>
						<phase>package</phase>
						<goals>
							<goal>unpack</goal>
						</goals>
						<configuration>
							<artifactItems>
								<artifactItem>
									<groupId>com.bosh.tis</groupId>
									<artifactId>${project.parent.artifactId}-common-assembly</artifactId>
									<version>${tis_tools_version}</version>
									<outputDirectory>${project.build.directory}/runtime</outputDirectory>
									<includes>META-INF/assembly/**</includes>
								</artifactItem>
							</artifactItems>
						</configuration>
					</execution>
				</executions>
			</plugin>
			<!-- 根据assembly 组装并打包输出部署介质*.gz包 -->
			<plugin>
				<artifactId>maven-assembly-plugin</artifactId>
				<configuration>
					<descriptor>src/main/assembly/assembly.xml</descriptor>
				</configuration>
				<executions>
					<execution>
						<id>make-assembly</id>
						<phase>package</phase>
						<goals>
							<goal>single</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
	<!-- end:编译输出部署安装包 -->
	
	...

6、别忘记依赖必要的jar
	zookeeper
	zkclient
	dubbo
	
	//TODO 开发自己的Dubbo archetypes

## 接下来，体验良好的maven构建管理过程：

1、编译并输出部署包
	
	>mvn clean install 
	
	生成 target/*-assembly.tar.gz
	

2、启动
	
	eclipse 直接运行 src/test/java/..../RunServiceNew
	
	或
	
	将target/*-assembly.tar.gz 解压
	执行 bin/server.sh start 或 bin/start.sh
	执行 bin/server.sh stop 或 bin/stop.sh 停止服务（推荐通过脚本停止服务，而不是直接kill -9 ，因为不能享受dubbo的优雅停止能力！）
	
	//TODO 脚本中可以加入远程调试参数，通过 server.sh debug 启动并进入远程调试模式

3、基本的dubbo服务管理技巧
	
	3.1、通过 dubbo admin 查看 （动手操作即可不多讲）
	
	3.2、通过telnet ，如： telnet ip port 链接后可直接查看服务基本信息 
		（参见： http://dubbo.io/Telnet+Command+Reference-zh.htm ）


4、检测服务
	
	4.1、 //TODO 开发接口测试界面（自动代码生成）
	
	
## 在业务工程中引入并调用服务提供者提供的服务：（例子：参加 web-tools 工程）


## 问题

问题1: 采用注解的方式公布服务，但是重构接口名称后服务发布不成功，只能修改会配置文件方式发布服务；
	
	重构前，服务接口名为： com.bosh.tis.tools.devmgr.service.api.DevMgrRemoteService
	重构后，服务接口名为： org.tis.tools.service.api.devmgr.DevMgrRemoteService
	
	重启 StartProviderDevmgr ，成功
	但是在 dubbo Admin 中无法看见服务
	consumer调用时也报错，无服务
	
解决：
	
	放弃注解方式发布服务  StartProviderDevmgr //@Service(group  ....
	使用传统的配置文件方式 见： dubbo-provider-devmgr.xml
	
	重启 StartProviderDevmgr，成功
	在dubbo Admin中能看见服务
	consumer调用
	
原因：
	暂时没查到，可能是dubbo的bug


	
	
	
	
	