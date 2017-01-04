
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.AcRolefunc;

 
/**
 * 权限集(角色)功能对应关系(AC_ROLEFUNC)的基础远程服务接口定义
 * from bizmodel 'tuser'
 * auto generated
 *
 */
public interface IAcRolefuncRService {
	
	/**
	 * 新增权限集(角色)功能对应关系(AC_ROLEFUNC)
	 * @param t 新记录
	 */
	public void insert(AcRolefunc t);

	/**
	 * 更新权限集(角色)功能对应关系(AC_ROLEFUNC),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(AcRolefunc t);

	/**
	 * 强制更新权限集(角色)功能对应关系(AC_ROLEFUNC)
	 * @param t 新值
	 */
	public void updateForce(AcRolefunc t);

	/**
	 * 删除权限集(角色)功能对应关系(AC_ROLEFUNC)
	 * @param id 记录id
	 */
	public void delete(String id);

	/**
	 * 根据条件删除权限集(角色)功能对应关系(AC_ROLEFUNC)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新权限集(角色)功能对应关系(AC_ROLEFUNC)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, AcRolefunc t);

	/**
	 * 根据条件查询权限集(角色)功能对应关系(AC_ROLEFUNC)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcRolefunc> query(WhereCondition wc);

	/**
	 * 根据条件统计权限集(角色)功能对应关系(AC_ROLEFUNC)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询权限集(角色)功能对应关系(AC_ROLEFUNC)记录
	 * @param id 记录id
	 * @return 匹配的记录
	 */
	public AcRolefunc loadById(String id);
}
