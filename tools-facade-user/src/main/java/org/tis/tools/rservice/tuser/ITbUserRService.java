
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.TbUser;

 
/**
 * 用户(tb_user)的基础远程服务接口定义
 * from bizmodel 'tuser'
 * auto generated
 *
 */
public interface ITbUserRService {
	
	/**
	 * 新增用户(tb_user)
	 * @param t 新记录
	 */
	public void insert(TbUser t);

	/**
	 * 更新用户(tb_user),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(TbUser t);

	/**
	 * 强制更新用户(tb_user)
	 * @param t 新值
	 */
	public void updateForce(TbUser t);

	/**
	 * 删除用户(tb_user)
	 * @param id 记录id
	 */
	public void delete(String id);

	/**
	 * 根据条件删除用户(tb_user)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新用户(tb_user)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, TbUser t);

	/**
	 * 根据条件查询用户(tb_user)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<TbUser> query(WhereCondition wc);

	/**
	 * 根据条件统计用户(tb_user)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询用户(tb_user)记录
	 * @param guid 记录guid
	 * @return 匹配的记录
	 */
	public TbUser loadByGuid(String guid);
}
