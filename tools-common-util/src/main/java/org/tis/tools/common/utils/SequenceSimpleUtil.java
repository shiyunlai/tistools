/**
 * 
 */
package org.tis.tools.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <pre>
 * 序号资源工具的简单工具类
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
	 * 启动重启时从0开始的序号资源
	 */
	private static ConcurrentMap<String, AtomicInteger> seqNoResourcesSinceStart = new ConcurrentHashMap<String, AtomicInteger>();
	
	/**
	 * 启动重启时，从当前秒开始的序号资源
	 */
	private static ConcurrentMap<String, AtomicLong>    seqNoResourcesSinceRuntime = new ConcurrentHashMap<String, AtomicLong>();
	
	/**
	 * <pre>
	 * 获取seqKey对应的下一个序号资源，自从系统启动开始从0顺序步长为1.
	 * 
	 * 注意：
	 * 重启系统后，序号资源会置0，会重号。
	 * </pre>
	 * 
	 * @param seqKey
	 *            序号键值
	 * @return 序号数
	 */
	public int nextSeqNoSinceStart(String seqKey) {
		return nextSeqNoSinceStart(seqKey,1) ;
	}
	
	/**
	 * <pre>
	 * 获取seqKey对应的下一个序号资源，自从系统启动开始从0顺序步长为delta.
	 * 借助启动后的系统内存实现.
	 * 
	 * 注意：
	 * 重启系统后，序号资源会置0，会重号。
	 * </pre>
	 * 
	 * @param seqKey
	 *            序号键值
	 * @param delta
	 *            步长
	 * @return 序号数
	 */
	public int nextSeqNoSinceStart(String seqKey,int delta) {

		if (!seqNoResourcesSinceStart.containsKey(seqKey)) {
			AtomicInteger seqNos = new AtomicInteger(0);
			seqNoResourcesSinceStart.put(seqKey, seqNos);
		}

		return seqNoResourcesSinceStart.get(seqKey).addAndGet(delta) ;
	}
	
	/**
	 * <pre>
	 * 获取seqKey对应的下一个序号资源，自从系统启动开始从当前秒数顺序步长为1.
	 * 借助启动后的系统内存实现.
	 * 
	 * </pre>
	 * 
	 * @param seqKey
	 *            序号键值
	 * @return 序号数
	 */
	public long nextSeqNoSinceRuntime(String seqKey) {
		return nextSeqNoSinceRuntime(seqKey, 1);
	}
	
	/**
	 * <pre>
	 * 获取seqKey对应的下一个序号资源，自从系统启动开始从当前秒数顺序步长为delta.
	 * 借助启动后的系统内存实现.
	 * 
	 * 注意：
	 * 重启系统后，又从启动秒开始计数，一般不会重号。
	 * </pre>
	 * 
	 * @param seqKey
	 *            序号键值
	 * @param delta
	 *            步长
	 * @return 序号数
	 */
	public long nextSeqNoSinceRuntime(String seqKey, int delta) {

		if (!seqNoResourcesSinceRuntime.containsKey(seqKey)) {
			long startNo = System.currentTimeMillis() / 1000;
			AtomicLong seqNos = new AtomicLong(startNo);
			seqNoResourcesSinceRuntime.put(seqKey, seqNos);
		}

		return seqNoResourcesSinceRuntime.get(seqKey).addAndGet(delta);
	}

	/**
	 * 产生一个携带标识（sign）的GUID字符串
	 * @param sign 标识
	 * @return
	 */
	public String GUID(String sign) {
		
		return sign + nextSeqNoSinceRuntime(sign) ;
	}
	
	/**
	 * 取一个UUID
	 * 	
	 * @return UUID字符串（去掉了'-'字符）
	 */
	public String UUID() {
		String uuid =  UUID.randomUUID().toString().replace("-", "") ;//去掉了'-'字符
		return uuid ; 
	}

}
