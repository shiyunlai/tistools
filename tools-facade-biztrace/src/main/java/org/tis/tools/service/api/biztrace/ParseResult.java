/**
 * 
 */
package org.tis.tools.service.api.biztrace;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 业务日志解析结果
 * 
 * @author megapro
 *
 */
public class ParseResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1122766043482167154L;
	
	private List<SimpleParseStatics> parseInfo ; 
	
	public List<SimpleParseStatics> getParseInfo(){
		return this.parseInfo ; 
	}
	
	public void setParseInfo(List<SimpleParseStatics> parseInfo){
		this.parseInfo = parseInfo ; 
	}
	
	
	public class SimpleParseStatics implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 6053182358205239493L;

		//日志所属日期 yyyy-MM-dd （也说明biztrace日志文件中包括有happenDay当天的日志明细）
		private String happenDate ; 
		
		//有多少行日志
		private long recordNum ; 
		
		//有多少比交易流水
		private long serialNoCount ;
		
		
		//TODO 继续增加字段，解析后即可获取的信息....

		/**
		 * 发生日期
		 * @return
		 */
		public String getHappenDate() {
			return happenDate;
		}

		public void setHappenDate(String happenDate) {
			this.happenDate = happenDate;
		}

		/**
		 * 日志明细数量
		 * @return
		 */
		public long getRecordNum() {
			return recordNum;
		}

		public void setRecordNum(long recordNum) {
			this.recordNum = recordNum;
		}

		/**
		 * 发生日内包括的交易流水号数量
		 * @return
		 */
		public long getSerialNoCount() {
			return serialNoCount;
		}

		public void setSerialNoCount(long serialNoCount) {
			this.serialNoCount = serialNoCount;
		} 
	}
	
}
