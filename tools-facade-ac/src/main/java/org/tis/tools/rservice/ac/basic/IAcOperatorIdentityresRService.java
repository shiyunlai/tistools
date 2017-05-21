
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.ac.basic;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.ac.AcOperatorIdentityres;

 
/**
 * <pre>
 * 身份权限集(AC_OPERATOR_IDENTITYRES)的基础远程服务接口定义
 * </pre>
 *
 * @autor generated by tools:gen-dao
 *
 */
public interface IAcOperatorIdentityresRService {
	
	/**
	 * 新增身份权限集(AC_OPERATOR_IDENTITYRES)
	 * @param t 新记录
	 */
	public void insert(AcOperatorIdentityres t);

	/**
	 * 更新身份权限集(AC_OPERATOR_IDENTITYRES),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(AcOperatorIdentityres t);

	/**
	 * 强制更新身份权限集(AC_OPERATOR_IDENTITYRES)
	 * @param t 新值
	 */
	public void updateForce(AcOperatorIdentityres t);

	/**
	 * 删除身份权限集(AC_OPERATOR_IDENTITYRES)
	 * @param guid 记录guid
	 */
	public void delete(String guid);

	/**
	 * 根据条件删除身份权限集(AC_OPERATOR_IDENTITYRES)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新身份权限集(AC_OPERATOR_IDENTITYRES)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, AcOperatorIdentityres t);

	/**
	 * 根据条件查询身份权限集(AC_OPERATOR_IDENTITYRES)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcOperatorIdentityres> query(WhereCondition wc);

	/**
	 * 根据条件统计身份权限集(AC_OPERATOR_IDENTITYRES)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询身份权限集(AC_OPERATOR_IDENTITYRES)记录
	 * @param guid 记录guid
	 * @return 匹配的记录
	 */
	public AcOperatorIdentityres loadByGuid(String guid);
}