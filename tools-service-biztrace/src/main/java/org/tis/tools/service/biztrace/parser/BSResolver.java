/**
 * 
 */
package org.tis.tools.service.biztrace.parser;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 对bs.log拆分解析
 * @author megapro
 *
 */
@Repository("bsResolver")
public class BSResolver extends AbstractResolver {

	@Override
	protected boolean isCompletedLine(String line) {
		if( line.startsWith("[") ){
			return true ; 
		}
		
		return false;
	}

	@Override
	protected void doResolve(String wholeLine) {
		logger.warn("还未实现对bs.log的解析....");
		//System.out.println(wholeLine);
	}
}
