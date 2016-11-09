package bos.tis.biztrace.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bos.tis.biztrace.redis.AbstractRedisHandler;
import bos.tis.biztrace.utils.RunConfig;
import redis.clients.jedis.Jedis;

public class DayLogInfoSumReport extends AbstractRedisHandler{
	private Jedis jedis ;
	public static final DayLogInfoSumReport instance = new DayLogInfoSumReport() ;
	private Map<String,String> result = null;
	
	public Map<String,String> report(String date){
		try {
			jedis = jedisPool.getResource() ;
			
			String keyPattern = String.format(RunConfig.KP_MAP_SUMLOGINFO, date) ;
			result = jedis.hgetAll(keyPattern);						
		} catch (Exception e) {
			e.printStackTrace(); 
		}finally{
			jedis.close();
		}
		
		return result;
	}
}
