
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.sys.basic;

import java.util.List;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.sys.SysChannelCtl;

 
/**
 * <pre>
 * 渠道参数控制表(SYS_CHANNEL_CTL)的基础远程服务接口定义
 * </pre>
 *
 * @autor generated by tools:gen-dao
 *
 */
public interface ISysChannelCtlRService {
	
	/**
	 * 新增渠道参数控制表(SYS_CHANNEL_CTL)
	 * @param t 新记录
	 */
	public void insert(SysChannelCtl t);

	/**
	 * 更新渠道参数控制表(SYS_CHANNEL_CTL),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(SysChannelCtl t);

	/**
	 * 强制更新渠道参数控制表(SYS_CHANNEL_CTL)
	 * @param t 新值
	 */
	public void updateForce(SysChannelCtl t);

	/**
	 * 删除渠道参数控制表(SYS_CHANNEL_CTL)
	 * @param guid 记录guid
	 */
	public void delete(String guid);

	/**
	 * 根据条件删除渠道参数控制表(SYS_CHANNEL_CTL)
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新渠道参数控制表(SYS_CHANNEL_CTL)
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, SysChannelCtl t);

	/**
	 * 根据条件查询渠道参数控制表(SYS_CHANNEL_CTL)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<SysChannelCtl> query(WhereCondition wc);

	/**
	 * 根据条件统计渠道参数控制表(SYS_CHANNEL_CTL)记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询渠道参数控制表(SYS_CHANNEL_CTL)记录
	 * @param guid 记录guid
	 * @return 匹配的记录
	 */
	public SysChannelCtl loadByGuid(String guid);
}