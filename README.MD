# TTT  
###### ___(The TIS Tools，TIS项目开发&管理工具)___
--------

##	quick start

1. git clone https://github.com/shiyunlai/tistools.git

2. mvn eclipse:eclipse

3. mvn clean install

4. 导入，eclipse --> import... --> Existing Maven Porjects...

5. 运行服务提供者，eclipse定位到StartProviderDevmgr类，右键->Run as->Java Application

5. 启动 tools-web-tools  

	mvn tomcat7:run k

5. 访问应用 http//localhost:8089/tis/tools/index.html  

5. 测试dubbo服务调用，直接在浏览器中输入http地址: http://localhost:8089/tis/tools/testController/calldubbo

--------

##	[基于github开发协作](./readme/基于Github开发协作.MD "开发管理")

![MDT分支策略](./readme/img/Git-MDT.png)

--------

## 技术背景

 基于 *[dubbo 2.8.4a](https://github.com/shiyunlai/tistools.git)*  
 采用 *zookeeper* 做注册中心  
 使用2.8.4a中的dubbo admin  
 使用tomcat6部署dubbo admin
 使用disconf进行配置管理
 jdk1.8  

## 主要功能

[用户权限](./tools-service-user/README-user.md "")  
[开发管理](./tools-service-devmgr/README-devmgr.md "")  
[业务日志分析](./tools-service-biztrace/README-biztrace.md "")  
[对maven的扩展](./tools-maven-plugin/README-tools-maven-plugin.md "")  

--------

## [TODO list](./readme/simple-plan.MD "工作计划")

--------

##	开发说明

#### [如何在tools工程集中新增一个dubbo服务提供者XXX](./readme/新增一个服务提供者工程.MD "")

#### [如何在tools工程集中新增一个dubbo消费者工程XXX](./readme/新增一个服务消费者工程.MD "")

--------

##	开发规范

#### [工程命名规范](http://www.bronsp.com:8060/index.php?s=/5&page_id=106 "工程命名")

#### [package规范](http://www.bronsp.com:8060/index.php?s=/5&page_id=107 "package命名")

#### [单元测试规范](./readme/如何进行单元测试.MD "单元测试")

#### [持续集成规范](./readme/如何进行持续集成.MD "持续集成")

--------

## [问题](./readme/问题收集.MD "关于问题")

--------

## 开发成员

	shiyunlai
	gaojie
	zhaochunhai
