/**
 * 
 */
package org.tis.tools.service.biztrace;

/**
 * @author megapro
 *
 */
public interface IBizTraceReporter {
	/**
	 * 给出报告
	 * @param date
	 * @return 报告，直接显示
	 */
	public String report(String date) ;
}
