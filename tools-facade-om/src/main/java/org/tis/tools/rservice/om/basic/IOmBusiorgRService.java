
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.om.basic;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.om.OmBusiorg;

 
/**
 * <pre>
 * 业务机构(OM_BUSIORG)的基础远程服务接口定义
 * </pre>
 *
 * @autor generated by tools:gen-dao
 *
 */
public interface IOmBusiorgRService {
	
	/**
	 * 新增业务机构(OM_BUSIORG)
	 * @param t 新记录
	 */
	public void insert(OmBusiorg t);

	/**
	 * 更新业务机构(OM_BUSIORG),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(OmBusiorg t);

	/**
	 * 强制更新业务机构(OM_BUSIORG)
	 * @param t 新值
	 */
	public void updateForce(OmBusiorg t);

	/**
	 * 删除业务机构(OM_BUSIORG)
	 * @param guid 记录guid
	 */
	public void delete(String guid);

	/**
	 * 根据条件删除业务机构(OM_BUSIORG)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新业务机构(OM_BUSIORG)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, OmBusiorg t);

	/**
	 * 根据条件查询业务机构(OM_BUSIORG)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<OmBusiorg> query(WhereCondition wc);

	/**
	 * 根据条件统计业务机构(OM_BUSIORG)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询业务机构(OM_BUSIORG)记录
	 * @param guid 记录guid
	 * @return 匹配的记录
	 */
	public OmBusiorg loadByGuid(String guid);
}