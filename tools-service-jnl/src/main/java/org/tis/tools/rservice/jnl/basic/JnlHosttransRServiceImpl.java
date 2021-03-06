
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.jnl.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.jnl.JnlHosttrans;
import org.tis.tools.rservice.jnl.basic.IJnlHosttransRService;
import org.tis.tools.service.jnl.JnlHosttransService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 主机交易流水(JNL_HOSTTRANS) 基本远程服务实现(服务提供者)
 * 
 * @author by generated by tools:gen-dao
 *
 */
//TEMPLATES
@Service(group = "jnl", version = "1.0", interfaceClass = IJnlHosttransRService.class, timeout = 2000, document = "主机交易流水的基础远程服务")
public class JnlHosttransRServiceImpl implements IJnlHosttransRService {

	@Autowired
	JnlHosttransService jnlHosttransService;

	@Override
	public void insert(JnlHosttrans t) {
		jnlHosttransService.insert(t);
	}

	@Override
	public void update(JnlHosttrans t) {
		jnlHosttransService.update(t);
	}

	@Override
	public void updateForce(JnlHosttrans t) {
		jnlHosttransService.updateForce(t);
	}

	@Override
	public void delete(String guid) {
		jnlHosttransService.delete(guid);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		jnlHosttransService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, JnlHosttrans t) {
		jnlHosttransService.updateByCondition(wc,t);
	}

	@Override
	public List<JnlHosttrans> query(WhereCondition wc) {
		return jnlHosttransService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return jnlHosttransService.count(wc);
	}

	@Override
	public JnlHosttrans loadByGuid(String guid) {
		return jnlHosttransService.loadByGuid(guid);
	}

}
