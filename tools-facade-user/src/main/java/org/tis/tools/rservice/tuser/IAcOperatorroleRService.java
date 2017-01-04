
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.AcOperatorrole;

 
/**
 * 操作员与权限集（角色）对应关系(AC_OPERATORROLE)的基础远程服务接口定义
 * from bizmodel 'tuser'
 * auto generated
 *
 */
public interface IAcOperatorroleRService {
	
	/**
	 * 新增操作员与权限集（角色）对应关系(AC_OPERATORROLE)
	 * @param t 新记录
	 */
	public void insert(AcOperatorrole t);

	/**
	 * 更新操作员与权限集（角色）对应关系(AC_OPERATORROLE),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(AcOperatorrole t);

	/**
	 * 强制更新操作员与权限集（角色）对应关系(AC_OPERATORROLE)
	 * @param t 新值
	 */
	public void updateForce(AcOperatorrole t);

	/**
	 * 删除操作员与权限集（角色）对应关系(AC_OPERATORROLE)
	 * @param id 记录id
	 */
	public void delete(String id);

	/**
	 * 根据条件删除操作员与权限集（角色）对应关系(AC_OPERATORROLE)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新操作员与权限集（角色）对应关系(AC_OPERATORROLE)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, AcOperatorrole t);

	/**
	 * 根据条件查询操作员与权限集（角色）对应关系(AC_OPERATORROLE)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcOperatorrole> query(WhereCondition wc);

	/**
	 * 根据条件统计操作员与权限集（角色）对应关系(AC_OPERATORROLE)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询操作员与权限集（角色）对应关系(AC_OPERATORROLE)记录
	 * @param id 记录id
	 * @return 匹配的记录
	 */
	public AcOperatorrole loadById(String id);
}
