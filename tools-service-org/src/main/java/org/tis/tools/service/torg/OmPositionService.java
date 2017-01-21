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

import org.tis.tools.dao.mapper.torg.OmPositionMapper;
import org.tis.tools.model.po.torg.OmPosition;


/**
 * 业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class OmPositionService {

	@Autowired
	OmPositionMapper omPositionMapper;
	
    public void insert(OmPosition t){
    	omPositionMapper.insert(t);
    }
    
    public void update(OmPosition t){
    	omPositionMapper.update(t); 
    }
   
    public void updateForce(OmPosition t){
    	omPositionMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	omPositionMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	omPositionMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,OmPosition t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	omPositionMapper.updateByCondition(map); 
    }

    public List<OmPosition> query(WhereCondition wc){
    	return omPositionMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return omPositionMapper.count(wc);
    }
   
    public OmPosition loadById(String id){
    	return omPositionMapper.loadById(id);
    }
}
