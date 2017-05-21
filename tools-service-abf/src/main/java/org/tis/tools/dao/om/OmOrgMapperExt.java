/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.dao.om;

import java.util.List;

import org.tis.tools.model.po.om.OmOrg;

/**
 * 
 * 机构信息表(OM_ORG)的DAO
 * 
 * @author generated by tools:gen-dao
 *
 */
public interface OmOrgMapperExt {

	/**
	 * 根据机构代码查询机构记录
	 * @param orgCode 机构代码
	 * @return 机构记录
	 */
	public OmOrg loadByOrgCode(String orgCode) ; 
	
	/**
	 * 查询第一层下级机构
	 * @param orgCode 机构代码
	 * @return 第一层下级机构列表
	 */
	public List<OmOrg> queryFirstChilds(String orgCode) ; 
	
	/**
	 * 查询所有根机构
	 * @return
	 */
	public List<OmOrg> queryAllRoot() ;
}