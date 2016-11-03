

## TODO list

	//TODO 增加xsd自动生成功能 
	
	
## 如何使用

	vi setting.xml
	
	<pluginGroups>
	...
		<pluginGroup>org.tis</pluginGroup>
	...
	</pluginGroups>
	
	在pom文件位置执行：
	
	mvn tools:test
	
## 问题

问题1: pom.xml文件中 <packaging> 节点上提示错误信息 

	Plugin execution not covered by lifecycle configuration: org.apache.maven.plugins:maven-plugin-plugin:3.2:descriptor (execution: default-descriptor, phase: generate-resources)
 
	原因：未明
	 
	解决：在pom.xml的OverView视图下，点击eclipse中的错误提示，根据提示选择即可；

问题2: 。。。
 