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

import org.tis.tools.dao.mapper.tuser.AcFunctionMapper;
import org.tis.tools.model.po.tuser.AcFunction;


/**
 * 业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class AcFunctionService {

	@Autowired
	AcFunctionMapper acFunctionMapper;
	
    public void insert(AcFunction t){
    	acFunctionMapper.insert(t);
    }
    
    public void update(AcFunction t){
    	acFunctionMapper.update(t); 
    }
   
    public void updateForce(AcFunction t){
    	acFunctionMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	acFunctionMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	acFunctionMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,AcFunction t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	acFunctionMapper.updateByCondition(map); 
    }

    public List<AcFunction> query(WhereCondition wc){
    	return acFunctionMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return acFunctionMapper.count(wc);
    }
   
    public AcFunction loadById(String id){
    	return acFunctionMapper.loadById(id);
    }
}
