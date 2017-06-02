/**
 * 
 */
package org.tis.tools.common.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 *
 * 基础工具类<br>
 * 无法分类的工具方法；<br>
 * 或对其他工具类方法再次包装；<br>
 * @author mega-t420
 *
 */
public class BasicUtil {

	/**
	 * 以 key = value 的方式显示Map对象数据
	 * @param maps
	 * @return
	 */
	public static String showMaps(Map maps){
		
		if( null == maps || maps.size() == 0 ){
			return "maps为空" ;
		}
		
		StringBuffer sb = new StringBuffer() ; 
		
		Set<Entry>  set = maps.entrySet() ;
		Iterator<Entry> i = set.iterator() ;
		while( i.hasNext() ){
			Entry e = i.next() ; 
			sb.append(e.getKey()).append(" = ").append(e.getValue()).append("\n") ;
		}
		
		return sb.toString() ; 
	}
	
	/**
	 * 检查输入的参数中是否有空值
	 * @param strs
	 * @return
	 */
	public static boolean isEmpty(String ... strs){
		if(strs==null) return true;
		for(String str : strs){
			if(str==null || str.trim()==""){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 对身份证号进行敏感处理
	 * @param paperNo
	 */
	public static String ensitivePaperNo(String paperNo){
		return  ensitiveStr(paperNo,4,4,'*',6);
	}
	
	
	/**
	 * 对手机号进行敏感处理
	 * @param phoneNO
	 */
	public static String ensitivePhoneNo(String phoneNO){
		return  ensitiveStr(phoneNO,3,4,'*',4); 
	}

	/**
	 * 对账号进行敏感处理
	 * @param acctNO
	 */
	public static String ensitiveAcctNo(String acctNO){
		return  ensitiveStr(acctNO,0,4,'*',0);
	}
	
	/**
	 * 对字符串进行敏感性处理
	 * @param str
	 * @param hLen 头显示位数
	 * @param tLen 尾显示位数
	 * @param tag 占位符(默认用*)
	 * @param tagLen 占位符长度
	 * @return 如：str="532424198107241651" 
	 * ensitiveStr(str,4,4) 
	 * 则返回：
	 * 5324******1651
	 * 如果str的长度小于hLen或小于tLen或小于(hLen+tLen)，均返回str
	 */
	private static String ensitiveStr(String str, int hLen,int tLen,char tag, int tagLen){
		
		if( StringUtils.isEmpty(str)) {
			return "" ;
		}
		
		if( StringUtils.length(str) < hLen || 
			StringUtils.length(str) < tLen || 
			StringUtils.length(str) < (tLen + hLen))
		{
			return str ;
		}
		
		int len = StringUtils.length(str) ;
		StringBuffer sb = new StringBuffer() ;

		String hstr = StringUtils.substring(str, 0, hLen)  ; 
		sb.append(hstr);
		
		for( int i = 0 ; i < tagLen ; i ++ ){
			sb.append(tag) ;
		}
		
		String tstr = StringUtils.substring(str, len - tLen) ;
		sb.append(tstr);

		return sb.toString() ;
	}
	
	/**
	 * 连接args后输出
	 * @param args
	 * @return 
	 */
	public static String concat( Object... args ) {
		StringBuffer sb = new StringBuffer() ; 
		for( Object o : args ){
			sb.append(o.toString()) ;
		}
		return sb.toString();
	}
	
	/**
	 * 把多个参数以Object数组形式返回
	 * 
	 * @param args
	 *            不定参数
	 * @return 参数数组
	 */
	public static Object[] wrap(Object... args) {
		return args;
	}
}
