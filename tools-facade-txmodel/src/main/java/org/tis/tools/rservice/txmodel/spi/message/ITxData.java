/**
 * 
 */
package org.tis.tools.rservice.txmodel.spi.message;

import org.tools.core.sdo.DataField;

/**
 * <pre>
 * 
 * 交易请求数据（Trade Request Data 简称： TxData）
 * 
 * 一个交易的数据，是由交易界面（就是交易的form表单）上各个字段构成，包括：
 * 
 * 	显示字段：在交易画面中呈现的数据项；
 * 
 * 	隐藏字段：不在画面中呈现，但是属于交易数据的一部分，往往是隐藏在交易操作过程中的数据项；
 * 
 * 	过程字段：不在画面中呈现，而是在交易提交过程中，由系统为交易补全的数据项；
 * 
 * 交易数据描述了交易操作请求时，当前交易的数据内容，每个交易的数据内容结构不一，但是都能通过path或者数据名称的方式获取到其中的值。
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
// TODO 引入DataObject封装任意数据结构,DataObject 作为基础能力，放到 core-basic 中
public interface ITxData extends IExtPropertyAble {

	/**
	 * 取所有交易数据字段名称
	 * 
	 * @return 当前交易所有的数据字段名称
	 */
	public String[] getFieldsName();
	
//	/**
//	 * <pre>
//	 * 取所有交易数据字段
//	 * 只返回第一层数据字段名称
//	 * </pre>
//	 * @return 当前交易所有的数据字段
//	 */
//	public <T extends DataField> T[] getFields();

	/**
	 * 根据字段名称，从交易数据中取数据字段
	 * 
	 * @param fieldName
	 *            字段名称
	 * @return {@link DataField 名称对应的数据字段}
	 */
	public <T extends DataField> T getField(String fieldName);

	/**
	 * 根据字段名称，从交易数据中取字段值
	 * 
	 * @param fieldName
	 *            交易字段名称
	 * @return 数据字段的值（区分类型）
	 */
	public <T> T getFieldValue(String fieldName);

}
