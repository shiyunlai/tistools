
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.AcRolefunc;
import org.tis.tools.rservice.tuser.IAcRolefuncRService;
import org.tis.tools.service.tuser.AcRolefuncService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 权限集(角色)功能对应关系(AC_ROLEFUNC) 基本远程服务实现(服务提供者)
 * 
 * @author by auto-gen
 *
 */
//TEMPLATES
@Service(group = "tuser", version = "1.0", interfaceClass = IAcRolefuncRService.class, timeout = 2000, document = "权限集(角色)功能对应关系的基础远程服务")
public class AcRolefuncRServiceImpl implements IAcRolefuncRService {

	@Autowired
	AcRolefuncService acRolefuncService;

	@Override
	public void insert(AcRolefunc t) {
		acRolefuncService.insert(t);
	}

	@Override
	public void update(AcRolefunc t) {
		acRolefuncService.update(t);
	}

	@Override
	public void updateForce(AcRolefunc t) {
		acRolefuncService.updateForce(t);
	}

	@Override
	public void delete(String id) {
		acRolefuncService.delete(id);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		acRolefuncService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, AcRolefunc t) {
		acRolefuncService.updateByCondition(wc,t);
	}

	@Override
	public List<AcRolefunc> query(WhereCondition wc) {
		return acRolefuncService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return acRolefuncService.count(wc);
	}

	@Override
	public AcRolefunc loadById(String id) {
		return acRolefuncService.loadById(id);
	}

}
