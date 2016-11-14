/**
 * 
 */
package org.tis.tools.service.biztrace.analyzer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.tis.tools.service.biztrace.BizTraceLogRecord;
import org.tis.tools.service.biztrace.TransStepTimeConsuming;
import org.tis.tools.service.biztrace.TransTimeConsuming;
import org.tis.tools.service.biztrace.utils.RunConfig;
import org.tis.tools.utils.BasicUtil;
import org.tis.tools.utils.TimeUtil;

import redis.clients.jedis.Jedis;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * 交易耗时分析
 * @author megapro
 *
 */
public class TransTimeConsumingAnalyzer extends AbstractAnalyzer {

	private RuntimeSchema<TransTimeConsuming> schemaTTC = RuntimeSchema.createFrom(TransTimeConsuming.class);
	private RuntimeSchema<BizTraceLogRecord> schemaBizTraceLogRecord = RuntimeSchema.createFrom(BizTraceLogRecord.class);
	
	/**
	 * <pre>
	 * 分析某笔交易的执行过程:
     * 取出一笔交易完整的biztrace
     * 根据时间排序
     * 计算首位两条记录的时间差 A
     * 将A、流水号、类型 记入sort set，以A排序的耗时排序队列（keypattern： set-date-TTC）
     * 继续完成两日志间时间差计算，形成交易步骤耗时对象 STC －Step Time Consuming
     * 在redis中新增一条耗时记录 key pattern=ttc-{serialNo} ，其value为TTC
	 * </pre>
	 * @param date
	 * @param jedis
	 */
	@Override
	protected void doAnalyzed(String date, Jedis jedis) {
		
		//取出date日所有的流水号
		String keyPattern = String.format(RunConfig.KP_SET_SERIALNOS, date) ;
		Set<String> serialNos = jedis.sinter(keyPattern) ;
		
		//根据流水号取交易日志map
		String ttcPattern = String.format(RunConfig.KP_ZSET_DATE_TTC, date) ;
		//当日总共的交互次数
		long totalRSTimes = 0;
		for( String serialNo : serialNos )
		{
			logger.debug(BasicUtil.concat("分析交易流水:",serialNo));
			
			//BiztraceAnalyResult bizAnalyResult = new BiztraceAnalyResult();
			Map<String,String> bizAnalyResult = new HashMap<String,String>();
			
			keyPattern = String.format(RunConfig.KP_MAP_SERIALNO,serialNo) ;
			Map<byte[],byte[]> transBiztrace = jedis.hgetAll(keyPattern.getBytes()) ; 
			TransTimeConsuming ttc = calculate(transBiztrace,serialNo,bizAnalyResult) ;//计算交易耗时
			ttc.serialNo = serialNo ;
			
			totalRSTimes = totalRSTimes + transBiztrace.keySet().size();
			bizAnalyResult.put("handle_time",ttc.totalTimeConsuming+"");//交易耗时
			bizAnalyResult.put("request_times",transBiztrace.keySet().size()+"");//交互次数
			bizAnalyResult.put("trans_serial",serialNo);	
			
			String kp_analyzed_serialNo = String.format(RunConfig.KP_ANALYZED_SERIALNO, serialNo) ;
			jedis.hmset(kp_analyzed_serialNo, bizAnalyResult);
			
			jedis.zadd(RunConfig.KP_SOTEDSET_SERIALNO_SORTBYTIME, ttc.totalTimeConsuming, serialNo);
			
			//保存交易耗时 ttc
			byte[] ttcKeyPattern = String.format(RunConfig.KP_STR_TTC, serialNo).getBytes() ;
			jedis.set(ttcKeyPattern, chg2Bytes(ttc)) ;
			
			//收集交易耗时
			jedis.zadd( ttcPattern.getBytes(), ttc.totalTimeConsuming, ttcKeyPattern ) ;
		}
		
		String linesPattern = String.format(RunConfig.KP_DAY_LOG_LINES, date) ;
		String totalLinesStr = jedis.get(linesPattern);
		long totalLines = 0;
		if(totalLinesStr!=null && !"".equals(totalLinesStr)){
			totalLines = Long.parseLong(totalLinesStr);
		}
		Map<String,String> dayLogSumMap = new HashMap<String,String>();
		dayLogSumMap.put("dayLogLines", totalLines+"");
		dayLogSumMap.put("dayTransSerials", serialNos.size()+"");
		dayLogSumMap.put("dayRSTimes", totalRSTimes+"");
		
		//保存当日日志汇总信息
		String kp_map_sumLogInfo = String.format(RunConfig.KP_MAP_SUMLOGINFO, date) ;
		jedis.hmset(kp_map_sumLogInfo, dayLogSumMap);
	}

	/**
	 * 将ttc转换为bytes
	 * @param ttc
	 * @return
	 */
	private byte[] chg2Bytes(TransTimeConsuming ttc) {
		return ProtostuffIOUtil.toByteArray(ttc, schemaTTC,
				LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
	}

	/**
	 * 计算某个交易的耗时
	 * @param transBiztrace 
	 * @return
	 */
	private TransTimeConsuming calculate(Map<byte[],byte[]> transBiztrace,String serialNo,Map<String,String> bizAnalyResult) {
		
		TransTimeConsuming ttc = new TransTimeConsuming() ; 
		Set<Entry<byte[],byte[]>> ens = transBiztrace.entrySet() ; 
		Iterator<Entry<byte[],byte[]>> i = ens.iterator() ;
		
		while( i.hasNext() ){
			
			Entry<byte[],byte[]> e = i.next() ; 
			
			//原biztrace对象
			BizTraceLogRecord b = schemaBizTraceLogRecord.newMessage();
			ProtostuffIOUtil.mergeFrom(e.getValue(), b, schemaBizTraceLogRecord);
			
			if(serialNo.equals(b.serialNoCorr)){//当该条日志记录为提交时的日志时
				bizAnalyResult.put("trans_date", b.date);
				bizAnalyResult.put("trans_code","");
				bizAnalyResult.put("trans_name","");
			}
						
			TransStepTimeConsuming stc = new TransStepTimeConsuming() ; 
			stc.stepLog = b ; 
			stc.stepTimes = TimeUtil.toTime(new String(e.getKey()).substring(0, 23),"yyyy-MM-dd HH:mm:ss,SSS") ;//将步骤执行时间转换为毫秒数			
			stc.stepName = b.desc ;
			ttc.stepDtl.add(stc) ;
		}
		
		//按时间从小到大(执行顺序)排序 步骤
		
		Collections.sort(ttc.stepDtl);
		
		//计算步骤耗时
		long lastStepTimes = 0 ;
		long totalTimes = 0 ;//交易总计耗时 
		for( TransStepTimeConsuming s : ttc.stepDtl ) {			
			if( 0 == lastStepTimes ){
			
				s.timeConsuming = 0 ; 

			}else{
				
				s.timeConsuming = s.stepTimes - lastStepTimes  ;
			}
			
			totalTimes += s.timeConsuming ;
			lastStepTimes = s.stepTimes ; 
		}
		
		ttc.totalTimeConsuming = Double.valueOf(totalTimes).doubleValue() ;
		
		return ttc;//返回交易耗时
	}
}
