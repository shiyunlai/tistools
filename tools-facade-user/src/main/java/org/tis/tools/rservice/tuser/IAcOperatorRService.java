
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.AcOperator;

 
/**
 * 操作员(AC_OPERATOR)的基础远程服务接口定义
 * from bizmodel 'tuser'
 * auto generated
 *
 */
public interface IAcOperatorRService {
	
	/**
	 * 新增操作员(AC_OPERATOR)
	 * @param t 新记录
	 */
	public void insert(AcOperator t);

	/**
	 * 更新操作员(AC_OPERATOR),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(AcOperator t);

	/**
	 * 强制更新操作员(AC_OPERATOR)
	 * @param t 新值
	 */
	public void updateForce(AcOperator t);

	/**
	 * 删除操作员(AC_OPERATOR)
	 * @param id 记录id
	 */
	public void delete(String id);

	/**
	 * 根据条件删除操作员(AC_OPERATOR)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新操作员(AC_OPERATOR)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, AcOperator t);

	/**
	 * 根据条件查询操作员(AC_OPERATOR)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcOperator> query(WhereCondition wc);

	/**
	 * 根据条件统计操作员(AC_OPERATOR)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询操作员(AC_OPERATOR)记录
	 * @param id 记录id
	 * @return 匹配的记录
	 */
	public AcOperator loadById(String id);
}
