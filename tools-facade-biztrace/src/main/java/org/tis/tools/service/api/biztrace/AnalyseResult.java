/**
 * 
 */
package org.tis.tools.service.api.biztrace;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * 分析结果
 * 
 * @author megapro
 *
 */
public class AnalyseResult implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4341993874775681756L;

	//总分析耗时(单位毫秒)
	private long spendTime ; 
	
	//某日内(String)统计摘要信息
	private Map<String,SimpleStatist> statistInfo ; 
	
	public long getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(long spendTime) {
		this.spendTime = spendTime;
	}

	public Map<String, SimpleStatist> getStatistInfo() {
		return statistInfo;
	}

	public void setStatistInfo(Map<String, SimpleStatist> statistInfo) {
		this.statistInfo = statistInfo;
	}


	public class SimpleStatist implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2148936724262487908L;
		private long serialNoNum ; 
		private long uuidNum ;
		public long getSerialNoNum() {
			return serialNoNum;
		}
		public void setSerialNoNum(long serialNoNum) {
			this.serialNoNum = serialNoNum;
		}
		public long getUuidNum() {
			return uuidNum;
		}
		public void setUuidNum(long uuidNum) {
			this.uuidNum = uuidNum;
		} 
	}
}
