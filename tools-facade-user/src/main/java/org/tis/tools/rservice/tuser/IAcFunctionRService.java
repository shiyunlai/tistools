
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.AcFunction;

 
/**
 * 功能(AC_FUNCTION)的基础远程服务接口定义
 * from bizmodel 'tuser'
 * auto generated
 *
 */
public interface IAcFunctionRService {
	
	/**
	 * 新增功能(AC_FUNCTION)
	 * @param t 新记录
	 */
	public void insert(AcFunction t);

	/**
	 * 更新功能(AC_FUNCTION),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(AcFunction t);

	/**
	 * 强制更新功能(AC_FUNCTION)
	 * @param t 新值
	 */
	public void updateForce(AcFunction t);

	/**
	 * 删除功能(AC_FUNCTION)
	 * @param id 记录id
	 */
	public void delete(String id);

	/**
	 * 根据条件删除功能(AC_FUNCTION)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新功能(AC_FUNCTION)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, AcFunction t);

	/**
	 * 根据条件查询功能(AC_FUNCTION)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcFunction> query(WhereCondition wc);

	/**
	 * 根据条件统计功能(AC_FUNCTION)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询功能(AC_FUNCTION)记录
	 * @param id 记录id
	 * @return 匹配的记录
	 */
	public AcFunction loadById(String id);
}
