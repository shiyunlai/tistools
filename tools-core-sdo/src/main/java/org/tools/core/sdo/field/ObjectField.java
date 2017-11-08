package org.tools.core.sdo.field;

/**
 * <pre>
 * 
 * 数据域：对象（Object）类型的字段
 * 
 * 对象字段，为包含一些扩展对象如枚举而使用
 * 
 * 如：附件、扩展参数
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class ObjectField extends AbstractField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 686638250625545353L;
	/**
	 * 具体值
	 */
	private Object value = null;

	public ObjectField(String name) {
		super(name);
	}

	public ObjectField(String name, Object obj) {
		super(name);
		this.value = obj;
	}

	public void setValue(Object val) {
		this.value = val;
	}

	public Object getValue() {
		return this.value;
	}

	@Override
	public String getStringValue() {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}

	@Override
	public void setValueWithString(String val) {
		logger.error("系统暂时不支持String转换为对象！");
		throw new RuntimeException("Not support setValueWithString: " + val);
	}

	@Override
	public Object getProtoValue() {
		return value;
	}
	
	/////////////////////
	// test
	/////////////////////
	
	public static enum TestStatusEnum {
		SUCCESS, FAILURE, ERROR;
	};

	public static void main(String[] args) throws Throwable {

		ObjectField field1 = new ObjectField("status",TestStatusEnum.SUCCESS);
		System.out.println("Value: " + field1.toString());
	}
}
