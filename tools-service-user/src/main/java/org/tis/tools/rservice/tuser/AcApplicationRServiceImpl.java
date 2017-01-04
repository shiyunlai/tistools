
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.AcApplication;
import org.tis.tools.rservice.tuser.IAcApplicationRService;
import org.tis.tools.service.tuser.AcApplicationService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 应用系统(AC_APPLICATION) 基本远程服务实现(服务提供者)
 * 
 * @author by auto-gen
 *
 */
//TEMPLATES
@Service(group = "tuser", version = "1.0", interfaceClass = IAcApplicationRService.class, timeout = 2000, document = "应用系统的基础远程服务")
public class AcApplicationRServiceImpl implements IAcApplicationRService {

	@Autowired
	AcApplicationService acApplicationService;

	@Override
	public void insert(AcApplication t) {
		acApplicationService.insert(t);
	}

	@Override
	public void update(AcApplication t) {
		acApplicationService.update(t);
	}

	@Override
	public void updateForce(AcApplication t) {
		acApplicationService.updateForce(t);
	}

	@Override
	public void delete(String id) {
		acApplicationService.delete(id);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		acApplicationService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, AcApplication t) {
		acApplicationService.updateByCondition(wc,t);
	}

	@Override
	public List<AcApplication> query(WhereCondition wc) {
		return acApplicationService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return acApplicationService.count(wc);
	}

	@Override
	public AcApplication loadById(String id) {
		return acApplicationService.loadById(id);
	}

}
