### 记录对tools的设计代码

src/main/txengine 交易引擎设计思考

### 作为consumer对服务提供者进行单元测试

>规定

	1、服务提供者对外提供的服务，如: version="0.9" 表示拥有单元测试、version="1.0"为正式环境版本；
	2、单独运行单元测试 
		
		cd tools-design
		mvn test
	
	3、运行服务单元测试前，请提供对应的服务模块；
	


