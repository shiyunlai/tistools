
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.ac.basic;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.ac.AcBhvtypeDef;

 
/**
 * <pre>
 * 行为类型定义(AC_BHVTYPE_DEF)的基础远程服务接口定义
 * </pre>
 *
 * @autor generated by tools:gen-dao
 *
 */
public interface IAcBhvtypeDefRService {
	
	/**
	 * 新增行为类型定义(AC_BHVTYPE_DEF)
	 * @param t 新记录
	 */
	public void insert(AcBhvtypeDef t);

	/**
	 * 更新行为类型定义(AC_BHVTYPE_DEF),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(AcBhvtypeDef t);

	/**
	 * 强制更新行为类型定义(AC_BHVTYPE_DEF)
	 * @param t 新值
	 */
	public void updateForce(AcBhvtypeDef t);

	/**
	 * 删除行为类型定义(AC_BHVTYPE_DEF)
	 * @param guid 记录guid
	 */
	public void delete(String guid);

	/**
	 * 根据条件删除行为类型定义(AC_BHVTYPE_DEF)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新行为类型定义(AC_BHVTYPE_DEF)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, AcBhvtypeDef t);

	/**
	 * 根据条件查询行为类型定义(AC_BHVTYPE_DEF)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcBhvtypeDef> query(WhereCondition wc);

	/**
	 * 根据条件统计行为类型定义(AC_BHVTYPE_DEF)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询行为类型定义(AC_BHVTYPE_DEF)记录
	 * @param guid 记录guid
	 * @return 匹配的记录
	 */
	public AcBhvtypeDef loadByGuid(String guid);
}
