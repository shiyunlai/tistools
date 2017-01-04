
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.AcOperator;
import org.tis.tools.rservice.tuser.IAcOperatorRService;
import org.tis.tools.service.tuser.AcOperatorService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 操作员(AC_OPERATOR) 基本远程服务实现(服务提供者)
 * 
 * @author by auto-gen
 *
 */
//TEMPLATES
@Service(group = "tuser", version = "1.0", interfaceClass = IAcOperatorRService.class, timeout = 2000, document = "操作员的基础远程服务")
public class AcOperatorRServiceImpl implements IAcOperatorRService {

	@Autowired
	AcOperatorService acOperatorService;

	@Override
	public void insert(AcOperator t) {
		acOperatorService.insert(t);
	}

	@Override
	public void update(AcOperator t) {
		acOperatorService.update(t);
	}

	@Override
	public void updateForce(AcOperator t) {
		acOperatorService.updateForce(t);
	}

	@Override
	public void delete(String id) {
		acOperatorService.delete(id);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		acOperatorService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, AcOperator t) {
		acOperatorService.updateByCondition(wc,t);
	}

	@Override
	public List<AcOperator> query(WhereCondition wc) {
		return acOperatorService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return acOperatorService.count(wc);
	}

	@Override
	public AcOperator loadById(String id) {
		return acOperatorService.loadById(id);
	}

}
