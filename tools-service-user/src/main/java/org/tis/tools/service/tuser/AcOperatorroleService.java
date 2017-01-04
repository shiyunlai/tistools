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

import org.tis.tools.dao.mapper.tuser.AcOperatorroleMapper;
import org.tis.tools.model.po.tuser.AcOperatorrole;


/**
 * 业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class AcOperatorroleService {

	@Autowired
	AcOperatorroleMapper acOperatorroleMapper;
	
    public void insert(AcOperatorrole t){
    	acOperatorroleMapper.insert(t);
    }
    
    public void update(AcOperatorrole t){
    	acOperatorroleMapper.update(t); 
    }
   
    public void updateForce(AcOperatorrole t){
    	acOperatorroleMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	acOperatorroleMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	acOperatorroleMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,AcOperatorrole t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	acOperatorroleMapper.updateByCondition(map); 
    }

    public List<AcOperatorrole> query(WhereCondition wc){
    	return acOperatorroleMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return acOperatorroleMapper.count(wc);
    }
   
    public AcOperatorrole loadById(String id){
    	return acOperatorroleMapper.loadById(id);
    }
}
