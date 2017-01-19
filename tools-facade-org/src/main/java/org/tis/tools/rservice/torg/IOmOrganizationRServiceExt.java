package org.tis.tools.rservice.torg;

import java.util.List;

import org.tis.tools.model.po.torg.OmEmployee;
import org.tis.tools.model.po.torg.OmOrganization;

/**
 * <pre>
 * 机构信息表(OM_ORGANIZATION)的基础远程服务扩展接口定义
 * 模型：机构信息表 OM_ORGANIZATION
 * 描述：机构信息表
 * 
 * </pre>
 */
public interface IOmOrganizationRServiceExt {
	
	public List<OmEmployee> loadEmpByOrg(String orgId);
	
	public Integer genOrgId();

}
