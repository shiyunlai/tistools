
>注意： 关于maven扩展插件的开发，另见[开发maven扩展插件](../tools-maven-plugin/README.MD "maven扩展插件的开发")

## 使用命令前先知道

[定义模版](#如何定义模型 "定义模型")
[特定规范说明](#存放自动生成代码的工程需要做如下依赖规范 "使用命令前要知道的一些规范")
[配置扩展maven命令生效](#使用插件的前提 "设置maven环境")

## maven插件功能包括：

[生成DAO](#使用maven命令生成dao "生成DAO")
[清理DAO](#清理maven命令生成的dao "清理DAO")

## 使用插件的前提

	前提：必须是Maven工程！

	1、方式一：只在某个项目下使用。修改工程pom.xml文件，增加以下配置，引入tools-maven-plugin插件：

```xml
	...
	<build>
		<plugins>
	      <plugin>
	        <groupId>org.tools</groupId>
	        <artifactId>tools-maven-plugin</artifactId>
	        <version>0.0.1</version>
	      </plugin>
		</plugins>
	</build>
	...
```

	2、方式二(推荐使用)：全局可用。修改maven设置 ${user.home}/.m2/settings.xml，增加如下配置：

```xml
	...
	<pluginGroups>
		...
		<pluginGroup>org.tools</pluginGroup>
		...
	</pluginGroups>
	...

	注： 此处<pluginGroup>配置的是插件工程的groupId。
	这种方法，所有工程都可以用该插件了 —— 不必每个工程的pom.xml中增加pulgin配置

```

###	使用maven命令生成dao

-----

	扫描工程中 model/ 目录下，所有*.xml 模型定义文件，并生成其中模型对应的dao层代码

	mvn tools:gen-dao

-----

	扫描工程中 model/ 目录下，所有*.erm 模型定义文件，并生成其中模型对应的dao层代码

	mvn tools:gen-dao -Dmodel.file.type=erm

-----

	扫描工程中 model/ 目录下，所有*.erm 模型定义文件，但只记载并显示模型定义情况（检查模型定义情况）

	mvn tools:gen-dao -Dmodel.file.type=erm -Djust.show=true

-----

	扫描工程中 model/ 目录下，所有*.erm 模型定义文件，只生成SYS_TEST这个模型，同时使用.../templates4erm/biz 这个目录下的模版文件生成代码

	mvn tools:gen-dao -Dmodel.file.type=erm -Dfixed.model=SYS_TEST -Dtemplates.path=/Users/megapro/Develop/tis/tools/tools-core/model/templates4erm/biz

-----

	扫描工程中 model/ 目录下，所有*.erm 模型定义文件，只生成其中jnl的应用领域，但只记载并显示模型定义情况（检查模型定义情况）

	mvn tools:gen-dao -Dmodel.file.type=erm -Dfixed.bizmodels=jnl -Djust.show=true

-----

	扫描工程中 model/ 目录下，所有*.erm 模型定义文件，只生成其中jnl的应用领域

	mvn tools:gen-dao -Dmodel.file.type=erm -Dfixed.bizmodels=jnl

-----

	更多使用说明

	mvn tools:help -Ddetail=true


### 清理maven命令生成的dao

待开发....

##	如何定义模型

### xml方式

*	参见 tools-maven-plugin/model/model-demo.xml,model-acct.xml

### ERMaster方式（推荐）

1.	下载ERMaster插件和使用手册

	链接: https://pan.baidu.com/s/1skAAGZ7 密码: 2p3x

1.	安装eclipse的ERMaster插件

	拷贝： org.insightech.er_1.0.0.v20150619-0219.jar 到 eclipse/plugins 目录，重启eclipse即可

1.	在工程路径上 右键 --> New --> Other... --> ERMaster --> ERMaster

1.	定义过程可参考手册


##	存放自动生成代码的工程需要做如下依赖规范

* 1、使用本命令的工程必须依赖 tools-base、tools-common 项目，因为生成的代码中，包括了一些已经指定好的类，如：org.fone.tools.base.WhereCondation

	```xml
	<dependency>
		<groupId>org.tis</groupId>
		<artifactId>tools-core</artifactId>
	</dependency>

	<dependency>
		<groupId>org.tis</groupId>
		<artifactId>tools-common</artifactId>
	</dependency>
	```
