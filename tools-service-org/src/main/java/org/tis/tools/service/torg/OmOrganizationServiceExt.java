package org.tis.tools.service.torg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	
	public List<OmEmployee> loadEmpByOrg(String orgId) {
		
		return omOrganizationMapperExt.loadEmpByOrg(orgId);
		
	}
	
	public Integer genOrgId() {
		Integer orgId = omOrganizationMapperExt.queryOrgId();
		omOrganizationMapperExt.updateOrgId(orgId + 1); 
		return orgId;
		
	}

}
