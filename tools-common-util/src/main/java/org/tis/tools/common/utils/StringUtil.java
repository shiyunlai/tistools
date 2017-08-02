/**
 * 
 */
package org.tis.tools.common.utils;

import java.text.MessageFormat;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author megapro
 *
 */
public class StringUtil {
	
	/**
	 * 检查输入的参数中是否有空值
	 * @param strs
	 * @return
	 */
	public static boolean isEmpty(String ... strs){
		if(strs==null) return true;
		for(String str : strs){
			if(str==null || str.trim().length()==0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 进行字符串格式化，使用args顺序替换目标字符串中的占位符
	 * 
	 * @param patternStr 如： lexical error at position {0}, encountered {1}, expected {2}
	 * @param args 
	 * @return 替换后的字符串
	 */
	public static String format(String patternStr, Object ...args){
		return MessageFormat.format(patternStr, args) ; 
	}
	
	/**
	 * 将多个字符串按顺序拼接在一起
	 * @param strings
	 * @return 拼接好的字符串
	 */
	public static String concat(String ...strings) {
		StringBuffer sb = new StringBuffer() ; 
		for( String s : strings ){
			sb.append(s) ; 
		}
		return sb.toString() ; 
	}
	
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

	/**
	 * 判断所有str字符串是否都为非空。
	 * 只要有一个为空，则返回false
	 * @param strs
	 * @return true 都非空  false 有空值
	 */
	public static boolean noEmpty(String... strs) {
		
		for( String s : strs ){
			if( org.apache.commons.lang.StringUtils.isEmpty(s) ) {
				return false ; //只要有一个为空，则返回false
			}
		}
		return true;
	}

	/**
	 * 判断字符是否在一个数组中
	 * @param str 需要判断的字符
	 * @param strs　用于比较的字符
	 * @return
	 */
	public static boolean isEqualsIn(String str, String... strs) {
		if (str == null || str.length() == 0)
			return false;
		for (String s : strs) {
			if(isEquals(str, s)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isEquals(String s1, String s2) {
		if (s1 == null && s2 == null)
			return true;
		if (s1 == null || s2 == null)
			return false;
		return s1.equals(s2);
	}


}
