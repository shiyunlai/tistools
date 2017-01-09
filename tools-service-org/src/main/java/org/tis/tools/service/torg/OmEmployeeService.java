/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.service.torg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tis.tools.base.WhereCondition;

import org.tis.tools.dao.mapper.torg.OmEmployeeMapper;
import org.tis.tools.model.po.torg.OmEmployee;


/**
 * 业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class OmEmployeeService {

	@Autowired
	OmEmployeeMapper omEmployeeMapper;
	
    public void insert(OmEmployee t){
    	omEmployeeMapper.insert(t);
    }
    
    public void update(OmEmployee t){
    	omEmployeeMapper.update(t); 
    }
   
    public void updateForce(OmEmployee t){
    	omEmployeeMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	omEmployeeMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	omEmployeeMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,OmEmployee t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	omEmployeeMapper.updateByCondition(map); 
    }

    public List<OmEmployee> query(WhereCondition wc){
    	return omEmployeeMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return omEmployeeMapper.count(wc);
    }
   
    public OmEmployee loadById(String id){
    	return omEmployeeMapper.loadById(id);
    }
}
