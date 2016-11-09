package bos.tis.biztrace.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import bos.tis.biztrace.redis.AbstractRedisHandler;
import bos.tis.biztrace.utils.RunConfig;

public class BodyInfoReport extends AbstractRedisHandler{
	private Jedis jedis ;
	public static final BodyInfoReport instance = new BodyInfoReport() ;
	private List<Map<String,String>> results = null;
	
//	public BodyInfoReport(){
//		results = new ArrayList<Map<String,String>>();
//	}
	
	public List<Map<String,String>> report(String serialNo,String date){
		results = new ArrayList<Map<String,String>>();		
		try {
			jedis = jedisPool.getResource() ;
			
			if("".equals(serialNo) && "".equals(date)){
				Set<String> serialNos = jedis.zrevrange(RunConfig.KP_SOTEDSET_SERIALNO_SORTBYTIME, 0, -1);
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
			jedis.close();
		}
		
		return results;
	}
	
	private void queryByDate(String date){
		//取出date日所有的流水号
		String keyPattern = String.format(RunConfig.KP_SET_SERIALNOS, date) ;
		Set<String> serialNos = jedis.smembers(keyPattern) ;
		for(String serialNo : serialNos){
			queryBySerialNo(serialNo);			
		}			
	}
	
	private void queryBySerialNo(String serialNo){
		String keyPattern = String.format(RunConfig.KP_ANALYZED_SERIALNO, serialNo) ;
		Map<String,String> result = jedis.hgetAll(keyPattern);
		results.add(result);		
	}
	
}
