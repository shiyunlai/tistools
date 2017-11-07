/**
 * 
 */
package org.tis.tools.rservice.txmodel.impl.message;

import org.tis.tools.common.utils.FormattingUtil;
import org.tis.tools.rservice.txmodel.spi.message.ITxControl;
import org.tis.tools.rservice.txmodel.spi.message.ITxData;
import org.tis.tools.rservice.txmodel.spi.message.ITxHeader;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 交易响应对象实现类
 * 
 * @author megapro
 *
 */
public class TxResponseImpl implements ITxResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7748743478957837002L;

	private ITxControl txControl;
	private ITxHeader txHeader;
	private ITxData txResponseData;
	private String token;

	public TxResponseImpl() {
		txControl = new TxControlImpl() ; 
		txHeader = new TxHeaderImpl() ; 
		txResponseData = new TxDataImpl() ; 
		token = "asdfsafsfsdf" ;//TODO 临时 
	}
	
	public TxResponseImpl(ITxControl txControl,ITxHeader txHeader,ITxData txResponseData, String token) {
		this.txControl = txControl ; 
		this.txHeader = txHeader ; 
		this.txResponseData = txResponseData ; 
		this.token = token ; 
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tis.tools.rservice.txmodel.message.ITxResponse#getTxControl()
	 */
	@Override
	public ITxControl getTxControl() {
		return this.txControl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tis.tools.rservice.txmodel.message.ITxResponse#getTxHeader()
	 */
	@Override
	public ITxHeader getTxHeader() {
		return this.txHeader ; 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tis.tools.rservice.txmodel.message.ITxResponse#getResponseData()
	 */
	@Override
	public ITxData getResponseData() {
		return this.txResponseData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tis.tools.rservice.txmodel.message.ITxResponse#getToken()
	 */
	@Override
	public String getToken() {
		return this.token;
	}

	@Override
	public void setTxControl(ITxControl txControl) {
		this.txControl = txControl;
	}

	@Override
	public void setTxHeader(ITxHeader txHeader) {
		this.txHeader = txHeader;
	}

	@Override
	public void setResponseData(ITxData txResponseData) {
		this.txResponseData = txResponseData;
	}

	@Override
	public void setToken(String token) {

		this.token = token;
	}

	@Override
	public String toString() {
		return FormattingUtil.instance().formatJsonString( JSON.toJSONString(this) ) ; // 使用ali fastjson
		//return FormattingUtil.instance().toJsonStringFormatted(this) ; 
	}
	
}
