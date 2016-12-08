package org.tis.tools.service.biztrace.handler;

import java.util.Set;

import org.tis.tools.service.biztrace.helper.RunConfig;
import org.tis.tools.service.biztrace.redis.AbstractRedisHandler;

public class BizLogDateHandler extends AbstractRedisHandler{
	
	private Set<String> dateList = null;
	public static final BizLogDateHandler instance = new BizLogDateHandler() ;
		
	public Set<String> getUnanalyzedBizLogDate(){
		try{
			dateList = redisClientTemplate.smembers(RunConfig.KP_UNANALYZED_DATE);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		
		return dateList;
	}
	
	public void deleteAnalyzedBizLogDate(Object[] analyzedDateArry){
		try{
			for(int i=0;i<analyzedDateArry.length;i++){
				redisClientTemplate.srem(RunConfig.KP_UNANALYZED_DATE,analyzedDateArry[i].toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
	}
	
	public Set<String> getResolvedBizLogDate(){
		try{
			dateList = redisClientTemplate.smembers(RunConfig.KP_SET_RESOLVED_DATE);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		return dateList;
	}
}
