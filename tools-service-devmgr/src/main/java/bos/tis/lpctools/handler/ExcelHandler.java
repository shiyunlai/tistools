package bos.tis.lpctools.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import bos.tis.lpctools.entity.CommitListContent;
import bos.tis.lpctools.util.HelperUtil;
import bos.tis.lpctools.util.ParamsConfig;

public class ExcelHandler {
	
	protected final JedisPool jedisPool=null ;//TODO 为了代码编译通过，临时写死
	private Jedis jedis ;
	private List<CommitListContent> commitListContents = null;
	public static final ExcelHandler instance = new ExcelHandler() ;
	
	/**
	 * 将清单内容存入redis中,其中key为清单名称
	 * @param fileName	清单名
	 * @param contents	清单内容
	 */
	public void savaExcelContent(String fileName, List<Map<String,String>> contents){
		List<CommitListContent> commitListContents = new ArrayList<CommitListContent>();
		for(Map<String,String> map : contents){
			//System.out.println(map);
			CommitListContent commitListContent = new CommitListContent(map.get("project_name"),map.get("import_type"),map.get("import_name"),
					map.get("deploy_position"),
					map.get("code_path"),
					map.get("version"),
					map.get("per_version"),
					map.get("submitter"));
			commitListContents.add(commitListContent);
		}
				
		byte[] commitListContentsByte = HelperUtil.serialize(commitListContents);
		try{
			jedis = jedisPool.getResource() ;		
			String keyPattern = String.format(ParamsConfig.KP_SERIALIZE_EXCEL_NAME, fileName.substring(0, fileName.lastIndexOf("."))) ;
			jedis.set(keyPattern.getBytes(), commitListContentsByte);
		} catch (Exception e) {
			e.printStackTrace(); 
			throw new IllegalArgumentException("存储清单内容时redis异常!");
		}finally{
			jedis.close();
		}
	}
	
	/**
	 * 	根据清单名称获取请清单内容
	 * @param fileName	清单名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CommitListContent> getExcelConetent(String fileName){
		try{
			jedis = jedisPool.getResource() ;		
			String keyPattern = String.format(ParamsConfig.KP_SERIALIZE_EXCEL_NAME, fileName.substring(0, fileName.lastIndexOf("."))) ;
			byte[] excelContentsByte = jedis.get(keyPattern.getBytes());

			commitListContents = (List<CommitListContent>) HelperUtil.deserialize(excelContentsByte);
			
			//System.out.println("commitListContents:"+commitListContents.get(0).toString());
		} catch (Exception e) {			
			e.printStackTrace();
			throw new IllegalArgumentException("获取清单内容时异常!"); 
		}finally{
			jedis.close();
		}
		return commitListContents;
	}
}
