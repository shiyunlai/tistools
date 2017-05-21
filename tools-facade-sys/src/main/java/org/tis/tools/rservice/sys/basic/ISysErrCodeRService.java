
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.sys.basic;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.sys.SysErrCode;

 
/**
 * <pre>
 * 错误码表(SYS_ERR_CODE)的基础远程服务接口定义
 * </pre>
 *
 * @autor generated by tools:gen-dao
 *
 */
public interface ISysErrCodeRService {
	
	/**
	 * 新增错误码表(SYS_ERR_CODE)
	 * @param t 新记录
	 */
	public void insert(SysErrCode t);

	/**
	 * 更新错误码表(SYS_ERR_CODE),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(SysErrCode t);

	/**
	 * 强制更新错误码表(SYS_ERR_CODE)
	 * @param t 新值
	 */
	public void updateForce(SysErrCode t);

	/**
	 * 删除错误码表(SYS_ERR_CODE)
	 * @param guid 记录guid
	 */
	public void delete(String guid);

	/**
	 * 根据条件删除错误码表(SYS_ERR_CODE)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新错误码表(SYS_ERR_CODE)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, SysErrCode t);

	/**
	 * 根据条件查询错误码表(SYS_ERR_CODE)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<SysErrCode> query(WhereCondition wc);

	/**
	 * 根据条件统计错误码表(SYS_ERR_CODE)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询错误码表(SYS_ERR_CODE)记录
	 * @param guid 记录guid
	 * @return 匹配的记录
	 */
	public SysErrCode loadByGuid(String guid);
}