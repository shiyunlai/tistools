/**
 * 
 */
package org.tis.tools.service.biztrace.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.service.biztrace.IBizTraceResolver;
import org.tis.tools.service.biztrace.TISLogFile;
import org.tis.tools.service.biztrace.helper.RunConfig;
import org.tis.tools.service.biztrace.redis.RedisClientTemplate;

import redis.clients.jedis.Jedis;

/**
 * <pre>
 * 日志文件拆分解析抽象类
 * 提前完成
 * 1、过滤注释行
 * 2、过滤空行
 * 3、拼接完整行
 * 4、统计日志文件行数
 * </pre>
 * @author megapro
 *
 */
public abstract class AbstractResolver implements IBizTraceResolver {

	@Autowired
	RedisClientTemplate redisClientTemplate ; 
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/* (non-Javadoc)
	 * @see bos.tis.biztrace.IBizTraceResolver#resolve(bos.tis.biztrace.TISLogFile)
	 */
	@Override
	public long resolve(TISLogFile logFile) throws IOException {
		
		String line = null;
		long lineNum = 0 ; 
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile.logFile), "UTF-8"));
		
		StringBuffer sb = new StringBuffer() ;
		while ((line = br.readLine()) != null){
			
			lineNum ++ ; //记录行数
			
			//跳过注释行
			if( line.startsWith("#") || line.startsWith("!") ){
				continue ;
			}
			
			sb.append(line) ;//拼接为整行
			
			if ( !isCompletedLine( line ) ) {
				continue ;
			} else {
				doResolve(sb.toString()) ;//把一整行日志哪去解析
				sb.replace(0, sb.length(), "");//清空临时字符串	
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(logFile.dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		String keyPattern = String.format(RunConfig.KP_DAY_LOG_LINES, sdf.format(date)) ;
		String totalLinesStr = redisClientTemplate.get(keyPattern);	
		if(totalLinesStr==null || "".equals(totalLinesStr)){
			redisClientTemplate.set(keyPattern, lineNum+"");
		}else{
			long totalLines = Long.parseLong(totalLinesStr);
			redisClientTemplate.set(keyPattern, lineNum+totalLines+"");
		}
		
		logger.debug(BasicUtil.concat("行数 ",lineNum));
		
		return lineNum ; 
	}
	

	/**
	 * 是否为整行（有些日志折行）
	 * @param line
	 * @return true 已经是整行 false 非整行
	 */
	protected abstract boolean isCompletedLine(String line) ; 

	/**
	 * 拆分解析一行日志
	 * @param wholeLine
	 * @param jedis
	 */
	protected abstract void doResolve(String wholeLine) ; 
}
