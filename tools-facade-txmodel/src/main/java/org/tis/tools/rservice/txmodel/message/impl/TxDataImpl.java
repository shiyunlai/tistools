/**
 * 
 */
package org.tis.tools.rservice.txmodel.message.impl;

import org.tis.tools.rservice.txmodel.message.ITxData;
import org.tools.core.sdo.DataField;
import org.tools.core.sdo.dataobject.DynamicDataObject;

/**
 * <pre>
 * 交易数据实现类 
 * 继承动态数据对象，实现ITxData接口
 * </pre>
 * 
 * @author megapro
 *
 */
public class TxDataImpl extends DynamicDataObject implements ITxData {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3251428677661454214L;

	@Override
	public String[] getFieldsName() {
		 
		return getPropertyNames() ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataField> T[] getFields() {
		
		return (T[])this.getDataMap().values().toArray() ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getFieldValue(String fieldName) {
		return (T) getData(fieldName).getProtoValue();
	}
	
	@Override
	public <T extends DataField> T getField(String propertyName) {
		return super.getField(propertyName);
	}
	
}
