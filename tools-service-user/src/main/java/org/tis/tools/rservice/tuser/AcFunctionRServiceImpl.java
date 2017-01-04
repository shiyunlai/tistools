
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.AcFunction;
import org.tis.tools.rservice.tuser.IAcFunctionRService;
import org.tis.tools.service.tuser.AcFunctionService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 功能(AC_FUNCTION) 基本远程服务实现(服务提供者)
 * 
 * @author by auto-gen
 *
 */
//TEMPLATES
@Service(group = "tuser", version = "1.0", interfaceClass = IAcFunctionRService.class, timeout = 2000, document = "功能的基础远程服务")
public class AcFunctionRServiceImpl implements IAcFunctionRService {

	@Autowired
	AcFunctionService acFunctionService;

	@Override
	public void insert(AcFunction t) {
		acFunctionService.insert(t);
	}

	@Override
	public void update(AcFunction t) {
		acFunctionService.update(t);
	}

	@Override
	public void updateForce(AcFunction t) {
		acFunctionService.updateForce(t);
	}

	@Override
	public void delete(String id) {
		acFunctionService.delete(id);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		acFunctionService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, AcFunction t) {
		acFunctionService.updateByCondition(wc,t);
	}

	@Override
	public List<AcFunction> query(WhereCondition wc) {
		return acFunctionService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return acFunctionService.count(wc);
	}

	@Override
	public AcFunction loadById(String id) {
		return acFunctionService.loadById(id);
	}

}
