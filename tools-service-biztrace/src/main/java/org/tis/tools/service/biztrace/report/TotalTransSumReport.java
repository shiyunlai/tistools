/**
 * 
 */
package org.tis.tools.service.biztrace.report;

import org.tis.tools.service.biztrace.helper.RunConfig;

import redis.clients.jedis.Jedis;

/**
 * 统计指定日期的交易总量
 * @author megapro
 *
 */
public class TotalTransSumReport extends AbstractReporter {

	@Override
	public String doReport(String date, Jedis jedis) {
		//交易总量直接查看 "set-serialNos:2017-09-08" 集合
		String key = String.format(RunConfig.KP_SET_SERIALNOS, date) ; 
		StringBuffer sb = new StringBuffer() ; 
		sb.append("日期<").append(date).append(">交易量为:").append(jedis.scard(key)).append("\n");
		return sb.toString();
	}
}
