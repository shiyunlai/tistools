package org.tis.tools.dao.mapper.torg;

import java.util.List;
import java.util.Map;

import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.torg.OmEmployee;

public interface OmOrganizationMapperExt {

	public List<OmEmployee> loadEmpByOrg(WhereCondition wc);
	
	public Integer queryOrgId();
	
	public void updateOrgId(Integer orgId);

	public Integer countEmpByOrg(WhereCondition wc);

	public void insertEmpWithOrg(Map<String, Object> params);

	public void deleteEmpWithOrg(String empId);

	public List<OmEmployee> loadEmpByPosi(WhereCondition wc);

	public Integer countEmpByPosi(WhereCondition wc);

	public void insertEmpWithPosi(Map<String, Object> params);

	public void deleteEmpWithPosi(String empId);
}
