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

import org.tis.tools.dao.ac.AcRoleFuncMapper;
import org.tis.tools.model.po.ac.AcRoleFunc;


/**
 * 权限集(角色)功能对应关系(AC_ROLE_FUNC)数据的基础业务逻辑
 * @author by generated by tools:gen-dao
 *
 */
@Service
public class AcRoleFuncService {

	@Autowired
	AcRoleFuncMapper acRoleFuncMapper;
	
    public void insert(AcRoleFunc t){
    	acRoleFuncMapper.insert(t);
    }
    
    public void update(AcRoleFunc t){
    	acRoleFuncMapper.update(t); 
    }
   
    public void updateForce(AcRoleFunc t){
    	acRoleFuncMapper.updateForce(t); 
    }
   
    public void delete(String guid){
    	acRoleFuncMapper.delete(guid);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	acRoleFuncMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,AcRoleFunc t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	acRoleFuncMapper.updateByCondition(map); 
    }

    public List<AcRoleFunc> query(WhereCondition wc){
    	return acRoleFuncMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return acRoleFuncMapper.count(wc);
    }
   
    public AcRoleFunc loadByGuid(String guid){
    	return acRoleFuncMapper.loadByGuid(guid);
    }
}
