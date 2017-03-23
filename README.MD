# TTT  
###### ___(The TIS Tools，TIS项目开发&管理工具)___
---

## 开发成员

	shiyunlai55555
	hanbenyuan
	caodongqian
	史云来 分支开发测试
	branch work test 2016 12 16

## 技术背景

> 实践分布式服务开发  
>>  
 基于 *[dubbo 2.8.4a](https://github.com/shiyunlai/tistools.git)*  
 采用 *zookeeper* 做注册中心  
 使用2.8.4a中的dubbo admin  
 使用tomcat6部署dubbo admin  
 使用jdk1.8  
 more...

## 主要功能
> 目录  
>> [用户权限](/tools-service-user/README-user.md)  
>> [开发管理](/tools-service-devmgr/README-devmgr.md)  
>> [业务日志分析](/tools-service-biztrace/README-biztrace.md)  
>> [对maven的扩展](/tools-maven-plugin/README-tools-maven-plugin.md)  

## TODO list

### 框架开发

	//OK 加入dubbo能力
	//OK 对redis使用方式的重构
	//OK 开发一个公共调用controller的页面： 输入controller地址、post/get.. 协议、调用数据，调用结束，无格式要求，把返回结果展示出来；
	//OK 集成disconf
	//OK disconf改造 tools-service-XXX
	//Ok disconf改造 tools-web-XXX 
	//OK 增加 tools-maven-plugin 的使用说明，为应用模块开发做准备（目前 mvn brons:gen-sdo 不适用分布式项目开发）

	//TODO 增加单元测试能力  分支：develop_use_junit_springtest
	//TODO 动态菜单树
	//TODO 增加tools工程集的持续继承能力 jenkins
	//TODO 扩展dubbo的路由，实现根据ip:port调用指定的服务提供者 
		思路： 找github上dubbo的分支，看其他开发者有无这方面的开发经验
	//TODO 如何通过mvn编译后输出支持集中式部署的系统
	
### 应用开发(整个tools作为基础应用平台)
	
	//TODO 设计并开发交易流水管理模块(jnlmgr)

	//TODO 开发机构用户模块（机构、业务机构、岗位、组织、人员）
	//TODO 开发权限管理模块(角色、操作员、数据操作权限、应用功能操作权限、操作员角色权限)
	//TODO 移植brons中的交易引擎参数管理功能
	
		
##	quick start

1. git clone https://github.com/shiyunlai/tistools.git

2. mvn eclipse:eclipse

3. mvn clean install

4. 导入，eclipse --> import... --> Existing Maven Porjects...

5. 运行服务提供者，eclipse定位到StartProviderDevmgr类，右键->Run as->Java Application

5. 启动服务消费者tools-web-tools  

	mvn tomcat7:run  

5. 访问应用 http//localhost:8089/tis/tools/index.html  

5. 测试dubbo服务调用，直接在浏览器中输入http地址: http://localhost:8089/tis/tools/testController/calldubbo

##	开发说明

###	如何在tools工程集中新增一个dubbo服务提供者XXX  

#### 1. 新建java工程(必须是maven工程)  

1. 推荐方法：  

	双击tools/pom.xml，增加模块(module)  
	在Overview模式下 --> Modules --> create... --> 输入 Module Name --> 选择 maven-archetype-quickstart --> 完成

1. 手工建立：

	命令新建java工程tools-test（最好在tools目录下运行命令，如此工程集所有工程都在同一目录下）

	```
	mvn archetype:generate -DgroupId=org.tis -DartifactId=tools-test -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
	```

	然后修改 tools/pom.xml 在<modules>节点中增加<module>tools-test</module>，完成。

	maven建立工程资料：[Maven学习总结(三)——使用Maven构建项目](http://www.cnblogs.com/xdp-gacl/p/4240930.html,"来自cnblogs孤傲苍狼")

1. 注意：
	archetypeArtifactId=maven-archetype-quickstart 建立java工程
	archetypeArtifactId=maven-archetype-webapp 建立web工程

#### 2. 增加dubbo配置（作为服务提供者）  

1. 参照 tools-service-devmgr 增加dubbo配置文件:  

	src/main/assembly/assembly.xml  
	src/main/resources/META-INF/spring/dubbo-provider.xml  
	src/main/resources/META-INF/spring/dubbo-provider-***.xml  
	src/main/resources/META-INF/spring/spring-context.xml
	src/main/resources/META-INF/dubbo.properties

1. 增加dubbo所需jar

	``` xml
	<!-- zookeeper & zkclient -->
	<dependency>
		<groupId>org.apache.zookeeper</groupId>
		<artifactId>zookeeper</artifactId>
		<exclusions>
			<exclusion>
				<artifactId>log4j</artifactId>
				<groupId>log4j</groupId>
			</exclusion>
			<exclusion>
				<groupId>org.slf4j</groupId>
				<artifactId>slf4j-log4j12</artifactId>
			</exclusion>
		</exclusions>
	</dependency>
	<dependency>
		<groupId>com.101tec</groupId>
		<artifactId>zkclient</artifactId>
	</dependency>

	<!-- dubbo -->
	<dependency>
		<groupId>com.alibaba</groupId>
		<artifactId>dubbo</artifactId>
	</dependency>
	```

#### 3. 增加编译脚本（为了maven编译输出可部署的安装介质）  

``` xml  
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
```

#### 4. 增加启动类  

	src/test/java/.../StartProviderXXX.java，内容固定为：
``` java
	public class StartProviderXXX {
		/**
		* @param args
		*/
		public static void main(String[] args) {
			com.alibaba.dubbo.container.Main.main(args);
		}
	}
```

#### 5. 建议为工程增加 README.md  

	记录模块中的功能，开发中遇到的问题等重要信息。

###	如何在tools工程集中新增一个dubbo消费者工程 CXXX ？

#### 1. 创建工程(必须是maven工程)  

1. 步骤参考 [如何在tools工程集中新增一个dubbo服务提供者XXX ？] (#如何在tools工程集中新增一个dubbo服务提供者xxx)
2. 消费者工程类型，目前可以是jar或war
	```xml
	<packaging>war</packaging>
	<packaging>jar</packaging>
	```

#### 2. 参照tools-web-tools工程，增加dubbo配置（作为消费者）
1. src/main/resources/dubbo-consumer.xml  
2. src/main/resources/dubbo-reference-XXX.xml  
3. edit-> applicationContext.xml 引入dubbo配置，以便Spring启动时拖起dubbo  
``` xml  
	<!-- 引入Dubbo配置 -->
    <import resource="dubbo-consumer.xml" />
```

#### 3. 增加dubbo所需jar包
``` xml
<!-- dubbo 相关jar start -->
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>dubbo</artifactId>
</dependency>
<dependency>
	<groupId>com.101tec</groupId>
	<artifactId>zkclient</artifactId>
</dependency>
<dependency>
	<groupId>org.apache.zookeeper</groupId>
	<artifactId>zookeeper</artifactId>
</dependency>
<!-- dubbo 相关jar end -->
```

#### 4. 如果是工程类型为 war，增加maven启动配置( 完整配置可参照 tools-web-tools/pom.xml )  

方式一： 使用tomcat7(推荐)
``` xml
<plugin>
	<groupId>org.apache.tomcat.maven</groupId>
	<artifactId>tomcat7-maven-plugin</artifactId>
	<version>2.1</version>
	<configuration>
		<port>8089</port><!-- port，根据需要修改 -->
		<path>/tis</path><!-- 服务名，根据需要修改 -->
		<uriEncoding>UTF-8</uriEncoding>
		<finalName>tis</finalName>
		<server>tomcat7</server>
	</configuration>
</plugin>
```
方式二：使用jetty
``` xml
...

<jetty.version>8.1.3.v20120416</jetty.version>

...

<plugin>
	<groupId>org.mortbay.jetty</groupId>
	<artifactId>jetty-maven-plugin</artifactId>
	<version>${jetty.version}</version>
	<configuration>
		<webApp>
			<contextPath>/tis</contextPath>
		</webApp>
	</configuration>
</plugin>
```


#### 5. 启动工程（工程类型为war）  

mvn tomcat7:run  
或  
mvn jetty:run  

访问： http://localhost:port/tis/tools/index.html  
![tools](./tis-tools.png "tools主界面")

###	开发规范

#### package规范：

//FIXME 新的想法，更符合思维逻辑习惯，tools-service-jnl 模块已经如此规范，并且 tools 的maven插件模版也如此实施
	
	[公司].[产品].[系统分层].[业务域].[业务域功能模块]
	如：
	org.tis.tools.rservice.jnl.integrate
	[公司.产品] org.tis.tools
	[系统分层]  rservice
	[业务域]   jnl
	[功能模块]  integrate
	
	把系统分层放在业务域前的目的是让配置文件中可以统一，不用区分业务域；
	
	//另见，![包命名方案](./tis-package-named-rule.png "包命名逻辑")
	
	组织.产品.功能.限定.模块

	组织： org.tis
	产品： tools

	功能： 如：service服务、model模型...，详情见下表：


|   功能         | 说明                                                   |
| :------------- | :----------------------------------------------------- |
|util         |业务域内的基础工具实现 |
|helper       |帮助类，一些顶层逻辑的编写和整合 |
|base         |基础能力，同common一样，但偏向业务域范围内的‘基础’|
|model        |数据模型定义，其中可进步分类为：po、vo、dto等用途(见：功能类型限定)|
|dao          |持久层功能实现|
|biz          |业务逻辑层，完成业务域中的业务逻辑实现|
|controller   |业访问控制，处理业务领域范围内的action请求，一般只做服务提供者时无这个功能划分|
|service      |服务，业务领域对外提供的服务能力|
|more by req  |根据实际情况扩充|


	限定： 如：model功能时，会有vo、po、dto....，service时会有api、impl等.....，详情见下表：

  限定        |  说明     
-------------|-------------
api          |API接口定义，API是对外提供的服务接口，一旦提供很少变更，建议兼容而不是变更
spi          |SPI接口定义，SPI是模块为支持不同的实现预留的内部扩展接口，参加jdbc
impl         |对某个业务域中功能的实现
po           |持久对象(persistant object)，对应数据模型(数据库)，可以看成是与数据库中的表相映
             |射的java对象.最简单的PO就是对应数据库中某个表中的一条记录,多个记录可以用PO的集合
			 |（PO本身也可有部分业务逻辑的处理）。
			 |如： com.bronsp.user.model.po
vo           |值对象(value object)，仅仅包含数据，根据需要抽象出的业务对象,可以和表对应,也可以
             |不，常用于返回界面中需要的数据信息。
			 |如： com.bronsp.user.model.vo
dto          |传输对象(data transfer object)，对远程调用等需要大量传输对象的抽象。
             |如： com.bronsp.user.model.dto
more by req  |API接口定义，API是对外提供的服务接口，一旦提供很少变更，建议兼容而不是变更


	模块： 如：devmgr 开发管理，user 用户....

	package举例：
		org.tis.tools.service.api.devmgr
		org.tis.tools.service.impl.devmgr
		org.tis.tools.model.po.devmgr
		org.tis.tools.model.vo.devmgr
		org.tis.tools.model.dto.devmgr
		...

#### 单元测试规范：

1. 使用Junit 4进行单元测试；
1. 提供给外部模块调用的方法都必须有单元测试；
2. 通过AllTests.java以套件的方式组织所有单元测试类，如下：
``` java
	/**
	 * bronsp-maven-plugin 工程的测试套件
	 * @author megapro
	 *
	 */
	@RunWith(Suite.class)
	@SuiteClasses({
			HumpClassNameTest.class,
			CommonUtilTest.class,
			FreeMarkerUtilTest.class,
			KeyWordUtilTest.class,
			Xml22BeanUtilTest.class
		})
	public class AllTests {}
```

#### 持续集成规范：//TODO


## 问题

1、通过maven archetype新建新增工程时，如何默认jdk版本为1.8 ？  
	解决：
	设置Maven全局setting.xml，在 profiles 节点之间增加一下配置  
``` xml
<profiles>
...
	<profile>
	    <id>jdk18</id>
	    <activation>  
	        <activeByDefault>true</activeByDefault>  
	        <jdk>1.8</jdk>  
	    </activation>
	    <properties>
	        <maven.compiler.source>1.8</maven.compiler.source>
	        <maven.compiler.target>1.8</maven.compiler.target>
	        <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
	    </properties>  
	</profile>
...
</profiles>
```

2、
