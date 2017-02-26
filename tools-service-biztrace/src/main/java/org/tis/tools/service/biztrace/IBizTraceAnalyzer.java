/**
 * 
 */
package org.tis.tools.service.biztrace;

/**
 * biztrace日志分析器，根据已经倒入到redis中的数据完成一种分析
 * @author megapro
 *
 */
public interface IBizTraceAnalyzer {

	/**
	 * 对date日的biztrace记录进行分析
	 * @param date
	 */
	public void analyzed(String date) ;
}
