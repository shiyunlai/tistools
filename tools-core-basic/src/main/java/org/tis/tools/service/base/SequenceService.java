/**
 * 
 */
package org.tis.tools.service.base;

import org.springframework.stereotype.Service;
import org.tis.tools.common.utils.SequenceSimpleUtil;

/**
 * <pre>
 * 基础服务：“序号资源”相关业务逻辑
 * 
 * 注意：
 * 	此实现基于内存，不确保系统重启后的序号数连贯性！
 * 	另外，可参考数据库版序号资源服务实现 —— SYS 业务域中 SYS_SEQNO 模型
 * </pre>
 * @author megapro
 *
 */
@Service
public class SequenceService {

	/**
	 * 
	 * 获取一个唯一id字符串
	 * 
	 * @return
	 * 	当前秒 ＋ 四位顺序号(一秒内不重复)
	 */
	public String getId(){
		return System.currentTimeMillis() + "" + SequenceSimpleUtil.instance.getSeqNo() ; 
	}
	
	/**
	 * 获取带有获取者（gainer）标识的唯一id字符串
	 * @param gainer 获取者信息
	 * @return gainer＋id
	 */
	public String generateId(String gainer){
		return gainer + getId() ; 
	}
	
	/**
	 * 根据seqKey获取下一个序号数
	 * 
	 * @param seqKey
	 *            序号键值（见表：sys_seqno）
	 * @return 下一个序号
	 */
	public int getNextSeqNo(String seqKey) {
		return SequenceSimpleUtil.instance.nextSeqNoSinceStart(seqKey) ;
	}

}
