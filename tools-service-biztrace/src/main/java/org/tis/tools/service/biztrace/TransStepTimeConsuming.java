/**
 * 
 */
package org.tis.tools.service.biztrace;


/**
 * 单步耗时
 * @author megapro
 *
 */
public class TransStepTimeConsuming implements Comparable<TransStepTimeConsuming>{
	
	public String stepName = null ; //步骤
	public long stepTimes = 0 ; //步骤时点（毫秒数）
	public long timeConsuming = 0 ; //步骤耗时（减去前一步骤时点所得结果）
	public BizTraceLogRecord stepLog = null ; //步骤对应的biztrace日志
	
	public String toString(){
		StringBuffer sb = new StringBuffer() ; 
		sb.append(timeConsuming/1000).append("\t")
		.append(timeConsuming).append("\t\t")
		.append(stepLog.time).append("\t")
		.append(stepTimes).append("\t").append(stepName).append("\n") ;
		return sb.toString() ;
	}

	@Override
	public int compareTo(TransStepTimeConsuming o) {
		
		if (o.stepTimes > this.stepTimes) {
			return -1;
		} else if (o.stepTimes < this.stepTimes) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
