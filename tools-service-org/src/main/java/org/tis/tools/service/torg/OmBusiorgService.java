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

import org.tis.tools.dao.mapper.torg.OmBusiorgMapper;
import org.tis.tools.model.po.torg.OmBusiorg;


/**
 * 业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class OmBusiorgService {

	@Autowired
	OmBusiorgMapper omBusiorgMapper;
	
    public void insert(OmBusiorg t){
    	omBusiorgMapper.insert(t);
    }
    
    public void update(OmBusiorg t){
    	omBusiorgMapper.update(t); 
    }
   
    public void updateForce(OmBusiorg t){
    	omBusiorgMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	omBusiorgMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	omBusiorgMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,OmBusiorg t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	omBusiorgMapper.updateByCondition(map); 
    }

    public List<OmBusiorg> query(WhereCondition wc){
    	return omBusiorgMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return omBusiorgMapper.count(wc);
    }
   
    public OmBusiorg loadById(String id){
    	return omBusiorgMapper.loadById(id);
    }
}
