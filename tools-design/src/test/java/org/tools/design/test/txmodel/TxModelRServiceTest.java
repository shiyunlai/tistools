/**
 * 
 */
package org.tools.design.test.txmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.rservice.api.txmodel.ITxModelRService;
import org.tis.tools.rservice.txmodel.TxModelConstants;
import org.tis.tools.rservice.txmodel.impl.message.TxRequestImpl;
import org.tis.tools.rservice.txmodel.spi.message.ITxRequest;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.design.SpringJunitSupport;

/**
 * @author megapro
 *
 */
public class TxModelRServiceTest extends SpringJunitSupport {

	@Autowired
	ITxModelRService txModelRService;

	@Before
	public void before() {

	}

	@After
	public void after() {

	}

	/**
	 * <pre>
	 * 测试 交易模式服务：提交交易
	 * 相当于模拟了 API Gateway 对交易模式服务的 调用
	 * 
	 * </pre>
	 */
	@Test
	public void testOpenTx() {

		//构造一个交易请求
		ITxRequest request = new TxRequestImpl();
		request.getTxHeader().setTxCode("TX010505");
		request.getTxHeader().setBhvCode(TxModelConstants.BHVCODE.OPEN_TX.name());
		
		ITxResponse response = txModelRService.execute(request);
		System.out.println(response);
//		Assert.assertEquals(expected, actual);
	}

}
