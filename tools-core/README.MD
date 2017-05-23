
tools-core的职责：
	
	定义业务域的模型 —— model/目录下 erm文件
	业务域中的模型对象 —— org.tis.tools.model.po.*
	实现tools基础能力 —— 如：exception、翻页等等
	基本的异常体系 —— org.tis.tools.core.exception,
		所有异常都集成自 ToolsRuntimeException 或 ToolsException；
		各业务域实现自己的 ExceptionCodes 类，定义自己的异常码；
		异常码格式： 业务域ID-nn-nnnn；
	


============================

1. 负责定义tools中各种业务域的模型；

2. 每个文件定义一个业务模块的模型；模型中各属性含义，参见： 
	
	bronsp-maven-plugin/model/model-acct.xml
	或阅读源码 org.bronsp.maven.plugin.gendao.Field.java
	
3. 模型文件存放在model/目录下；

4. tools采取分布式开发工程结构，因此每个业务域需建立以下工程，新建工程步骤见 README中(1. 新建java工程)[../README.MD]
	
	java工程： tools-facade-业务域id
	java工程： tools-service-业务域id
	web工程：  tools-web-业务域id
	
	其中依赖关系为：
	
		‘tools-facade-业务域id’ 依赖 tools-core
		‘tools-facade-业务域id’ 依赖 tools-common
		
		‘tools-service-业务域id’ 依赖 tools-core
		‘tools-service-业务域id’ 依赖 tools-common
		‘tools-service-业务域id’ 依赖 tools-facade-业务域id

		‘tools-web-业务域id’ 依赖 tools-core
		‘tools-web-业务域id’ 依赖 tools-facade-业务域id
		‘tools-web-业务域id’ 依赖 tools-web-boss
		
		
	另外对3rd的依赖请参照 tools-service-biztrace 
	
5. 之后，在tools-core工程下执行命令： 
	
	mvn bronsp:gen-dao -Dtemplates.path=/Users/megapro/Develop/tis/tools/tools-core/model/templates/biz
	
	其中： -Dtemplates.path 指定了生成源码时采用的模版文件位置
	另外：
	mvn bronsp:clean  //清理自动生成的源码
	mvn bronsp:help   //更多命令参考 

6. 代替5，在tools-core工程下执行命令
	
	mvn tools:gen-dao -Dmodel.file.type=erm -Dfixed.bizmodels=jnl
	
	详见 ./model/命令.txt
	