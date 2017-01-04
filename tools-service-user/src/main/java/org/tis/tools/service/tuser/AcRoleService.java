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

import org.tis.tools.dao.mapper.tuser.AcRoleMapper;
import org.tis.tools.model.po.tuser.AcRole;


/**
 * 业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class AcRoleService {

	@Autowired
	AcRoleMapper acRoleMapper;
	
    public void insert(AcRole t){
    	acRoleMapper.insert(t);
    }
    
    public void update(AcRole t){
    	acRoleMapper.update(t); 
    }
   
    public void updateForce(AcRole t){
    	acRoleMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	acRoleMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	acRoleMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,AcRole t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	acRoleMapper.updateByCondition(map); 
    }

    public List<AcRole> query(WhereCondition wc){
    	return acRoleMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return acRoleMapper.count(wc);
    }
   
    public AcRole loadById(String id){
    	return acRoleMapper.loadById(id);
    }
}
