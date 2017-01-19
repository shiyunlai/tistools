package org.tis.tools.rservice.torg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.model.po.torg.OmEmployee;
import org.tis.tools.model.po.torg.OmOrganization;
import org.tis.tools.service.torg.OmOrganizationService;
import org.tis.tools.service.torg.OmOrganizationServiceExt;

import com.alibaba.dubbo.config.annotation.Service;
@Service(group = "org", version = "1.0", interfaceClass = IOmOrganizationRServiceExt.class, timeout = 2000, document = "机构信息表的扩展远程服务")
public class OmOrganizationRServiceImplExt implements IOmOrganizationRServiceExt{

	@Autowired
	OmOrganizationServiceExt omOrganizationServiceExt;
	
	@Override
	public List<OmEmployee> loadEmpByOrg(String orgId) {
		// TODO Auto-generated method stub
		return omOrganizationServiceExt.loadEmpByOrg(orgId);
	}

	@Override
	public Integer genOrgId() {
		// TODO Auto-generated method stub
		return omOrganizationServiceExt.genOrgId();
	}



}
