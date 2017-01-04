
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.AcRole;
import org.tis.tools.rservice.tuser.IAcRoleRService;
import org.tis.tools.service.tuser.AcRoleService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 角色(AC_ROLE) 基本远程服务实现(服务提供者)
 * 
 * @author by auto-gen
 *
 */
//TEMPLATES
@Service(group = "tuser", version = "1.0", interfaceClass = IAcRoleRService.class, timeout = 2000, document = "角色的基础远程服务")
public class AcRoleRServiceImpl implements IAcRoleRService {

	@Autowired
	AcRoleService acRoleService;

	@Override
	public void insert(AcRole t) {
		acRoleService.insert(t);
	}

	@Override
	public void update(AcRole t) {
		acRoleService.update(t);
	}

	@Override
	public void updateForce(AcRole t) {
		acRoleService.updateForce(t);
	}

	@Override
	public void delete(String id) {
		acRoleService.delete(id);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		acRoleService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, AcRole t) {
		acRoleService.updateByCondition(wc,t);
	}

	@Override
	public List<AcRole> query(WhereCondition wc) {
		return acRoleService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return acRoleService.count(wc);
	}

	@Override
	public AcRole loadById(String id) {
		return acRoleService.loadById(id);
	}

}
