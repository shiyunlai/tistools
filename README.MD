# TTT  
###### ___(The TIS Tools，TIS项目开发&管理工具)___
---

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

	//TODO 开发机构用户模块
	//TODO 集成gendao能力
	//TODO 增加tools工程集的持续基础能力

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

1. 步骤参考[如何在tools工程集中新增一个dubbo服务提供者XXX ？](#如何在tools工程集中新增一个dubbo服务提供者xxx)
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

	组织.产品.功能.限定.模块

	组织： org.tis
	产品： tools

	功能： 如：service服务、model模型...
	限定： 如：model功能时，会有vo、po、dto....，service时会有api、impl等.....
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
