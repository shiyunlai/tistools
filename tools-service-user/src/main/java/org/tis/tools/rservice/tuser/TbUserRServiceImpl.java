
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.tuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.tuser.TbUser;
import org.tis.tools.rservice.tuser.ITbUserRService;
import org.tis.tools.service.tuser.TbUserService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 用户(tb_user) 基本远程服务实现(服务提供者)
 * 
 * @author by auto-gen
 *
 */
//TEMPLATES
@Service(group = "tuser", version = "1.0", interfaceClass = ITbUserRService.class, timeout = 2000, document = "用户的基础远程服务")
public class TbUserRServiceImpl implements ITbUserRService {

	@Autowired
	TbUserService tbUserService;

	@Override
	public void insert(TbUser t) {
		tbUserService.insert(t);
	}

	@Override
	public void update(TbUser t) {
		tbUserService.update(t);
	}

	@Override
	public void updateForce(TbUser t) {
		tbUserService.updateForce(t);
	}

	@Override
	public void delete(String id) {
		tbUserService.delete(id);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		tbUserService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, TbUser t) {
		tbUserService.updateByCondition(wc,t);
	}

	@Override
	public List<TbUser> query(WhereCondition wc) {
		return tbUserService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return tbUserService.count(wc);
	}

	@Override
	public TbUser loadByGuid(String guid) {
		tbUserService.loadByGuid(guid) ;
		return null;
	}

}
