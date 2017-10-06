package org.tools.core.sdo.field;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.tis.tools.common.utils.BasicUtil;
import org.tools.core.sdo.exception.SDOException;
import org.tools.core.sdo.exception.SDOExceptionCodes;

/**
 * <pre>
 * 
 * 数据域：时间（Time）类型的字段
 * 
 * 如：交易时间、发送时间...
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class TimeField extends AbstractField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Time value;
	private String timeFormat = "HH:MM:SS" ; 

	/**
	 * 构造函数
	 * 
	 * @param fieldName
	 *            数据名称
	 * @param timeFormat
	 *            时间格式样式
	 */
	public TimeField(String fieldName, String timeFormat) {
		super(fieldName);
		this.timeFormat = timeFormat;
	}

	/**
	 * 构造函数
	 * 
	 * @param fieldName
	 *            数据名称
	 */
	public TimeField(String fieldName) {
		super(fieldName);
	}

	/**
	 * 取数据域的字符串值
	 * 
	 * @return 数据域的字符串值
	 */
	@Override
	public String getStringValue() {
		return (value == null ? "" : value.toString());
	}

	/**
	 * 用字符串设置数据的值 HHMMSS格式
	 * 
	 * @param value
	 *            数据域的字符串值
	 */
	@Override
	public void setValueWithString(String val) {
		if (val == null) {
			value = null;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
			Date date;
			try {
				date = sdf.parse(val);
				value = new Time(date.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				throw new SDOException(SDOExceptionCodes.FIELD_INVALID_TIME_VALUE, BasicUtil.wrap(val));
			}
		}
	}

	@Override
	public Object getProtoValue() {
		return value;
	}
}