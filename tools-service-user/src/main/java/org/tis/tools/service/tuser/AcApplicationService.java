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

import org.tis.tools.dao.mapper.tuser.AcApplicationMapper;
import org.tis.tools.model.po.tuser.AcApplication;


/**
 * 业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class AcApplicationService {

	@Autowired
	AcApplicationMapper acApplicationMapper;
	
    public void insert(AcApplication t){
    	acApplicationMapper.insert(t);
    }
    
    public void update(AcApplication t){
    	acApplicationMapper.update(t); 
    }
   
    public void updateForce(AcApplication t){
    	acApplicationMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	acApplicationMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	acApplicationMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,AcApplication t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	acApplicationMapper.updateByCondition(map); 
    }

    public List<AcApplication> query(WhereCondition wc){
    	return acApplicationMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return acApplicationMapper.count(wc);
    }
   
    public AcApplication loadById(String id){
    	return acApplicationMapper.loadById(id);
    }
}
