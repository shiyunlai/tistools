/**
 * 
 */
package org.tis.tools.service.biztrace.parser;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.tis.tools.service.biztrace.IBizTraceResolver;
import org.tis.tools.service.biztrace.TISLogFile;

/**
 * 
 * 默认日志解析器，不做任何处理
 * 
 * @author megapro
 *
 */
@Repository("defaultResolver")
public class DefaultResolver implements IBizTraceResolver {

	private final static Logger logger = LoggerFactory.getLogger(DefaultResolver.class);
	
	/* (non-Javadoc)
	 * @see bos.tis.biztrace.IBizTraceResolver#resolve(bos.tis.biztrace.TISLogFile, redis.clients.jedis.Jedis)
	 */
	@Override
	public long resolve(TISLogFile logFile) throws IOException {
		logger.warn("默认Resolver实现！无解析处理....");
		return 0;
	}

}
