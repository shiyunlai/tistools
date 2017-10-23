/**
 * 
 */
package org.tis.tools.rservice.txmodel.message.impl;

import org.tis.tools.rservice.txmodel.message.ITxControl;
import org.tis.tools.rservice.txmodel.message.ITxData;
import org.tis.tools.rservice.txmodel.message.ITxHeader;
import org.tis.tools.rservice.txmodel.message.ITxRequest;

/**
 * 
 * 交易请求实现类
 * 
 * @author megapro
 *
 */
public class TxRequestImpl implements ITxRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4206102931429636996L;

	private ITxControl txControl ; 
	private ITxHeader txHeader ; 
	private ITxData txData ; 
	private String token ; 
	
	
	public TxRequestImpl() {
		super();
		txControl = new TxControlImpl() ; 
		txHeader = new TxHeaderImpl() ; 
		txData = new TxDataImpl() ; 
		token = null ; 
	}
	
	public TxRequestImpl(ITxControl txControl, ITxHeader txHeader, ITxData txData, String token) {
		super();
		this.txControl = txControl;
		this.txHeader = txHeader;
		this.txData = txData;
		this.token = token;
	}

	@Override
	public ITxControl getTxControl() {
		return this.txControl ;
	}

	@Override
	public ITxHeader getTxHeader() {
		return this.txHeader;
	}

	@Override
	public ITxData getRequestData() {
		return this.txData;
	}

	@Override
	public Object getToken() {
		//TODO 生成token
		this.token = "tokenstring" ; 
		return this.token;
	}
	
	/**
	 * <pre>
	 * 
	 * 设置交易请求的token
	 * 
	 * token, 由交易请求者使用公钥对指定数据内容进行加密后生成basic64数据串
	 * 
	 * 每请求一个token
	 * 
	 * </pre>
	 * 
	 * @param token
	 */
	public void setToken(String token){
		this.token = token ; 
	}

	/**
	 * 检查当前请求数据是否有效 
	 * 使用私钥解密token，并进行数据项检查，通过则认为合法
	 * 
	 * @return
	 */
	private boolean checkToken() {
		// TODO Auto-generated method stub 使用私钥解密token，并进行数据项检查，通过则认为合法
		return true;
	}

}
