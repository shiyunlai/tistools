
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.ac.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.ac.AcFuncgroup;
import org.tis.tools.rservice.ac.basic.IAcFuncgroupRService;
import org.tis.tools.service.ac.AcFuncgroupService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 功能组(AC_FUNCGROUP) 基本远程服务实现(服务提供者)
 * 
 * @author by generated by tools:gen-dao
 *
 */
//TEMPLATES
@Service(group = "ac", version = "1.0", interfaceClass = IAcFuncgroupRService.class, timeout = 2000, document = "功能组的基础远程服务")
public class AcFuncgroupRServiceImpl implements IAcFuncgroupRService {

	@Autowired
	AcFuncgroupService acFuncgroupService;

	@Override
	public void insert(AcFuncgroup t) {
		acFuncgroupService.insert(t);
	}

	@Override
	public void update(AcFuncgroup t) {
		acFuncgroupService.update(t);
	}

	@Override
	public void updateForce(AcFuncgroup t) {
		acFuncgroupService.updateForce(t);
	}

	@Override
	public void delete(String guid) {
		acFuncgroupService.delete(guid);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		acFuncgroupService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, AcFuncgroup t) {
		acFuncgroupService.updateByCondition(wc,t);
	}

	@Override
	public List<AcFuncgroup> query(WhereCondition wc) {
		return acFuncgroupService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return acFuncgroupService.count(wc);
	}

	@Override
	public AcFuncgroup loadByGuid(String guid) {
		return acFuncgroupService.loadByGuid(guid);
	}

}