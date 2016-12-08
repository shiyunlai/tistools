package org.tis.tools.service.biztrace.report;

import java.util.Map;

import org.tis.tools.service.biztrace.helper.RunConfig;
import org.tis.tools.service.biztrace.redis.AbstractRedisHandler;

public class DayLogInfoSumReport extends AbstractRedisHandler {

	public static final DayLogInfoSumReport instance = new DayLogInfoSumReport();
	private Map<String, String> result = null;

	public Map<String, String> report(String date) {
		try {
			String keyPattern = String.format(RunConfig.KP_MAP_SUMLOGINFO, date);
			result = redisClientTemplate.hgetAll(keyPattern);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return result;
	}
}
