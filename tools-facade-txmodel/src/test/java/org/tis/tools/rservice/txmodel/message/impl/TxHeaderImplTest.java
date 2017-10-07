/**
 * 
 */
package org.tis.tools.rservice.txmodel.message.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.tis.tools.common.utils.FormattingUtil;
import org.tis.tools.common.utils.TimeUtil;
import org.tis.tools.rservice.txmodel.message.ITxHeader;

import junit.framework.Assert;

/**
 * 对交易请求头对象的单元测试
 * 
 * @author megapro
 *
 */
public class TxHeaderImplTest {

	/**
	 * 创建并使用TxHeadmerImpl
	 */
	@Test
	public void testNewTxHeaderImpl() {
		
		ITxHeader header = new TxHeaderImpl() ; 
		header.setBhvCode("commit-tx");
		header.setBhvGUID("123-123-123");
		header.setChannelID("tws");
		header.setOrgCode("66000");
		header.setTerminalCode("tws001");
		header.setTxCode("010505");
		Date d = new Date() ; 
		header.setTxDate(d);
		header.setTxSerialNo("123456789012");
		header.setUserID("66000998");
		
		header.addHeaderData("ext-String", "请求头中特别的数据4T24"); 
		Date dd = new Date() ; 
		header.addHeaderData("ext-Date", dd); 
		String timeStr = TimeUtil.formatDate(dd, "HHmmSSsss") ; 
		String timeStr2 = TimeUtil.formatDate(dd, "HHmmSS") ; 
		String timestampStr = TimeUtil.formatDate(dd, "yyyyMMddHHmmSSsss") ; 
		
		header.addHeaderData("ext-Integer",new Integer(123)); 
		header.addHeaderData("ext-int",123); 
		header.addHeaderData("ext-Boolean",false); 
		
		BigDecimal bd = new BigDecimal(100034) ; 
		header.addHeaderData("ext-BigDecimal", bd ); 

		Assert.assertEquals("commit-tx", header.getBhvCode()); 
		Assert.assertEquals("123-123-123", header.getBhvGUID());
		Assert.assertEquals("tws", header.getChannelID()); 
		Assert.assertEquals("66000", header.getOrgCode()); 
		Assert.assertEquals("tws001", header.getTerminalCode()); 
		Assert.assertEquals("010505", header.getTxCode()); 
		Assert.assertEquals(d, header.getTxDate()); 
		Assert.assertEquals("123456789012", header.getTxSerialNo()); 
		Assert.assertEquals("66000998", header.getUserID()); 

		Assert.assertEquals("请求头中特别的数据4T24", header.getHeaderData("ext-String")); 

		Assert.assertEquals(dd, header.getHeaderData("ext-Date")); 
		Assert.assertEquals(timeStr, header.getTxTime(null)); 
		Assert.assertEquals(timeStr2, header.getTxTime("HHmmSS")); 
		Assert.assertEquals(timestampStr, header.getTxTimestamp()); 
		
		
		Integer extInteger = header.getHeaderData("ext-Integer") ; 
		Assert.assertEquals(123, extInteger.intValue()); 
		
		int extInt = header.getHeaderData("ext-int") ; 
		Assert.assertEquals(123, extInt); 
		
		boolean extBoolean = header.getHeaderData("ext-Boolean") ; 
		Assert.assertEquals(false, extBoolean); 
		
		BigDecimal extBigdecimal = header.getHeaderData("ext-BigDecimal") ; 
		Assert.assertEquals(bd, extBigdecimal); 
		
		// 打印出来看看样子
		System.out.println( FormattingUtil.instance().formatJsonString(header.toString()));
		//System.out.println(DataObjectUtility.instance().toString((DataObject)header));
	}
}
