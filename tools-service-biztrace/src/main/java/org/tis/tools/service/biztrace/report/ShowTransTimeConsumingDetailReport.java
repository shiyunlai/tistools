/**
 * 
 */
package org.tis.tools.service.biztrace.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tis.tools.service.biztrace.BizTraceLogRecord;
import org.tis.tools.service.biztrace.helper.RunConfig;

import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * 某笔交易的耗时分析
 * @author megapro
 *
 */
public class ShowTransTimeConsumingDetailReport extends AbstractReporter {

	private RuntimeSchema<TransactionTimeConsuming> schemaTTC = RuntimeSchema
			.createFrom(TransactionTimeConsuming.class);

	private String serialNo = null ; 
	
	public static List<Map<String,String>> results = null;
	
	public void setSerialNo(String serialNo){
		this.serialNo = serialNo ; 
		
		results = new ArrayList<Map<String,String>>();
	}

	/**
	 * 交易耗时
	 */
	public static class TransactionTimeConsuming{
		
		public String serialNo = null ; //交易流水
		public String transCode = null ; //交易代码
		public String transName = null ; //交易名称
		public Double totalTimeConsuming ;//总耗时
		public List<StepTimeConsuming> stepDtl = new ArrayList<StepTimeConsuming>() ;
		
		public String toString(){
			return toString(false) ;
		}
		
		/**
		 * 展示耗时信息
		 * @param isDetail true展示细节
		 * @return
		 */
		public String toString(boolean isDetail){

			StringBuffer sb = new StringBuffer() ; 
			sb.append("流水号: ").append(serialNo)
			.append(" 交易名称: ").append(transCode).append(" ").append(transName)
			.append(" 办理过程总耗时: ")
			.append(totalTimeConsuming).append("毫秒,")
			.append(totalTimeConsuming/1000).append("秒,")
			.append(totalTimeConsuming/1000/60).append("分") ; 
			if (isDetail) {
				sb.append(" 耗时步骤详情为:").append("\n");
				sb.append("\t\t秒")
					.append("\t毫秒")
					.append("\t\t执行时间点")
					.append("\t\t\t时间点毫秒数")
					.append("\t描述")
					.append("\n");
				for (StepTimeConsuming stc : stepDtl) {
					sb.append("\t\t").append(stc.toString(isDetail));
				}
			} else {
				sb.append("\n");
			}
			return sb.toString() ;
		}
	}
	
	/**
	 * 步骤耗时
	 * @author megapro
	 *
	 */
	public static class StepTimeConsuming{
		
		public String stepName = null ; //步骤
		public long setpTimes = 0 ; //步骤时点（毫秒数）
		public long timeConsuming = 0 ; //步骤耗时（减去前一步骤时点所得结果）
		public BizTraceLogRecord stepLog = null ; //步骤对应的biztrace日志
		
		public String toString(){
			return toString(false) ;
		}
		
		public String toString(boolean isDetail){
			
			StringBuffer sb = new StringBuffer() ; 
			sb.append(timeConsuming/1000).append("\t")
			.append(timeConsuming).append("\t\t")
			.append(stepLog.time).append("\t")
			.append(setpTimes).append("\t");
			if( isDetail ){
				sb.append(stepLog.uuid).append("\t") ;
			}
			sb.append(stepName);
			if( isDetail ){
				sb.append(stepLog.data).append("\n") ;
			}else{
				sb.append("\n") ;
			}
			return sb.toString() ;
		}
	}
	
	/**
	 * 报告某个交易耗时详情
	 * @return 
	 */
	@Override
	protected String doReport(String date) {
		
		if( StringUtils.isEmpty( this.serialNo ) ){
			throw new RuntimeException("必须指定一个交易流水号！");
		}
		
		//交易耗时有序set的key
		String keyPattern = String.format(RunConfig.KP_STR_TTC, this.serialNo) ; 
		//StringBuffer sb = new StringBuffer() ;
		
		//取交易耗时对象
		byte[] b = redisClientTemplate.get( keyPattern.getBytes() ) ; 
		
		TransactionTimeConsuming ttc = schemaTTC.newMessage();
		ProtostuffIOUtil.mergeFrom(b, ttc, schemaTTC);//反序列化为对象
		
		List<StepTimeConsuming> stepDtls = ttc.stepDtl;
		for(StepTimeConsuming stc : stepDtls){
			String uuid = stc.stepLog.uuid;
			String time = stc.stepLog.time;
			String timeConsuming = stc.timeConsuming+"";
			String desc = stc.stepLog.desc;
			String data = stc.stepLog.data;
			
			Map<String,String> stepDtl = new HashMap<String,String>();
			stepDtl.put("uuid", uuid);
			stepDtl.put("time", time);
			stepDtl.put("timeConsuming", timeConsuming);//与上一步骤的时间差
			stepDtl.put("desc", desc);
			stepDtl.put("data", data);
			results.add(stepDtl);
		}
		
		//sb.append(ttc.toString(true)) ;
		return "";
	}
}
