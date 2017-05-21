//TODO List

### 框架开发

	//OK 加入dubbo能力
	//OK 对redis使用方式的重构
	//OK 开发一个公共调用controller的页面： 输入controller地址、post/get.. 协议、调用数据，调用结束，无格式要求，把返回结果展示出来；
	//OK 集成disconf
	//OK disconf改造 tools-service-XXX
	//Ok disconf改造 tools-web-XXX 
	//OK 增加 tools-maven-plugin 的使用说明，为应用模块开发做准备（目前 mvn brons:gen-sdo 不适用分布式项目开发）
	//OK rest相关的annotation放在实现类 见： DictRServiceImpl
	//OK 同时发布dubbo和rest两种服务协议 见： DictRServiceImpl

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