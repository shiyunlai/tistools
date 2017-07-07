
ABF应用框架


==========

包括以下业务域：
	OM  组织管理
	AC  权限管理
	SYS 系统管理, 如： 序号资源、错误码、系统参数....


==========


开发计划估计：

	服务端人员： 成成
	前端人员：  高杰
	
	1、OM
	2、AC
	3、SYS
	
	时间： 5月12号 OM全面完工，AC全面完工，SYS必要功能完成；

	
==========

开发中遇到的一些问题

－－－－

	问题：
		通过 StartProviderABF的main方法，启动tools-service-abf 时报错
		Initialization of bean failed; nested exception is java.lang.NoClassDefFoundError: org/apache/ibatis/session/SqlSession
	
	原因：
		
		缺少 mybatis的jar包
		
	解决：
		在父工程中统一定义mybatis
		``` xml
		...
		<mybatis.version>3.4.1</mybatis.version>
		...
		<dependency>
				<groupId>org.mybatis</groupId>
				<artifactId>mybatis</artifactId>
				<version>${mybatis.version}</version>
		</dependency>
		```
			
		在tools-service-abf中引入mybatis
		``` xml
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis</artifactId>
		</dependency>
		```