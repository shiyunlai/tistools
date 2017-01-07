
/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.torg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.torg.OmOrganization;
import org.tis.tools.rservice.torg.IOmOrganizationRService;
import org.tis.tools.service.torg.OmOrganizationService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * <pre>
 * auto generated by mvn bronsp:gen-dao, don't change</br>
 * 机构信息表(OM_ORGANIZATION) 基本远程服务实现(服务提供者)
 * 模型：机构信息表 OM_ORGANIZATION
 * 描述：机构信息表
 * 业务域：torg
 * 定义文件：D:\GitSpace\tistools\tools-core\model\model-user.xml
 * </pre>
 */
@Service(group = "org", version = "1.0", interfaceClass = IOmOrganizationRService.class, timeout = 2000, document = "机构信息表的基础远程服务")
public class OmOrganizationRServiceImpl implements IOmOrganizationRService {

	@Autowired
	OmOrganizationService omOrganizationService;

	@Override
	public void insert(OmOrganization t) {
		omOrganizationService.insert(t);
	}

	@Override
	public void update(OmOrganization t) {
		omOrganizationService.update(t);
	}

	@Override
	public void updateForce(OmOrganization t) {
		omOrganizationService.updateForce(t);
	}

	@Override
	public void delete(String id) {
		omOrganizationService.delete(id);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		omOrganizationService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, OmOrganization t) {
		omOrganizationService.updateByCondition(wc,t);
	}

	@Override
	public List<OmOrganization> query(WhereCondition wc) {
		return omOrganizationService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return omOrganizationService.count(wc);
	}

	@Override
	public OmOrganization loadById(String id) {
		return omOrganizationService.loadById(id);
	}

}
