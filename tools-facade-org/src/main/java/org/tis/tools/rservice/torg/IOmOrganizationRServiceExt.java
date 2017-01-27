package org.tis.tools.rservice.torg;

import java.util.List;
import java.util.Map;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.torg.OmEmployee;

/**
 * <pre>
 * 机构信息表(OM_ORGANIZATION)的基础远程服务扩展接口定义
 * 模型：机构信息表 OM_ORGANIZATION
 * 描述：机构信息表
 * 
 * </pre>
 */
public interface IOmOrganizationRServiceExt {
	
	public List<OmEmployee> loadEmpByOrg(WhereCondition wc);
	
	public Integer genOrgId();

	public Integer countEmpByOrg(WhereCondition wc);

	public void insertEmpWithOrg(Map<String, Object> params);

	public void deleteEmpWithOrg(String empId);

	public List<OmEmployee> loadEmpByPosi(WhereCondition wc);

	public Integer countEmpByPosi(WhereCondition wc);

}
