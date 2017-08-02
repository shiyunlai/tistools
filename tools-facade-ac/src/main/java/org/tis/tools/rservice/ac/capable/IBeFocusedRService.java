/**
 * 
 */
package org.tis.tools.rservice.ac.capable;

import java.util.List;

import org.tis.tools.model.po.ac.AcOperator;
import org.tis.tools.model.po.ac.AcRole;

/**
 * 测试服务
 * @author megapro
 *
 */
public interface IBeFocusedRService {

	/**
	 * 测试List作为参数和返回值的Dubbo服务调用
	 * @param operators
	 * @return
	 */
	public List<AcRole> roles(List<AcOperator> operators) ;
	
}
