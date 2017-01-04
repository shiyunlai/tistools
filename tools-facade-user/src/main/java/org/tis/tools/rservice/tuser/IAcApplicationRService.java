
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.AcApplication;

 
/**
 * 应用系统(AC_APPLICATION)的基础远程服务接口定义
 * from bizmodel 'tuser'
 * auto generated
 *
 */
public interface IAcApplicationRService {
	
	/**
	 * 新增应用系统(AC_APPLICATION)
	 * @param t 新记录
	 */
	public void insert(AcApplication t);

	/**
	 * 更新应用系统(AC_APPLICATION),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(AcApplication t);

	/**
	 * 强制更新应用系统(AC_APPLICATION)
	 * @param t 新值
	 */
	public void updateForce(AcApplication t);

	/**
	 * 删除应用系统(AC_APPLICATION)
	 * @param id 记录id
	 */
	public void delete(String id);

	/**
	 * 根据条件删除应用系统(AC_APPLICATION)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新应用系统(AC_APPLICATION)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, AcApplication t);

	/**
	 * 根据条件查询应用系统(AC_APPLICATION)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcApplication> query(WhereCondition wc);

	/**
	 * 根据条件统计应用系统(AC_APPLICATION)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询应用系统(AC_APPLICATION)记录
	 * @param id 记录id
	 * @return 匹配的记录
	 */
	public AcApplication loadById(String id);
}
