
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.TbOrg;

 
/**
 * 机构(tb_org)的基础远程服务接口定义
 * from bizmodel 'tuser'
 * auto generated
 *
 */
public interface ITbOrgRService {
	
	/**
	 * 新增机构(tb_org)
	 * @param t 新记录
	 */
	public void insert(TbOrg t);

	/**
	 * 更新机构(tb_org),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(TbOrg t);

	/**
	 * 强制更新机构(tb_org)
	 * @param t 新值
	 */
	public void updateForce(TbOrg t);

	/**
	 * 删除机构(tb_org)
	 * @param id 记录id
	 */
	public void delete(String id);

	/**
	 * 根据条件删除机构(tb_org)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新机构(tb_org)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, TbOrg t);

	/**
	 * 根据条件查询机构(tb_org)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<TbOrg> query(WhereCondition wc);

	/**
	 * 根据条件统计机构(tb_org)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询机构(tb_org)记录
	 * @param id 记录id
	 * @return 匹配的记录
	 */
	public TbOrg loadById(String id);
}