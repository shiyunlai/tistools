/**
 * 
 */
package org.tis.tools.rservice.txmodel.impl.message;

import org.tis.tools.rservice.txmodel.monitor.MonitorTypeEnum;
import org.tis.tools.rservice.txmodel.override.OverrideTypeEnum;
import org.tis.tools.rservice.txmodel.recheck.ReCheckTypeEnum;
import org.tis.tools.rservice.txmodel.spi.message.ITxControl;
import org.tools.core.sdo.DataObject;

/**
 * @author megapro
 *
 */
public class TxControlImpl implements ITxControl {

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#isSuccess()
	 */
	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#getRetCode()
	 */
	@Override
	public String getRetCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#getRetMessage()
	 */
	@Override
	public String getRetMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#isOverride()
	 */
	@Override
	public boolean isOverride() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#getOverrideType()
	 */
	@Override
	public OverrideTypeEnum getOverrideType() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#getOverrideControl()
	 */
	@Override
	public DataObject getOverrideControl() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#isReCheck()
	 */
	@Override
	public boolean isReCheck() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#getReCheckType()
	 */
	@Override
	public ReCheckTypeEnum getReCheckType() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#getReCheckControl()
	 */
	@Override
	public DataObject getReCheckControl() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#isDoubleRecord()
	 */
	@Override
	public boolean isDoubleRecord() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#getDoubleRecordControl()
	 */
	@Override
	public DataObject getDoubleRecordControl() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#isMonitor()
	 */
	@Override
	public boolean isMonitor() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#getMonitorType()
	 */
	@Override
	public MonitorTypeEnum getMonitorType() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.txmodel.message.ITxControl#getMonitorControl()
	 */
	@Override
	public DataObject getMonitorControl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getProperty(String key, T Default) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperty(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

}
