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
import org.tis.tools.model.po.sys.SysDictItem;

/**
 * <pre>
 * 导入业务字典
 * 
 * 直接运行main，将解析《tools-core/model/业务字典.xlsx》到SYS_DICT，SYS_DICT_ITEM
 * 
 * 注意：
 * 只是提供一个数据整理数据后，放入数据库的方法，执行导入前，请手工del两个表的数据.
 * 
 * </pre>
 * @author megapro
 *
 */
public class impSysDict {

	static final String port = "8888";
	static final String host = "http://localhost"; 
	static final String provider = "/services"; 
	static final String add_sys_dict_service = "/dict.json" ;
	static final String add_sys_dict_item_service = "/dict/item.json" ;
	
	public static void main(String[] args) {
		/*
		 * (POI)解析excel获得业务字典和业务字典项
		 * 
		 * 然后调用远程服务插入数据库
		 * addDict()
		 * addDictItm()
		 */
		//TODO @高杰
		
		
	}
	
	/**
	 * 增加一条SYS_DICT记录
	 * @param dict 业务字典
	 * @return
	 */
	static boolean addDict(SysDict dict) {
		
		String url = host + ":" + port + provider + add_sys_dict_service ;
		return callService(url, dict,MediaType.APPLICATION_JSON_TYPE ) ; 
	}
	
	/**
	 * 增加一条SYS_DICT_ITEM记录
	 * @param dictItem 业务字典项
	 * @return
	 */
	static boolean addDictItm(SysDictItem dictItem) {
		
		String url = host + ":" + port + provider + add_sys_dict_item_service ;
		return callService(url, dictItem,MediaType.APPLICATION_JSON_TYPE ) ; 
	}
	
	
	static <T> boolean callService(String url, T t , MediaType mediaType) {
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		Response response = target.request().post(Entity.entity(t, mediaType));
		try {
			if (response.getStatus() != 200) {
				System.out.println("Failed with HTTP error code : " + response.getStatus());
				System.out.println("got result: " + response.readEntity(String.class));
				return false; 
			}
			return true ; 
		} finally {
			response.close();
			client.close();
		}
	}
	
}
