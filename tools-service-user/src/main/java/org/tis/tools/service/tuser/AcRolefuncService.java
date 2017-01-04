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

import org.tis.tools.dao.mapper.tuser.AcRolefuncMapper;
import org.tis.tools.model.po.tuser.AcRolefunc;


/**
 * 业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class AcRolefuncService {

	@Autowired
	AcRolefuncMapper acRolefuncMapper;
	
    public void insert(AcRolefunc t){
    	acRolefuncMapper.insert(t);
    }
    
    public void update(AcRolefunc t){
    	acRolefuncMapper.update(t); 
    }
   
    public void updateForce(AcRolefunc t){
    	acRolefuncMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	acRolefuncMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	acRolefuncMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,AcRolefunc t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	acRolefuncMapper.updateByCondition(map); 
    }

    public List<AcRolefunc> query(WhereCondition wc){
    	return acRolefuncMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return acRolefuncMapper.count(wc);
    }
   
    public AcRolefunc loadById(String id){
    	return acRolefuncMapper.loadById(id);
    }
}
