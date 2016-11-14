/**
 * 
 */
package org.tis.tools.service.biztrace.parser;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.service.biztrace.BizTraceLogRecord;
import org.tis.tools.service.biztrace.redis.RedisClientTemplate;
import org.tis.tools.service.biztrace.utils.RunConfig;

import redis.clients.jedis.Jedis;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * <pre>
 * 拆分解析一行biztrace日志
 * 1、解析biztrace行
 * 2、存储到redis
 * </pre>
 * @author megapro
 *
 */
public class BizTraceResolver extends AbstractResolver {

	@Autowired
	RedisClientTemplate redisClientTemplate ; 
	
	//biztrace日志对象schema
	private RuntimeSchema<BizTraceLogRecord> schema = RuntimeSchema.createFrom(BizTraceLogRecord.class);
		
	@Override
	protected void doResolve(String wholeLine,Jedis jedis) {
		
		String uuid = parseUuid(wholeLine);//此时的uuid结构可能为： serialNo:uuid
		if (uuid == null) {
			return ;//缺少uuid，不满足biztrace日志完整性要求，不解析
		}
		String time = parseTime(wholeLine);
		String type = parseType(wholeLine);
		String serialNo = parseSERI(wholeLine);
		String bsId = parseBSID(wholeLine);
		String desc = parseDesc(wholeLine);
		String data = parseData(wholeLine);
		String expt = parseExpt(wholeLine);
		
		//组成biztrace日志
		BizTraceLogRecord log = new BizTraceLogRecord(uuid,time, type, serialNo, bsId, desc, data, expt) ;
		
		doRedisSave(log,jedis) ;
	}

	@Override
	protected boolean isCompletedLine(String line) {
		
		if( line.endsWith("[$$]") ){
			return true ; //biztrace日志以 [##]开头，以[$$]结尾
		}
		
		return false;
	}
	
	/**
	 * 将日志存入reids //FIXME 未按服务器分组日志
	 *
	 * @param log
	 */
	private void doRedisSave(BizTraceLogRecord log, Jedis jedis) {
		
		//每笔流水是一个map，map中的一个域为一个time-uuid，值为日志记录
		if( !StringUtils.isEmpty(log.serialNo) && !"null".equals(log.serialNo) ){
			
			/*
			 * #按日期存储交易流水号集合
			 */
			jedis.sadd(String.format(RunConfig.KP_SET_SERIALNOS, log.date), log.serialNo) ;//用set保存date日的交易流水号
			//redisClientTemplate.sadd(String.format(RunConfig.KP_SET_SERIALNOS, log.date), log.serialNo) ; 
			
			/*
			 * #存储交易的过程
			 */
			byte[] logBytes = ProtostuffIOUtil.toByteArray(log, schema,
					LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
			
			jedis.hset(
					(String.format(RunConfig.KP_MAP_SERIALNO, log.serialNo)).getBytes(), 
					(log.time+"-"+log.uuid).getBytes(), 
					logBytes) ;
//			redisClientTemplate.hset((String.format(RunConfig.KP_MAP_SERIALNO, log.serialNo)).getBytes(), 
//					(log.time+"-"+log.uuid).getBytes(), 
//					logBytes) ; 
		}

		/*
		 * 如果该日志记录有流水号则： #存储有流水号关联的uuid
		 * 否则：#存储无流水号关联的uuid
		 */
		if( StringUtils.isEmpty(log.serialNo ) ){

			jedis.sadd(String.format(RunConfig.KP_SET_UNLINK_UUID,log.date), log.uuid) ;
		}else{
			
			jedis.sadd(String.format(RunConfig.KP_SET_LINK_UUID,log.date), log.uuid) ;
		}
		
		/*
		 * #存储 某次请求的类型
		 */
		if( !StringUtils.isEmpty(log.type) && !"null".equals(log.type) ){
			
			jedis.sadd(String.format(RunConfig.KP_SET_REQUEST_TYPE, log.date, log.type), log.type+":"+log.uuid) ;
		}
		
	}
	
	private String parseUuid(String line) {
		String key = "@UUID{";
		int idx1 = line.indexOf(key);
		if (idx1 < 0)
			return null;

		idx1 += key.length();
		int idx2 = line.indexOf("}", idx1);
		if (idx2 < 0)
			System.out.println("line: " + line);

		String uuid = line.substring(idx1, idx2);
		return uuid;
	}

	private String parseTime(String line) {
		String key = "@TIME{";
		return parseString(key, line);
	}

	private String parseType(String line) {
		String key = "@TYPE{";
		return parseString(key, line);
	}

	private String parseSERI(String line) {
		String key = "@SERI{";
		return parseString(key, line);
	}

	private String parseBSID(String line) {
		String key = "@BSID{";
		return parseString(key, line);
	}

	private String parseDesc(String line) {
		String key = "@DESC{";
		return parseString(key, line);
	}

	private String parseData(String line) {
		String key = "@DATA{";
		return parseString(key, line);
	}

	private String parseExpt(String line) {
		String key = "@EXPT{";
		return parseString(key, line);
	}

	private String parseString(String key, String line) {
		int idx1 = line.indexOf(key);
		if (idx1 < 0)
			return null;

		idx1 += key.length();
		int idx2 = line.indexOf("}@EXPT", idx1);
		if (idx2 < 0)
			System.out.println("line: " + line);

		String serialNoStr = line.substring(idx1, idx2);
		//return serialNoStr.replace('.', ',');
		return serialNoStr;
	}
}
