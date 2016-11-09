/**
 * 
 */
package bos.tis.biztrace;

import java.util.LinkedList;
import java.util.List;

/**
 * 交易耗时
 * @author megapro
 *
 */
public class TransTimeConsuming{
	
	public String serialNo = null ; //交易流水
	public String transCode = null ; //交易代码
	public String transName = null ; //交易名称
	public Double totalTimeConsuming ;//总耗时
	public List<TransStepTimeConsuming> stepDtl = new LinkedList<TransStepTimeConsuming>() ;
	
	public String toString(){
		StringBuffer sb = new StringBuffer() ; 
		sb.append("流水号: ").append(serialNo)
			.append(" 交易名称: ").append(transCode).append(" ").append(transName)
			.append(" 办理过程总耗时: ").append(totalTimeConsuming).append("毫秒 ").append(totalTimeConsuming/1000).append("秒").append("\n");
		
		//sb.append("\t\t秒").append("\t毫秒").append("\t\t执行时间点").append("\t\t\t时间点毫秒数").append("\t描述").append("\n");
//		for( StepTimeConsuming stc : stepDtl ){
//			sb.append("\t\t").append(stc.toString() ); 
//		}
		return sb.toString() ;
	}
}
