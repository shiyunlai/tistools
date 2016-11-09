/**
 * 
 */
package bos.tis.biztrace.report;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import bos.tis.biztrace.TransTimeConsuming;
import bos.tis.biztrace.utils.RunConfig;

import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * 前tops笔耗时交易报告
 * @author megapro
 *
 */
public class TopsTransTimeConsumingReport extends AbstractReporter {

	private RuntimeSchema<TransTimeConsuming> schemaTTC = RuntimeSchema.createFrom(TransTimeConsuming.class);

	/**
	 * 报告前{@link RunConfig#tops}个耗时的交易
	 */
	@Override
	public String doReport(String date, Jedis jedis) {
		
		//交易耗时有序set的key
		String keyPattern = String.format(RunConfig.KP_ZSET_DATE_TTC, date) ; 
		StringBuffer sb = new StringBuffer() ; 
		
		//取出前tops耗时交易
		Set<Tuple> topTTC = jedis.zrevrangeWithScores(keyPattern, 0, RunConfig.tops) ; 
		sb.append("日期<").append(date).append("> 前"+RunConfig.tops+"耗时交易:").append("\n") ;
		for(Tuple t : topTTC ){
			
			TransTimeConsuming ttc = schemaTTC.newMessage();
			ProtostuffIOUtil.mergeFrom(jedis.get(t.getBinaryElement()), ttc, schemaTTC);
			sb.append("\t").append(ttc.toString()) ;
		}
		
		return sb.toString();
	}

}
