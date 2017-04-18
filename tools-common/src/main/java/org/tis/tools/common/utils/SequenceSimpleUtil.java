/**
 * 
 */
package org.tis.tools.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * 序号工具的简单工具类
 * 适用于非分布式部署情况(不确保多台服务器同时运行时产生相同ID)，
 * 实现机制：当前秒 ＋ 四位顺序号
 * </pre>
 * @author megapro
 *
 */
public class SequenceSimpleUtil {

	public static final SequenceSimpleUtil instance = new SequenceSimpleUtil() ; 
	
	private  static int i=0;
	private  static int maxSeqNo = 1000 ; 
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	private SequenceSimpleUtil(){
	}
	
	/**
	 * 循环返回不大于 maxSeqNo 的数字
	 * @return
	 */
	private synchronized int getSeq(){
		i++;
		if( i>=maxSeqNo ){
			i=1;
		}
		int a = i+maxSeqNo;
		return a;
	}
	
	/**
	 * <pre>
	 * 获取唯一id
	 * 规则： 当前秒 ＋ 四位顺序号
	 * 说明： 每秒内有 9999 个不重复id可用
	 * </pre>
	 * @return
	 */
	public String getId(String tagString){
		
		return tagString + System.currentTimeMillis()+""+getSeq();
	}
	
	/**
	 * <pre>
	 * 获取唯一id
	 * 规则： 当前秒 ＋ 四位顺序号
	 * 说明： 每秒内有 9999 个不重复id可用
	 * </pre>
	 * @return
	 */
	public String getId(){
		
		return System.currentTimeMillis()+""+getSeq();
	}
	
	/**
	 * <pre>
	 * 获得当前秒内的序号SeqNo
	 * 不保证返回值在分布式系统中的唯一性
	 * </pre>
	 * @return
	 */
	public int getSeqNo(){
		return getSeq() ;
	}
	
	/**
	 * merNO+yyyymmdd+10位一天内不能重复的数字。
	 * @param merNO
	 * @return
	 */
	public synchronized String getWxOrderNO(String merNO){
		String a=merNO+sdf.format(new Date())+((System.currentTimeMillis()+"").substring(3));
		return a;
	}
	
	/**
	 * 随机启动的序号资源
	 */
	private static ConcurrentMap<String, AtomicInteger> seqNoResources = new ConcurrentHashMap<String, AtomicInteger>();
	
	/**
	 * <pre>
	 * 获取seqKey对应的下一个序号资源
	 * 
	 * 注意：
	 * 系统不负责保留seqKey对应的序号资源状态，当系统重启后，序号资源会置0，从头开始数数。
	 * </pre>
	 * 
	 * @param seqKey
	 *            序号键值
	 * @return 序号数
	 */
	public int getNextSeqNo(String seqKey) {

		if (!seqNoResources.containsKey(seqKey)) {
			AtomicInteger seqNos = new AtomicInteger(0);
			seqNoResources.put(seqKey, seqNos);
		}
		
		return seqNoResources.get(seqKey).incrementAndGet();
	}
	
	/**
	 * <pre>
	 * 获取seqKey对应的下一个序号资源
	 * 
	 * 注意：
	 * 系统不负责保留seqKey对应的序号资源状态，当系统重启后，序号资源会置0，从头开始数数。
	 * </pre>
	 * 
	 * @param seqKey
	 *            序号键值
	 * @param delta
	 *            增量
	 * @return 序号数
	 */
	public int getNextSeqNo(String seqKey,int delta) {

		if (!seqNoResources.containsKey(seqKey)) {
			AtomicInteger seqNos = new AtomicInteger(0);
			seqNoResources.put(seqKey, seqNos);
		}

		return seqNoResources.get(seqKey).addAndGet(delta) ;
	}

}
