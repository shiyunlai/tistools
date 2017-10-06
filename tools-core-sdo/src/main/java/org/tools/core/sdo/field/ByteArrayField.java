/**
 * 
 */
package org.tools.core.sdo.field;

/**
 * <pre>
 * 
 * 数据域：字节数组（byte[]）类型的字段
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class ByteArrayField extends AbstractField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private byte[] value = null;

	public ByteArrayField(String fieldName) {
		super(fieldName);
	}

	/**
	 * @return Returns the value.
	 */
	public byte[] getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(byte[] value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fone.ctc.extend.data.DataField#getStringValue()
	 */
	@Override
	public String getStringValue() {
		return value.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fone.ctc.extend.data.DataField#setValueWithString(java.lang.String)
	 */
	@Override
	public void setValueWithString(String val) {
		this.value = val.getBytes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fone.ctc.extend.data.Data#getProtoValue()
	 */
	@Override
	public byte[] getProtoValue() {
		return value;
	}

}
