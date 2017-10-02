/**
 * 
 */
package org.tis.tools.rservice.txmodel.message;

import java.io.Serializable;

import org.tools.core.sdo.Field;

/**
 * <pre>
 * 
 * 交易数据
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
 * @author megapro
 *
 */
//TODO 引入DataObject封装任意数据结构,DataObject 作为基础能力，放到 core-basic 中
public interface TxData extends Serializable {

	/**
	 * 取所有交易数据字段名称
	 * @return
	 */
	public String[] getFieldsName() ;
	
	/**
	 * 去所有交易数据字段
	 * @return 
	 */
	public Field[] getFields() ;
	
	/**
	 * 根据字段名称取交易字段
	 * @param fieldName 字段名称
	 * @return
	 */
	public Field getField(String fieldName) ;
	
	/**
	 * 根据字段名称取字段值
	 * @param fieldName 交易字段名称
	 * @return
	 */
	public Object getFieldValue(String fieldName) ;
	
}
