/**
 * 
 */
package org.tis.tools.service.biztrace.report;

import org.tis.tools.service.biztrace.helper.RunConfig;

/**
 * 统计指定日期的总的访问（TWS向BS发送过的请求）次数
 * @author megapro
 *
 */
public class TotalRequestTimesReport extends AbstractReporter {

	@Override
	public String doReport(String date) {
		
		//TWS与BS间请求/响应次数
		String keyNull = String.format(RunConfig.KP_SET_UNLINK_UUID, date) ;
		String keyWith = String.format(RunConfig.KP_SET_LINK_UUID, date) ;
		
		StringBuffer sb = new StringBuffer() ; 
		long requesNumNull = redisClientTemplate.scard(keyNull) ; 
		long requesNumWith = redisClientTemplate.scard(keyWith) ;
		sb.append("日期<").append(date).append(">请求总次数:").append(requesNumNull+requesNumWith).append("\n") ;
		sb.append("\t").append("其中与交易相关的请求次数：").append(requesNumWith).append("\n");
		sb.append("\t").append("其中与交易无相的请求次数：").append(requesNumNull).append("\n");
		
		return sb.toString();
	}
}
