
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.AcRole;

 
/**
 * 角色(AC_ROLE)的基础远程服务接口定义
 * from bizmodel 'tuser'
 * auto generated
 *
 */
public interface IAcRoleRService {
	
	/**
	 * 新增角色(AC_ROLE)
	 * @param t 新记录
	 */
	public void insert(AcRole t);

	/**
	 * 更新角色(AC_ROLE),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(AcRole t);

	/**
	 * 强制更新角色(AC_ROLE)
	 * @param t 新值
	 */
	public void updateForce(AcRole t);

	/**
	 * 删除角色(AC_ROLE)
	 * @param id 记录id
	 */
	public void delete(String id);

	/**
	 * 根据条件删除角色(AC_ROLE)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新角色(AC_ROLE)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, AcRole t);

	/**
	 * 根据条件查询角色(AC_ROLE)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcRole> query(WhereCondition wc);

	/**
	 * 根据条件统计角色(AC_ROLE)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询角色(AC_ROLE)记录
	 * @param id 记录id
	 * @return 匹配的记录
	 */
	public AcRole loadById(String id);
}
