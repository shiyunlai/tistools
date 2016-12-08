package org.tis.tools.common.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 提供金额计算的工具，避免因为精度问题导致金额不一致。
 * BigDecimal 只有使用字符串型的构造函数，才能保证计算的准确性
 * @author wade
 *
 */
public class MoneyUtil {
	
	/** BigDecimal 类型的0*/
	public static final BigDecimal zero = new BigDecimal("0") ;
	/** BigDecimal 类型的100*/
	public static final BigDecimal hundred = new BigDecimal("100") ;
	
	/**
	 * 双精度的加法计算，返回精确的双精度数据
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double add(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.add(b2).doubleValue();
	}
	
	/**
	 * 双精度的加法计算，返回精确的双精度数据
	 * @param d1
	 * @param d2
	 * @return d1 + d2 
	 */
	public static BigDecimal add(BigDecimal d1, BigDecimal d2) {
		return d1.add(d2);
	}

	/**
	 * 双精度的减法计算，返回精确的双精度数据
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double subtract(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.subtract(b2).doubleValue();
	}
	
	/**
	 * 双精度的减法计算，返回精确的双精度数据
	 * @param d1
	 * @param d2
	 * @return d1 - d2
	 */
	public static BigDecimal subtract(BigDecimal d1, BigDecimal d2) {
		if(d1==null){
			d1= zero;
		}
		if(d2==null){
			d2= zero;
		}
		return d1.subtract(d2);
	}

	/**
	 * 双精度的乘法计算，返回精确的双精度数据
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double multiply(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	 * 双精度的乘法计算，返回精确的双精度数据
	 * @param d1
	 * @param d2
	 * @return d1 × d2
	 */
	public static BigDecimal multiply(BigDecimal d1, BigDecimal d2) {
		return d1.multiply(d2);
	}
	
	/**
	 * 双精度的除法计算，返回精确的双精度数据
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double divide(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 除法计算，返回BigDecimal
	 * @param d1
	 * @param d2
	 * @return d1/d2
	 */
	public static BigDecimal divide(BigDecimal d1, BigDecimal d2) {
		return d1.divide(d2);
	}
	
	/**
	 * 双精度的除法计算，并支持对计算的结果做精度设置，返回精确的双精度数据
	 * @param d1
	 * @param d2
	 * @param scale
	 * @return
	 */
	public static double divide(double d1, double d2, int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 除法计算，并支持对计算的结果做精度设置，返回精确的双精度数据<br>
	 * 采用HALF_UP方式处理小数
	 * @param d1
	 * @param d2
	 * @param scale
	 * @return d1/d2 按照HALF_UP方式四舍五入，保留scale位小数
	 */
	public static BigDecimal divide(BigDecimal d1, BigDecimal d2, int scale) {
		//return d1.divide(d2, scale, RoundingMode.HALF_UP) ;
		return divide(d1, d2, scale,RoundingMode.HALF_UP) ;
	}
	
	/**
	 * 除法计算
	 * @param d1 除数
	 * @param d2 被除数
	 * @param scale 小数保留精度
	 * @param mode 指定小数处理方式
	 * @return 精确的双精度数据
	 */
	public static BigDecimal divide(BigDecimal d1, BigDecimal d2, int scale,RoundingMode mode) {
		return d1.divide(d2, scale, mode) ;
	}

	/**
	 * 双精度的四舍五入计算，设置精度
	 * @param d
	 * @return
	 */
	public static double round(double data, int scale){
		String formatStr = "0";//默认取整
		if(scale>0){
			formatStr = StringUtils.rightPad("0.", scale+2, "0");//整理出精度字符串格式。例如0.00
		}
		DecimalFormat myformat = new DecimalFormat(formatStr);
		String str = myformat.format(data);
		return Double.valueOf(str);
	}
	
	/**
	 * 四舍五入计算，设置精度
	 * @param d
	 * @return 以HALF_UP的方式保留data小数后返回
	 */
	public static BigDecimal round(BigDecimal data, int scale){
//		MathContext mc = new MathContext(scale, RoundingMode.HALF_UP) ;
//		return data.round(mc) ;
		
		String formatStr = "0";//默认取整
		if(scale>0){
			formatStr = StringUtils.rightPad("0.", scale+2, "0");//整理出精度字符串格式。例如0.00
		}
		DecimalFormat myformat = new DecimalFormat(formatStr);
		String str = myformat.format(data);
		return new BigDecimal(str);
	}
	
	public static void main(String[] args) {
		
		BigDecimal kk =new BigDecimal("0.135556") ;
		System.out.println(MoneyUtil.round(kk, 2) );
		
		BigDecimal a =new BigDecimal("0.5") ;
		BigDecimal b =new BigDecimal("0.2") ;
		
		System.out.println(a.divide(b));
		
		MathContext mc = new MathContext(2, RoundingMode.HALF_DOWN);
		System.out.println(a.divide(b, mc) );
		
		BigDecimal c =new BigDecimal("54000") ;
		BigDecimal d =new BigDecimal("17000") ;
		BigDecimal e =new BigDecimal("61573.54") ;
		System.out.println(divide(multiply(d, e), c, 8).toString());
	}
	
	/**
	 * 双精度的四舍五入计算，设置精度0
	 * @param d
	 * @return
	 */
	public static double round(double data){
		return round(data,0);
	}
	
	/**
	 * 双精度的四舍五入计算，设置精度0
	 * @param d
	 * @return
	 */
	public static BigDecimal round(BigDecimal data){
		return round(data,0);
	}
	
	/**
	 * 检测当前输入字符串是否是数字的字符串
	 * @param value
	 * @return
	 */
	public static boolean isNumber(String value) { // 检查是否是数字
		String patternStr = "^\\d+$";
		Pattern p = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE); // 忽略大小写;
		Matcher m = p.matcher(value);
		return m.find();
	}
	
	/**
	 * 将double转换为字符串<br>
	 * double在出现大数据时，避免科学计数法出现
	 * @param d
	 * @return
	 */
	public static String formateDouble(Double d){
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(true);
		return nf.format(d);
	}
	
	/**
	 * 将BigDecimal转换为字符串
	 * @param d
	 * @return
	 */
	public static String formateBigDecimal(BigDecimal d){
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(true);
		return nf.format(d);
	}
	
	/**
	 * 比较两个BigDecimal类型的大小
	 * @param d1
	 * @param d2
	 * @return 
	 * 	-1 : d1小于d2<br>
	 * 	0 : d1等于d2<br>
	 * 	1 : d1大于d2.
	 */
	public static int compareTo(BigDecimal d1, BigDecimal d2){
		
		return d1.compareTo(d2) ;
	}
	
	/**
	 * 比较d1是否大于0
	 * @param d1
	 * @return true-大于0 false-小于或等于0
	 */
	public static boolean isGreaterThanZero(BigDecimal d1){
		
		if( compareTo(d1, zero) == 1){
			return true ;
		}
		
		return false ; 
	}
	
	/**
	 * 比较d1是否等于0
	 * @param d1
	 * @return true-等于0 false-大于或小于0(不等于0)
	 */
	public static boolean isEqualZero(BigDecimal d1){
		
		if( compareTo(d1, zero) == 0 ){
			return true ;
		}
		
		return false ; //大于0 或者 小于0
	}
	
	/**
	 * 取余
	 * @param d1
	 * @param d2
	 * @return d1 % d2 
	 */
	public static int remainder(BigDecimal d1, BigDecimal d2){
		
		return d1.remainder(d2).setScale(0,BigDecimal.ROUND_HALF_UP).intValue() ;//四舍五入去小数点
	}
}
