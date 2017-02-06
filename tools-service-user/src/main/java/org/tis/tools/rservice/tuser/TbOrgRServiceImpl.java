
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.TbOrg;
import org.tis.tools.rservice.tuser.ITbOrgRService;
import org.tis.tools.service.tuser.TbOrgService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 机构(tb_org) 基本远程服务实现(服务提供者)
 * 
 * @author by auto-gen
 *
 */
//TEMPLATES
@Service(group = "tuser", version = "1.0", interfaceClass = ITbOrgRService.class, timeout = 2000, document = "机构的基础远程服务")
public class TbOrgRServiceImpl implements ITbOrgRService {

	@Autowired
	TbOrgService tbOrgService;

	@Override
	public void insert(TbOrg t) {
		tbOrgService.insert(t);
	}

	@Override
	public void update(TbOrg t) {
		tbOrgService.update(t);
	}

	@Override
	public void updateForce(TbOrg t) {
		tbOrgService.updateForce(t);
	}

	@Override
	public void delete(String id) {
		tbOrgService.delete(id);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		tbOrgService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, TbOrg t) {
		tbOrgService.updateByCondition(wc,t);
	}

	@Override
	public List<TbOrg> query(WhereCondition wc) {
		return tbOrgService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return tbOrgService.count(wc);
	}

	@Override
	public TbOrg loadByGuid(String guid) {
		return tbOrgService.loadByGuid(guid);
	}

}
