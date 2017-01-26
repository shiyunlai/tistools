package org.tis.tools.rservice.torg;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
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
	public List<OmEmployee> loadEmpByOrg(WhereCondition wc) {
		// TODO Auto-generated method stub
		return omOrganizationServiceExt.loadEmpByOrg(wc);
	}

	@Override
	public Integer genOrgId() {
		// TODO Auto-generated method stub
		return omOrganizationServiceExt.genOrgId();
	}

	@Override
	public Integer countEmpByOrg(WhereCondition wc) {
		// TODO Auto-generated method stub
		return omOrganizationServiceExt.countEmpByOrg(wc);
	}

	@Override
	public void insertEmpWithOrg(Map<String, Object> params) {
		// TODO Auto-generated method stub
		omOrganizationServiceExt.insertEmpWithOrg(params);
	}

	@Override
	public void deleteEmpWithOrg(String empId) {
		// TODO Auto-generated method stub
		omOrganizationServiceExt.deleteEmpWithOrg(empId);
	}



}
