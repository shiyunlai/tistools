
/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.rservice.log.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.log.LogCustomerContact;
import org.tis.tools.rservice.log.basic.ILogCustomerContactRService;
import org.tis.tools.service.log.LogCustomerContactService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 客户接触日志(LOG_CUSTOMER_CONTACT) 基本远程服务实现(服务提供者)
 * 
 * @author by generated by tools:gen-dao
 *
 */
//TEMPLATES
@Service(group = "log", version = "1.0", interfaceClass = ILogCustomerContactRService.class, timeout = 2000, document = "客户接触日志的基础远程服务")
public class LogCustomerContactRServiceImpl implements ILogCustomerContactRService {

	@Autowired
	LogCustomerContactService logCustomerContactService;

	@Override
	public void insert(LogCustomerContact t) {
		logCustomerContactService.insert(t);
	}

	@Override
	public void update(LogCustomerContact t) {
		logCustomerContactService.update(t);
	}

	@Override
	public void updateForce(LogCustomerContact t) {
		logCustomerContactService.updateForce(t);
	}

	@Override
	public void delete(String guid) {
		logCustomerContactService.delete(guid);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		logCustomerContactService.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, LogCustomerContact t) {
		logCustomerContactService.updateByCondition(wc,t);
	}

	@Override
	public List<LogCustomerContact> query(WhereCondition wc) {
		return logCustomerContactService.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return logCustomerContactService.count(wc);
	}

	@Override
	public LogCustomerContact loadByGuid(String guid) {
		return logCustomerContactService.loadByGuid(guid);
	}

}
