/**
 * 
 */
package org.tools.design.init;

import java.io.FileNotFoundException;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;

import org.tis.tools.common.utils.BasicUtil;
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
 * 
 * @author megapro
 *
 */
public class impSysDict {

	static final String port = "8888";
	static final String host = "http://localhost";
	static final String provider = "/services";
	static final String add_sys_dict_service = "/dict.json";
	static final String add_sys_dict_item_service = "/dict/item.json";

	public static void main(String[] args) {
		
		/*
		 * (POI)解析excel获得业务字典和业务字典项
		 * 
		 * 然后调用远程服务插入数据库 addDict() addDictItm()
		 */
		// TODO @高杰
		try {
			String filepath = "/Users/gaojie/work/tistools/tools-core-basic/model/业务字典.xlsx";
			ReadExcelUtils excelReader = new ReadExcelUtils(filepath);
			// 对读取Excel表格标题测试
			// String[] title = excelReader.readExcelTitle();
			// System.out.println("获得Excel表格的标题:");
			// for (String s : title) {
			// System.out.print(s + " ");
			// }

			// 对读取Excel表格内容测试
			Map<Integer, Map<Integer, Object>> map = excelReader.readExcelContent("业务字典");
			// 遍历结果集,逐条插入
			SysDict dict = new SysDict();
			SysDictItem dictItem = new SysDictItem();
			for (int i = 1; i <= map.size(); i++) {
				Map rs = map.get(i);
				if (rs.get(0) != null && rs.get(0) != "") {
					dict.setDictName(rs.get(1).toString());
					dict.setDictKey(rs.get(0).toString());
					dict.setDictType(rs.get(5).toString());
					dict.setDictDesc(rs.get(2).toString());
				

					SysDict sd = addDict(dict);
					String Guid = sd.getGuid();
					Map<Integer, Map<Integer, Object>> map2 = excelReader.readExcelContent2(dict.getDictKey());
					for(int k = 3;k <= map2.size(); k++){
						Map rs2 = map2.get(k);
						if(rs2.get(0) != null && rs2.get(0) != ""){
							dictItem.setGuidDict(Guid);
							dictItem.setItemName(rs2.get(2).toString());
							dictItem.setItemValue(rs2.get(0).toString());
							dictItem.setSendValue(rs2.get(1).toString());
							addDictItm(dictItem);
						}
					}
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 增加一条SYS_DICT记录
	 * 
	 * @param dict
	 *            业务字典
	 * @return
	 */

	static SysDict addDict(SysDict dict) {
		
		String url = host + ":" + port + provider + add_sys_dict_service ;
		return callService(url, dict,MediaType.APPLICATION_JSON_TYPE ) ; 

	}

	/**
	 * 增加一条SYS_DICT_ITEM记录
	 * 
	 * @param dictItem
	 *            业务字典项
	 * @return
	 */

	static SysDictItem addDictItm(SysDictItem dictItem) {
		
		String url = host + ":" + port + provider + add_sys_dict_item_service ;

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

		return callService(url, dictItem,MediaType.APPLICATION_JSON_TYPE ) ; 
	}
	
	
	static <T> T callService(String url, T t , MediaType mediaType) {
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		Response response = target.request().post(Entity.entity(t, mediaType));
		try {
			if (response.getStatus() != 200) {
				System.out.println("Failed with HTTP error code : " + response.getStatus());
				System.out.println("got result: " + response.readEntity(String.class));

				return null ; 
			}

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

			T tback = (T) response.readEntity(t.getClass()) ; 
			return tback ; 

		} finally {
			response.close();
			client.close();
		}
	}

	
	
	/**
	 * 看看测试
	 * 1、 启动 tools-service-abf
	 * 2、 执行本测试
	 */
	static void test() {
		SysDict dict = new SysDict() ; 
		dict.setDictKey("SHIYL_TEST111");
		dict.setDictName("来测试");
		dict.setDictType("A");
		dict.setDictDesc("增加测试by REST");
		SysDict kkk = addDict(dict) ; 
		System.out.println(kkk);
	}

}
