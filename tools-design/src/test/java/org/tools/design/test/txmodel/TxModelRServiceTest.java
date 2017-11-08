/**
 * 
 */
package org.tools.design.test.txmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.rservice.txmodel.TxModelEnums;
import org.tis.tools.rservice.txmodel.api.ITxModelRService;
import org.tis.tools.rservice.txmodel.impl.message.TxRequestImpl;
import org.tis.tools.rservice.txmodel.spi.message.ITxRequest;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.design.SpringJunitSupport;

import com.alibaba.fastjson.JSON;

import junit.framework.Assert;

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
		request.setRequestID("asfdasfdsafsd");
		request.getTxHeader().setChannelID("TWS");//模拟柜面渠道发起交易请求
		request.getTxHeader().setTxCode("TX010505");
		request.getTxHeader().setBhvCode(TxModelEnums.BHVCODE.OPEN_TX.name());
		
		System.out.println("TxModelRServiceTest 发送交易请求："+JSON.toJSONString(request));
		ITxResponse response = txModelRService.execute(request);
		System.out.println("TxModelRServiceTest 收到响应结果："+JSON.toJSONString(response));
		
		Assert.assertNotNull(response.getTxHeader().getTxSerialNo());
		System.out.println(response.getTxHeader().getTxSerialNo());
		
	}

}
