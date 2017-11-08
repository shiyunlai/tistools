/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.service.ac;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tis.tools.base.WhereCondition;

import org.tis.tools.dao.ac.AcRoleBhvMapper;
import org.tis.tools.model.po.ac.AcRoleBhv;


/**
 * 权限集(角色)功能行为对应关系(AC_ROLE_BHV)数据的基础业务逻辑
 * @author by generated by tools:gen-dao
 *
 */
@Service
public class AcRoleBhvService {

	@Autowired
	AcRoleBhvMapper acRoleBhvMapper;
	
    public void insert(AcRoleBhv t){
    	acRoleBhvMapper.insert(t);
    }
    
    public void update(AcRoleBhv t){
    	acRoleBhvMapper.update(t); 
    }
   
    public void updateForce(AcRoleBhv t){
    	acRoleBhvMapper.updateForce(t); 
    }
   
    public void delete(String guid){
    	acRoleBhvMapper.delete(guid);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	acRoleBhvMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,AcRoleBhv t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	acRoleBhvMapper.updateByCondition(map); 
    }

    public List<AcRoleBhv> query(WhereCondition wc){
    	return acRoleBhvMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return acRoleBhvMapper.count(wc);
    }
   
    public AcRoleBhv loadByGuid(String guid){
    	return acRoleBhvMapper.loadByGuid(guid);
    }
}
