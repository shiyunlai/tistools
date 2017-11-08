/**
 * 
 */
package org.tools.core.sdo.dataobject;

/**
 * <pre>
 * 
 * 静态数据对象
 * 1、需要事先定义 MetaObject
 * 2、其属性不可以任意增加和删除，事先定义的属性为静态的
 * 
 * </pre>
 */

public class StaticDataObject extends DynamicDataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 元对象定义ID */
	private String metaObjectId;

	public StaticDataObject(String metaId) {
		super();
		this.metaObjectId = metaId;
	}

	/**
	 * @return the metaObjectId
	 */
	@Override
	public String getMetaObjectId() {
		return metaObjectId;
	}

	/**
	 * @param metaObjectId
	 *            the metaObjectId to set
	 */
	public void setMetaObjectId(String metaObjectId) {
		this.metaObjectId = metaObjectId;
	}

}