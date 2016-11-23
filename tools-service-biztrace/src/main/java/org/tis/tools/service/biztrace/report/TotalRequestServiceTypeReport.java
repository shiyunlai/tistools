/**
 * 
 */
package org.tis.tools.service.biztrace.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.tis.tools.service.biztrace.helper.RunConfig;

import redis.clients.jedis.Jedis;

/**
 * 统计指定日期各种服务类型的数量
 * @author megapro
 *
 */
public class TotalRequestServiceTypeReport extends AbstractReporter{

	@Override
	public String doReport(String date) {
		
		List<ServiceTypeStatistical> l = new ArrayList<ServiceTypeStatistical>() ;

		// 查找 set-type:2017-09-08:* 所有keypattern，然后逐个遍历累计
		String keyPattern = String.format(RunConfig.KP_SET_REQUEST_TYPE, date,"*");
		Set<String> keys = redisClientTemplate.keys(keyPattern) ;
		int types = keys.size() ;//服务类型数
		for( String key : keys ){
			ServiceTypeStatistical a = new ServiceTypeStatistical() ;
			a.serviceTypeName = key ;
			a.requestNum = redisClientTemplate.scard(key) ; //某种类型有几次请求交互
			l.add(a) ;
		}
		
		//按请求次数大小排序
		Collections.sort(l,new Comparator<ServiceTypeStatistical>() {
			@Override
			public int compare(ServiceTypeStatistical o1,ServiceTypeStatistical o2) {
				if( o1.requestNum > o2.requestNum ){

					return -1 ;
				}else if (  o1.requestNum == o2.requestNum  ){
					
					return 0 ;
				}else{
					
					return 1;
				}
			}
		});
		
		StringBuffer sb = new StringBuffer() ; 
		sb.append("日期<").append(date).append(">共发生 ").append(types).append(" 种服务类型，").append("其中各发生次数明细如下：").append("\n") ;
		
		for( ServiceTypeStatistical s : l ){
			sb.append("\t").append(s.toString()).append("\n") ;
		}
		
		return sb.toString();
	}
	
	public static class ServiceTypeStatistical{
		public String serviceTypeName ;
		public long requestNum ;
		public String toString(){
			return requestNum + "\t" +serviceTypeName.split(":")[1]+"\t"+serviceTypeName.split(":")[2];
		}
	}
}
