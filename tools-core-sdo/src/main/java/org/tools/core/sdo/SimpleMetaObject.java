/**
 * 
 */
package org.tools.core.sdo;

/**
 * <pre>
 * 
 * 简单数据对象的定义
 * 
 * 1、属性都是  Field
 * 2、属性名称 和数据字段名称对应关系，如下：
 *    { "propName", "fieldName" } - 
 *    { "propName",  null       } - 表示数据字典名称和属性名称一样
 *    例：
 *    
 *    JnlDataObject - 流水数据对象，属性可定义如下
 *    传入的数组如下所示
 *    {
 *    { "dr_acct_no", "acct_no" }
 *    { "dr_acct_no", "acct_no" } 借方帐号，账号
 *    { "cr_acct_no", "acct_no" } 贷方帐号，账号
 *    { "amount",      null     } 金额，          金额
 *    }
 * 
 * History:
 * ---------------------------------------------------------
 * Date        Author      Action       Reason
 * 2012/09/22  SHEN        Create
 * ---------------------------------------------------------
 *
 * Version: 1.0
 *
 * </pre>
 */

public class SimpleMetaObject extends MetaObject {

	/**
	 * <pre>
	 * 属性名称 和数据字段名称对应关系，如下：
	 *    { "propName", "fieldName" } - 
	 *    { "propName",  null       } - 表示数据字典名称和属性名称一样
	 *    例：
	 *    
	 *    JnlDataObject - 流水数据对象，属性可定义如下
	 *    传入的数组如下所示
	 *    {{ "dr_acct_no", "acct_no" },{ "cr_acct_no", "acct_no" },{ "amount", null }}
	 *    { "dr_acct_no", "acct_no" } 借方帐号，账号
	 *    { "cr_acct_no", "acct_no" } 贷方帐号，账号
	 *    { "amount",      null     } 金额，          金额
	 * </pre>
	 * 
	 * @param def
	 */

	public SimpleMetaObject(String[][] def) {
		MetaObjectProperty[] properties = new MetaObjectProperty[def.length];
		for (int i = 0; i < def.length; i++) {
			String propertyName = def[i][0];// 属性名
			String dictionaryName = def[i][1];// 数据字典名
			MetaObjectProperty metaprop = new MetaObjectProperty();// 开始设置属性
			metaprop.setName(propertyName);
			metaprop.setDataType("Field");// 设置为dataField
			metaprop.setListData(false);// 不为多值
			metaprop.setDataName(dictionaryName == null ? propertyName : dictionaryName);// 当数据字典名称为空时
																							// 使用属性名作为字典名
			metaprop.setDisplayName(propertyName);
			properties[i] = metaprop;
		}
		this.setProperties(properties);
	}

	/**
	 * <pre>
	 * 等同于上一方法中的所有属性和数据字典名称一样，即
	 *    JnlDataObject - 流水数据对象，属性可定义如下
	 *    传入的数组如下所示
	 *    JnlDataObject - 流水数据对象，属性可定义如下
	 *    传入的数组如下所示
	 *    {{ "dr_acct_no",null }
	 *    { "cr_acct_no", null } 贷方帐号，账号 
	 *    { "amount",     null     } 金额，          金额
	 *    }
	 * </pre>
	 * 
	 * @param def
	 */
	public SimpleMetaObject(String[] def) {
		MetaObjectProperty[] properties = new MetaObjectProperty[def.length];
		for (int i = 0; i < def.length; i++) {
			String propertyName = def[i];// 属性名
			String dictionaryName = def[i + 1];// 数据字典名
			MetaObjectProperty metaprop = new MetaObjectProperty();// 开始设置属性
			metaprop.setName(propertyName);
			metaprop.setDataType("Field");// 设置为dataField
			metaprop.setListData(false);// 不为多值
			metaprop.setDataName(dictionaryName == null ? propertyName : dictionaryName);// 当数据字典名称为空时
																							// 使用属性名作为字典名
			metaprop.setDisplayName(propertyName);
		}
		this.setProperties(properties);
	}

}
