package bos.tis.biztrace.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bos.tis.biztrace.redis.AbstractRedisHandler;
import bos.tis.biztrace.utils.RunConfig;
import redis.clients.jedis.Jedis;

public class BodyInfoByRankReport extends AbstractRedisHandler{
	private Jedis jedis ;
	public static final BodyInfoByRankReport instance = new BodyInfoByRankReport() ;
	private List<Map<String,String>> results = null;
	
//	public BodyInfoByRankReport(){
//		results = new ArrayList<Map<String,String>>();
//	}
	
	public List<Map<String,String>> report(int pre_index){
		results = new ArrayList<Map<String,String>>();
		try {
			jedis = jedisPool.getResource() ;
			
			Set<String> serialNos = jedis.zrevrange(RunConfig.KP_SOTEDSET_SERIALNO_SORTBYTIME, 0, -1);
			
			int index = 0;
			for(String serialNo : serialNos){
				if(index>=pre_index){
					break;
				}
				String keyPattern = String.format(RunConfig.KP_ANALYZED_SERIALNO, serialNo) ;
				Map<String,String> result = jedis.hgetAll(keyPattern);
				results.add(result);				
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}finally{
			jedis.close();
		}
		
		return results;
	}
}
