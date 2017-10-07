/**
 * 
 */
package org.tools.core.sdo.field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tools.core.sdo.Data;
import org.tools.core.sdo.DataField;

/**
 * <pre>
 * 
 * 抽象数据Field实现
 * 
 * </pre>
 */

//TODO shiyl 20171005 应该加入field的验证处理（validation）能力，以便在交易处理逻辑中进行字段合法性的验证
public abstract class AbstractField implements DataField , Comparable<AbstractField>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** 元数据 */
	private String metaId;

	/** 数据名称 */
	private String name;

	/** 长度:变长取最大长度 */
	private short length;

	/**
	 * <pre>
	 * 构造函数
	 * 每个数据域必需指定数据名称
	 * </pre>
	 * 
	 * @param fieldName
	 *            数据名称
	 */
	public AbstractField(String fieldName) {
		setName(fieldName);
	}

	/**
	 * 取数据类型
	 * 
	 * @return
	 */
	public String getDataType() {
		return (Data.DATA_TYPE_FIELD);
	}

	/**
	 * 设置数据域的长度
	 * 
	 * @param fieldLength
	 *            数据长度
	 */
	public void setLength(short fieldLength) {
		this.length = fieldLength;
	}

	/**
	 * 取数据域长度
	 * 
	 * @return 数据长度
	 */
	public int getLength() {
		return length;
	}

	/**
	 * 判断是否是空field
	 * 
	 * @retuen 否
	 */
	public boolean isNullField() {
		return (false);
	}

	@Override
	public int compareTo(AbstractField o) {
		if( isEquals(o) ){
			return 0 ; // 相同 
		}
		return -1; // 不同
	}
	
	/**
	 * 判断是否相同field
	 * 
	 * @param fld
	 *            数据域
	 */
	//TODO shiyl 20171005 比较两个Filed是否相等，应该要放给子类实现
	private boolean isEquals(AbstractField fld) {
		
		if (fld == null)
			return false;
		
		String name = fld.getName();
		String val = fld.getStringValue();
		String valself = getStringValue();

		if (name != null && name.equals(this.getName()) && fld.getClass().getName().equals(this.getClass().getName())) {
			if ((val == null) != (valself == null))
				return false;
			if (val == null)
				return true;
			else
				return val.equals(valself);
		} else {
			return false;
		}
	}
	
	//TODO shiyl 20171005 由父类统一实现，做统一日志和异常处理，但是把转换过程放到子类中即可
//	@Override
//	public void setValueWithString(String val) {
//		// TODO Auto-generated method stub
//		
//	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the metaId
	 */
	public String getMetaId() {
		return metaId;
	}

	/**
	 * @param metaId
	 *            the metaId to set
	 */
	public void setMetaId(String metaId) {
		this.metaId = metaId;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [metaId=" + metaId + ", name=" + name + ", length=" + length
				+ ", value= " + getStringValue() + "]";
	}

}
