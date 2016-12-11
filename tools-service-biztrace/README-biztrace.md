## 业务日志分析
	(biztrace log parse & analyse )
	
---
### 使用说明
	
	假如：bs安装路径为 ~/temp/bs
	
	cd ~/develop/tools # 定位到tools主工程路径
	mvn clean install -Dmaven.skip.test=true # 编译
	cd tools/service-biztrace/target
	cp tools-service-biztrace-0.0.1-assembly.tar ~/temp
	tar -zxvf tools-service-biztrace-0.0.1-assembly.tar 
	cd tools-service-biztrace/conf
	vi dubbo.properties # 根据情况设置 dubbo.protocol.port、dubbo.protocol.name
	vi redis.properties # 设置实际的reids地址
	cd ../bin
	./start.sh #启动日志代理服务
	./stop.sh #停止日志代理服务

	
	可通过Dubbo Admin查看当前有多少日志代理服务提供者
	tools-web-tools 启动后，会自动显示有那些日志代理服务，见：[tools-web-tools](../tools-web-tools/README.md)
		
### 功能说明

#### 业务日志分析用户场景
![业务日志分析用户场景](readme/业务日志分析-用户场景.png)

#### 业务日志分析工程规划
![业务日志分析工程规划](readme/业务日志分析工程规划.png)

#### 业务日志分析功能
![业务日志分析功能](readme/业务日志分析功能.png)

### 开发过程

－－－－
	问题：dubbo服务启动后，无法自动注入RedisClientTemplate对象到LogFileParser.java中
	
	原因：不应该使用new进行对象的创建
	
	解决：增加SpringContextUtil，从而可以借助Spring的依赖注入进行对象的创建。
		见SpringContextUtil.getBean()
	
－－－－
	问题：
	原因：
	解决：

－－－－
	问题：
	原因：
	解决：
	
－－－－
	问题：
	原因：
	解决：


