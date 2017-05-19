1.biztrace.properties
2.disconf.properties   *** 不用上传 diconf-web，新部署时根据需求做微调（disconf配置版本信息）
3.dubbo.properties
4.log4j.xml
5.mybatis.properties
6.redis.properties

eclipse运行时：均需要这些properties

打包部署时（见assembly.xml）：
	2 部署到 conf
	1,3,4,5,6 上传到disconf-web，可以不打包到 jar中
	