/**
 * 
 */
package org.tis.tools.common.utils;

import java.time.LocalDateTime;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author megapro
 *
 */
public class TestAny {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCurrSeconds() {
		int i = 20 ;
		while( true ){
			
			if( i == 0 ){
				break ;
			}
			i -- ; 
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			LocalDateTime ldt = LocalDateTime.now() ;
			System.out.println(ldt.getSecond() ) ;
			System.out.println( System.currentTimeMillis()/1000);
			
		}
	}
}
