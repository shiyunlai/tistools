
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.ac.basic;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.ac.AcRoleDatascope;

 
/**
 * <pre>
 * 角色数据范围权限对应(AC_ROLE_DATASCOPE)的基础远程服务接口定义
 * </pre>
 *
 * @autor generated by tools:gen-dao
 *
 */
public interface IAcRoleDatascopeRService {
	
	/**
	 * 新增角色数据范围权限对应(AC_ROLE_DATASCOPE)
	 * @param t 新记录
	 */
	public void insert(AcRoleDatascope t);

	/**
	 * 更新角色数据范围权限对应(AC_ROLE_DATASCOPE),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(AcRoleDatascope t);

	/**
	 * 强制更新角色数据范围权限对应(AC_ROLE_DATASCOPE)
	 * @param t 新值
	 */
	public void updateForce(AcRoleDatascope t);

	/**
	 * 删除角色数据范围权限对应(AC_ROLE_DATASCOPE)
	 * @param guid 记录guid
	 */
	public void delete(String guid);

	/**
	 * 根据条件删除角色数据范围权限对应(AC_ROLE_DATASCOPE)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新角色数据范围权限对应(AC_ROLE_DATASCOPE)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, AcRoleDatascope t);

	/**
	 * 根据条件查询角色数据范围权限对应(AC_ROLE_DATASCOPE)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcRoleDatascope> query(WhereCondition wc);

	/**
	 * 根据条件统计角色数据范围权限对应(AC_ROLE_DATASCOPE)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询角色数据范围权限对应(AC_ROLE_DATASCOPE)记录
	 * @param guid 记录guid
	 * @return 匹配的记录
	 */
	public AcRoleDatascope loadByGuid(String guid);
}