/**
 * 
 */
package org.tis.tools.service.biztrace.utils;

/**
 * 运行配置
 * 
 * @author megapro
 *
 */
public class RunConfig {

	// Redis键命名 KP_｛SET｝_XXXXX , KP 固定 ｛SET｝为Redis存储类型
	
	////////////// 存储拆分后日志的KP
	/** key pattern : 某日已经解析过的文件。如： set-resolved-log-file:2016-09-08 */
	public static final String KP_SET_RESOLVED_LOG_FILE = "set-resolved-log-file:%s" ;
	
	/** key pattern : 某日无交易关联的交互记录 。如： set-unlinke-uuids:2017-09-08 */
	public static final String KP_SET_UNLINK_UUID = "set-unlink-uuids:%s";

	/** key pattern : 某日与交易相关的交互记录 。如： set-link-uuids:2017-09-08 */
	public static final String KP_SET_LINK_UUID = "set-link-uuids:%s";
	
	/** key pattern : 某日当天内的交易流水号集合 。如： set-serialNos:2017-09-08 */
	public static final String KP_SET_SERIALNOS = "set-serialNos:%s";

	/** key pattern : 某交易的交互日志map集合，域 。如： map-serialNo:20160704318790800288 */
	public static final String KP_MAP_SERIALNO = "map-serialNo:%s" ;
	
	/** key pattern : 某种类型的请求。如： set-type:2017-09-08:TRANS */
	public static final String KP_SET_REQUEST_TYPE = "set-reques-type:%s:%s";
	
	//////////////存储分析结果的KP
	
	/** key pattern : 某日交易办理耗时排序的集合。如： zset-ttc:2017-09-08 */
	public static final String KP_ZSET_DATE_TTC = "zset-ttc:%s" ;
	
	/** key pattern : 某交易的耗时结果。如： ttc:20160704318790800288 */
	public static final String KP_STR_TTC = "ttc:%s" ; 
	
	/** key pattern : 对某流水号做完分析后对应的信息的map集合 。如： analyzed-serialNo:20160704318790800288 */
	public static final String KP_ANALYZED_SERIALNO = "analyzed-serialNo:%s" ;
	
	/** key pattern : 存储未被分析的日志文件日期的set集合。如：unanalyzed-date-set */
	public static final String KP_UNANALYZED_DATE = "unanalyzed-date-set" ;
	
	/** key pattern : 按交易耗时排序的所有被分析流水号的集合。如：serialNo-sortByTime-sotedSet */
	public static final String KP_SOTEDSET_SERIALNO_SORTBYTIME = "serialNo-sortByTime-sotedSet" ;
	
	/** key pattern : 某日生成日志的行数。如：day-log-lines:2016-07-18 */
	public static final String KP_DAY_LOG_LINES = "day-log-lines:%s" ;
	
	/** key pattern : 某日日志汇总信息的map集合 。如： day-sumLogInfo-map:2016-07-18 */
	public static final String KP_MAP_SUMLOGINFO = "day-sumLogInfo-map:%s" ;
	
	/** key pattern : 所有被解析日志的日期set集合 。如： resolved-date-set */
	public static final String KP_SET_RESOLVED_DATE = "resolved-date-set" ;
	
	
	//Redis 参数
	
	/** redis 服务器ip地址 */
	public static String redisIp = "127.0.0.1";
	
	/** redis 服务器端口 */
	public static int redisPort = 6379; 
	
	/** 带解析biztrace日志文件路径 */
	//public static String logFilesPath = "/Users/megapro/temp/biztrace/bs13/20160704" ;
	//public static String logFilesPath = "/Users/megapro/temp/biztrace/test/" ;
	public static String logFilesPath = "C:/Users/HP/Desktop/M2/log/bs15";
	
	/** 解析文件并行线程数 */
	public static int threadNum = 12 ;
	
	
	// Report 相关参数
	
	/** 查看前tops耗时交易 */
	public static int tops = 100 ; 
	
}
