/**
 * 
 */
package org.tis.tools.common.utils;

import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @author megapro
 *
 */
public class TestAny {

	/**
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCurrSeconds() {
		int i = 2 ;
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
	
	@Test
	public void testSubStr(){
		
		String oldOrgSeq = "111.222.333.444.555" ; 
		String oldOrgSeq2 = "111" ; 
		String guid = "8888" ; 
		String out = oldOrgSeq.substring(0, oldOrgSeq.lastIndexOf(".")+1)+guid ; 
		Assert.assertEquals("111.222.333.444.8888", out); 
		Assert.assertEquals(true, oldOrgSeq.contains("."));
		Assert.assertEquals(false, oldOrgSeq2.contains("."));
	}
}
