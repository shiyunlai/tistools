
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.ac.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.ac.AcConfig;
import org.tis.tools.rservice.ac.basic.IAcConfigRService;
import org.tis.tools.service.ac.AcConfigService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 个性化配置(AC_CONFIG) 基本远程服务实现(服务提供者)
 * 
 * @author by generated by tools:gen-dao
 *
 */
//TEMPLATES
@Service(group = "ac", version = "1.0", interfaceClass = IAcConfigRService.class, timeout = 2000, document = "个性化配置的基础远程服务")
public class AcConfigRServiceImpl implements IAcConfigRService {

	@Autowired
	AcConfigService acConfigService;

	@Override
	public void insert(AcConfig t) {
		acConfigService.insert(t);
	}

	@Override
	public void update(AcConfig t) {
		acConfigService.update(t);
	}

	@Override
	public void updateForce(AcConfig t) {
		acConfigService.updateForce(t);
	}

	@Override
	public void delete(String guid) {
		acConfigService.delete(guid);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		acConfigService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, AcConfig t) {
		acConfigService.updateByCondition(wc,t);
	}

	@Override
	public List<AcConfig> query(WhereCondition wc) {
		return acConfigService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return acConfigService.count(wc);
	}

	@Override
	public AcConfig loadByGuid(String guid) {
		return acConfigService.loadByGuid(guid);
	}

}
