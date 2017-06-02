### 本工程意义何为

1. 本工程不具备独立部署能力；
1. 专门实现服务提供者的公共能力、基础能力；
1. 服务提供者（tools-service-***）依赖本工程，并使用其中的能力；
1. 如果这些基础能力不适应特定服务提供者的需求，可在服务提供者工程中自行实现；

### 都有哪些公共能力？

1. Dubbo提供rest服务时的异常拦截，确保服务提供者抛处的异常，可回传给服务调用者（包括非java系统）
	
	org.tools.common.dubbo.rest.suppor.ExceptionMapperSupport
	
	使用
		<dubbo:protocol name="rest" port="8888" keepalive="true"
		server="netty" iothreads="5" threads="100" contextpath="services" 
		extension="org.tools.common.dubbo.rest.support.ExceptionMapperSupport, xxx.xxx"/>

