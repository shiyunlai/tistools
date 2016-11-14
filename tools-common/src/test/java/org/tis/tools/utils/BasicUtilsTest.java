/**
 * 
 */
package org.tis.tools.utils;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author megapro
 *
 */
public class BasicUtilsTest {

	@Test
	public void test() {
		
//		System.out.println( BasicUtils.ensitiveStr("532424198107241651",4,4,'#',6) );
//		System.out.println( BasicUtils.ensitiveStr("532424198107241651",3,5,'*',10) );
//		System.out.println( BasicUtils.ensitiveStr("532424198107241651",3,0,'%',6) );
//		System.out.println( BasicUtils.ensitiveStr("532424198107241651",0,4,'^',6) );
//		System.out.println( BasicUtils.ensitiveStr("532424198107241651",0,4,'c',0) );
//		System.out.println( BasicUtils.ensitiveStr("5324",4,4,'^',6) );
//		System.out.println( BasicUtils.ensitiveStr("",4,4,'^',6) );
		
//		System.out.println( BasicUtils.toPercentNice(new BigDecimal("12.345678")));
//		System.out.println( BasicUtils.toPercentNice(new BigDecimal("0.12345678")));
//		System.out.println( BasicUtils.toPercentNice(new BigDecimal("0.11112")));
//		System.out.println( BasicUtils.toPercentNice(new BigDecimal("0.567345678")));
//		System.out.println( BasicUtils.toPercentNice(new BigDecimal("0.000123")));
//		System.out.println( BasicUtils.toPercentNice(new BigDecimal("0.2000003")));
//		System.out.println( BasicUtils.toPercentNice(new BigDecimal("0.0850123")));
//System.out.println("------------------------------");
//		System.out.println( BasicUtils.toPercentOral(new BigDecimal("12.345678"),2,RoundingMode.HALF_DOWN));
//		System.out.println( BasicUtils.toPercentOral(new BigDecimal("0.12345678"),2,RoundingMode.HALF_DOWN));
//		System.out.println( BasicUtils.toPercentOral(new BigDecimal("0.11112"),2,RoundingMode.HALF_DOWN));
//		System.out.println( BasicUtils.toPercentOral(new BigDecimal("0.567345678"),2,RoundingMode.HALF_DOWN));
//		System.out.println( BasicUtils.toPercentOral(new BigDecimal("0.000123"),2,RoundingMode.HALF_DOWN));
		
		
		Map maps = new HashMap<String,String>() ;
		maps.put("123", "123") ;
		maps.put("1231", "123sd") ;
		maps.put("123d", "123rt") ;
		System.out.println(BasicUtil.showMaps(maps)) ;
	}

}
