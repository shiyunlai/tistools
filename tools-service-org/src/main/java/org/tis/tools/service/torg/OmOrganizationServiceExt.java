package org.tis.tools.service.torg;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.dao.mapper.torg.OmOrganizationMapperExt;
import org.tis.tools.model.po.torg.OmEmployee;

/**
 * 组织管理service扩展类
 * @author 
 *
 */
@Service
public class OmOrganizationServiceExt {
	
	@Autowired
	OmOrganizationMapperExt omOrganizationMapperExt;
	
	
	public List<OmEmployee> loadEmpByOrg(WhereCondition wc) {
		
		return omOrganizationMapperExt.loadEmpByOrg(wc);
		
	}
	
	public Integer genOrgId() {
		Integer orgId = omOrganizationMapperExt.queryOrgId();
		omOrganizationMapperExt.updateOrgId(orgId + 1); 
		return orgId;
		
	}

	public Integer countEmpByOrg(WhereCondition wc) {
		
		return omOrganizationMapperExt.countEmpByOrg(wc);
	}

	public void insertEmpWithOrg(Map<String, Object> params) {
		omOrganizationMapperExt.insertEmpWithOrg(params);
		
	}

	public void deleteEmpWithOrg(String empId) {
		omOrganizationMapperExt.deleteEmpWithOrg(empId);		
	}

	public List<OmEmployee> loadEmpByPosi(WhereCondition wc) {
		// TODO Auto-generated method stub
		return omOrganizationMapperExt.loadEmpByPosi(wc);
	}

	public Integer countEmpByPosi(WhereCondition wc) {
		// TODO Auto-generated method stub
		return omOrganizationMapperExt.countEmpByPosi(wc);
	}

}
