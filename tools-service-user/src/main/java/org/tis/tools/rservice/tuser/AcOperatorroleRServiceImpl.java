
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.AcOperatorrole;
import org.tis.tools.rservice.tuser.IAcOperatorroleRService;
import org.tis.tools.service.tuser.AcOperatorroleService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 操作员与权限集（角色）对应关系(AC_OPERATORROLE) 基本远程服务实现(服务提供者)
 * 
 * @author by auto-gen
 *
 */
//TEMPLATES
@Service(group = "tuser", version = "1.0", interfaceClass = IAcOperatorroleRService.class, timeout = 2000, document = "操作员与权限集（角色）对应关系的基础远程服务")
public class AcOperatorroleRServiceImpl implements IAcOperatorroleRService {

	@Autowired
	AcOperatorroleService acOperatorroleService;

	@Override
	public void insert(AcOperatorrole t) {
		acOperatorroleService.insert(t);
	}

	@Override
	public void update(AcOperatorrole t) {
		acOperatorroleService.update(t);
	}

	@Override
	public void updateForce(AcOperatorrole t) {
		acOperatorroleService.updateForce(t);
	}

	@Override
	public void delete(String id) {
		acOperatorroleService.delete(id);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		acOperatorroleService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, AcOperatorrole t) {
		acOperatorroleService.updateByCondition(wc,t);
	}

	@Override
	public List<AcOperatorrole> query(WhereCondition wc) {
		return acOperatorroleService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return acOperatorroleService.count(wc);
	}

	@Override
	public AcOperatorrole loadById(String id) {
		return acOperatorroleService.loadById(id);
	}

}
