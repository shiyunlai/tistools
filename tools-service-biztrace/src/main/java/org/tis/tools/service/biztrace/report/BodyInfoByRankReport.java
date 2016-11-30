package org.tis.tools.service.biztrace.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tis.tools.service.biztrace.helper.RunConfig;
import org.tis.tools.service.biztrace.redis.AbstractRedisHandler;

public class BodyInfoByRankReport extends AbstractRedisHandler{
	
	public static final BodyInfoByRankReport instance = new BodyInfoByRankReport() ;
	
	private List<Map<String,String>> results = null;
	
	public List<Map<String,String>> report(int pre_index){
		results = new ArrayList<Map<String,String>>();
		try {
			
			Set<String> serialNos = redisClientTemplate.zrevrange(RunConfig.KP_SOTEDSET_SERIALNO_SORTBYTIME, 0, -1);
			
			int index = 0;
			for(String serialNo : serialNos){
				if(index>=pre_index){
					break;
				}
				String keyPattern = String.format(RunConfig.KP_ANALYZED_SERIALNO, serialNo) ;
				Map<String,String> result = redisClientTemplate.hgetAll(keyPattern);
				results.add(result);				
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}finally{
		}
		
		return results;
	}
}
