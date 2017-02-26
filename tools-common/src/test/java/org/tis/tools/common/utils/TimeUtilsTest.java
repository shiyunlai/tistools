package org.tis.tools.common.utils;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.tis.tools.common.utils.TimeUtil;

public class TimeUtilsTest {

	@Test
	public void testLongToDate(){
		
		long dateLong = 1479100943510L ;//2016-11-14 13:22:23
		
		Assert.assertEquals("Mon Nov 14 13:22:23 CST 2016",TimeUtil.longToDate(dateLong).toString());
		Assert.assertEquals("2016-11-14 13:22:23:510",TimeUtil.longToDateStr(dateLong,null));
		Assert.assertEquals(dateLong,TimeUtil.toTime("2016-11-14 13:22:23:510", "yyyy-MM-dd HH:mm:ss:SSS"));
	}
	
	@Test
	public void test() {

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		System.out.println(sd.format(now));
		System.out.println(now.getTime());
		System.out
				.println(sd.format(TimeUtil.longToDate(now.getTime() / 1000)));
		System.out.println(sd.format(TimeUtil.longToDate(1446461871)));
		System.out.println("-----------------");
		Date d = TimeUtil.stringToDate("2014-09-08 17:16:54",
				"yyyy-MM-dd HH:mm:ss");
		System.out.println(d.getTime());
		System.out.println(TimeUtil.formatDateStr(d));
		System.out.println(TimeUtil.formatPayDateStr(d));
		System.out.println(TimeUtil.longToDate(d.getTime() / 1000));
		System.out.println("-----------------");
		System.out.println(TimeUtil.compareDate("20150101", "20150102"));
		System.out.println(TimeUtil.compareDate("20150101", "20150101"));
		System.out.println(TimeUtil.compareDate("20150101", "20150105"));
		System.out.println(TimeUtil.compareDate("20150106", "20150102"));
		System.out.println("------------------------");
		System.out.println("-----------------");
		System.out.println(TimeUtil.compareDate("20150101", "20150102"));
		System.out.println(TimeUtil.compareDate("20150101", "20150101"));
		System.out.println(TimeUtil.compareDate("20150101", "20150105"));
		System.out.println(TimeUtil.compareDate("20150106", "20150102"));
		System.out.println(TimeUtil.nextOneDate("20161231"));
		System.out.println(TimeUtil.nextOneDate("20150731"));
		System.out.println(TimeUtil.nextOneDate("20150228"));
		System.out.println(TimeUtil.nextOneDate("20140227"));
		System.out.println(TimeUtil.nextOneDate("20140228"));
		System.out.println(TimeUtil.nextOneDate("20120227"));
		System.out.println(TimeUtil.nextOneDate("20120228"));
		System.out.println(TimeUtil.nextOneDate("20120229"));
		System.out.println("------------------------");
		System.out.println(TimeUtil.nextDate("20161231", 2));
		System.out.println(TimeUtil.nextDate("20150731", 1));
		System.out.println(TimeUtil.nextDate("20150228", -1));
		System.out.println("------------------------");
		System.out.println(TimeUtil.getHourAndMinute(new Date()));

		System.out.println("-----------------");
		System.out.println(TimeUtil.compareTime("11:20", "11:30"));
		System.out.println(TimeUtil.compareTime("12:04", "12:04"));
		System.out.println(TimeUtil.compareTime("15:09", "17:40"));
		System.out.println(TimeUtil.compareTime("00:01", "21:30"));

		System.out.println("-----------------daysBetweenTwoDate");
		System.out
				.println(TimeUtil.daysBetweenTwoDate("20150102", "20150101"));
		System.out
				.println(TimeUtil.daysBetweenTwoDate("20150101", "20150101"));
		System.out
				.println(TimeUtil.daysBetweenTwoDate("20150220", "20150301"));
		System.out.println(TimeUtil.daysBetweenTwoDate("2015220", "20150301"));
		System.out
				.println(TimeUtil.daysBetweenTwoDate("20151201", "20151231"));
	}

}
