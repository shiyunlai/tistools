/**
 * 
 */
package org.tis.tools.rservice.txmodel.impl.message;

import org.tis.tools.rservice.txmodel.monitor.MonitorTypeEnum;
import org.tis.tools.rservice.txmodel.override.OverrideTypeEnum;
import org.tis.tools.rservice.txmodel.recheck.ReCheckTypeEnum;
import org.tis.tools.rservice.txmodel.spi.message.ITxControl;
import org.tools.core.sdo.DataObject;
import org.tools.core.sdo.dataobject.DynamicDataObject;

import com.alibaba.fastjson.JSON;

/**
 * <pre>
 * 交易控制对象.</br>
 * 前端和服务端借助交易控制对象，相互传递交易操作行为要求.</br>
 * 如：</br>
 * 渠道端发出交易操作时，强制不需要做授权处理时 override(false) ; </br>
 * 服务端收到交易操作请求，判断交易数据，发现需要执行复核操作时 reCheck(true) ; </br>
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class TxControlImpl extends DynamicDataObject implements ITxControl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2575982507600450430L;

	/**
	 * 成功标识.</br>
	 * true - 成功</br>
	 * false - 失败</br>
	 */
	private boolean success;

	/**
	 * 响应代码
	 */
	private String retCode;

	/**
	 * 响应信息
	 */
	private String retMessage;

	/**
	 * 是否需要授权
	 */
	private boolean isOverride;
	/**
	 * 授权方式
	 */
	private OverrideTypeEnum overrideType;
	/**
	 * 授权控制参数（按照参数执行复核操作）
	 */
	private DataObject overrideControl;

	/**
	 * 是否需要复核
	 */
	private boolean isReCheck;
	/**
	 * 复核方式
	 */
	private ReCheckTypeEnum reCheckType;
	/**
	 * 复核控制参数（按照参数执行复核操作处理）
	 */
	private DataObject reCheckControl;

	/**
	 * 是否需要双录
	 */
	private boolean isDoubleRecord;

	/**
	 * 双录控制参数（按照参数实施双录行为）
	 */
	private DataObject doubleRecordControl;

	/**
	 * 是否需要监控（调用摄像头）
	 */
	private boolean isMonitor;
	/**
	 * 交易监控方式
	 */
	private MonitorTypeEnum monitorType;
	/**
	 * 控控制参数（用于控制监控摄像头的参数）
	 */
	private DataObject monitorControl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#isSuccess()
	 */
	@Override
	public boolean isSuccess() {
		return this.success;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#getRetCode()
	 */
	@Override
	public String getRetCode() {
		return this.retCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#getRetMessage()
	 */
	@Override
	public String getRetMessage() {
		return this.retMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#isOverride()
	 */
	@Override
	public boolean isOverride() {
		return this.isOverride;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#getOverrideType()
	 */
	@Override
	public OverrideTypeEnum getOverrideType() {
		return this.overrideType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tis.tools.rservice.txmodel.message.ITxControl#getOverrideControl()
	 */
	@Override
	public DataObject getOverrideControl() {
		return this.overrideControl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#isReCheck()
	 */
	@Override
	public boolean isReCheck() {
		return this.isReCheck;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#getReCheckType()
	 */
	@Override
	public ReCheckTypeEnum getReCheckType() {
		return this.reCheckType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tis.tools.rservice.txmodel.message.ITxControl#getReCheckControl()
	 */
	@Override
	public DataObject getReCheckControl() {
		return this.reCheckControl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#isDoubleRecord()
	 */
	@Override
	public boolean isDoubleRecord() {
		return this.isDoubleRecord;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tis.tools.rservice.txmodel.message.ITxControl#getDoubleRecordControl(
	 * )
	 */
	@Override
	public DataObject getDoubleRecordControl() {
		return this.doubleRecordControl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#isMonitor()
	 */
	@Override
	public boolean isMonitor() {
		return this.isMonitor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#getMonitorType()
	 */
	@Override
	public MonitorTypeEnum getMonitorType() {
		return this.monitorType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tis.tools.rservice.txmodel.message.ITxControl#getMonitorControl()
	 */
	@Override
	public DataObject getMonitorControl() {
		return this.monitorControl;
	}

	@Override
	public <T> T getProperty(String key, T Default) {
		return getProperty(key, Default);
	}

	@Override
	public void setProperty(String key, Object value) {
		setProperty(key, value);
	}

	@Override
	public void success(boolean success) {
		this.success = success;
	}

	@Override
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	@Override
	public void setRetMessage(String message) {
		this.retMessage = message;
	}

	@Override
	public void override(boolean override) {
		this.isOverride = override;
	}

	@Override
	public void setOverrideType(OverrideTypeEnum overrideType) {
		this.overrideType = overrideType;
	}

	@Override
	public void setOverrideControl(DataObject overrideControl) {
		this.overrideControl = overrideControl;
	}

	@Override
	public void reCheck(boolean reCheck) {
		this.isReCheck = reCheck;
	}

	@Override
	public void setReCheckType(ReCheckTypeEnum reCheckType) {
		this.reCheckType = reCheckType;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}
}
