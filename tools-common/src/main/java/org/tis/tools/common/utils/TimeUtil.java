/**
 * 
 */
package org.tis.tools.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 时间、日期操作工具类
 * @author mega-t420
 *
 */
public class TimeUtil {

	private final static String DATE_FORMATE_SIMPLE = "yyyyMMdd" ; 
	private final static String DATE_FORMATE_SECONDS = "yyyyMMddHHmmss" ; 
	
	/**
	 * 格式化指定日期为字符串<br>
	 * @param tag
	 * @return yyyyMMdd格式的字符串
	 */
	public static String formatDateStr(Date date){
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMATE_SIMPLE);
		Date now = date;
		return df.format(now);
	}
	
	/**
	 * 格式化指定日期为字符串<br>
	 * @param tag
	 * @return yyyyMMddHHmmss格式的字符串
	 */
	public static String formatPayDateStr(Date date){
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMATE_SECONDS);
		Date now = date;
		return df.format(now);
	}
	
	/**
	 * 获取当前日期字符串<br>
	 * @return yyyyMMdd格式的字符串
	 */
	public static String getCurrDateStr(){
		Date now = new Date() ;
		return formatDateStr(now) ;
	}
	
	/**
	 * 判断now是否达到标的时间subTime
	 * @param subTime 标的时间
	 * @param now     当前时间
	 * @param interval 超过时间间隔要求(毫秒)
	 * @return true 达到  false 未达到
	 */
	public static boolean isOnTime(Date subTime, Date now, long interval) {
		
		if ( null == now || null == subTime ) {
			return false; 
		}
		
		if (( now.getTime() - subTime.getTime() ) >= interval )
			
			return true; // 达到标的时间
		else
			
			return false;
	}
	
	
	/**
	 * 计算两个日期之间的天数，返回日期list
	 * @param date1 必须是 yyyyMMdd格式
	 * @param date2 必须是 yyyyMMdd格式
	 * 
	 * @return 
	 * 如果 date1和date2都不是有效的 日期yyyyMMdd，则返回{}<br>
	 * 如果 date1大于date2, 返回{}; <br>
	 * 如果 date1等于date2, 返回{'date1'}; <br>
	 * 如果 date1小于date2, 返回{'date1','20150101','20150102',....,'date2-1'} <br>
	 */
	public static List<String> daysBetweenTwoDate(String date1,String date2) {
		
		List<String> days = new ArrayList<String> () ; 
		days.clear(); 

		if( !isValidDate(date1,DATE_FORMATE_SIMPLE) 
				&& 
			!isValidDate(date2,DATE_FORMATE_SIMPLE) ){
			return days ;
		}
		
		if( compareDate(date1, date2) == 1 ){
		}

		else if( compareDate(date1, date2) == 0 ){
			days.add(date1) ;
		}
		
		else if( compareDate(date1, date2) == -1 ){
			
			days.add(date1);
			
			String nextIntDate = date1 ; 
			while(true){
				
				nextIntDate = TimeUtil.nextDate(nextIntDate, 1);
				
				if( TimeUtil.compareDate(nextIntDate, date2) == 0 ){
					break ; //如果下一天已经等于date2，退出(算头不算尾) 
				}

				if( TimeUtil.compareDate(nextIntDate, date2) == 1 ){
					break ; //如果下一天已经大于date2，并退出
				}

				days.add(nextIntDate);//收集小于date2的日期
			}
		}
		
		return days;
	}
	
	/**
	 * 是否为有效的日期格式
	 * @param dateStr 日期字符串
	 * @param formatStr 日期格式(如果为空，则默认为 "yyyy/MM/dd HH:mm:ss")
	 * @return
	 */
	public static boolean isValidDate(String dateStr, String formatStr) {
		
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(dateStr);
		} catch (ParseException e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			//e.printStackTrace(); 
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 */
	public static String getPreTime(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
			throw new RuntimeException("getPreTime获取时间信息时发生异常！");

		}
		return mydate1;
	}
	
	/**
	 * 获取日期中的小时和分钟
	 * @param now
	 * @return 字符串 HH:mm
	 */
	public static String getHourAndMinute(Date now) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			
			String mydate1 = format.format(now);
			return StringUtils.substring(mydate1, 11, 16);
		} catch (Exception e) {
			e.printStackTrace();  
			throw new RuntimeException("getHourAndMinute获取时间信息时发生异常！");
		}
	}
	
	/**
	 * 将整数形式的时间转换为Date类型 
	 * @param timeMillis 精确到毫秒
	 * @param pattern 日期的格式样式，默认为： yyyy-MM-dd HH:mm:ss:SSS
	 * @return 对应的日期字符串
	 */
	public static String longToDateStr(long timeMillis,String pattern) {
		
		if( StringUtils.isEmpty(pattern) ){
			pattern = "yyyy-MM-dd HH:mm:ss:SSS" ;
		}
		
		DateFormat df=new SimpleDateFormat(pattern);
		
		Calendar cal = Calendar.getInstance();  
		cal.setTimeInMillis(timeMillis);
		return  df.format(cal.getTime()) ; 
	}

	/**
	 * 将整数形式的时间转换为Date类型 
	 * @param timeMillis 精确到毫秒
	 * @return 对应的日期类型
	 */
	public static Date longToDate(long timeMillis) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeMillis);
		return cal.getTime();
	}
	
	/**
	 * 将字符串形式的日期，转换为Date类型<br>
	 * @param dateStr  格式化的日期字符串
	 * @param format   dateStr对应的格式化
	 * @return 如果dateStr，format其一为空，则返回null，否则返回对应的Date
	 */
	public static Date stringToDate(String dateStr, String format){
		
		Date date = null ; 
		if( BasicUtil.isEmpty(dateStr,format)){
			return date ; 
		}
		
		DateFormat df = new SimpleDateFormat(format) ; 
		try {
			date = df.parse(dateStr) ;
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("stringToTime转换日期时发生异常！");
		}
		
		return date ; 
	}
	
	/**
	 * 比较两个时间(24小时制)的早晚
	 * @param time1 字符串形式的日期HH:mm
	 * @param time2 字符串形式的日期HH:mm
	 * @return -1 time1早于time2，如： 12:04 < 12:10<br> 0 两个时间相同<br> 1 time1晚于time2，如： 11:40 > 11:30
	 */
	public static int compareTime(String time1, String time2) {
		
		DateFormat df = new SimpleDateFormat("HH:mm");
		try {
			Date dt1 = df.parse(time1);
			Date dt2 = df.parse(time2);
			if (dt1.getTime() > dt2.getTime()) {
				//System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				//System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException("比较时间发生异常！");
		}
	}
	
	/**
	 * 比较两个日期早晚
	 * @param date1 字符串形式的日期yyyyMMdd
	 * @param date2 字符串形式的日期yyyyMMdd
	 * @return -1 date1早于date2，如： 20150101 < 20150109<br> 0 两个日期为同一天<br> 1 date1晚于date2，如： 20150105 > 20150101
	 */
	public static int compareDate(String date1, String date2) {

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				//System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				//System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException("比较日期发生异常！");
		}
	}
	
	/**
	 * 比较两个日期之间的大小
	 * 
	 * @param d1
	 * @param d2
	 * @return -1 date1早于date2，如： 20150101 < 20150109<br>
	 *         0 两个日期为同一天<br>
	 *         1 date1晚于date2，如： 20150105 > 20150101
	 */
	public int compareDate(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		int result = c1.compareTo(c2);
		if (result == 0)
			return 0;
		else if (result > 0)
			return 1;
		else
			return -1;
	}
	
	
	/**
	 * 将lastClrDate加一天返回
	 * @param lastClrDate 字符串格式yyyyMMdd的日期
	 * @return yyyyMMdd
	 */
	public static String nextOneDate(String lastClrDate) {
		
		return nextDate(lastClrDate,1) ; 
	}
	
	/**
	 * 返回指定日期往后指定天数的日期
	 * @param lastClrDate 日期
	 * @param days 天数 正整数表示往后天数，负整数表示往前天数
	 * @return yyyyMMdd
	 */
	public static String nextDate(String dateStr, int days){
		Date d = stringToDate(dateStr,DATE_FORMATE_SIMPLE) ;
		Calendar cld=Calendar.getInstance();
		cld.setTime(d);
		cld.add(Calendar.DATE, days);
		return formatDateStr(cld.getTime());
	}

	
	/**
	 * 返回日期的时间
	 * @param dateStr 字符串形式的日期
	 * @param dateFormat 指定日期的格式样式，不指定，默认使用  "yyyy-MM-dd HH:mm:ss,SSS"
	 * @return 如果dataStr不是有效的日期格式，返回0，否者返回该日期对应的毫秒数
	 */
	public static long toTime(String dateStr, String dateFormat) {
		
		if( StringUtils.isEmpty(dateFormat) ){
			dateFormat =  "yyyy-MM-dd HH:mm:ss,SSS"; ; //默认日期格式
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			System.out.println("日期格式化失败！" + e);
			e.printStackTrace();
			return 0  ; 
		}
		
		return date.getTime();
	}
	
	
	/**
	 * 返回path中第一个满足yyyyMMdd格式的日期字符串
	 * @param path
	 * @return 如果path中有日期则返回，否则返回空字符串
	 */
	public static String getDatePatternFromStr(String path) {
		String reg = "[1-9]\\d{3}(((0[13578]|1[02])([0-2]\\d|3[01]))|((0[469]|11)([0-2]\\d|30))|(02([01]\\d|2[0-8])))";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(path);
		while (matcher.find()) {
			return matcher.group();
		}
		return "";
	}
	
}
