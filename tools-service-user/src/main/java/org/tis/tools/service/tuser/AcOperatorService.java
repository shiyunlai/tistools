/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.service.tuser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tis.tools.base.WhereCondition;

import org.tis.tools.dao.mapper.tuser.AcOperatorMapper;
import org.tis.tools.model.po.tuser.AcOperator;


/**
 * 业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class AcOperatorService {

	@Autowired
	AcOperatorMapper acOperatorMapper;
	
    public void insert(AcOperator t){
    	acOperatorMapper.insert(t);
    }
    
    public void update(AcOperator t){
    	acOperatorMapper.update(t); 
    }
   
    public void updateForce(AcOperator t){
    	acOperatorMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	acOperatorMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	acOperatorMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,AcOperator t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	acOperatorMapper.updateByCondition(map); 
    }

    public List<AcOperator> query(WhereCondition wc){
    	return acOperatorMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return acOperatorMapper.count(wc);
    }
   
    public AcOperator loadById(String id){
    	return acOperatorMapper.loadById(id);
    }
}
