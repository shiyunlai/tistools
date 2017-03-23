/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.service.jnl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tis.tools.base.WhereCondition;

import org.tis.tools.dao.mapper.jnl.JnlTellerTraceMapper;
import org.tis.tools.model.po.jnl.JnlTellerTrace;


/**
 * 柜员操作日志(JNL_TELLER_TRACE)数据的基础业务逻辑
 * @author by generated by tools:gen-dao
 *
 */
@Service
public class JnlTellerTraceService {

	@Autowired
	JnlTellerTraceMapper jnlTellerTraceMapper;
	
    public void insert(JnlTellerTrace t){
    	jnlTellerTraceMapper.insert(t);
    }
    
    public void update(JnlTellerTrace t){
    	jnlTellerTraceMapper.update(t); 
    }
   
    public void updateForce(JnlTellerTrace t){
    	jnlTellerTraceMapper.updateForce(t); 
    }
   
    public void delete(String guid){
    	jnlTellerTraceMapper.delete(guid);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	jnlTellerTraceMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,JnlTellerTrace t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	jnlTellerTraceMapper.updateByCondition(map); 
    }

    public List<JnlTellerTrace> query(WhereCondition wc){
    	return jnlTellerTraceMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return jnlTellerTraceMapper.count(wc);
    }
   
    public JnlTellerTrace loadByGuid(String guid){
    	return jnlTellerTraceMapper.loadByGuid(guid);
    }
}