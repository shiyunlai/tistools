
#### 开发并发布RESTFul服务

1. [详细开发请参见官方文档](http://dangdangdotcom.github.io/dubbox/rest.html "") 

1. 服务开发过程，见[《开发并发布Dubbo服务》](./开发并发布Dubbo服务.MD "")

1. 提供RESTFul服务能力

	1. 在实现类上标注annotaion（推荐），使之具备REST服务能力；

	``` java
	
	@Path("dict")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
	@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
	public class DictRServiceImpl implements IDictRService {
	
	...
		@GET
		@Path("/actual/{dictKey}/{itemValue}")
		public String queryActualValue(@PathParam("dictKey") String dictKey, @PathParam("itemValue") String itemValue)
				throws ToolsRuntimeException {
	
			return sysDictServiceExt.getActualValue(dictKey, itemValue);
		}
	...
		
	}
	
	```
	
	如上代码参考自DictRServiceImpl.java；
	注意参考tools-service-abf的POM.xml引入resteasy所需的各种jar；
	
	2. 发布
	
		方式1: 通过配置文件指定协议为 rest
	
		``` xml
		...
		<!-- use netty server -->
		<dubbo:protocol name="rest" port="8888" keepalive="true"
		server="netty" iothreads="5" threads="100" contextpath="services">

		...
		<!-- 服务发布 -->
		<dubbo:service
		interface="org.tis.tools.rservice.sys.capable.IDictRService" group="sys" version="1.0"
		ref="dictRServiceImpl"
		protocol="rest,dubbo"  timeout="3000" />

		<!-- 具体实现 -->
	 	<bean id="dictRServiceImpl" class="org.tis.tools.rservice.sys.capable.DictRServiceImpl" />
		
		...

		```
		如上代码参照： dubbo-provider-sys.xml

		方式2: 在实现类上标记annotation [为什么不在接口上标记?](#为什么不推荐在接口上标记 "")；
		
		``` java
		@Service(protocol = {"rest", "dubbo"}, group = "annotationConfig", validation = "true")
		@Path("customers")
		@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
		@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
		public class AnnotationDrivenUserRestServiceImpl implements UserRestService {
		
			...
			@GET
		    @Path("{id : \\d+}")
		    public User getUser(@PathParam("id") Long id) {
		        
		        return userService.getUser(id);
		    }
		
		}

		```
		如上代码参考自dubbox官方demo： AnnotationDrivenUserRestServiceImpl.java 
	

#### 为什么不推荐在接口上标记

	保持接口清洁，避免污染接口；
	facade工程无过多依赖jar，比如对resteasy的依赖；
	因为服务调用着需要依赖facade，如果过多依赖，让调用者难度增加；
