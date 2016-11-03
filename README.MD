TIS项目开发&管理工具 tools
=========================

技术背景

	基于dubbo 2.8.4a 实践分布式服务开发
	zookeeper做注册中心
	使用2.8.4a中的dubbo admin
	使用tomcat6部署dubbo admin
	使用jdk1.8


##	quick start

	//TODO 上传github后，编写quick start
	
	1、 git clone http:....
	.....
	

	come on....
	//FIXED 1、移植pm工具  (ok)
	//TODO  2、开发用户模块

##	在tools工程集中新增一个服务提供者工程步骤
	
	推荐方法：
	双击tools/pom.xml
	在Overview模式下 --> Modules --> create... --> 输入 Module Name --> 选择 maven-archetype-quickstart --> 完成
	
	或者手工建立：
	
		1、命令新建java工程（最好在tools目录下运行命令，如此tools工程集中所有工程都在同一目录下）
		
		mvn archetype:generate -DgroupId=org.tis -DartifactId=tools-test -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
		
		2、然后修改 tools/pom.xml 在<modules>节点中增加<module>tools-test</module>
		
		
		推荐maven建立工程资料：
		[Maven学习总结(三)——使用Maven构建项目](http://www.cnblogs.com/xdp-gacl/p/4240930.html,"来自cnblogs孤傲苍狼")
		
	参照 tools-service-devmgr 增加dubbo配置文件
		
##	开发规范
	
主包路径： org.tis.tools

package规范说明：
	
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
	
## 问题

1、通过maven archetype新建新增工程时，如何默认jdk版本为1.8？

2、









