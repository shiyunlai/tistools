/**
 * 
 */
package org.tools.core.sdo.field;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.tis.tools.common.utils.BasicUtil;
import org.tools.core.sdo.exception.SDOException;
import org.tools.core.sdo.exception.SDOExceptionCodes;

/**
 * <pre>
 * 
 * 数据域：日期（Date）类型的字段
 * 
 * 如：开户日期、转账日期
 * 
 * </pre>
 * 
 * @author megapro
 *
 */

public class DateField extends AbstractField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6854719901317392103L;
	private String format = "yyyy-MM-dd";
	private Date dateValue;

	/**
	 * 构造函数
	 * 
	 * @param name
	 *            数据域名称，将采用默认的日期串型化格式（yyyyMMdd）
	 */
	public DateField(String name) {
		super(name);
	}

	/**
	 * 构造函数
	 * 
	 * @param name
	 *            数据域名称
	 * @param format
	 *            日期串型化格式
	 */
	public DateField(String name, String format) {
		super(name);
		this.format = format;
	}

	/**
	 * 取数据域的字符串值
	 * 
	 * @return 数据域的字符串值
	 */
	@Override
	public String getStringValue() {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(dateValue);
	}

	/**
	 * 用字符串设置数据的值 YYYYMMDD格式
	 * 
	 * @param value
	 *            数据域的字符串值
	 */
	@Override
	public void setValueWithString(String val) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			this.setValue(sdf.parse(val));
		} catch (Exception e) {
			e.printStackTrace(); 
			throw new SDOException(SDOExceptionCodes.FIELD_INVALID_DATE_VALUE, BasicUtil.wrap(val));
		}
	}

	@Override
	public Object getProtoValue() {
		return dateValue;
	}

	public void setValue(Date val) {
		this.dateValue = val;
	}

	public Date getValue() {
		return this.dateValue;
	}
}