/**
 * 
 */
package org.tis.tools.rservice.txmodel.impl.message;

import java.util.Date;

import org.tis.tools.common.utils.TimeUtil;
import org.tis.tools.rservice.txmodel.spi.message.ITxHeader;
import org.tools.core.sdo.dataobject.DynamicDataObject;

/**
 * <pre>
 * 交易头信息实现
 * 
 * 继承DynamicDataObject，通过指定固定的字段名称，实现ITxHeader对交易请求头的结构要求。
 * 
 * 如：
 * 	渠道ID HEADER_FIELD_CHANNEL_ID = "channelID" ; 
 * 	机构代码 HEADER_FIELD_ORG_CODE = "orgCode" ; 
 * 	...
 * 	
 * </pre>
 * @author megapro
 *
 */
public class TxHeaderImpl extends DynamicDataObject implements ITxHeader {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2850395012776300414L;
	
	/**
	 * 交易头数据字段： 渠道ID
	 */
	public static final String HEADER_FIELD_CHANNEL_ID = "channelID" ; 
	/**
	 * 交易头数据字段： 机构代码
	 */
	public static final String HEADER_FIELD_ORG_CODE = "orgCode" ; 
	/**
	 * 交易头数据字段： 终端标识
	 */
	public static final String HEADER_FIELD_TERMINAL_CODE = "terminalCode" ; 
	/**
	 * 交易头数据字段： 用户
	 */
	public static final String HEADER_FIELD_USER_ID = "userID" ; 
	/**
	 * 交易头数据字段： 交易日期
	 */
	public static final String HEADER_FIELD_TX_DATE = "txDate" ; 
	/**
	 * 交易头数据字段： 交易代码
	 */
	public static final String HEADER_FIELD_TX_CODE = "txCode" ; 
	/**
	 * 交易头数据字段： 行为代码（交易操作代码）
	 */
	public static final String HEADER_FIELD_BHV_CODE = "bhvCode" ; 
	/**
	 * 交易头数据字段： 交易流水号
	 */
	public static final String HEADER_FIELD_TX_SERIAL_NO = "txSerialNo" ; 
	/**
	 * 交易头数据字段： 本次操作行为的GUID
	 */
	public static final String HEADER_FIELD_BHV_GUID = "bhvGUID" ; 
	
	
	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxHeader#getChannelID()
	 */
	@Override
	public String getChannelID() {
		return super.getString(HEADER_FIELD_CHANNEL_ID);
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxHeader#getOrgCode()
	 */
	@Override
	public String getOrgCode() {
		return super.getString(HEADER_FIELD_ORG_CODE);
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxHeader#getTerminalCode()
	 */
	@Override
	public String getTerminalCode() {
		return super.getString(HEADER_FIELD_TERMINAL_CODE);
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxHeader#getUserID()
	 */
	@Override
	public String getUserID() {
		return super.getString(HEADER_FIELD_USER_ID);
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxHeader#getTxDate(java.lang.String)
	 */
	@Override
	public Date getTxDate() {
		return super.getDate(HEADER_FIELD_TX_DATE) ; 
	}
	
	@Override
	public String getTxDate(String format) {
		if( "".equals(format) || null == format ){
			 format = "yyyyMMdd"  ;
		}
		Date d = getTxDate() ; 
		return TimeUtil.formatDate(d, format) ; 
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxHeader#getTxTime(java.lang.String)
	 */
	@Override
	public String getTxTime(String format) {
		if( "".equals(format) || null == format ){
			 format = "HHmmSSsss"  ;
		}
		Date d = getTxDate() ; 
		return TimeUtil.formatDate(d, format) ; 
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxHeader#getTxTimestamp(java.lang.String)
	 */
	@Override
	public String getTxTimestamp() {
		Date d = getTxDate() ; 
		return TimeUtil.formatDate(d, "yyyyMMddHHmmSSsss") ; 
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxHeader#getTxCode()
	 */
	@Override
	public String getTxCode() {
		return super.getString(HEADER_FIELD_TX_CODE) ; 
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxHeader#getBhvCode()
	 */
	@Override
	public String getBhvCode() {
		return super.getString(HEADER_FIELD_BHV_CODE);
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxHeader#getTxSerialNo()
	 */
	@Override
	public String getTxSerialNo() {
		return super.getString(HEADER_FIELD_TX_SERIAL_NO) ; 
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxHeader#setTxSerialNo(java.lang.String)
	 */
	@Override
	public void setTxSerialNo(String txSerialNo) {
		super.setString(HEADER_FIELD_TX_SERIAL_NO, txSerialNo);
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxHeader#getBhvGUID()
	 */
	@Override
	public String getBhvGUID() {
		return super.getString(HEADER_FIELD_BHV_GUID);
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxHeader#setExtMessage(java.lang.String, java.lang.String)
	 */
	@Override
	public void addHeaderData(String name, Object value) {
		super.set(name, value);
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxHeader#getExtMessage(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getHeaderData(String name) {
		return (T)super.get(name) ; 
	}

	@Override
	public void setChannelID(String channelID) {
		super.setString(HEADER_FIELD_CHANNEL_ID, channelID); 
	}

	@Override
	public void setOrgCode(String orgCode) {
		super.setString(HEADER_FIELD_ORG_CODE, orgCode); 
	}

	@Override
	public void setTerminalCode(String terminalCode) {
		super.setString(HEADER_FIELD_TERMINAL_CODE, terminalCode); 
	}

	@Override
	public void setUserID(String userID) {
		super.setString(HEADER_FIELD_USER_ID, userID); 
	}

	@Override
	public void setTxDate(Date txDate) {
		super.setDate(HEADER_FIELD_TX_DATE, txDate); 
	}

	@Override
	public void setTxCode(String txCode) {
		super.setString(HEADER_FIELD_TX_CODE, txCode); 
	}

	@Override
	public void setBhvCode(String bhvCode) {
		super.setString(HEADER_FIELD_BHV_CODE, bhvCode); 
	}

	@Override
	public void setBhvGUID(String bhvGUID) {
		super.setString(HEADER_FIELD_BHV_GUID, bhvGUID); 
	}

	@Override
	public <T> T getProperty(String key, T Default) {
		
		return getProperty(key, Default);
	}

	@Override
	public void setProperty(String key, Object value) {
		this.set(key, value) ; 
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
