
/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.torg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.torg.OmBusiorg;
import org.tis.tools.rservice.torg.IOmBusiorgRService;
import org.tis.tools.service.torg.OmBusiorgService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * <pre>
 * auto generated by mvn bronsp:gen-dao, don't change</br>
 * 业务机构(OM_BUSIORG) 基本远程服务实现(服务提供者)
 * 模型：业务机构 OM_BUSIORG
 * 描述：业务机构
 * 业务域：torg
 * 定义文件：C:\git-repositories\tistools\tools-core\model\model-user.xml
 * </pre>
 */
@Service(group = "torg", version = "1.0", interfaceClass = IOmBusiorgRService.class, timeout = 2000, document = "业务机构的基础远程服务")
public class OmBusiorgRServiceImpl implements IOmBusiorgRService {

	@Autowired
	OmBusiorgService omBusiorgService;

	@Override
	public void insert(OmBusiorg t) {
		omBusiorgService.insert(t);
	}

	@Override
	public void update(OmBusiorg t) {
		omBusiorgService.update(t);
	}

	@Override
	public void updateForce(OmBusiorg t) {
		omBusiorgService.updateForce(t);
	}

	@Override
	public void delete(String id) {
		omBusiorgService.delete(id);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		omBusiorgService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, OmBusiorg t) {
		omBusiorgService.updateByCondition(wc,t);
	}

	@Override
	public List<OmBusiorg> query(WhereCondition wc) {
		return omBusiorgService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return omBusiorgService.count(wc);
	}

	@Override
	public OmBusiorg loadById(String id) {
		return omBusiorgService.loadById(id);
	}

}
