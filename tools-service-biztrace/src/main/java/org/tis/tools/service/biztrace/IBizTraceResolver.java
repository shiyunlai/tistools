/**
 * 
 */
package org.tis.tools.service.biztrace;

import java.io.IOException;

/**
 * 日志拆分解析器：对日志进行解析拆分后并存储，以便后续分析
 * @author megapro
 *
 */
public interface IBizTraceResolver{
	
	/**
	 * 解析拆分日志文件
	 * @param logFile 日志
	 * @return 日志行数
	 */
	public long resolve(TISLogFile logFile) throws IOException;
}
