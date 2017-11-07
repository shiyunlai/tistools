/**
 * 
 */
package org.tis.tools.rservice.txmodel;

import org.junit.Test;
import org.tis.tools.rservice.txmodel.TxModelEnums.BHVCODE;
import org.tis.tools.rservice.txmodel.TxModelEnums.BHVTYPE;

import junit.framework.Assert;

/**
 * 对TxModelConstants的单元测试
 * 
 * @author megapro
 *
 */
public class TxModelEnumsTest {

	@Test
	public void testBHVCODE() {
		BHVCODE nonOp = BHVCODE.valueOf("NONOPERATOR");
		BHVCODE openTx = BHVCODE.valueOf("OPEN_TX");
		BHVCODE closeTx = BHVCODE.valueOf("CLOSE_TX");

		Assert.assertEquals(BHVCODE.NONOPERATOR, nonOp);
		Assert.assertEquals("空操作", nonOp.getDesc());
		
		Assert.assertEquals(BHVCODE.OPEN_TX, openTx);
		Assert.assertEquals("OPEN_TX:打开交易", openTx.toString());

		Assert.assertEquals(BHVCODE.CLOSE_TX, closeTx);
	}
	
	
	@Test
	public void testBHVTYPE() {
		BHVTYPE account = BHVTYPE.valueOf("ACCOUNT") ; 
		BHVTYPE maintain = BHVTYPE.valueOf("MAINTAIN") ; 
		BHVTYPE noCategory = BHVTYPE.valueOf("NO_CATEGORY") ; 

		
		Assert.assertEquals(BHVTYPE.ACCOUNT, account);
		Assert.assertEquals("账务类", account.getDesc());
		
		Assert.assertEquals(BHVTYPE.MAINTAIN, maintain);
		Assert.assertEquals("MAINTAIN:维护类", maintain.toString());
		
		Assert.assertEquals(BHVTYPE.NO_CATEGORY, noCategory);
	}
}
