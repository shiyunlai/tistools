/**
 * 
 */
package org.tis.tools.common.utils.marshaller;

/**
 * 数据对象包裹
 *
 * @author megapro
 */
public class DataWrapper implements java.io.Serializable {

	private static final long serialVersionUID = 4453190795843175352L;

	private Object data = null;

	public Object getData() {
		return data;
	}

	public void setData(Object object) {
		this.data = object;
	}
}