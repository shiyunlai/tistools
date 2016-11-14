package org.tis.tools.service.biztrace.handler;

import java.util.HashSet;
import java.util.Set;

import org.tis.tools.service.biztrace.redis.AbstractRedisHandler;
import org.tis.tools.service.biztrace.utils.RunConfig;

import redis.clients.jedis.Jedis;

public class RedisHandler extends AbstractRedisHandler{
	private Jedis jedis ;
	private String redisDbSize;
	public static final RedisHandler instance = new RedisHandler() ;
	
	public String getRedisSpaceUsage(){		
		try{
			jedis = jedisPool.getResource() ;
			String redisInfo = jedis.info();
			int start_index = redisInfo.indexOf("used_memory_human:")+"used_memory_human:".length();
			int end_index = redisInfo.indexOf("used_memory_rss", start_index);
			redisDbSize = redisInfo.substring(start_index, end_index);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		
		return redisDbSize;
	}
	
	public void cleanRedisSpace(String date){		
		try{
			jedis = jedisPool.getResource() ;
						
			//取出date日所有的流水号
			String kp_date_serialNos = String.format(RunConfig.KP_SET_SERIALNOS, date) ;
			Set<String> serialNos = jedis.smembers(kp_date_serialNos) ;
			for(String serialNo : serialNos){
				
				String kp_map_serialNo = String.format(RunConfig.KP_MAP_SERIALNO, serialNo);
				jedis.del(kp_map_serialNo);
				
				String kp_str_ttc = String.format(RunConfig.KP_STR_TTC, serialNo);
				jedis.del(kp_str_ttc);
				
				String kp_analyzed_serialNo = String.format(RunConfig.KP_ANALYZED_SERIALNO, serialNo);
				jedis.del(kp_analyzed_serialNo);
				
				jedis.zrem(RunConfig.KP_SOTEDSET_SERIALNO_SORTBYTIME, serialNo);
			}
			
			String date1 = date.replace("-", "");
			String set_resolved_log_file = String.format(RunConfig.KP_SET_RESOLVED_LOG_FILE, date1);
			jedis.del(set_resolved_log_file);
			
			String set_unlinke_uuids = String.format(RunConfig.KP_SET_UNLINK_UUID, date);
			jedis.del(set_unlinke_uuids);
			
			String set_link_uuids = String.format(RunConfig.KP_SET_LINK_UUID, date);
			jedis.del(set_link_uuids);
			
			String set_serialNos = String.format(RunConfig.KP_SET_SERIALNOS, date);
			jedis.del(set_serialNos);
			
			String  set_type = String.format(RunConfig.KP_SET_REQUEST_TYPE, date,"*");
			Set<String> set_type_keys = jedis.keys(set_type);
			for(String key : set_type_keys){
				jedis.del(key);
			}
			
			String  zset_ttc = String.format(RunConfig.KP_ZSET_DATE_TTC, date);
			jedis.del(zset_ttc);
			
			String  day_log_lines = String.format(RunConfig.KP_DAY_LOG_LINES, date);
			jedis.del(day_log_lines);
			
			String  day_sumLogInfo_map = String.format(RunConfig.KP_MAP_SUMLOGINFO, date);
			jedis.del(day_sumLogInfo_map);
			
			jedis.srem(RunConfig.KP_UNANALYZED_DATE, date);
			
			jedis.srem(RunConfig.KP_SET_RESOLVED_DATE, date);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}				
	}
}
