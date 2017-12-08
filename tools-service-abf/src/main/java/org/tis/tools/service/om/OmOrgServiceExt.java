/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.service.om;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.dao.om.OmOrgMapper;
import org.tis.tools.dao.om.OmOrgMapperExt;
import org.tis.tools.model.po.om.OmOrg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 机构信息扩展业务逻辑
 * @author megapro
 *
 */
@Service
public class OmOrgServiceExt {

	@Autowired
	OmOrgMapper omOrgMapper;
	@Autowired
	OmOrgMapperExt omOrgMapperExt;
	
	/**
	 * 取机构记录
	 * 
	 * @param orgCode
	 *            机构代码
	 * @return 机构记录
	 */
	public OmOrg loadByOrgCode(String orgCode) {

		return omOrgMapperExt.loadByOrgCode(orgCode);
	}

	/**
	 * 判断机构代码（orgCode）对应的机构记录是否已经存在
	 * 
	 * @param orgCode
	 *            机构代码
	 * @return true 存在 false 不存在
	 */
	public boolean isExit(String orgCode) {

		OmOrg org = omOrgMapperExt.loadByOrgCode(orgCode);
		if (null != org) {
			return true;
		}
		return false;
	}

	/**
	 * 检查机构（guid）是否为'空机构'
	 * 
	 * @param guid
	 *            机构guid
	 * @return true 空机构 false 非空机构
	 */
	public boolean isEmptyOrg(String guid) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	/**
	 * 查询第一层下属机构
	 * @param orgCode 机构代码
	 * @return 第一层下属机构列表 
	 */
	public List<OmOrg> queryFirstChilds( String orgCode ) {
		List<OmOrg> orgs = omOrgMapperExt.queryFirstChilds(orgCode) ;  
		if( null == orgs ){
			orgs = new ArrayList<OmOrg>() ; 
		}
		return orgs ; 
	}
	
	/**
	 * 查询所有根机构
	 * @return
	 */
	public List<OmOrg> queryAllRoot()  {
		
		List<OmOrg> orgs = omOrgMapperExt.queryAllRoot() ; 
		if( null == orgs ){
			orgs = new ArrayList<OmOrg>() ; 
		}
		return orgs ; 
	}


	public void reorderOrg(String targetGuid, BigDecimal index, String flag){
		omOrgMapperExt.reorderOrg(targetGuid, index, flag);
	}

	/**
	 * 查询员工所有的岗位及对应的职务
	 * @param posiGuids
	 * @return
	 */
	public List<Map> queryPosDutybyEmpCode(List<String> posiGuids) {
		return omOrgMapperExt.queryPosDutybyEmpCode(StringUtil.list2String(posiGuids));
	}
}
