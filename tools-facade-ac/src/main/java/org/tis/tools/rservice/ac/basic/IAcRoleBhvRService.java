
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.ac.basic;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.ac.AcRoleBhv;

 
/**
 * <pre>
 * 权限集(角色)功能行为对应关系(AC_ROLE_BHV)的基础远程服务接口定义
 * </pre>
 *
 * @autor generated by tools:gen-dao
 *
 */
public interface IAcRoleBhvRService {
	
	/**
	 * 新增权限集(角色)功能行为对应关系(AC_ROLE_BHV)
	 * @param t 新记录
	 */
	public void insert(AcRoleBhv t);

	/**
	 * 更新权限集(角色)功能行为对应关系(AC_ROLE_BHV),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(AcRoleBhv t);

	/**
	 * 强制更新权限集(角色)功能行为对应关系(AC_ROLE_BHV)
	 * @param t 新值
	 */
	public void updateForce(AcRoleBhv t);

	/**
	 * 删除权限集(角色)功能行为对应关系(AC_ROLE_BHV)
	 * @param guid 记录guid
	 */
	public void delete(String guid);

	/**
	 * 根据条件删除权限集(角色)功能行为对应关系(AC_ROLE_BHV)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新权限集(角色)功能行为对应关系(AC_ROLE_BHV)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, AcRoleBhv t);

	/**
	 * 根据条件查询权限集(角色)功能行为对应关系(AC_ROLE_BHV)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcRoleBhv> query(WhereCondition wc);

	/**
	 * 根据条件统计权限集(角色)功能行为对应关系(AC_ROLE_BHV)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询权限集(角色)功能行为对应关系(AC_ROLE_BHV)记录
	 * @param guid 记录guid
	 * @return 匹配的记录
	 */
	public AcRoleBhv loadByGuid(String guid);
}
