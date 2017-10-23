/**
 * 
 */
package org.tis.tools.rservice.txmodel.message.impl;

import org.tis.tools.rservice.txmodel.message.ITxControl;
import org.tis.tools.rservice.txmodel.message.ITxData;
import org.tis.tools.rservice.txmodel.message.ITxHeader;
import org.tis.tools.rservice.txmodel.message.ITxResponse;

/**
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
	private Object token;

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
		return this.getTxHeader();
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
	public Object getToken() {
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
	public void setToken(Object token) {

		this.token = token;
	}

}
