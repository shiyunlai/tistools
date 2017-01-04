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

import org.tis.tools.dao.mapper.tuser.AcFuncgroupMapper;
import org.tis.tools.model.po.tuser.AcFuncgroup;


/**
 * 业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class AcFuncgroupService {

	@Autowired
	AcFuncgroupMapper acFuncgroupMapper;
	
    public void insert(AcFuncgroup t){
    	acFuncgroupMapper.insert(t);
    }
    
    public void update(AcFuncgroup t){
    	acFuncgroupMapper.update(t); 
    }
   
    public void updateForce(AcFuncgroup t){
    	acFuncgroupMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	acFuncgroupMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	acFuncgroupMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,AcFuncgroup t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	acFuncgroupMapper.updateByCondition(map); 
    }

    public List<AcFuncgroup> query(WhereCondition wc){
    	return acFuncgroupMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return acFuncgroupMapper.count(wc);
    }
   
    public AcFuncgroup loadById(String id){
    	return acFuncgroupMapper.loadById(id);
    }
}
