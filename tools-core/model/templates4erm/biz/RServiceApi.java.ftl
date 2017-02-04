<#assign rServiceClassNameVar="I${poClassName}RService">
<#assign wcClassPackageVar="${mainPackage}.base.WhereCondition">

/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package ${packageName};

import java.util.List;

import ${wcClassPackageVar};
import ${mainPackage}.model.po.${bizmodelId}.${poClassName};

 
/**
 * ${table.name}(${table.id})的基础远程服务接口定义
 * from bizmodel '${bizmodelId}'
 * auto generated
 *
 */
public interface ${rServiceClassNameVar} {
	
	/**
	 * 新增${table.name}(${table.id})
	 * @param t 新记录
	 */
	public void insert(${poClassName} t);

	/**
	 * 更新${table.name}(${table.id}),只修改t对象有值的字段
	 * @param t 新值
	 */
	public void update(${poClassName} t);

	/**
	 * 强制更新${table.name}(${table.id})
	 * @param t 新值
	 */
	public void updateForce(${poClassName} t);

	/**
	 * 删除${table.name}(${table.id})
	 * @param id 记录id
	 */
	public void delete(String id);

	/**
	 * 根据条件删除${table.name}(${table.id})
	 * @param wc 条件
	 */
	public void deleteByCondition(WhereCondition wc);

	/**
	 * 根据条件更新${table.name}(${table.id})
	 * @param wc 条件
	 * @param t 新值
	 */
	public void updateByCondition(WhereCondition wc, ${poClassName} t);

	/**
	 * 根据条件查询${table.name}(${table.id})
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<${poClassName}> query(WhereCondition wc);

	/**
	 * 根据条件统计${table.name}(${table.id})记录数
	 * @param wc 条件
	 * @return 记录数
	 */
	public int count(WhereCondition wc);

	/**
	 * 根据id查询${table.name}(${table.id})记录
	 * @param id 记录id
	 * @return 匹配的记录
	 */
	public ${poClassName} loadById(String id);
}
