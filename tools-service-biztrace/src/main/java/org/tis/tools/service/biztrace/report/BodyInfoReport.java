package org.tis.tools.service.biztrace.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tis.tools.service.biztrace.helper.RunConfig;
import org.tis.tools.service.biztrace.redis.AbstractRedisHandler;

public class BodyInfoReport extends AbstractRedisHandler{
	
	public static final BodyInfoReport instance = new BodyInfoReport() ;
	private List<Map<String,String>> results = null;
	
	public List<Map<String,String>> report(String serialNo,String date){
		results = new ArrayList<Map<String,String>>();		
		try {
			
			if("".equals(serialNo) && "".equals(date)){
				Set<String> serialNos = redisClientTemplate.zrevrange(RunConfig.KP_SOTEDSET_SERIALNO_SORTBYTIME, 0, -1);
				for(String serial_no : serialNos){
					queryBySerialNo(serial_no);
				}
			}else if("".equals(serialNo) && !"".equals(date)){
				queryByDate(date);
			}else{
				queryBySerialNo(serialNo);
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}finally{
		}
		
		return results;
	}
	
	private void queryByDate(String date){
		//取出date日所有的流水号
		String keyPattern = String.format(RunConfig.KP_SET_SERIALNOS, date) ;
		Set<String> serialNos = redisClientTemplate.smembers(keyPattern) ;
		for(String serialNo : serialNos){
			queryBySerialNo(serialNo);			
		}
	}
	
	private void queryBySerialNo(String serialNo){
		String keyPattern = String.format(RunConfig.KP_ANALYZED_SERIALNO, serialNo) ;
		Map<String,String> result = redisClientTemplate.hgetAll(keyPattern);
		results.add(result);		
	}
}
