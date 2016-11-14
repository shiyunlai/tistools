/**
 * 
 */
package org.tis.tools.service.biztrace.client;

import org.tis.tools.service.biztrace.BizTraceAnalyManage;
import org.tis.tools.service.biztrace.redis.AbstractRedisHandler;
import org.tis.tools.service.biztrace.report.BodyInfoReport;
import org.tis.tools.service.biztrace.utils.RunConfig;

import redis.clients.jedis.Jedis;


/**
 *
 * @author megapro
 */
public class GoBizTrace {
	 
	public static void main(String[] args) {
		
		/*
		 * 拆分解析
		 * 要求日志文件存放目录结构：
		 * /path/yyyyMMdd/*.log
		 * 系统不会重复解析同一个目录下的同名文件
		 */
		BodyInfoReport.instance.report("", "");
		//BizTraceAnalyManage.instance.resolve(RunConfig.logFilesPath, RunConfig.threadNum);

		/*
		 * 分析
		 */
		//BizTraceAnalyManage.instance.analyze("2016-08-15");
		
		/*
		 * 报告
		 */
		//BizTraceAnalyManage.instance.report("2016-08-15");
		
	}
}
