
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.ac.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.ac.AcOperatorBehavior;
import org.tis.tools.rservice.ac.basic.IAcOperatorBehaviorRService;
import org.tis.tools.service.ac.AcOperatorBehaviorService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 操作员特殊功能行为配置(AC_OPERATOR_BEHAVIOR) 基本远程服务实现(服务提供者)
 * 
 * @author by generated by tools:gen-dao
 *
 */
//TEMPLATES
@Service(group = "ac", version = "1.0", interfaceClass = IAcOperatorBehaviorRService.class, timeout = 2000, document = "操作员特殊功能行为配置的基础远程服务")
public class AcOperatorBehaviorRServiceImpl implements IAcOperatorBehaviorRService {

	@Autowired
	AcOperatorBehaviorService acOperatorBehaviorService;

	@Override
	public void insert(AcOperatorBehavior t) {
		acOperatorBehaviorService.insert(t);
	}

	@Override
	public void update(AcOperatorBehavior t) {
		acOperatorBehaviorService.update(t);
	}

	@Override
	public void updateForce(AcOperatorBehavior t) {
		acOperatorBehaviorService.updateForce(t);
	}

	@Override
	public void delete(String guid) {
		acOperatorBehaviorService.delete(guid);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		acOperatorBehaviorService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, AcOperatorBehavior t) {
		acOperatorBehaviorService.updateByCondition(wc,t);
	}

	@Override
	public List<AcOperatorBehavior> query(WhereCondition wc) {
		return acOperatorBehaviorService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return acOperatorBehaviorService.count(wc);
	}

	@Override
	public AcOperatorBehavior loadByGuid(String guid) {
		return acOperatorBehaviorService.loadByGuid(guid);
	}

}