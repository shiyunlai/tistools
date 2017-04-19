/**
 * 
 */
package org.tis.tools.common.utils;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author megapro
 *
 */
public class StringUtils {
	
	/**
	 * <pre>
	 * 在字符（target）左边补充字符（filler），并返回总长度为len的字符串
	 * 如：
	 * fillLeft("1",5,'0')      => "00001"
	 * fillLeft("123",5,'0')    => "00123"
	 * fillLeft("12345",5,'0')  => "12345"
	 * fillLeft("123456",5,'0') => "12345"
	 * 
	 * </pre>
	 * 
	 * @param target
	 *            目标字符串
	 * @param len
	 *            总长度
	 * @param alexin
	 *            填充字符
	 * @return 填充后的字符串
	 */
	public static String leftPad(String target, int len, char alexin) {
		// 在字符（target）左边补充字符（alexin），并返回总长度为len的字符串
		return org.apache.commons.lang.StringUtils.leftPad(target, len, alexin) ;
	}
	
	/**
	 * <pre>
	 * 在字符（target）右边补充字符（filler），并返回总长度为len的字符串
	 * 如：
	 * fillRight("1",5,'0')      => "10000"
	 * fillRight("123",5,'0')    => "12300"
	 * fillRight("12345",5,'0')  => "12345"
	 * fillRight("123456",5,'0') => "12345"
	 * 
	 * </pre>
	 * 
	 * @param target
	 *            目标字符串
	 * @param len
	 *            总长度
	 * @param alexin
	 *            填充字符
	 * @return 填充后的字符串
	 */
	public static String rightPad(String target, int len, char alexin) {
		//在字符（target）右边补充字符（filler），并返回总长度为len的字符串
		return org.apache.commons.lang.StringUtils.rightPad(target, len, alexin);
	}
	
	/**
	 * 将对象进行字符串序列化
	 * 
	 * @param obj
	 *            任意对象
	 * @return 序列化后的字符串
	 */
	public static String toString(Object obj) {
		
		return ToStringBuilder.reflectionToString(obj) ;
	}
	
}
