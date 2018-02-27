## 开发中的问题

	1、随着应用功能的完善、重构而变化，开发过程中的数据库表结构、基础数据经常需要调整；
	2、开发中以SQL脚本，或者java程序（数据迁移程序）进行数据库调整；
	3、这些数据库变动，需要在开发环境、测试环境、生产环境执行，以同步这些更新；
	4、每个环境都要手工逐个执行脚本，费时，顺序错误还有可能前功尽弃...
	5、团队开发时，协作问题（容易无次序，遗漏数据库变更）；

## 解决方法

	通过flyway，进行这些数据库脚本(migrations)的管理.
	包括对脚本命名规范
&emsp;&emsp;[命名规范](#flyway的migration文件命名规范)

	记录数据库变化内容的方式：
&emsp;&emsp;[sql文件](#sql脚本方式的migration)
&emsp;&emsp;[java方式](#java方式的migration)

	以及在各环境中的同步生效(flyway的6个命令)；


## 快速使用

	开发阶段

	1、对数据库修改的脚本按照命名规范起名，顺序放在 src/main/resources/db/migration 目录下
	2、生效脚本，在工程下执行 mvn flyway:migrate

	部署运行

	1、在服务器上安装flyway
	2、然后执行 flyway migrate，会依次执行数据库脚本，完成数据库建设

	更多细节请继续往下看！

## 使用flyway能获得哪些功能

	1、管理数据库脚本，进而管理数据库版本；
	2、提供.sql，.java 方式的数据库脚本方式；
	3、可用mvn flyway:xxxx 命令进行数据库版本管理；
	4、可在系统启动时，自动执行数据库版本同步；


## flyway的原理

&emsp;[见官网](https://flywaydb.org/documentation/migrations )
&emsp;[快速掌握和使用Flyway](https://blog.waterstrong.me/flyway-in-practice/ )

## 如何使用

	先看tools的架构，基于dubbo进行分布式应用拆分，模块化开发。
	于是，tools中每个应用模块都有自己的数据库，因此在provider工程中使用flyway。

	按照flyway的规约（约定大于配置），数据库变动脚本(migration)默认存放在classpath路径下的 db/migration目录下,命名规范如下：

### flyway的migration文件命名规范

	并且脚本／java类命名规范必选为：

	[Prefix][Version][Separator(two underscores)][Description][Suffix]

		如：Versioned Migrations

			V1__description.sql
			V201712171433.1__description.java

		Undo Migrations

			U1__description.sql
			U2017-12-13.1__description.java

		Repeatable Migrations

			R1__description.sql
			R2__description.java

	结构说明：
	Prefix: V for versioned (configurable),
			U for undo (configurable) and
			R for repeatable migrations (configurable)
	Version: Version with dots or underscores separate as many parts as you like (Not for repeatable migrations)
	Separator: __ (two underscores) (configurable)
	Description: Underscores or spaces separate the words
	Suffix: .sql (configurable)

	对flyway的使用分为开发阶段，和运行阶段：

&emsp;(开发阶段使用)[#开发阶段使用方法 ]
&emsp;(运行阶段使用)[#运行阶段使用方法 ]


### sql脚本方式的migration

	就是普通的sql脚本，把数据库的变化通过SQL记录起来，如果考虑回滚时，需要提供对应的回滚脚本(U)。
	@史云来 举例

### java方式的migration

	在java代码中完成数据库的修改（比如：处理BLOB、CLOB等大字段时，一般需要java代码）
	一般实现 JdbcMigration、或 SpringJdbcMigration 接口
	@史云来 举例


### 开发阶段使用方法

	通过maven命令使用flyway。

&emsp;[在pom.xml中引入以下配置内容](#pom.xml中的flyway配置 )

	之后，使用以下命令进行数据库变化同步：

``` shell

	mvn flyway:info			显示当前数据库版本及变化历史

	mvn flyway:baseline		从当前起建立基线。执行时会自动建立flyway_version_history表，并生成一条version为1的基线记录（举例：已经存在的系统，要用flyway管理数据库数据库时，可以先做一次mvn flyway:baseline 然后开始进入flyway的机制。可以修改自动生成的版本号，以适应自己的version规则）

	mvn flyway:migrate		发现并生效最新的数据库版本（SQL脚本、java类）

	mvn flyway:validate		验证所有数据库脚本是否已经在当前数据库中生效

	mvn flyway:undo			执行前缀为U的脚本（U为回滚脚本，与V脚本对应，写V时一起提供。开源版不支持undo功能，需要回滚时，请手工执行U脚本）

	手工代替undo： 对需要回滚的数据库变更，准备U前缀的回滚脚本，如果是SQL，直接在数据库环境运行，如果是java，可通过java调用main运行；

	mvn flyway:repair		用官网的原话Remove failed migration entries (only for databases that do NOT support DDL transactions);Realign the checksums, descriptions and types of the applied migrations with the ones of the available migrations.
	例如：当修改已经执行过的脚本文件名后（不能修改版本号），可运行repair同步到flwyway_schema_history表中对应的纪录上

	mvn flyway:clean		清除当前数据库所有内容（退回到新建database，为了重头再来一次建立数据库,比如：大规模的调整了数据库表、初始数据，还不如重新建一次数据库环境）

```


### 运行阶段使用方法

#### 方式1: 系统启动时，自动进行数据库版本同步  @史云来  未完待续

	provider启动时自动执行（如果集群部署时要考虑，每个provider的启动先后顺序，先启动一个完成数据库版本同步，之后再启动其他）

	1、[pom.xml中加入配置](#运行阶段方式1需要引入对flyway的依赖)
	1、[在spring配置文件中加入以下配置](#spring配置文件中的flyway配置 )
	2、[在start.sh脚本文件中加入以下配置](#spring配置文件中的flyway配置 )
	3、之后直接start.sh启动provider模块即可 （provider中的FlywayMigration.java会先同步数据库版本）
	4、但是，如果要回滚或者migrate之外的功能，只能参照方式2；

#### 方式2: 系统启动前，手动执行数据库版本同步  @史云来 未完待续

	在运行服务器上用cli命令完成数据库版本同步；

	1、在provider服务启动前操作；
	2、在部署服务器上安装 [flyway的lic工具](https://flywaydb.org/getstarted/firststeps/commandline)，然后执行数据库版本命令

		如： $ flyway -user=myuser -schemas=schema1,schema2 -placeholders.keyABC=valueXYZ migrate

	3、注意，数据库脚本必选放到规定的路径；如：/home/flyway/sql ,具体参见 [flyway的cli使用说明](https://flywaydb.org/documentation/commandline/ )

#### 运行阶段方式1需要引入对flyway的依赖

``` xml
<project>
	......
	<dependencies>
		......
		<!-- flyway依赖 -->
		<dependency>
			<groupId>org.flywaydb</groupId>
			<artifactId>flyway-core</artifactId>
		</dependency>
		......
	</dependencies>
	......
</project>
```

#### pom.xml中的flyway配置

开发阶段使用

```xml
	......
	<build>
		<plugins>
		......
			<!-- 使用flyway 在开发阶段，对开发环境进行数据库版本管理 -->
			<plugin>
				<groupId>org.flywaydb</groupId>
				<artifactId>flyway-maven-plugin</artifactId>
				<version>5.0.3</version>
				<!-- 根据数据库做不同的configuration配置 -->
				<configuration>
					<driver>com.mysql.jdbc.Driver</driver>
					<url>jdbc:mysql://139.196.145.67:3306/toolsdev?useUnicode=true&amp;characterEncoding=utf8</url>
					<user>toolsdev</user>
					<password>toolsdev</password>
					<!-- 设置sql脚本文件的编码 -->
					<encoding>UTF-8</encoding>
					<!-- 设置执行migrate操作之前的validation行为 -->
					<validationMode>ALL</validationMode>
					<!-- 设置当validation失败时的系统行为 -->
					<validationErrorMode>FAIL</validationErrorMode>
				</configuration>
				<dependencies>
					<!-- 根据数据库做不同的添加不同的jdbc包 -->
					<dependency>
						<groupId>mysql</groupId>
						<artifactId>mysql-connector-java</artifactId>
						<version>${mysql-version}</version>
					</dependency>
				</dependencies>
			</plugin>
		......
		</plugins>
	</build>
```

	注意：以上配置使用mysql数据库，如果其他数据库请参考[官方说明](https://flywaydb.org/documentation/database/oracle)进行修改；

#### spring配置文件中的flyway配置

运行阶段使用

新建 spring-flyway.xml 内容如下
``` xml



```

修改其他配置中 使用了 datasource的地方，加上 depends-on="flyway"

如：spring-mybatis.xml 中

``` xml
	......
	<!-- define the SqlSessionFactory -->
	<bean id="sqlSessionFactory1" class="org.mybatis.spring.SqlSessionFactoryBean" depends-on="flyway">
		<!-- 实例化sqlSessionFactory时需要使用上述配置好的数据源以及SQL映射文件 -->
		<property name="dataSource" ref="dataSource" />
		<property name="typeAliasesPackage"
			value="org.tis.tools.dao.ac,
				   org.tis.tools.dao.om,
				   org.tis.tools.dao.sys" />
	</bean>
	......
```
#### 例子

	请看 tools-service-abf 工程
	注：该工程采用‘方式1’做运行时的数据库版本管理


### 一些学习资料：

	&[Flyway 官网地址](https://flywaydb.org/ )

		一看机会的网友博客
	[数据库版本管理工具Flyway——基础篇](http://casheen.iteye.com/blog/1749916 ) 可借鉴套路，但是其中使用的flyway版本太老，配置部分无参考意义
