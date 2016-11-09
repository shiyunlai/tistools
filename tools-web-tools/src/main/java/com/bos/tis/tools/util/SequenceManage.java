/**
 * 
 */
package com.bos.tis.tools.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author megapro
 *
 */
public class SequenceManage {

	
	private  static int i=0;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	public static void main(String[] args) {
		
		//System.out.println((System.currentTimeMillis()+"").substring(3));
		
		String temp =""; 
		List <String> l = new ArrayList<String>() ;
		
		long start = System.currentTimeMillis() ;
		for( int i =0 ; i < 1000 ; i ++ ){
			temp = getId() ;
			if( l.contains(temp) ){
				System.out.println("重复："+temp);
			}else{
				l.add(temp);
			}
		}
		System.out.println("耗时："+(System.currentTimeMillis() - start));
		
		System.out.println(System.currentTimeMillis());
		System.out.println(getId());
		
	}
	
	/**
	 * <pre>
	 * 获取唯一id
	 * 规则： 当前秒 ＋ 四位顺序号
	 * 说明： 每秒内有 9999个不重复id可用
	 * </pre>
	 * @return
	 */
	public static String getId(){
		
		return System.currentTimeMillis()+""+getSeq();
	}
	
	private static synchronized int getSeq(){
		i++;
		if(i>=1000){
			i=1;
		}
		int a = i+1000;
		return a;
	}
	
	//mch_id+yyyymmdd+10位一天内不能重复的数字。
	public static synchronized String getWxOrderNO(String merNO){
		String a=merNO+sdf.format(new Date())+((System.currentTimeMillis()+"").substring(3));
		return a;
	}
}
