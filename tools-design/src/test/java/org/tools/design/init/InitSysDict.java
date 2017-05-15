/**
 * 
 */
package org.tools.design.init;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.tis.tools.model.po.sys.SysDict;

/**
 * 
 * 初始化业务字典
 * 
 * @author megapro
 *
 */
public class InitSysDict {

	
	public static void main(String[] args) {
		/*
		 * 解析excel获得业务字典和业务字典项
		 * 
		 * 然后调用远程服务插入数据库
		 * 
		 */
		final String port = "8888";
		addDict("http://localhost:" + port + "/services/dict.json", MediaType.APPLICATION_JSON_TYPE);
		
	}
	
	static void addDict(String url, MediaType mediaType) {
		System.out.println("Registering user via " + url);
		SysDict dict = new SysDict() ; 
		dict.setDictKey("TEST_TSET");
		dict.setDictName("测试测试");
		//dict.setDictType("A");
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		Response response = target.request().post(Entity.entity(dict, mediaType));

		try {
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
			}
			System.out.println("Successfully got result: " + response.readEntity(String.class));
		} finally {
			response.close();
			client.close();
		}
	}
}
